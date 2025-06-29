/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao.car;

import jakarta.persistence.EntityManager;
import java.util.List;
import model.Manufacture;
import util.JPAUtil;

/**
 *
 * @author ADMIN
 */
public class ManufactureDAO {
    public List<Manufacture> findAll(){
        EntityManager em =JPAUtil.getEntityManager(); 
        try {
            return em.createQuery("SELECT m FROM Manufacture m ORDER BY m.name", Manufacture.class).getResultList(); 
            
        } finally{
            if(em != null) em.close();
        }
    }
    
}
