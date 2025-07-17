package Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import model.Account;
import model.Car;
import model.Feedback;
import service.car.CarService;
import service.feedbackService.FeedbackService;

@WebServlet("/addFeedback")
public class AddFeedbackServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Account currentUser = (Account) request.getSession().getAttribute("currentUser");

        if (currentUser == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String content = request.getParameter("content");
        String ratingStr = request.getParameter("rating");
        String carIdStr = request.getParameter("carId");

        
        if (carIdStr == null || ratingStr == null || content == null || content.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing parameters");
            return;
        }

        try {
            int carId = Integer.parseInt(request.getParameter("carId"));
            int rating = Integer.parseInt(request.getParameter("rating"));

// Lấy xe từ DB
            CarService carService = new CarService();
            Optional<Car> optionalCar = carService.findById(carId);
            if (optionalCar.isEmpty()) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Car not found");
                return;
            }
            Car car = optionalCar.get();
           

// Tạo đối tượng Feedback
            Feedback feedback = new Feedback();
            feedback.setAccount(currentUser);
            feedback.setContent(content);
            feedback.setRating(rating);
            feedback.setCar(car); // ✅ Đã khai báo car

// Lưu feedback
            FeedbackService feedbackService = new FeedbackService();
            feedbackService.addFeedback(feedback);
 if (FeedbackService.hasUserRatedCar(currentUser.getId(), carId)) {
    // Redirect lại hoặc thông báo lỗi
    response.sendRedirect("car-detail?id=" + carId + "&error=alreadyRated");
    return;
}
// Redirect về lại trang chi tiết
            response.sendRedirect("car-detail?id=" + carId);

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid rating or car ID");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to add feedback");
        }
    }
}
