/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao.car;

import jakarta.persistence.EntityManager;
import java.util.List;
import model.EngineType;
import util.JPAUtil;

/**
 *
 * @author ADMIN
 */
public class EngineTypeDAO {
    public List<EngineType> findAll(){
        EntityManager em = JPAUtil.getEntityManager(); 
        try {
            return em.createQuery("SELECT et FROM EngineType et ORDER BY et.name", EngineType.class).getResultList();
        } finally{
            if(em != null) em.close();
        }
    }
    
}
