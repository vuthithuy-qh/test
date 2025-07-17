/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao.car;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import java.util.List;
import model.CarColor;
import util.JPAUtil;

/**
 *
 * @author ADMIN
 */
public class CarColorDAO {
    public List<CarColor> findAll(){
        EntityManager em = JPAUtil.getEntityManager(); 
        try {
            return em.createQuery("SELECT cc FROM CarColor cc ORDER BY cc.name", CarColor.class).getResultList(); 
            
        } finally{
            if(em != null) em.close();
        }
    }
    
    public boolean createColor (CarColor color){
        EntityManager em = JPAUtil.getEntityManager();
        try {
                em.getTransaction().begin();
                em.persist(color);
                em.getTransaction().commit();
                return true;
            } catch (Exception e) {
                if (em.getTransaction().isActive()) {
                    em.getTransaction().rollback();
                }
                e.printStackTrace();
                return false;
            } finally {
                em.close();
            }   
        }
    public boolean updateColor (CarColor color){
        EntityManager em = JPAUtil.getEntityManager();
        try {
                em.getTransaction().begin();
                em.merge(color);
                em.getTransaction().commit();
                return true;
            } catch (Exception e) {
                if (em.getTransaction().isActive()) {
                    em.getTransaction().rollback();
                }
                e.printStackTrace();
                return false;
            } finally {
                em.close();
            }   
    }
    public boolean deleteColorByHexCode(String hexCode) {
    if (hexCode == null) return false;

    EntityManager em = JPAUtil.getEntityManager();
    EntityTransaction tx = null;

    try {
        tx = em.getTransaction();
        tx.begin();

        String jpql = "DELETE FROM CarColor cc WHERE cc.hexCode = :hex";
        Query query = em.createQuery(jpql);
        query.setParameter("hex", hexCode);

        int rowsDeleted = query.executeUpdate();
        tx.commit();

        return rowsDeleted > 0;
    } catch (Exception e) {
        if (tx != null && tx.isActive()) tx.rollback();
        e.printStackTrace();
        return false;
    } finally {
        em.close();
    }
}
    
}
