package dao.booking;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.List;
import model.Booking;
import util.JPAUtil;

public class BookingDAO {

    public void save(Booking booking) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(booking);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }
    public List<Booking> filterBookings(String status, String type, LocalDate date) {
    EntityManager em = JPAUtil.getEntityManager();
    try {
        StringBuilder jpql = new StringBuilder("SELECT b FROM Booking b WHERE 1=1");

        if (status != null && !status.isBlank()) {
            jpql.append(" AND b.status = :status");
        }
        if (type != null && !type.isBlank()) {
            jpql.append(" AND b.type = :type");
        }
        if (date != null) {
            jpql.append(" AND b.bookingDate = :date");
        }

        TypedQuery<Booking> query = em.createQuery(jpql.toString(), Booking.class);

        if (status != null && !status.isBlank()) {
            query.setParameter("status", Enum.valueOf(model.BookingStatus.class, status));
        }
        if (type != null && !type.isBlank()) {
            query.setParameter("type", Enum.valueOf(model.BookingType.class, type));
        }
        if (date != null) {
            query.setParameter("date", date);
        }

        return query.getResultList();
    } finally {
        em.close();
    }
}

public boolean updateStatus(long bookingId, String newStatus) {
    EntityManager em = JPAUtil.getEntityManager();
    try {
        em.getTransaction().begin();
        Booking booking = em.find(Booking.class, bookingId);
        if (booking == null) return false;

        booking.setStatus(Enum.valueOf(model.BookingStatus.class, newStatus));
        em.merge(booking);
        em.getTransaction().commit();
        return true;
    } catch (Exception e) {
        e.printStackTrace();
        if (em.getTransaction().isActive()) em.getTransaction().rollback();
        return false;
    } finally {
        em.close();
    }
}
 public List<Booking> findAll() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Booking> query = em.createQuery("SELECT b FROM Booking b ORDER BY b.createdAt DESC", Booking.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

}
