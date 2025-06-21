/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.account;

import dao.account.AccountDAO;
import dao.account.AccountDAOImpl;
import dao.customerProfile.CustomerProfileDAO;
import dao.customerProfile.CustomerProfileDAOImpl;
import java.time.LocalDate;
import model.Account;
import model.CustomerProfile;

/**
 *
 * @author ADMIN
 */
public class AccountService {
    private AccountDAO accountDAO = new AccountDAOImpl(); 
    private CustomerProfileDAO profileDAO = new CustomerProfileDAOImpl();
    public boolean registerNewUser(String username, String password, String email) throws Exception{
        if (accountDAO.findByUsername(username) != null){
            throw new Exception("Username has existed before!");
        }
        
        if (accountDAO.findbyEmail(email) != null){
            throw new Exception("Email has existed before"); 
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
    
    public boolean changePassword(Long accountId, String oldPassword, String newPassword) throws Exception{
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
    
    
}
