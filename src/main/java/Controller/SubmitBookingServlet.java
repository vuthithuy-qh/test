package Controller;

import dao.booking.BookingDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.*;
import service.customer.CustomerService;
import util.ValidationException;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

public class SubmitBookingServlet extends HttpServlet {

    private final CustomerService customerService = new CustomerService();
    private final BookingDAO bookingDAO = new BookingDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("currentUser");

        // Kiểm tra đăng nhập và đúng role
        if (account == null || !"CUSTOMER".equalsIgnoreCase(account.getRole())) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Lấy thông tin từ form
        String fullName = request.getParameter("fullName");
        String phone = request.getParameter("phone");
        String note = request.getParameter("note");
        String type = request.getParameter("type");
        String dateStr = request.getParameter("bookingDate");
        String timeStr = request.getParameter("bookingTime");

        try {
            // Lấy và kiểm tra profile
            CustomerProfile profile = customerService.viewProfile(account.getId());
            boolean updated = false;

            if ((profile.getName() == null || profile.getName().isBlank()) && fullName != null && !fullName.isBlank()) {
                profile.setName(fullName);
                updated = true;
            }

            if ((profile.getPhone() == null || profile.getPhone().isBlank()) && phone != null && !phone.isBlank()) {
                profile.setPhone(phone);
                updated = true;
            }

            if (profile.getName() == null || profile.getPhone() == null ||
                profile.getName().isBlank() || profile.getPhone().isBlank()) {

                request.setAttribute("errorMsg", "Vui lòng cung cấp đầy đủ họ tên và số điện thoại.");
                request.setAttribute("type", type);
                request.getRequestDispatcher("booking.jsp").forward(request, response);
                return;
            }

            if (updated) {
                customerService.updateProfile(profile); 
                session.setAttribute("currentUser", account);
            }

            // Tạo đối tượng booking
            Booking booking = new Booking();
            booking.setCustomer(account);
            booking.setType(BookingType.valueOf(type));
            booking.setStatus(BookingStatus.WAITING);
            booking.setNote(note);

            if ("TEST_DRIVE".equals(type)) {
                booking.setBookingDate(LocalDate.parse(dateStr));
                booking.setBookingTime(LocalTime.parse(timeStr));
            } else {
                booking.setBookingDate(LocalDate.now());
            }

            bookingDAO.save(booking);

            request.setAttribute("successMsg", "Đặt lịch thành công!");
        } catch (ValidationException ve) {
            request.setAttribute("errorMsg", "Dữ liệu không hợp lệ: " + ve.getErrors());
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMsg", "Lỗi hệ thống: " + e.getMessage());
        }

        request.getRequestDispatcher("bookingresult.jsp").forward(request, response);
    }
}
