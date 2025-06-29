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
import jakarta.servlet.http.HttpSession;
import java.time.LocalDate;
import model.Account;
import model.CustomerProfile;
import service.customer.CustomerService;
import util.ValidationException;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "CustomerProfileServlet", urlPatterns = {"/customer-profile"})
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
        HttpSession session = request.getSession(false);  // lay session, khong tao moi neu chua co
        if (session == null || session.getAttribute("currentUser") == null){
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;    
        }
        
        // neu da dang nhap, lay thong tin tai khoan nguoi dung
        Account currentUser = (Account)session.getAttribute("currentUser"); 
        

       CustomerProfile profile = profileService.viewProfile(currentUser.getId()); 
       
       if (profile == null ){
           profile = new CustomerProfile(); 
           profile.setAccount(currentUser);
       }
       // kiem tra xem co thong bao thanh cong nao dc gui tu doPost vao session hay ko
       
       if (session.getAttribute("successMsg") != null){
           request.setAttribute("successMsg", session.getAttribute("successMsg"));
           // xoa bo thong bao khoi session de no chi hien thi duy nhat
           session.removeAttribute("successMsg");
       }
       
       // gui du lieu toi view
       //dat doi tuong profile vao request de trang jsp co the truuy cap
       request.setAttribute("profile", profile);
       
       // chuyen tiep yeu cau cung voi request va response den trang jsp de render giao dien
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
        
        HttpSession session = request.getSession(false); 
        
        if (session == null || session.getAttribute("currentUser") == null){
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return; 
        }
        Account currentUser = (Account)request.getSession().getAttribute("currentUser"); 
        
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
        
        try {
            profileService.updateProfile(profile); 
            
            // neu khong co exception nao nghia la cap nhat thanh cong
            session.setAttribute("successMsg", "Update data success");
            response.sendRedirect(request.getContextPath() + "/member? secction=profile" );
        } catch (ValidationException e) {
            //gui ve danh sach loi cho ng dung
            request.setAttribute("errors", e.getErrors());
            
            //gui lai chinh doi tuong profile ma ng dung da nhap sai
            // de dien lai form, giup ng dung ko phai dang nhap lai tu dau
            request.setAttribute("profile", profile);
            
            // dat lai active session de jsp biet hien thi phan nao
            request.setAttribute("activeSection", "profile");
            
            request.getRequestDispatcher("/member-dashboard.jsp").forward(request, response);
            
            
        }catch(Exception e){
            request.setAttribute("errorMsg", "Error System");
            request.getRequestDispatcher("/member-dashboard.jsp").forward(request, response);
        }
      
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
