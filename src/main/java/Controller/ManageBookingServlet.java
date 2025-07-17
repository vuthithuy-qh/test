//package Controller;
//
//import dao.booking.BookingDAO;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.*;
//import model.Booking;
//import model.BookingStatus;
//
//import java.io.IOException;
//import java.time.LocalDate;
//import java.util.List;
//
//@WebServlet("/ManageBookingServlet")
//public class ManageBookingServlet extends HttpServlet {
//    private final BookingDAO bookingDAO = new BookingDAO();
//
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        String status = request.getParameter("status");
//        String dateStr = request.getParameter("bookingDate");
//
//        List<Booking> bookings;
//
//        if ((status != null && !status.isEmpty()) || (dateStr != null && !dateStr.isEmpty())) {
//            LocalDate bookingDate = null;
//            if (dateStr != null && !dateStr.isEmpty()) {
//                bookingDate = LocalDate.parse(dateStr);
//            }
//            bookings = bookingDAO.filterBookings(status, bookingDate);
//        } else {
//            bookings = bookingDAO.findAll();
//        }
//
//        request.setAttribute("bookings", bookings);
//        request.getRequestDispatcher("booking_manage.jsp").forward(request, response);
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//
//        String action = request.getParameter("action");
//        if ("updateStatus".equals(action)) {
//            try {
//                long bookingId = Long.parseLong(request.getParameter("id"));
//                String newStatus = request.getParameter("newStatus");
//
//                Booking booking = bookingDAO.findById(bookingId);
//                if (booking != null && BookingStatus.WAITING.equals(booking.getStatus())) {
//                    booking.setStatus(BookingStatus.valueOf(newStatus));
//                    bookingDAO.update(booking);
//                }
//
//                // Chuyển hướng về trang quản lý để cập nhật lại danh sách
//                response.sendRedirect("ManageBookingServlet");
//
//            } catch (Exception e) {
//                e.printStackTrace();
//                request.setAttribute("errorMsg", "Lỗi cập nhật trạng thái: " + e.getMessage());
//                request.getRequestDispatcher("booking_manage.jsp").forward(request, response);
//            }
//
//        } else {
//            response.sendRedirect("ManageBookingServlet");
//        }
//    }
//}
