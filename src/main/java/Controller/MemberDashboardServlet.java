/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import dto.DashboardOverviewDTO;
import dto.OrderHistoryPageDTO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import model.Account;
import model.CustomerProfile;
import model.Order;
import service.customer.CustomerService;
import service.memdashboard.MemberService;
import service.order.OrderService;
import util.ValidationException;

/**
 *
 * @author ADMIN
 */
@WebServlet("/member")
public class MemberDashboardServlet extends HttpServlet {

    private MemberService memberService;
    private CustomerService customerService;
    private OrderService orderService;

    @Override
    public void init() throws ServletException {
        this.memberService = new MemberService();
        this.customerService = new CustomerService();
        this.orderService = new OrderService();

    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet MemberDashboardServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet MemberDashboardServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("\n--- BAT DAU MOT YEU CAU GET MOI ---");

        // luon kiem tra luong dang nhap dau tien
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("currentUser") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        Account currentUser = (Account) session.getAttribute("currentUser");
        //debug
//        if (currentUser != null) {
//            System.out.println("DEBUG SERVLET: Nguoi dung trong session la: " + currentUser.getUsername() + " | ID: " + currentUser.getId());
//        } else {
//            System.out.println("DEBUG SERVLET: Khong tim thay currentUser trong session!");
//        }

        //lay tham so section de biet nguoi dung muon xem gi
        String section = request.getParameter("section");

//        if (section == null || section.isEmpty() || section.equals("overview")) {
//            section = "welcome"; // mac dinh la trang tong quan
//        }
        System.out.println("CONTROLLER: Section duoc xac dinh la: '" + section + "'");
        System.out.println("CONTROLLER: Dang chuan bi lay du lieu cho user ID: " + currentUser.getId());

        try {
            if (section == null || section.isEmpty() || section.equals("overview")) {
                request.setAttribute("activeSection", "overview");

                DashboardOverviewDTO overviewData = memberService.getDashboardOverviewDTO(currentUser.getId());
                request.setAttribute("dashboardData", overviewData);

            } else {
                request.setAttribute("activeSection", section);
                switch (section) {
                    case "overview":
                        DashboardOverviewDTO overviewData = memberService.getDashboardOverviewDTO(currentUser.getId());
                        request.setAttribute("dashboardData", overviewData);
                        break;
                    case "profile":
                        CustomerProfile profile = memberService.getProfileForEdit(currentUser.getId());
                        //neu tim thay profile, dat no vao request de jsp co the hien thi
                        if (profile != null) {
                            request.setAttribute("profile", profile);
                            if (profile.getBirthdate() != null) {
                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

                                String formattedBirthDate = profile.getBirthdate().format(formatter);
                                request.setAttribute("formattedBirthdate", formattedBirthDate);
                            }
                        }
                        break;

                    case "orders":
                        //lay lich su don hang co phan trang
                        String pageStr = request.getParameter("page");
                        int page = (pageStr == null || pageStr.isEmpty()) ? 1 : Integer.parseInt(pageStr);

                        OrderHistoryPageDTO pageData = memberService.getMemberOrderHistory(currentUser.getId(), page, 10);

                        request.setAttribute("pageData", pageData);
                        System.out.println("DEBUG - pageData.orders size: " + pageData.getOrders().size());

                        break;
                    case "order-detail":
                        String orderIdStr = request.getParameter("orderId");
                        if (orderIdStr != null) {
                            int orderId = Integer.parseInt(orderIdStr);
                            Optional<Order> orderOpt = memberService.getMemberOrderDetail(orderId, currentUser.getId());
                            if (orderOpt.isPresent()) {
                                request.setAttribute("order", orderOpt.get());
                            } else {
                                request.setAttribute("errorMessage", "Order not found or you do not have permission to view this order");
                            }
                        }
                        break;
                    case "cancel-order":
                        String orderIdToCancelStr = request.getParameter("orderId");
                        if (orderIdToCancelStr != null) {
                            int orderId = Integer.parseInt(orderIdToCancelStr);
                            boolean success = memberService.cancelMemberOrder(orderId, currentUser.getId());
                            if (success) {
                                session.setAttribute("successMsg", "Canceled " + orderId + "");

                            } else {
                                session.setAttribute("errorMsg", "Cancel is not success");
                            }

                            response.sendRedirect(request.getContextPath() + "/member?section=orders");
                            return;

                        }
                        break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "System has errors.Try again");
        }

        //CHUAN BI DU LIEU CUOI CUNG VA FORWARD DEN VIEW
        request.setAttribute("activeSection", section);  // de jsp biet muc nao dang active

        //lay va xoa flash message tu do post neu co
        if (session.getAttribute("successMsg") != null) {
            //  Lấy message từ session và đặt nó vào request
            request.setAttribute("successMsg", session.getAttribute("successMsg"));
            //Xóa nó khỏi session để nó không hiển thị lại ở các lần request sau
            session.removeAttribute("successMsg");
        }

        request.getRequestDispatcher("/member-dashboard.jsp").forward(request, response);
        System.out.println("--- KET THUC YEU CAU GET ---");
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("currentUser") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        Account currentUser = (Account) session.getAttribute("currentUser");
        String action = request.getParameter("action");

        if (action == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Hanh dong khong dc chi dinh");
            return;
        }
        switch (action) {
            case "update_profile":
                handleUpdateProfile(request, response, currentUser);
                break;

            case "change_password":
                handleChangePassword(request, response, currentUser);
                break;
            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Hanh dong khong hop le");
        }

    }

    private void handleUpdateProfile(HttpServletRequest request, HttpServletResponse response, Account currentUser)
            throws ServletException, IOException {
        // lay du lieu tu form ra
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String birthdate = request.getParameter("birthdate");
        String gender = request.getParameter("gender");
        String shippingAddress = request.getParameter("shippingAddress");

        CustomerProfile profile = customerService.viewProfile(currentUser.getId());
        if (profile == null) {
            profile = new CustomerProfile();
            profile.setAccount(currentUser);
        }

        profile.setName(name);
        profile.setPhone(phone);
        profile.setAddress(address);
        profile.setGender(gender);
        profile.setShippingAddress(shippingAddress);
        if (birthdate != null && !birthdate.isEmpty()) {
            profile.setBirthdate(LocalDate.parse(birthdate));
        }

        try {
            memberService.updateMemberProfile(profile);
            request.getSession().setAttribute("successMsg", "Update infor success");
            response.sendRedirect(request.getContextPath() + "/member?section=profile");
        } catch (ValidationException e) {
            request.setAttribute("errors", e.getErrors());
            request.setAttribute("profile", profile);
            request.setAttribute("activeSection", "profile");
            request.getRequestDispatcher("/member-dashboard.jsp").forward(request, response);
        }
    }

    /**
     * Phương thức private để xử lý logic đổi mật khẩu trong tương lai.
     */
    private void handleChangePassword(HttpServletRequest request, HttpServletResponse response, Account currentUser) throws ServletException, IOException {
        // TODO: Viết logic xử lý đổi mật khẩu ở đây
        // 1. Lấy oldPassword, newPassword, confirmPassword từ request
        // 2. Gọi một phương thức trong service, ví dụ: accountService.changePassword(...)
        // 3. Xử lý kết quả (thành công hoặc thất bại) và redirect/forward tương ứng.

        // Tạm thời chỉ thông báo
        response.getWriter().println("Chức năng đổi mật khẩu đang được phát triển.");
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}