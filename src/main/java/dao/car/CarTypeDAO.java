/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao.car;

import jakarta.persistence.EntityManager;
import java.util.List;
import model.CarType;
import util.JPAUtil;

/**
 *
 * @author ADMIN
 */
public class CarTypeDAO {
    public List<CarType> finAll(){
        EntityManager em = JPAUtil.getEntityManager(); 
        try {
            return em.createQuery("SELECT ct FROM CarType ct ORDER BY ct.name", CarType.class).getResultList(); 
            
        } finally{
            if(em != null) em.close();
        }
                
    }
    
}
