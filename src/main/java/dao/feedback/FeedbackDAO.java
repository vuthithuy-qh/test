package dao.feedback;

import java.util.List;
import model.Feedback;

public interface FeedbackDAO {
    void save(Feedback feedback);
    List<Feedback> findByCarId(int carId);
    void insertFeedback(Feedback feedback);
}