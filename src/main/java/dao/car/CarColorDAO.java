/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao.car;

import jakarta.persistence.EntityManager;
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
    
}
