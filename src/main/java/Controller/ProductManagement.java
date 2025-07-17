/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import constant.CarStatus;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import dao.car.CarDAO;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.Part;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Map;
import java.util.Optional;
import model.Car;
import model.CarModel;
import service.car.CarService;
import util.CarValidation;
import util.JPAUtil;

/**
 *
 * @author nguye
 */
public class ProductManagement extends HttpServlet {

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
            out.println("<title>Servlet ProductManagement</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ProductManagement at " + request.getContextPath() + "</h1>");
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
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        CarDAO carD = new CarDAO();
        switch (action) {
            case "new":
                request.getRequestDispatcher("/createProduct.jsp").forward(request, response);
                break;
            case "edit": {
                int carId = Integer.parseInt(request.getParameter("productId"));
                Car car = carD.findById(carId).orElse(null);
                request.setAttribute("product", car);
                request.getRequestDispatcher("/updateProduct.jsp").forward(request, response);
                break;
            }
            case "delete": {
                int carId = Integer.parseInt(request.getParameter("id"));
                boolean isDeleted = carD.deleteCarById(carId);

                List<Car> cars = carD.findAll(); // Lấy lại danh sách sau khi xóa
                request.setAttribute("carList", cars);

                if (!isDeleted) {
                    request.setAttribute("error", "Không thể xóa sản phẩm.");
                }

                request.getRequestDispatcher("/productList.jsp").forward(request, response);
                break;
            }

            default: {
                // Default: list products
                List<Car> cars = carD.findAll();
                request.setAttribute("carList", cars);
                request.getRequestDispatcher("/productList.jsp").forward(request, response);
            }
        }

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
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        EntityManager em = JPAUtil.getEntityManager();
        CarDAO carD = new CarDAO();
        CarService carS = new CarService();
        try {
            switch (action) {
                case "create": {
                    try {
                        Car car = new Car();
                        car.setVin(request.getParameter("vin"));
                        car.setImportPrice(new BigDecimal(request.getParameter("importPrice")));
                        car.setSellingPrice(new BigDecimal(request.getParameter("sellingPrice")));
                        car.setImportDate(Date.valueOf(request.getParameter("importDate")));
                        car.setStatus(CarStatus.valueOf(request.getParameter("status")));
                        car.setCarModel(em.find(CarModel.class, Long.valueOf(request.getParameter("carModelId"))));
                        Map<String, String> errors = CarValidation.validateCar(car, "create");

                        if (!errors.isEmpty()) {
                            request.setAttribute("errors", errors);
                            request.setAttribute("product", car);
                            request.getRequestDispatcher("createProduct.jsp").forward(request, response);
                            return;
                        }
                        boolean created = carS.createNewProduct(car);
                        if (created) {
                            request.setAttribute("success", "Thêm sản phẩm thành công.");
                        } else {
                            request.setAttribute("error", "Không thể thêm sản phẩm.");
                        }
                    } catch (Exception e) {
                        request.setAttribute("error", "Lỗi khi thêm sản phẩm: " + e.getMessage());
                    }
                    break;
                }

                case "update": {
                    try {
                        int carId = Integer.parseInt(request.getParameter("productId"));
                        Car car = new Car();
                        car.setId(carId); // ✅ QUAN TRỌNG!
                        car.setVin(request.getParameter("vin"));
                        car.setImportPrice(new BigDecimal(request.getParameter("importPrice")));
                        car.setSellingPrice(new BigDecimal(request.getParameter("sellingPrice")));
                        car.setImportDate(Date.valueOf(request.getParameter("importDate")));
                        car.setStatus(CarStatus.valueOf(request.getParameter("status")));
                        car.setCarModel(em.find(CarModel.class, Long.valueOf(request.getParameter("carModelId"))));

                        Map<String, String> errors = CarValidation.validateCar(car, "update");

                        if (!errors.isEmpty()) {
                            request.setAttribute("errors", errors);
                            request.setAttribute("product", car);
                            request.getRequestDispatcher("updateProduct.jsp").forward(request, response);
                            return;
                        }

                        boolean updated = carS.updateProduct(car);
                        if (updated) {
                            request.setAttribute("success", "Cập nhật sản phẩm thành công.");
                        } else {
                            request.setAttribute("error", "Không thể cập nhật sản phẩm.");
                        }
                    } catch (Exception e) {
                        request.setAttribute("error", "Lỗi khi cập nhật sản phẩm: " + e.getMessage());
                    }
                    break;
                }
            }
        } finally {
            em.close();
        }

        List<Car> cars = carD.findAll();
        request.setAttribute("carList", cars);
        request.getRequestDispatcher("/productList.jsp").forward(request, response);
        request.setAttribute("section", "products");
        request.getRequestDispatcher("/dashboard.jsp").forward(request, response);
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
