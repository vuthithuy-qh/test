/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import dto.CarFilterDataDTO;
import dto.CarListPageDTO;
import dto.CarSearchCriteriaDTO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import service.car.CarService;

/**
 *
 * @author ADMIN
 */
@WebServlet("/cars")
public class CarListingServlet extends HttpServlet {
    private final CarService carService = new CarService(); 

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
            out.println("<title>Servlet CarListingServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CarListingServlet at " + request.getContextPath() + "</h1>");
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
        String pageStr = request.getParameter("page"); 
        int page = (pageStr == null || pageStr.isEmpty()) ? 1: Integer.parseInt(pageStr); 
        
        // dong goi tieu chi tim kiem tu request vao DTO
        CarSearchCriteriaDTO criteria = new CarSearchCriteriaDTO(); 
        criteria.setKeyword(request.getParameter("keyword"));
        
        try {
            String manufactureIdStr = request.getParameter("manufactureId"); 
            if(manufactureIdStr != null && !manufactureIdStr.isEmpty()){
                criteria.setManufactureId(Integer.parseInt(manufactureIdStr));
            }
            
            String carTypeIdStr = request.getParameter("carTypeId"); 
            if(carTypeIdStr != null && !carTypeIdStr.isEmpty() ){
                criteria.setCarTypeId(Integer.parseInt(carTypeIdStr));
            }
            
            String yearStr = request.getParameter("year"); 
            if(yearStr != null && !yearStr.isEmpty()){
                criteria.setYear(Integer.parseInt(yearStr));
            }
            
            String colorIdStr = request.getParameter("colorId"); 
            if(colorIdStr != null && !colorIdStr.isEmpty()){
                criteria.setColorId(Integer.parseInt(colorIdStr));
            }
            
            String engineTypeIdStr = request.getParameter("engineTypeId");
            if(engineTypeIdStr != null && !engineTypeIdStr.isEmpty()){
                criteria.setEngineTypeId(Integer.parseInt(engineTypeIdStr));
            }
            
            String minPriceStr = request.getParameter("minPrice"); 
            if(minPriceStr != null && !minPriceStr.isEmpty()){
                criteria.setMinPrice(new BigDecimal(minPriceStr));
            }
            
            String maxPriceStr = request.getParameter("maxPrice"); 
            if(maxPriceStr != null && !maxPriceStr.isEmpty()){
                criteria.setMaxPrice(new BigDecimal(maxPriceStr));
            }
        } catch (NumberFormatException e) {
            request.setAttribute("erroe", "Input is not valid");
        }
        
        //Lay du lieu cho dropdown bo loc
        CarFilterDataDTO filterData =carService.getFilterData(); 
        
        //tim kiem xe dua tren tieu chi
        CarListPageDTO pageData = carService.searchCars(criteria, page, 10); 
        
        //set cac attribute de truyen ra jsp
        request.setAttribute("filterData", filterData);
        request.setAttribute("pageData", pageData);
        request.setAttribute("searchCriteria", criteria);
        
        //forward sang trang car-listing
        request.getRequestDispatcher("/car_listing.jsp").forward(request, response);
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
        processRequest(request, response);
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
