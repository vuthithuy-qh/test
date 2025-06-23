/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao.customerProfile;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;
import model.CustomerProfile;
import util.JPAUtil;

/**
 *
 * @author ADMIN
 */
public class CustomerProfileDAOImpl {

    public boolean save(CustomerProfile profile) {
        EntityManager em = null;
        try {
            em = JPAUtil.getEntityManager();
            em.getTransaction().begin();
            em.persist(profile);
            em.getTransaction().commit();
            return true;
        } catch (Exception ex) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            ex.printStackTrace();
            return false;
        } finally {
            if (em != null) em.close();
        }
    }

    /**
     *
     * @param accountId
     * @return
     */
 
    public CustomerProfile  findById(Long accountId) {
       EntityManager em = null;
        try {
            em = JPAUtil.getEntityManager();
            return em.find(CustomerProfile.class, accountId);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        } finally {
            if (em != null) em.close();
        }
    }

    public List<CustomerProfile> findAll() {
       EntityManager em = null;
        try {
            em = JPAUtil.getEntityManager();
            String jpql = "SELECT c FROM CustomerProfile c";
            TypedQuery<CustomerProfile> query = em.createQuery(jpql, CustomerProfile.class);
            return query.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        } finally {
            if (em != null) em.close();
        }
    }

    public boolean update(CustomerProfile profile) {
        EntityManager em = null;
        try {
            em = JPAUtil.getEntityManager();
            em.getTransaction().begin();
            em.merge(profile);
            em.getTransaction().commit();
            return true;
        } catch (Exception ex) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            ex.printStackTrace();
            return false;
        } finally {
            if (em != null) em.close();
        }
    }

    public boolean delete(Long accountId) {
        EntityManager em = null;
        try {
            em = JPAUtil.getEntityManager();
            em.getTransaction().begin();
            CustomerProfile profile = em.find(CustomerProfile.class, accountId);
            if (profile != null) {
                em.remove(profile);
                em.getTransaction().commit();
                return true;
            } else {
                em.getTransaction().rollback();
                return false;
            }
        } catch (Exception ex) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            ex.printStackTrace();
            return false;
        } finally {
            if (em != null) em.close();
        }
        
    }

    public CustomerProfile findByPhone(String phone) {
       EntityManager em = null;
        try {
            em = JPAUtil.getEntityManager();
            String jpql = "SELECT c FROM CustomerProfile c WHERE c.phone = :phone";
            TypedQuery<CustomerProfile> query = em.createQuery(jpql, CustomerProfile.class);
            query.setParameter("phone", phone);
            List<CustomerProfile> result = query.getResultList();
            return result.isEmpty() ? null : result.get(0);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        } finally {
            if (em != null) em.close();
        }
    }

    public List<CustomerProfile> findByName(String name) {
        EntityManager em = null;
        try {
            em = JPAUtil.getEntityManager();
            String jpql = "SELECT c FROM CustomerProfile c WHERE c.name = :name";
            TypedQuery<CustomerProfile> query = em.createQuery(jpql, CustomerProfile.class);
            query.setParameter("name", name);
            return query.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        } finally {
            if (em != null) em.close();
        }
    }
}
     
    

