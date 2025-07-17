package dao.feedback;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import java.sql.Connection;
import java.util.List;
import model.Feedback;
import util.JPAUtil;

public class FeedbackDAOImpl implements FeedbackDAO {

    @Override
    public void save(Feedback feedback) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(feedback);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Feedback> findByCarId(int carId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Feedback> query = em.createQuery(
                "SELECT f FROM Feedback f WHERE f.car.id = :carId ORDER BY f.createdAt DESC",
                Feedback.class
            );
            query.setParameter("carId", carId);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public void insertFeedback(Feedback feedback) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = null;

        try {
            tx = em.getTransaction();
            tx.begin();

            em.persist(feedback);  // JPA tự động insert vào DB

            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
            throw new RuntimeException("Insert feedback failed", e);
        } finally {
            em.close();
        }
    }
}
