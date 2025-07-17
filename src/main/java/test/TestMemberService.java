/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package test;

import dto.OrderHistoryPageDTO;
import java.lang.reflect.Member;
import model.Order;
import service.memdashboard.MemberService;

/**
 *
 * @author ADMIN
 */
public class TestMemberService {
    public static void main(String[] args) {
        System.out.println("---Bat dau test MemberService---");
        MemberService service = new MemberService(); 
        
        int customerIdToTest = 1; 
        int page = 1; 
        int pageSize = 5; 
        
        try {
            System.out.println("Service test: dang goi getMemberOrderHistory cho account ID:" + customerIdToTest);
            OrderHistoryPageDTO pageData = service.getMemberOrderHistory(customerIdToTest, page, pageSize); 
            
            System.out.println(" Ket qua");
            if(pageData == null){
                System.out.println("Loi null");
                        
            }else if (pageData.getOrders().isEmpty()) {
                System.out.println("Thah cong nhung khong tim thay don hang nao");
                
            }else {
                System.out.println("Thanh cong, da tim thay don hang " + pageData.getOrders().size());
                System.out.println("Trang hien tai " + pageData.getCurrentPage() + "/ Tong so trang: " + pageData.getTotalPages()) ;
                for (Order order : pageData.getOrders()) {
                    System.out.println(""
                            + "Order ID: " + order.getId());
                    
                    
                }
            }
            
        } catch (Exception e) {
            System.out.println("Da co loi xay trong qua trinh test service");
            e.printStackTrace();
        }
        
        //    <img src="${detail.car.imageUrl}" alt="${detail.car.carModel.name}" width="80" style="margin-right: 15px"/>
    }
    
}
