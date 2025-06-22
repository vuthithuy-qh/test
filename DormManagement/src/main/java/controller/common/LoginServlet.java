package controller.common;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String emailOrUsername = request.getParameter("emailOrUsername") != null ? request.getParameter("emailOrUsername").trim() : null;
        String password = request.getParameter("password") != null ? request.getParameter("password").trim() : null;

        if (emailOrUsername == null || emailOrUsername.isEmpty()
                || password == null || password.isEmpty()) {
            request.setAttribute("error", "Email/Username và mật khẩu là bắt buộc.");
            request.getRequestDispatcher("/view/common/login.jsp").forward(request, response);
            return;
        }

        request.setAttribute("emailOrUsername", emailOrUsername);
        request.setAttribute("password", password);

        // PHÂN LUỒNG: nếu bắt đầu bằng 'admin' thì forward đến AdminLoginController
        if (emailOrUsername.equalsIgnoreCase("admin") || emailOrUsername.startsWith("admin")) {
            request.getRequestDispatcher("/AdminLoginController").forward(request, response);
        } else {
            request.getRequestDispatcher("/StudentLoginServlet").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/view/common/login.jsp").forward(request, response);
    }
}
