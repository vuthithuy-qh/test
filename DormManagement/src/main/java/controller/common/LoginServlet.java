/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.common;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import model.dao.AdminDAO;
import model.dao.StudentDAO;
import model.entity.Admin;
import model.entity.Students;

/**
 *
 * @author HP
 */
public class LoginServlet extends HttpServlet {

    private final AdminDAO adminDAO = new AdminDAO();
    private final StudentDAO studentDAO = new StudentDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String emailOrUsername = request.getParameter("emailOrUsername");
        String password = request.getParameter("password");

        // Kiểm tra đầu vào cơ bản
        if (emailOrUsername == null || emailOrUsername.trim().isEmpty() ||
            password == null || password.trim().isEmpty()) {
            request.setAttribute("error", "Email/Username và mật khẩu là bắt buộc.");
            request.getRequestDispatcher("/view/common/login.jsp").forward(request, response);
            return;
        }

        request.setAttribute("emailOrUsername", emailOrUsername);
        request.setAttribute("password", password);

        try {
            // Kiểm tra admin
            Admin admin = adminDAO.findAdminByEmailOrUsername(emailOrUsername);
            if (admin != null) {
                request.getRequestDispatcher("/AdminController").forward(request, response);
                return;
            }

            // Kiểm tra student
            Students student = studentDAO.findStudentByEmailOrUsername(emailOrUsername);
            if (student != null) {
                request.getRequestDispatcher("/UserLoginController").forward(request, response);
                return;
            }

            // Nếu không tìm thấy
            request.setAttribute("error", "Tên đăng nhập hoặc email không tồn tại.");
            request.getRequestDispatcher("/views/common/login.jsp").forward(request, response);
        } catch (SQLException e) {
            request.setAttribute("error", "Lỗi hệ thống: " + e.getMessage());
            request.getRequestDispatcher("/view/common/login.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/view/common/login.jsp").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Chuyển hướng yêu cầu đăng nhập đến UserLoginController và AdminController";
    }
}