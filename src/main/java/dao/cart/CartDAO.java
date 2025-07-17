package dao.cart;

import jakarta.persistence.*;
import model.*;
import util.JPAUtil;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public class CartDAO {

    /**
     * Lấy ID của cart tương ứng với account, nếu chưa có thì tạo mới.
     */
    public int getOrCreateCartId(int accountId) {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            TypedQuery<Cart> query = em.createQuery(
                    "SELECT c FROM Cart c WHERE c.customer.id = :accountId", Cart.class);
            query.setParameter("accountId", accountId);

            List<Cart> results = query.getResultList();
            if (!results.isEmpty()) {
                return results.get(0).getId();
            }

            em.getTransaction().begin();

            Account account = em.find(Account.class, accountId);
            if (account == null) {
                throw new IllegalArgumentException("Account with id " + accountId + " not found.");
            }

            Cart newCart = new Cart();
            newCart.setCustomer(account);
            newCart.setCreateDate(LocalDateTime.now());

            em.persist(newCart);
            em.getTransaction().commit();

            return newCart.getId();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    /**
     * Thêm một xe vào giỏ hàng nếu chưa có.
     */
    public boolean addToCart(int accountId, int carId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            int cartId = getOrCreateCartId(accountId);

            TypedQuery<CartItem> query = em.createQuery(
                    "SELECT ci FROM CartItem ci WHERE ci.cart.id = :cartId AND ci.car.id = :carId", CartItem.class);
            query.setParameter("cartId", cartId);
            query.setParameter("carId", carId);

            if (!query.getResultList().isEmpty()) {
                return false; // Đã tồn tại, không thêm nữa
            }

            em.getTransaction().begin();
            Cart cart = em.find(Cart.class, cartId);
            Car car = em.find(Car.class, carId);

            if (cart == null) {
                throw new IllegalArgumentException("Cart with id " + cartId + " not found.");
            }
            if (car == null) {
                throw new IllegalArgumentException("Car with id " + carId + " not found.");
            }

            CartItem item = new CartItem();
            item.setCart(cart);
            item.setCar(car);
            em.persist(item);
            em.getTransaction().commit();
            return true;

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    /**
     * Xoá một xe khỏi giỏ hàng theo accountId và carId.
     */
    public void removeFromCart(int accountId, int carId) {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            int cartId = getOrCreateCartId(accountId);

            em.getTransaction().begin();
            TypedQuery<CartItem> query = em.createQuery(
                    "SELECT ci FROM CartItem ci WHERE ci.cart.id = :cartId AND ci.car.id = :carId", CartItem.class);
            query.setParameter("cartId", cartId);
            query.setParameter("carId", carId);

            List<CartItem> items = query.getResultList();
            for (CartItem item : items) {
                em.remove(em.contains(item) ? item : em.merge(item));
            }

            em.getTransaction().commit();

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

public List<CartItem> getCartItems(int accountId) {
    EntityManager em = JPAUtil.getEntityManager();
    try {
        // Lấy cartId hiện tại của user
        TypedQuery<Integer> cartIdQuery = em.createQuery(
            "SELECT c.id FROM Cart c WHERE c.customer.id = :accountId", Integer.class);
        cartIdQuery.setParameter("accountId", accountId);

        List<Integer> cartIds = cartIdQuery.getResultList();
        if (cartIds.isEmpty()) {
            return Collections.emptyList();
        }

        int cartId = cartIds.get(0);

        // Lấy các CartItem và JOIN FETCH tất cả các quan hệ liên quan
        TypedQuery<CartItem> itemQuery = em.createQuery(
            "SELECT ci FROM CartItem ci " +
            "JOIN FETCH ci.car c " +
            "JOIN FETCH c.carModel m " +
            "JOIN FETCH m.color " +
            "JOIN FETCH m.manufacture " +
            "JOIN FETCH m.engineType " +
            "LEFT JOIN FETCH c.images " +
            "WHERE ci.cart.id = :cartId", CartItem.class);
        itemQuery.setParameter("cartId", cartId);

        return itemQuery.getResultList();
    } finally {
        em.close();
    }
}


}
