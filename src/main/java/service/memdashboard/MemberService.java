/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.memdashboard;

import dao.order.OrderDAO;
import dao.customerProfile.CustomerProfileDAOImpl;
import dto.DashboardOverviewDTO;
import dto.OrderHistoryPageDTO;
import java.util.Optional;
import model.CustomerProfile;
import model.Order;
import service.customer.CustomerService;
import service.order.OrderService;
import util.ValidationException;

/**
 *
 * @author ADMIN
 */
public class MemberService {
    
    private final CustomerService customerService; 
    private final OrderService orderService; 
    private final CustomerProfileDAOImpl profileDAO; 
    


    public MemberService() {
        this.customerService = new CustomerService(); 
        this.orderService = new OrderService(); 
        this.profileDAO = new CustomerProfileDAOImpl(); 
    }
    
    /**
     * Lấy dữ liệu tổng hợp cho trang dashboard overview.
     * Phiên bản này xử lý việc DAO trả về null trực tiếp.
     * @param accountId ID của người dùng.
     * @return một DTO chứa tất cả thông tin cần thiết.
     */
    public DashboardOverviewDTO getDashboardOverviewDTO(int accountId){
        DashboardOverviewDTO dto = new DashboardOverviewDTO(); 
        
        //lay profile
        CustomerProfile profile = customerService.viewProfile(accountId); 
        
        
        if (profile != null){
            dto.setProfile(profile);
            dto.setMemberTier(determineMemberTier(profile.getLoyaltyPoints()));
        }else {
            dto.setMemberTier("New Member");
        }
        
        long totalOrders = orderService.countOrdersByAccountId(accountId); 
        dto.setTotalOrders(totalOrders);
        
        Order recentOrder = orderService.getLatestOrder(accountId); 
        if (recentOrder != null){
            dto.setRecentOrder(recentOrder);
        }
        
        return dto; 
        
    }

    private String determineMemberTier(Integer loyaltyPoints) {
        if (loyaltyPoints >= 10000) return "Diamon"; 
        if (loyaltyPoints >=5000) return "Gold"; 
        if (loyaltyPoints >= 1000) return "Silver"; 
        return "New Member";
    }
    
    public CustomerProfile getProfileForEdit(int accountId){
        return profileDAO.findById(accountId); 
    }
    
    /**
     * Ủy quyền việc cập nhật profile cho service chuyên trách.
     */
    
    public void updateMemberProfile(CustomerProfile profile) throws ValidationException {
        customerService.updateProfile(profile); 
    }
    
    public OrderHistoryPageDTO getMemberOrderHistory(int accountId, int page, int pageSize){
        return orderService.getOrderHistoryForCustomer(accountId, page, pageSize); 
    }
    
    public Optional<Order> getMemberOrderDetail(int orderId, int accountId){
        return orderService.getOrderDetailForCustomer(orderId, accountId); 
    }
    
    public boolean cancelMemberOrder(int orderId, int accountId) throws Exception{
        return orderService.cancelOrderForCustomer(orderId, accountId); 
    }
    
}
