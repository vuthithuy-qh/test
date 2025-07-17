package Controller;

import constant.OrderStatus;
import dao.car.CarDAO;
import dao.order.OrderDAO;
import dao.order.OrderDetailDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@WebServlet(
    name = "CreateOrderServlet",
    urlPatterns = {"/create-order"}
)
public class CreateOrderServlet extends HttpServlet {
    private OrderDAO orderDAO = new OrderDAO();
    private CarDAO carDAO = new CarDAO();
    private OrderDetailDAO orderDetailDAO = new OrderDetailDAO();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Hiển thị form tạo đơn hàng
        // Set ngày hiện tại cho form
        request.setAttribute("currentDate", LocalDate.now().toString());
        request.getRequestDispatcher("create_order.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Set encoding để xử lý tiếng Việt
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        try {
            // === 1. VALIDATE VÀ LẤY DỮ LIỆU CƠ BẢN ===
            String customerIdStr = request.getParameter("customerId");
            String sellerIdStr = request.getParameter("sellerId");
            String saleDateStr = request.getParameter("saleDate");
            String statusStr = request.getParameter("status");
            String note = request.getParameter("note");
            
            // Validation cơ bản
            if (customerIdStr == null || customerIdStr.trim().isEmpty() ||
                sellerIdStr == null || sellerIdStr.trim().isEmpty()) {
                throw new IllegalArgumentException("Customer ID và Seller ID không được để trống");
            }
            
            int customerId = Integer.parseInt(customerIdStr.trim());
            int sellerId = Integer.parseInt(sellerIdStr.trim());
            
            if (customerId <= 0 || sellerId <= 0) {
                throw new IllegalArgumentException("Customer ID và Seller ID phải lớn hơn 0");
            }
            
            // Parse ngày bán
            LocalDate saleDate;
            if (saleDateStr != null && !saleDateStr.trim().isEmpty()) {
                saleDate = LocalDate.parse(saleDateStr);
            } else {
                saleDate = LocalDate.now();
            }
            
            // Parse trạng thái
            OrderStatus status;
            try {
                status = (statusStr != null && !statusStr.trim().isEmpty()) 
                    ? OrderStatus.valueOf(statusStr.trim().toUpperCase()) 
                    : OrderStatus.PENDING;
            } catch (IllegalArgumentException e) {
                status = OrderStatus.PENDING;
            }
            
            // === 2. XỬ LÝ DANH SÁCH XE ===
            String[] carIds = request.getParameterValues("carId");
            String[] prices = request.getParameterValues("price");
            String[] discounts = request.getParameterValues("discount");
            
            if (carIds == null || carIds.length == 0) {
                throw new IllegalArgumentException("Phải có ít nhất một xe trong đơn hàng");
            }
            
            // Validate số lượng tham số
            if (prices == null || discounts == null || 
                carIds.length != prices.length || carIds.length != discounts.length) {
                throw new IllegalArgumentException("Dữ liệu xe không đồng bộ");
            }
            
            // === 3. TẠO ORDER ===
            Order order = new Order();
            order.setSaleDate(saleDate);
            order.setNote(note != null ? note.trim() : "");
            order.setStatus(status);
            
            // Tạo customer và seller (chỉ set ID)
            Account customer = new Account();
            customer.setId(customerId);
            order.setCustomer(customer);
            
            Account seller = new Account();
            seller.setId(sellerId);
            order.setSeller(seller);
            
            // === 4. TẠO DANH SÁCH ORDER DETAIL ===
            List<OrderDetail> orderDetails = new ArrayList<>();
            BigDecimal totalOrderAmount = BigDecimal.ZERO;
            
            for (int i = 0; i < carIds.length; i++) {
                try {
                    // Parse và validate từng dòng
                    String carIdStr = carIds[i];
                    String priceStr = prices[i];
                    String discountStr = discounts[i];
                    
                    if (carIdStr == null || carIdStr.trim().isEmpty() ||
                        priceStr == null || priceStr.trim().isEmpty()) {
                        continue; // Skip dòng trống
                    }
                    
                    int carId = Integer.parseInt(carIdStr.trim());
                    BigDecimal price = new BigDecimal(priceStr.trim());
                    BigDecimal discount = (discountStr != null && !discountStr.trim().isEmpty()) 
                        ? new BigDecimal(discountStr.trim()) 
                        : BigDecimal.ZERO;
                    
                    // Validate giá trị
                    if (carId <= 0) {
                        throw new IllegalArgumentException("Car ID ở dòng " + (i+1) + " phải lớn hơn 0");
                    }
                    if (price.compareTo(BigDecimal.ZERO) <= 0) {
                        throw new IllegalArgumentException("Giá xe ở dòng " + (i+1) + " phải lớn hơn 0");
                    }
                    if (discount.compareTo(BigDecimal.ZERO) < 0 || discount.compareTo(new BigDecimal("100")) > 0) {
                        throw new IllegalArgumentException("Chiết khấu ở dòng " + (i+1) + " phải từ 0 đến 100%");
                    }
                    
                    // Kiểm tra xe có tồn tại không
                    Car car = new Car();
car.setId(carId); 
                    // Tạo OrderDetail
                    OrderDetail detail = new OrderDetail();
                    detail.setOrder(order);
                    detail.setCar(car);
                    detail.setSinglePrice(price);
                    detail.setDiscount(discount);
                    
                    orderDetails.add(detail);
                    
                    // Tính tổng tiền (giá - chiết khấu)
                    BigDecimal discountAmount = price.multiply(discount).divide(new BigDecimal("100"));
                    BigDecimal lineTotal = price.subtract(discountAmount);
                    totalOrderAmount = totalOrderAmount.add(lineTotal);
                    
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Dữ liệu số ở dòng " + (i+1) + " không hợp lệ: " + e.getMessage());
                }
            }
            
            if (orderDetails.isEmpty()) {
                throw new IllegalArgumentException("Phải có ít nhất một xe hợp lệ trong đơn hàng");
            }
            
            // === 5. GẮN ORDER DETAIL VÀO ORDER ===
            order.setOrderDetails(orderDetails);
            
            // === 6. LƯU VÀO DATABASE ===
            // Sử dụng OrderDAO.save() đã được sửa để tự động tạo composite key
            orderDAO.save(order);
            
            // === 7. REDIRECT THÀNH CÔNG ===
            // Thêm thông báo thành công
            HttpSession session = request.getSession();
            session.setAttribute("successMessage", 
                "Tạo hóa đơn thành công! ID: " + order.getId() + 
                ", Tổng tiền: " + formatCurrency(totalOrderAmount));
            
            response.sendRedirect(request.getContextPath() + "/admin/order/list");
            
        } catch (NumberFormatException e) {
            handleError(request, response, "Dữ liệu nhập vào không đúng định dạng số: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            handleError(request, response, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            handleError(request, response, "Có lỗi xảy ra khi tạo hóa đơn: " + e.getMessage());
        }
    }
    
    /**
     * Xử lý lỗi và trả về form với thông báo lỗi
     */
    private void handleError(HttpServletRequest request, HttpServletResponse response, String errorMessage) 
            throws ServletException, IOException {
        request.setAttribute("error", errorMessage);
        request.setAttribute("currentDate", LocalDate.now().toString());
        
        // Giữ lại dữ liệu đã nhập
        request.setAttribute("customerId", request.getParameter("customerId"));
        request.setAttribute("sellerId", request.getParameter("sellerId"));
        request.setAttribute("saleDate", request.getParameter("saleDate"));
        request.setAttribute("status", request.getParameter("status"));
        request.setAttribute("note", request.getParameter("note"));
        
        request.getRequestDispatcher("create_order.jsp").forward(request, response);
    }
    
    /**
     * Format tiền tệ
     */
    private String formatCurrency(BigDecimal amount) {
        return String.format("%,.0f VNĐ", amount);
    }
}