/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao.car;

import dto.CarSearchCriteriaDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import model.Car;
import util.JPAUtil;

/**
 *
 * @author ADMIN
 */
public class CarDAO {
    public void save(Car car){
        EntityManager em = JPAUtil.getEntityManager(); 
        try {
            em.getTransaction().begin();
            em.persist(car);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e; 
        }finally{
            em.close();
        }
    }
    
    public Car update(Car car){
        EntityManager em = JPAUtil.getEntityManager(); 
        try {
            em.getTransaction().begin();
            Car updatedcar = em.merge(car); 
            em.getTransaction().commit();
            return updatedcar; 
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e; 
        }finally{
            em.close();
        }
    }
    
    public Optional<Car> findById(long  carId){
        EntityManager em = JPAUtil.getEntityManager(); 
        try {
            return  Optional.ofNullable(em.find(Car.class, carId)); 
        } finally{
            em.close();
        }
    }
    
    /**
     * Tìm một danh sách các xe dựa trên danh sách ID.
     * Rất quan trọng cho OrderService để kiểm tra giỏ hàng.
     * @param carIds Danh sách các ID của xe.
     * @return Danh sách các đối tượng Car tương ứng.
     */
    public List<Car> findCarsByIds(List<Long> carIds){
        if (carIds == null || carIds.isEmpty()){
            return Collections.emptyList(); 
        }
        
        EntityManager em = JPAUtil.getEntityManager(); 
        try {
            String jpql="SELECT c FROM Car c WHERE c.id IN :carIds"; 
            TypedQuery<Car>query = em.createQuery(jpql, Car.class); 
            query.setParameter("carIds", carIds); 
            return query.getResultList(); 
        } finally{
            em.close();
        }
    }
    
    /**
     * Lấy danh sách tất cả các xe có sẵn để bán (có phân trang).
     * Dùng cho trang hiển thị sản phẩm chính.
     */
    public List<Car> findAllAvailableWithPagination(int page, int pageSize){
        EntityManager em = JPAUtil.getEntityManager(); 
        try {
            String jpql = "SELECT c FROM Car c WHERE c.status = :status ORDER BY c.importDate DESC"; 
            TypedQuery<Car>query = em.createQuery(jpql, Car.class); 
            query.setParameter("status", CarStatus.AVAILABLE); 
            query.setFirstResult((page -1)*pageSize); 
            query.setMaxResults(pageSize);
            return query.getResultList(); 
        }finally{
            em.close();
        }
    }
    
    public List<Car> searchCar(CarSearchCriteriaDTO criteria){
        EntityManager em = JPAUtil.getEntityManager(); 
        try {
            StringBuilder jpqlBuilder = new StringBuilder("SELECT c FROM Car c JOIN c.carModel m WHERE 1=1"); 
            
            if (criteria.getKeyword() != null && !criteria.getKeyword().isEmpty()){
                jpqlBuilder.append(" AND m.name LIKE :keyword");
            }
            if (criteria.getManufactureId() != null) {
                jpqlBuilder.append(" AND m.manufacture.id = :manufactureId");
            }
   
            if (criteria.getCarTypeId() != null){
                jpqlBuilder.append(" AND m.carType.id = :carTypeId"); 
            }
            
            if (criteria.getYear() != null) {
                jpqlBuilder.append(" AND m.year = :year"); 
                
            }
            
            if (criteria.getMinPrice() != null){
                jpqlBuilder.append(" AND c.importPrice >= :minPrice"); 
            }
            
            if (criteria.getMaxPrice() != null){
                jpqlBuilder.append(" AND c.importPrice <= :maxPrice"); 
            }
            
            TypedQuery<Car> query = em.createQuery(jpqlBuilder.toString(), Car.class); 
            //gan gia tri cho cac tham so da dc them vao
            if (criteria.getKeyword() != null  && !criteria.getKeyword().isEmpty()){
                query.setParameter("keyword", "%" +criteria.getKeyword() + "%"); 
                
            }
            
            if (criteria.getManufactureId() != null){
                query.setParameter("manufactureId", criteria.getManufactureId() ); 
            }
            
            if (criteria.getCarTypeId()!= null){
                query.setParameter("carTypeId", criteria.getCarTypeId()); 
            }
            
            if (criteria.getYear()!= null){
                query.setParameter("year", criteria.getYear()); 
            }
            
            if (criteria.getMinPrice()!= null){
                query.setParameter("minPrice", criteria.getMinPrice()); 
            }
            
            if (criteria.getMaxPrice()!= null){
                query.setParameter("maxPrice", criteria.getMaxPrice()); 
            }
            
            return query.getResultList(); 
        } finally{
            em.close();
        }
    }
    
    
    
    
    
}
