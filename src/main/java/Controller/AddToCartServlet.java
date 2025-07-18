package Controller;

import constant.CarStatus;
import dao.car.CarDAO;
import dao.cart.CartDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Account;
import model.Car;

import java.io.IOException;
import java.util.Optional;

// IMPORTANT: Changed servlet mapping to match the JSP's href attribute
@WebServlet("/addtocart") // Changed from "/addtocart"
public class AddToCartServlet extends HttpServlet {

    private final CarDAO carDAO = new CarDAO();
    private final CartDAO cartDAO = new CartDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("currentUser");

        // If user is not logged in, redirect to login page
        if (account == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String carIdParam = request.getParameter("carId");
        Integer carId = parseIntOrNull(carIdParam);
        String action = request.getParameter("action"); // Get the 'action' parameter

        // Validate carId
        if (carId == null) {
            session.setAttribute("errorMsg", "Invalid car ID."); // Translated message
            // Redirect back to car list or car detail page if carId is invalid
            response.sendRedirect(request.getContextPath() + "/cars");
            return;
        }

        try {
            Optional<Car> carOpt = carDAO.findById(carId);
            if (carOpt.isEmpty()) {
                session.setAttribute("errorMsg", "Car does not exist."); // Translated message
                // Redirect back to car list if car does not exist
                response.sendRedirect(request.getContextPath() + "/cars");
            } else {
                Car car = carOpt.get();
                // Check car status before adding to cart
                if (car.getStatus() != CarStatus.AVAILABLE) {
                    session.setAttribute("errorMsg", "Car is sold or unavailable, cannot be added to cart."); // Translated message
                    // Redirect back to the car detail page for context
                    response.sendRedirect(request.getContextPath() + "/car-detail?id=" + carId);
                } else {
                    // Attempt to add car to cart
                    boolean added = cartDAO.addToCart(account.getId(), car.getId());
                    if (added) {
                        session.setAttribute("successMsg", "Car " + car.getCarModel().getName() + " added to cart successfully!"); // Translated message
                    } else {
                        session.setAttribute("infoMsg", "Car is already in the cart!"); // Translated message
                    }

                    // Determine redirect based on 'action' parameter
                    if ("book_test_drive".equals(action)) {
                        // If action is 'book_test_drive', redirect to cart and trigger scroll
                        response.sendRedirect(request.getContextPath() + "/cart?scrollTo=test_drive");
                    } else {
                        // Default behavior: redirect to cart page
                        response.sendRedirect(request.getContextPath() + "/cart");
                    }
                }
            }
        } catch (Exception e) {
            // Log the exception for debugging purposes
            e.printStackTrace();
            throw new ServletException("Error adding to cart: " + e.getMessage(), e); // Translated message
        }
    }

    // Helper method to safely parse Integer
    private Integer parseIntOrNull(String value) {
        try {
            return (value == null || value.isBlank()) ? null : Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
