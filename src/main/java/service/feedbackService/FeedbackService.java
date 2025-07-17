/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.feedbackService;
import dao.feedback.FeedbackDAO;
import dao.feedback.FeedbackDAOImpl;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import model.Account;
import model.Car;
import model.Feedback;
import model.OrderDetail;
import util.JPAUtil;

import java.util.List;

public class FeedbackService {
    private final FeedbackDAO feedbackDAO = new FeedbackDAOImpl();

    public List<Feedback> getFeedbackByCarId(int carId) {
        return feedbackDAO.findByCarId(carId);
    }    public void save(Feedback feedback) {
        feedbackDAO.save(feedback);
    }
    public void addFeedback(Feedback feedback) throws Exception {
        feedbackDAO.insertFeedback(feedback);
    }
    public List<Feedback> getFeedbacksByCarId(int carId) {
        return feedbackDAO.findByCarId(carId);
    }

    public boolean hasPurchasedCar(int accountId, int carId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            String jpql = "SELECT COUNT(od) FROM OrderDetail od " +
                    "WHERE od.order.customer.id = :accountId AND od.car.id = :carId";
            Long count = em.createQuery(jpql, Long.class)
                    .setParameter("accountId", accountId)
                    .setParameter("carId", carId)
                    .getSingleResult();
            return count > 0;
        } finally {
            em.close();
        }
    }

    public boolean hasGivenFeedback(int accountId, int carId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            String jpql = "SELECT COUNT(f) FROM Feedback f " +
                    "WHERE f.account.id = :accountId AND f.car.id = :carId";
            Long count = em.createQuery(jpql, Long.class)
                    .setParameter("accountId", accountId)
                    .setParameter("carId", carId)
                    .getSingleResult();
            return count > 0;
        } finally {
            em.close();
        }
    }
    public List<Feedback> findByCarId(int carId) {
    EntityManager em = JPAUtil.getEntityManager();
    try {
        return em.createQuery("SELECT f FROM Feedback f WHERE f.car.id = :carId ORDER BY f.createdAt DESC", Feedback.class)
                 .setParameter("carId", carId)
                 .getResultList();
    } finally {
        em.close();
    }
}
    public static boolean hasUserRatedCar(int userId, int carId) {
    EntityManager em = JPAUtil.getEntityManager();
    try {
        String jpql = "SELECT COUNT(f) FROM Feedback f WHERE f.account.id = :userId AND f.car.id = :carId";
        Long count = em.createQuery(jpql, Long.class)
                       .setParameter("userId", userId)
                       .setParameter("carId", carId)
                       .getSingleResult();
        return count > 0;
    } finally {
        em.close();
    }
}

}
