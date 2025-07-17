package Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Car;
import model.Feedback;
import service.car.CarService;
import service.feedbackService.FeedbackService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
//import service.feedbackService.FavoriteService;

@WebServlet("/car-detail")
public class CarDetailServlet extends HttpServlet {

    private final CarService carService = new CarService();
    private final FeedbackService feedbackService = new FeedbackService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String carIdStr = request.getParameter("id");
            if (carIdStr == null || carIdStr.isEmpty()) {
                response.sendRedirect("cars");
                return;
            }

            int carId = Integer.parseInt(carIdStr);
            Optional<Car> optionalCar = carService.findById(carId);
            List<Feedback> feedbackList = feedbackService.findByCarId(carId);
//                    FavoriteService favoriteService = new FavoriteService();
//        int count = favoriteService.countFavoritesByCar(carId);
//        request.setAttribute("favoriteCount", count);
//
            if (!optionalCar.isPresent()) {  // Kiểm tra xem có Car không
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Car not found");
                return;
            }

            // Lấy đối tượng Car từ Optional và đặt vào request
            Car car = optionalCar.get();
            request.setAttribute("car", car);
            request.setAttribute("feedbackList", feedbackList);
            request.getRequestDispatcher("/carDetail.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid car ID");
        }
    }
}