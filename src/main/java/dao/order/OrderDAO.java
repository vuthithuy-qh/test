/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao.order;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;
import model.Order;
import util.JPAUtil;

/**
 *
 * @author ADMIN
 */
public class OrderDAO {
    
    /**
     * Lưu một đơn hàng mới vào cơ sở dữ liệu.
     * Thường được gọi sau khi khách hàng hoàn tất quy trình thanh toán.
     * Nhờ có CascadeType.ALL, các OrderDetail con cũng sẽ được tự động lưu.
     * @param order Đối tượng Order cần lưu.
     */
    public  void save (Order order){
        EntityManager em = JPAUtil.getEntityManager(); 
        try {
            em.getTransaction().begin();
            em.persist(order);
            em.getTransaction().commit();
            
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e; 
        }finally{
            em.close();
        }
    }
    
    /**
     * Cập nhật thông tin của một đơn hàng đã tồn tại.
     * Thường dùng để thay đổi trạng thái đơn hàng (ví dụ: từ "đang xử lý" sang "đã giao").
     * @param order Đối tượng Order với thông tin đã được cập nhật.
     * @return Đối tượng Order sau khi đã được cập nhật và đồng bộ với DB.
     */
    public Order update(Order order){
        EntityManager em = JPAUtil.getEntityManager(); 
        try {
            em.getTransaction().begin();
            Order updateOrder = em.merge(order); 
            em.getTransaction().commit();
            return  updateOrder; 
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e; 
        }finally {
            em.close();
        }
    }
    
    /**
     * Tìm một đơn hàng cụ thể bằng ID của nó.
     * Rất quan trọng cho chức năng "Xem chi tiết đơn hàng".
     * Sử dụng JOIN FETCH để tải sẵn các thông tin cần thiết, tránh lỗi N+1.
     * @param orderId ID của đơn hàng cần tìm.
     * @return một Optional chứa Order nếu tìm thấy, hoặc Optional rỗng.
     */
    public Optional<Order> findById(int orderId){
        EntityManager em = JPAUtil.getEntityManager(); 
        
        try {
            String jpql = "SELECT o FROM Order o "
                    + " LEFT JOIN FETCH o.oderDetails od "
                    + "LEFT JOIN FETCH o.customer c "
                    + "LEFT JOIN FETCH c.account "
                    + "WHERE o.id = :orderId"; 
            TypedQuery<Order> query = em.createQuery(jpql, Order.class); 
            query.setParameter("orderId", orderId); 
            return Optional.of(query.getSingleResult()); 
        } catch (NoResultException e) {
            return Optional.empty(); 
        }finally{
            em.close();
        }
    }
    
    /**
     * Lấy lịch sử mua hàng của một khách hàng (có phân trang).
     * Rất quan trọng cho trang "Lịch sử mua hàng" để không tải hàng ngàn đơn hàng cùng lúc.
     * @param accountId ID của tài khoản khách hàng.
     * @param page Số trang hiện tại (bắt đầu từ 1).
     * @param pageSize Số đơn hàng trên mỗi trang.
     * @return Danh sách các đơn hàng trên trang hiện tại.
     */
    
    public List<Order> findAllByAccountIdWithPafnination(int accountId, int page, int pageSize){
        EntityManager em = JPAUtil.getEntityManager(); 
        
        try {
            String jpql = "SELCT o from Order o WHERE o.customer.id = :accountId ORDER BY o.saleDate DESC"; 
            TypedQuery<Order> query = em.createQuery(jpql, Order.class); 
            query.setParameter("accountId", accountId); 
            
            //logic phan trang 
            query.setFirstResult((page -1)*pageSize); // vi tri bat dau lay
            query.setMaxResults(pageSize); // so luong can lay
            
            return query.getResultList(); 
        } finally{
            em.close();
        }
    }
    
    /**
     * Đếm tổng số đơn hàng của một tài khoản.
     *
     * @param accountId ID của tài khoản khách hàng.
     * @return Tổng số đơn hàng.
     */
    public long countOrdersByAccountId(int accountId){
        EntityManager em = JPAUtil.getEntityManager();
        
        try {
            String jpql = "SELECT COUNT(o) FROM Order WHERE o.customer.id = :accoountId"; 
            TypedQuery<Long> query = em.createQuery(jpql, Long.class); 
            query.setParameter("accountId", accountId); 
            return query.getSingleResult(); 
        } finally{
            em.close();
        }
    }
    
    public Order findLastestOrderByAccountId(int accountId){
        EntityManager em = JPAUtil.getEntityManager(); 
        
        try {
            // FETCH chi thi dac biet cua JPA, khi lay doi tuong Order, hay dong thoi lay luon collection orderDetails
            //JPA sẽ tạo ra một câu SQL SELECT ... FROM [order] o LEFT JOIN order_detail od ON o.id = od.order_id ...
            String jpql = "SELECT o FROM Order o LEFT JOIN FETCH o.orderDetails "
                    + "WHERE o.customer.id = :accountId ORDER BY o.saleDate DESC"; 
            TypedQuery<Order> query = em.createQuery(jpql,  Order.class); 
            query.setParameter("accountId", accountId); 
            query.setMaxResults(4); // chi lay 4 don hang gan nhat
            List<Order> results = query.getResultList();
            return results.isEmpty() ? null : results.get(0); 
        } finally {
        }
    }
    
    // --- PHƯƠNG THỨC XÓA (DELETE) ---

    /**
     * Xóa một đơn hàng khỏi cơ sở dữ liệu bằng ID.
     * Thường chỉ dành cho quyền admin.
     * @param orderId ID của đơn hàng cần xóa.
     */
    
    public void deleteById(int orderId){
        EntityManager em = JPAUtil.getEntityManager(); 
        try{
            em.getTransaction().begin();
            Order order = em.find(Order.class, orderId); 
            if (order != null){
                em.remove(order);
            }
            em.getTransaction().commit();
        }catch(Exception e){
            em.getTransaction().rollback();
            throw e; 
        }finally{
            em.close();
        }
                
                
    }
    
}
