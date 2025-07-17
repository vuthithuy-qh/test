package dao.order;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import model.OrderDetail;
import util.JPAUtil;

public class OrderDetailDAO {

    public boolean save(OrderDetail detail) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = null;

        try {
            tx = em.getTransaction();
            tx.begin();
            em.persist(detail);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null && tx.isActive()) tx.rollback();
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }

    public OrderDetail findById(int orderId, int carId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.find(OrderDetail.class, new model.OrderDetailId(orderId, carId));
        } finally {
            em.close();
        }
    }

    public boolean delete(int orderId, int carId) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = null;

        try {
            tx = em.getTransaction();
            tx.begin();
            OrderDetail detail = em.find(OrderDetail.class, new model.OrderDetailId(orderId, carId));
            if (detail != null) {
                em.remove(detail);
                tx.commit();
                return true;
            }
            return false;
        } catch (Exception e) {
            if (tx != null && tx.isActive()) tx.rollback();
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }
}
