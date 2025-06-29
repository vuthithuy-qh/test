/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller;


import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Account;
import service.account.AccountService;

/**
 *
 * @author nguye
 */
public class LoginServlet extends HttpServlet {
    
    private AccountService accountService = new AccountService(); 
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
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
            out.println("<title>Servlet LoginServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LoginServlet at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
       String username = request.getParameter("username"); 
       String password = request.getParameter("password");
       
       if (username == null || password == null || username.isEmpty() || password.isEmpty()){
           request.setAttribute("errorMsg", "Please fill all");
           request.getRequestDispatcher("login.jsp").forward(request, response);
           return;
       }
       
        try {
            Account account = accountService.login(username, password); 
            //dang nhap thanh cong luu thong tin vao session
            HttpSession session = request.getSession(); 
            // luu thong tin tai khoan người dùng vào session
            session.setAttribute("currentUser", account); // currentUser is key, account is object containing infor of user
            
            //đảm bảo bất kì thông báo thành công nào (vd đăng kí tài khoản trước đó) còn sót lại 
            // trong session sẽ bị xóa đi khi nguoi dung dang nhap thanh cong
            session.removeAttribute("successMsg");
            response.sendRedirect("home.jsp");
            
        } catch (Exception e) {
            request.setAttribute("errorMsg", e.getMessage());
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
   }
    
public Account checkLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
    Account acc = (Account) request.getSession().getAttribute("currentUser");
    if (acc == null) {
        response.sendRedirect("login.jsp");
    }
    return acc;
}

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
