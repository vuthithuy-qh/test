package Controller;

import dao.cart.CartDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Account;
import model.CartItem;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public class CartViewServlet extends HttpServlet {
    
    private CartDAO cartDAO = new CartDAO();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Kiểm tra đăng nhập
        HttpSession session = request.getSession(false);
        Account currentUser = (Account) session.getAttribute("currentUser");
        
        if (currentUser == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        try {
            // Lấy danh sách cart items của user
            List<CartItem> cartItems = cartDAO.getCartItems(currentUser.getId());
            
            // Tính tổng tiền
            BigDecimal totalAmount = BigDecimal.ZERO;
            for (CartItem item : cartItems) {
                if (item.getCar() != null && item.getCar().getSellingPrice() != null) {
                    totalAmount = totalAmount.add(item.getCar().getSellingPrice());
                }
            }
            
            // Set attributes cho JSP
            request.setAttribute("cartItems", cartItems);
            request.setAttribute("totalAmount", totalAmount);
            request.setAttribute("itemCount", cartItems.size());
            
            // Forward tới cart.jsp
            request.getRequestDispatcher("cart.jsp").forward(request, response);
            
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMsg", "Có lỗi xảy ra khi tải giỏ hàng: " + e.getMessage());
            request.getRequestDispatcher("cart.jsp").forward(request, response);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        if ("remove".equals(action)) {
            removeFromCart(request, response);
        } else if ("consultation".equals(action)) {
            handleConsultation(request, response);
        } else if ("test_drive".equals(action)) {
            handleTestDrive(request, response);
        } else {
            doGet(request, response);
        }
    }
    
    private void removeFromCart(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        Account currentUser = (Account) session.getAttribute("currentUser");
        
        if (currentUser == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        String carIdParam = request.getParameter("carId");
        if (carIdParam == null || carIdParam.isEmpty()) {
            request.setAttribute("errorMsg", "ID xe không hợp lệ");
            doGet(request, response);
            return;
        }
        
        try {
            int carId = Integer.parseInt(carIdParam);
            cartDAO.removeFromCart(currentUser.getId(), carId);
            
            request.setAttribute("successMsg", "Đã xóa xe khỏi giỏ hàng thành công!");
            
        } catch (NumberFormatException e) {
            request.setAttribute("errorMsg", "ID xe không hợp lệ");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMsg", "Có lỗi xảy ra khi xóa xe: " + e.getMessage());
        }
        
        doGet(request, response);
    }
    
    private void handleConsultation(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    
    HttpSession session = request.getSession();
    Account currentUser = (Account) session.getAttribute("currentUser");
    
    if (currentUser == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    List<CartItem> cartItems = cartDAO.getCartItems(currentUser.getId());
    session.setAttribute("consultationCartItems", cartItems);

    request.setAttribute("type", "CONSULTATION"); // Để hiển thị đúng form
    request.getRequestDispatcher("booking.jsp").forward(request, response);
}

    private void handleTestDrive(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    
    HttpSession session = request.getSession();
    Account currentUser = (Account) session.getAttribute("currentUser");
    
    if (currentUser == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    String testDate = request.getParameter("testDate");
    String testTime = request.getParameter("testTime");

    if (testDate == null || testDate.isEmpty() || testTime == null || testTime.isEmpty()) {
        request.setAttribute("errorMsg", "Vui lòng chọn ngày và giờ trải nghiệm");
        doGet(request, response);
        return;
    }

    try {
        List<CartItem> cartItems = cartDAO.getCartItems(currentUser.getId());

        session.setAttribute("testDriveCartItems", cartItems);
        request.setAttribute("testDate", testDate);
        request.setAttribute("testTime", testTime);
        request.setAttribute("type", "TEST_DRIVE");

        request.getRequestDispatcher("booking.jsp").forward(request, response);
    } catch (Exception e) {
        e.printStackTrace();
        request.setAttribute("errorMsg", "Có lỗi xảy ra khi đặt lịch: " + e.getMessage());
        doGet(request, response);
    }
    }
}
