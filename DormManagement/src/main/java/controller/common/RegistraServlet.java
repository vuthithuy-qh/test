/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.common;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import model.service.UserService;
import model.dao.StudentDAO;
import model.entity.Students;

/**
 *
 * @author DELL
 */
public class RegistraServlet extends HttpServlet {
    private UserService userService;
    private StudentDAO studentDAO;

    @Override
    public void init() throws ServletException {
        userService = new UserService();
        studentDAO = new StudentDAO();
    }
   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        // Kiểm tra thông báo thành công từ session
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("successMessage") != null) {
            request.setAttribute("successMessage", session.getAttribute("successMessage"));
            session.removeAttribute("successMessage");
        }
        
        // Chuyển hướng đến trang đăng ký
        request.getRequestDispatcher("/view/common/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        // Lấy thông tin từ form đăng ký
        String role = request.getParameter("role"); // Thêm role
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String fullName = request.getParameter("fullName");
        String cccd = request.getParameter("cccd");
        String phone = request.getParameter("phone");
        request.setAttribute("phone", phone);

        // Lưu giá trị tạm để giữ nguyên khi có lỗi
        request.setAttribute("role", role);
        request.setAttribute("username", username);
        request.setAttribute("email", email);
        request.setAttribute("fullName", fullName);
        request.setAttribute("cccd", cccd);

        // Validate dữ liệu đầu vào - chỉ validate các trường cơ bản
        if (username == null || username.trim().isEmpty() ||
            email == null || email.trim().isEmpty() ||
            password == null || password.trim().isEmpty() ||
            confirmPassword == null || confirmPassword.trim().isEmpty() ||
            fullName == null || fullName.trim().isEmpty() ||
            cccd == null || cccd.trim().isEmpty() ||
            phone == null || phone.trim().isEmpty()) {
            request.setAttribute("error", "Vui lòng điền đầy đủ tất cả các trường thông tin bắt buộc.");
            request.getRequestDispatcher("/view/common/register.jsp").forward(request, response);
            return;
        }

        // Validate password
        if (!password.equals(confirmPassword)) {
            request.setAttribute("error", "Mật khẩu xác nhận không khớp.");
            request.getRequestDispatcher("/view/common/register.jsp").forward(request, response);
            return;
        }

        if (password.length() < 8) {
            request.setAttribute("error", "Mật khẩu phải có ít nhất 8 ký tự.");
            request.getRequestDispatcher("/view/common/register.jsp").forward(request, response);
            return;
        }

        // Validate email
        if (!userService.checkEmailFormat(email)) {
            request.setAttribute("error", "Email phải có định dạng @ hợp lệ.");
            request.setAttribute("email", ""); // Reset email
            request.getRequestDispatcher("/view/common/register.jsp").forward(request, response);
            return;
        }

        // Validate CCCD
        if (!userService.validateCCCD(cccd)) {
            request.setAttribute("error", "CCCD phải có đúng 12 ký tự số.");
            request.setAttribute("cccd", ""); // Reset CCCD
            request.getRequestDispatcher("/view/common/register.jsp").forward(request, response);
            return;
        }

        // Validate số điện thoại
        if (!phone.matches("^\\d{10,11}$")) {
            request.setAttribute("error", "Số điện thoại phải có 10 hoặc 11 số!");
            request.getRequestDispatcher("/view/common/register.jsp").forward(request, response);
            return;
        }

        // Kiểm tra trùng số điện thoại
        try {
            if (studentDAO.isPhoneExists(phone)) {
                request.setAttribute("error", "Số điện thoại đã tồn tại!");
                request.getRequestDispatcher("/view/common/register.jsp").forward(request, response);
                return;
            }
        } catch (SQLException e) {
            request.setAttribute("error", "Lỗi kiểm tra số điện thoại: " + e.getMessage());
            request.getRequestDispatcher("/view/common/register.jsp").forward(request, response);
            return;
        }

        try {
            // Thực hiện đăng ký
            if (userService.registerUser(username, email, password, fullName, cccd, phone)) {
                // Lấy thông tin sinh viên vừa đăng ký
                Students student = studentDAO.findStudentByEmailOrUsername(username);
                
                if (student != null) {
                    // Tạo session và điều hướng đến dashboard
                    HttpSession session = request.getSession();
                    session.setAttribute("student", student);
                    session.setAttribute("userType", "student");
                    session.setAttribute("successMessage", "Đăng ký thành công! Chào mừng bạn đến với hệ thống.");
                    
                    // Điều hướng đến dashboard
                    response.sendRedirect(request.getContextPath() + "/view/student/dashboardStudent.jsp");
                } else {
                    // Fallback: điều hướng về trang đăng nhập
                    HttpSession session = request.getSession();
                    session.setAttribute("successMessage", "Đăng ký thành công! Vui lòng đăng nhập.");
                    response.sendRedirect(request.getContextPath() + "/view/common/login.jsp");
                }
            } else {
                request.setAttribute("error", "Tên đăng nhập, Email hoặc Số điện thoại đã tồn tại.");
                request.getRequestDispatcher("/view/common/register.jsp").forward(request, response);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi khi đăng ký: " + e.getMessage());
            request.getRequestDispatcher("/view/common/register.jsp").forward(request, response);
        }
    }
}
   