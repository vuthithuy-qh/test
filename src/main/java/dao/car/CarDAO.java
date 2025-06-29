/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao.car;

import dto.CarSearchCriteriaDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    
    public Optional<Car> findById(int  carId){
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
    public List<Car> findCarsByIds(List<Integer> carIds){
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
    
    public List<Car> searchCars(CarSearchCriteriaDTO criteria, int page, int pageSize) {
    EntityManager em = JPAUtil.getEntityManager();
    try {
        Map<String, Object> parameters = new HashMap<>();

        StringBuilder jpql = new StringBuilder(
            "SELECT c FROM Car c " +
            "JOIN c.carModel m " +
            "JOIN m.carType ct " +
            "JOIN m.manufacture mf " +
            "WHERE c.status = :status"
        );
        parameters.put("status", CarStatus.AVAILABLE);

        // Lọc theo Manufacture
        if (criteria.getManufactureId() != null) {
            jpql.append(" AND mf.id = :manufactureId");
            parameters.put("manufactureId", criteria.getManufactureId());
        }

        // Lọc theo CarType
        if (criteria.getCarTypeId() != null) {
            jpql.append(" AND ct.id = :carTypeId");
            parameters.put("carTypeId", criteria.getCarTypeId());
        }

        // Lọc theo năm
        if (criteria.getYear() != null) {
            jpql.append(" AND m.year = :year");
            parameters.put("year", criteria.getYear());
        }

        // Lọc theo giá bán (BigDecimal)
        if (criteria.getMinPrice() != null) {
            jpql.append(" AND c.sellingPrice >= :minPrice");
            parameters.put("minPrice", criteria.getMinPrice());
        }

        if (criteria.getMaxPrice() != null) {
            jpql.append(" AND c.sellingPrice <= :maxPrice");
            parameters.put("maxPrice", criteria.getMaxPrice());
        }

        // Tìm kiếm keyword trên nhiều trường
        if (criteria.getKeyword() != null && !criteria.getKeyword().trim().isEmpty()) {
            String[] keywords = criteria.getKeyword().trim().split("[\\s,]+");
            if (keywords.length > 0) {
                jpql.append(" AND (");
                for (int i = 0; i < keywords.length; i++) {
                    if (i > 0) jpql.append(" OR ");
                    String param = "keyword" + i;

                    jpql.append("(LOWER(m.name) LIKE :").append(param)
                        .append(" OR LOWER(ct.name) LIKE :").append(param)
                        .append(" OR LOWER(mf.name) LIKE :").append(param).append(")");

                    parameters.put(param, "%" + keywords[i].toLowerCase() + "%");
                }
                jpql.append(")");
            }
        }

        jpql.append(" ORDER BY c.importDate DESC");

        TypedQuery<Car> query = em.createQuery(jpql.toString(), Car.class);

        // Gán tất cả tham số
        for (Map.Entry<String, Object> entry : parameters.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }

        // Phân trang
        query.setFirstResult((page - 1) * pageSize);
        query.setMaxResults(pageSize);

        return query.getResultList();

    } finally {
        if (em != null) em.close();
    }
}

    

    public long countCarsByCriteria(CarSearchCriteriaDTO criteria) {
    EntityManager em = JPAUtil.getEntityManager();
    try {
        Map<String, Object> parameters = new HashMap<>();

        StringBuilder jpqlBuilder = new StringBuilder(
            "SELECT COUNT(c) FROM Car c " +
            "JOIN c.carModel m " +
            "JOIN m.carType ct " +
            "JOIN m.manufacture mf " +
            "JOIN m.color cl " +
            "JOIN m.engineType et " +
            "WHERE c.status = :status"
        );
        parameters.put("status", CarStatus.AVAILABLE);

        // --- Các điều kiện lọc ---
        if (criteria.getManufactureId() != null) {
            jpqlBuilder.append(" AND mf.id = :manufactureId");
            parameters.put("manufactureId", criteria.getManufactureId());
        }
        if (criteria.getCarTypeId() != null) {
            jpqlBuilder.append(" AND ct.id = :carTypeId");
            parameters.put("carTypeId", criteria.getCarTypeId());
        }
        if (criteria.getColorId() != null) {
            jpqlBuilder.append(" AND cl.id = :colorId");
            parameters.put("colorId", criteria.getColorId());
        }
        if (criteria.getEngineTypeId() != null) {
            jpqlBuilder.append(" AND et.id = :engineTypeId");
            parameters.put("engineTypeId", criteria.getEngineTypeId());
        }
        if (criteria.getYear() != null) {
            jpqlBuilder.append(" AND m.year = :year");
            parameters.put("year", criteria.getYear());
        }
        if (criteria.getMinPrice() != null) {
            jpqlBuilder.append(" AND c.sellingPrice >= :minPrice");
            parameters.put("minPrice", criteria.getMinPrice());
        }
        if (criteria.getMaxPrice() != null) {
            jpqlBuilder.append(" AND c.sellingPrice <= :maxPrice");
            parameters.put("maxPrice", criteria.getMaxPrice());
        }

        // --- Tìm kiếm theo keyword trên nhiều trường ---
        if (criteria.getKeyword() != null && !criteria.getKeyword().trim().isEmpty()) {
            String[] keywords = criteria.getKeyword().trim().split("[\\s,]+");

            if (keywords.length > 0) {
                jpqlBuilder.append(" AND (");
                for (int i = 0; i < keywords.length; i++) {
                    if (i > 0) jpqlBuilder.append(" OR ");
                    String param = "keyword" + i;

                    jpqlBuilder.append("(LOWER(m.name) LIKE :").append(param)
                        .append(" OR LOWER(ct.name) LIKE :").append(param)
                        .append(" OR LOWER(mf.name) LIKE :").append(param)
                        .append(" OR LOWER(cl.name) LIKE :").append(param)
                        .append(" OR LOWER(et.name) LIKE :").append(param)
                        .append(")");

                    parameters.put(param, "%" + keywords[i].toLowerCase() + "%");
                }
                jpqlBuilder.append(")");
            }
        }

        // --- Tạo query ---
        TypedQuery<Long> query = em.createQuery(jpqlBuilder.toString(), Long.class);
        for (Map.Entry<String, Object> entry : parameters.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }

        return query.getSingleResult();
    } finally {
        if (em != null) em.close();
    }
}


    
    
}
