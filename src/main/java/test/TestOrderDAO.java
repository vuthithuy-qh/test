/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package test;

import dao.order.OrderDAO;
import java.util.List;
import model.Order;

/**
 *
 * @author ADMIN
 */
public class TestOrderDAO {
    public static void main(String[] args) {
        OrderDAO dao = new OrderDAO(); 
        int customerIdToTest = 1; 
        
        try {
            List<Order> orders = dao.findAllByAccountIdWithPagnination(customerIdToTest, 1, 10); 
            if (orders.isEmpty()){
                System.out.println("Rong");
            }else {
                System.out.println(orders.size() + " don hang cho customer id = 4");
                for (Order order : orders) {
                    System.out.println("order id:" + order.getId());
                }
            }
        } catch (Exception e) {
            System.out.println("Da co loi");
            e.printStackTrace();
            
        }
    }
    
}
