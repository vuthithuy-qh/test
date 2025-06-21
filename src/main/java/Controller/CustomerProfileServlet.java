/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import model.Account;
import model.CustomerProfile;
import service.customer.CustomerService;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "CustomerProfileServlet", urlPatterns = {"/CustomerProfileServlet"})
public class CustomerProfileServlet extends HttpServlet {
    
    private CustomerService profileService = new CustomerService(); 

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
            out.println("<title>Servlet CustomerProfileServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CustomerProfileServlet at " + request.getContextPath() + "</h1>");
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
        //Lay user hien tai tu session
        Account currentUser = (Account)request.getSession().getAttribute("currentUser"); 
        if (currentUser == null){
            response.sendRedirect("login.jsp");
            return; 
        }
        
        CustomerProfile profile = profileService.viewProfile(currentUser.getId()); 
        request.setAttribute("profile", profile);
        
        request.getRequestDispatcher("customerProfile.jsp").forward(request, response);
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
        Account currentUser = (Account)request.getSession().getAttribute("currentUser"); 
        if (currentUser == null){
            response.sendRedirect("login.jsp");
            return; 
        }
        
        // lay du lieu tu form ra
        String name = request.getParameter("name"); 
        String phone = request.getParameter("phone"); 
        String address= request.getParameter("address"); 
        String birthdate = request.getParameter("birthdate"); 
        String gender = request.getParameter("gender");
        String shippingAddress = request.getParameter("shippingAddress"); 
        
        CustomerProfile profile = profileService.viewProfile(currentUser.getId()); 
        if(profile == null){
            profile = new CustomerProfile(); 
            profile.setAccount(currentUser);
        }
        
        profile.setName(name);
        profile.setPhone(phone);
        profile.setAddress(address);
        profile.setGender(gender);
        profile.setShippingAddress(shippingAddress);
        if (birthdate != null && !birthdate.isEmpty()){
            profile.setBirthdate(LocalDate.parse(birthdate));
        }
        
        boolean ok = profileService.updateProfile(profile); 
        
        if (ok){
            request.setAttribute("successMsg", "Update success full");
        }else {
            request.setAttribute("errorMsg", "update failded");
        }
        
        request.setAttribute("profile", profile);
        request.getRequestDispatcher("customerProfile.jsp").forward(request, response);
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
