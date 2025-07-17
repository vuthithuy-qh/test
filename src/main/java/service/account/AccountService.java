/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.account;

import dao.account.AccountDAO;
import dao.account.AccountDAOImpl;
import dao.customerProfile.CustomerProfileDAOImpl;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import model.Account;
import model.CustomerProfile;
import util.JPAUtil;
import util.ValidateInforOfUser;

/**
 *
 * @author ADMIN
 */
public class AccountService {
    private AccountDAO accountDAO = new AccountDAOImpl(); 
    private CustomerProfileDAOImpl profileDAO = new CustomerProfileDAOImpl();
    public boolean registerNewUser(String username, String password, String email) throws Exception{
        if (accountDAO.findByUsername(username) != null){
            throw new Exception("Username has existed before!");
        }
        
        if (accountDAO.findbyEmail(email) != null){
            throw new Exception("Email has existed before"); 
        }
        
        if (!ValidateInforOfUser.validUsername(username)) {
            throw new Exception("Username is not valid");
        }

        if (!ValidateInforOfUser.validPassword(password)) {

            throw new Exception("Password contains 3 to 20 character");
        }
        
        Account account = new Account(); 
        account.setUsername(username);
        account.setPasswordHash(password);
        account.setEmail(email);
        account.setRole("customer");
        account.setCreateDate(LocalDate.now());
        
        CustomerProfile profile = new CustomerProfile(); 
        profile.setAccount(account);
        account.setCustomerProfiel(profile);
        return accountDAO.save(account); 
        
        
    }
    
    public Account login (String username, String password) throws Exception{
        Account account= accountDAO.findByUsername(username);
        
        if (account == null){
            throw new Exception("Account not found"); 
        }
        
        if (!account.getPasswordHash().equals(password)){
            throw new Exception("Wrong password");
        }
        
        return account; 
    }
    
    public boolean changePassword(int accountId, String oldPassword, String newPassword) throws Exception{
        Account account = accountDAO.findById(accountId); 
        
        if (account == null){
            throw new Exception("Account not found"); 
        }
        
        if (!account.getPasswordHash().equals(oldPassword)){
            throw new Exception("Old password is incorrect"); 
        }
        
        account.setPasswordHash(newPassword);
        return accountDAO.update(account); 
    }
    
        public boolean updateAccount(Account account) throws Exception {
    EntityManager em = JPAUtil.getEntityManager();
    try {
        Account existing = em.find(Account.class, account.getId());
        if (existing == null) {
            throw new Exception("Tài khoản không tồn tại.");
        }

        String jpql = "SELECT COUNT(a) FROM Account a WHERE (a.username = :username OR a.email = :email) AND a.id <> :id";
        long count = em.createQuery(jpql, Long.class)
            .setParameter("username", account.getUsername())
            .setParameter("email", account.getEmail())
            .setParameter("id", account.getId())
            .getSingleResult();
        if (count > 0) {
            throw new Exception("Username hoặc Email đã tồn tại.");
        }

        if (!ValidateInforOfUser.validUsername(account.getUsername())) {
            throw new Exception("Username không hợp lệ.");
        }

        if (!ValidateInforOfUser.validPassword(account.getPasswordHash())) {
            throw new Exception("Password chứa từ 3 đến 20 ký tự.");
        }

        em.getTransaction().begin();

        // Cập nhật từng trường
        existing.setUsername(account.getUsername());
        existing.setPasswordHash(account.getPasswordHash());
        existing.setEmail(account.getEmail());
        existing.setRole(account.getRole());
        existing.setUpdateDate(LocalDate.now());

        em.merge(existing);
        em.getTransaction().commit();
        return true;

    } catch (Exception e) {
        if (em.getTransaction().isActive()) em.getTransaction().rollback();
        e.printStackTrace();
        throw e;
    } finally {
        em.close();
    }
}
    
}
