/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao.account;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;
import model.Account;
import util.JPAUtil;

/**
 *
 * @author ADMIN
// */


public class AccountDAOImpl implements AccountDAO{

    @Override
    public boolean save(Account account) {
        EntityManager em = null;
        
        try {
            em = JPAUtil.getEntityManager(); 
            em.getTransaction().begin();
            em.persist(account);
            em.getTransaction().commit();
            return true; 
        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()){
                em.getTransaction().rollback();
            }
            e.printStackTrace();
            return false; 
        }finally {
            if (em != null){
                em.close();
            }
        }
    }

    @Override
    public Account findById(int id) {
        EntityManager em = null; 
        try{
            em = JPAUtil.getEntityManager(); 
            return em.find(Account.class, id); 
        }catch(Exception e){
            e.printStackTrace();
            return null; 
        }finally{
            if (em != null){
                em.close();
            }
        }
    }

    @Override
    public List<Account> findAll() {
        EntityManager em = null; 
        try{
            em = JPAUtil.getEntityManager(); 
            String jpql = "SELECT a FROM Account a"; 
            
            TypedQuery<Account> query = em.createQuery(jpql, Account.class); 
            
            return query.getResultList(); 
        }catch(Exception e){
            e.printStackTrace();
            return null; 
        }finally {
            if (em != null) em.close();
        }
    }

    @Override
public boolean update(Account account) {
    EntityManager em = null;
    try {
        em = JPAUtil.getEntityManager(); 
        em.getTransaction().begin();
        em.merge(account); 
        em.getTransaction().commit();
        return true; 
    } catch (Exception e) {
        if (em != null && em.getTransaction().isActive()){
            em.getTransaction().rollback();
        }
        e.printStackTrace();
        return false; 
    } finally {
        if (em != null && em.isOpen()) em.close(); // THÊM DÒNG NÀY
    }
}


    public boolean delete(int id) {
        EntityManager em = null; 
        try {
            em = JPAUtil.getEntityManager();
            em.getTransaction().begin();
            Account account = em.find(Account.class, id);
            
            if (account != null){
                em.remove(account);
                em.getTransaction().commit();
                return true; 
            }else {
                em.getTransaction().rollback();
                return false; 
            }
        } catch (Exception e){
            if (em != null && em.getTransaction().isActive()){
                em.getTransaction().rollback();
            }
            e.printStackTrace();
            return false; 
        }finally {
            if (em != null) em.close();
        }
    }
    
       public boolean isDuplicate(Account account) {
    EntityManager em = JPAUtil.getEntityManager();
    try {
        String jpql = "SELECT COUNT(a) FROM Account a " +
                      "WHERE (a.username = :username OR a.email = :email) " +
                      "AND (a.deleteDate IS NULL)";
        Long count = em.createQuery(jpql, Long.class)
                       .setParameter("username", account.getUsername())
                       .setParameter("email", account.getEmail())
                       .getSingleResult();
        return count > 0;
    } finally {
        em.close();
    }
}

       public boolean isDuplicateExcludeId(Account account) {
    EntityManager em = JPAUtil.getEntityManager();
    try {
        String jpql = "SELECT COUNT(a) FROM Account a " +
                      "WHERE (a.username = :username OR a.email = :email) AND a.id <> :id";

        Long count = em.createQuery(jpql, Long.class)
                       .setParameter("username", account.getUsername())
                       .setParameter("email", account.getEmail())
                       .setParameter("id", account.getId())
                       .getSingleResult();

        return count > 0;
    } finally {
        em.close();
    }
}



public Account findByUsername(String username) {
    EntityManager em = null; 
    try {
        em = JPAUtil.getEntityManager(); 
        String jpql = "SELECT a FROM Account a WHERE a.username = :username";
        TypedQuery<Account> query = em.createQuery(jpql, Account.class); 
        query.setParameter("username", username); 
        List<Account> result = query.getResultList(); 
        return result.isEmpty() ? null : result.get(0); 
    } catch (Exception e) {
        e.printStackTrace();
        return null; 
    } finally {
        if (em != null && em.isOpen()) em.close();
    }
}


public Account findbyEmail(String email) {
    EntityManager em = null; 
    try {
        em = JPAUtil.getEntityManager(); 
        String jpql = "SELECT a FROM Account a WHERE a.email = :email"; 
        TypedQuery<Account> query = em.createQuery(jpql, Account.class); 
        query.setParameter("email", email); 
        List<Account> result = query.getResultList(); 
        return result.isEmpty() ? null : result.get(0); 
    } catch (Exception e) {
        e.printStackTrace();
        return null; 
    } finally {
        if (em != null && em.isOpen()) em.close();
    }
}

    
    
    
}
