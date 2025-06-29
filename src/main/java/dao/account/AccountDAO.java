/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao.account;

import java.util.List;
import model.Account;

/**
 *
 * @author ADMIN
 */
public interface AccountDAO {
    
    boolean save(Account account); 
    
    Account findById(int id); 
    List<Account> findAll(); 
    
    boolean update(Account account); 
    boolean delete(int id); 
    
    Account findByUsername(String username); 
    Account findbyEmail(String email); 
}
