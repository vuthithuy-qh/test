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

@WebServlet("/addtocart")
public class AddToCartServlet extends HttpServlet {

    private final CarDAO carDAO = new CarDAO();
    private final CartDAO cartDAO = new CartDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("currentUser");

        if (account == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String carIdParam = request.getParameter("carId");
        Integer carId = parseIntOrNull(carIdParam);
        if (carId == null) {
            session.setAttribute("errorMsg", "Mã xe không hợp lệ.");
            response.sendRedirect("cars");
            return;
        }

        try {
            Optional<Car> carOpt = carDAO.findById(carId);
            if (carOpt.isEmpty()) {
                session.setAttribute("errorMsg", "Xe không tồn tại.");
            } else {
                Car car = carOpt.get();
                if (car.getStatus() != CarStatus.AVAILABLE) {
                    session.setAttribute("errorMsg", "Xe đã bán, không thể thêm vào giỏ hàng.");
                } else {
                    boolean added = cartDAO.addToCart(account.getId(), car.getId());
                    if (added) {
                        session.setAttribute("successMsg", "Đã thêm xe " + car.getCarModel().getName() + " vào giỏ hàng!");
                    } else {
                        session.setAttribute("infoMsg", "Xe đã có trong giỏ hàng!");
                    }
                }
            }
        } catch (Exception e) {
            throw new ServletException("Lỗi khi thêm vào giỏ hàng", e);
        }

        response.sendRedirect("cars");
    }

    private Integer parseIntOrNull(String value) {
    try {
            return (value == null || value.isBlank()) ? null : Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}