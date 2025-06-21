/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao.customerProfile;

import java.util.List;
import model.CustomerProfile;

/**
 *
 * @author ADMIN
 */
public interface CustomerProfileDAO {
    boolean save(CustomerProfile profile);
    CustomerProfile findById(Long accountId);
    List<CustomerProfile> findAll();
    boolean update(CustomerProfile profile);
    boolean delete(Long accountId);
    CustomerProfile findByPhone(String phone);
    List<CustomerProfile> findByName(String name);
}
