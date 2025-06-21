/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.customer;

import dao.account.AccountDAO;
import dao.account.AccountDAOImpl;
import dao.customerProfile.CustomerProfileDAO;
import dao.customerProfile.CustomerProfileDAOImpl;
import java.time.LocalDate;
import java.util.List;
import model.Account;
import model.CustomerProfile;

/**
 *
 * @author ADMIN
 */
public class CustomerService {
    private AccountDAO accountDAO = new AccountDAOImpl(); 
    private CustomerProfileDAO profileDAO = new CustomerProfileDAOImpl();
    
    public boolean register(String username, String password, String email, String name, String phone, String address){
        if (accountDAO.findByUsername(username) != null || accountDAO.findbyEmail(email) != null){
           
            
            return false; 
            
        }
        String passwordHash = password; 
        Account account = new Account(); 
        account.setUsername(username);
        account.setPasswordHash(passwordHash);
        account.setEmail(email);
        account.setRole("customer");
        account.setCreateDate(LocalDate.now());
        
        boolean ok = accountDAO.save(account); 
        
        if (!ok){
            
            return false; 
        }
        
        CustomerProfile profile = new CustomerProfile(); 
        profile.setAccountId(account.getId());
        profile.setAccount(account);
        profile.setName(name);
        profile.setPhone(phone);
        profile.setAddress(address);
        profile.setLoyaltyPoints(0);
        
        ok = profileDAO.save(profile); 
        if (!ok){
            
            accountDAO.delete(account.getId()); 
            return false; 
        }
        
        
        return true; 
        
    }
    
    public CustomerProfile login (String username, String password){
        Account account = accountDAO.findByUsername(username); 
        if (account == null){
            return null; 
        }
        
        if (!account.getPasswordHash().equals(password)){
            return null; 
        }
        
        CustomerProfile profile = profileDAO.findById(account.getId()); 
        if (profile == null){
            return null; 
        }
        
        return profile; 
    }
    
    public CustomerProfile viewProfile(Long accountId){
        return profileDAO.findById(accountId); 
    }
    
    public boolean updateProfile(CustomerProfile profile){
        return profileDAO.update(profile); 
    }
    
    public boolean deleteProfile(Long accountId){
        return profileDAO.delete(accountId); 
    }
    
    public boolean changePassword(Long accountId, String newPassword){
        Account account = accountDAO.findById(accountId); 
        if (account == null) return false; 
        account.setPasswordHash(newPassword);
        return accountDAO.update(account); 
    }
    
    public boolean forgotPassword(String email, String newPassword){
        Account account = accountDAO.findbyEmail(email); 
        if (account == null) return false; 
        account.setPasswordHash(newPassword);
        return accountDAO.update(account); 
    }
    
    public List<CustomerProfile> findProfilesByName(String name){
        return profileDAO.findByName(name); 
    }
    
    
    
}
