/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao.car;

import jakarta.persistence.EntityManager;
import java.util.List;
import util.JPAUtil;

/**
 *
 * @author ADMIN
 */
public class CarModelDAO {
    public List<Integer> findAvailableYears(){
        EntityManager em = JPAUtil.getEntityManager(); 
        try {
            return em.createQuery("SELECT DISTINCT m.year FROM CarModel m ORDER BY m.year DESC", Integer.class).getResultList(); 
        }finally{
            if(em != null) em.close();
        }
    }
}
