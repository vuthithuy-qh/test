/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import model.CustomerProfile;
import model.Order;

/**
 *
 * @author ADMIN
 */
public class DashboardOverviewDTO {
    private CustomerProfile profile; 
    
    private long totalOrders; 
    
    private Order recentOrder; 
    
    private String memberTier; 

    public DashboardOverviewDTO() {
    }

    public CustomerProfile getProfile() {
        return profile;
    }

    public void setProfile(CustomerProfile profile) {
        this.profile = profile;
    }

    public long getTotalOrders() {
        return totalOrders;
    }

    public void setTotalOrders(long totalOrders) {
        this.totalOrders = totalOrders;
    }

    public Order getRecentOrder() {
        return recentOrder;
    }

    public void setRecentOrder(Order recentOrder) {
        this.recentOrder = recentOrder;
    }

    public String getMemberTier() {
        return memberTier;
    }

    public void setMemberTier(String memberTier) {
        this.memberTier = memberTier;
    }

    
    
    
    
}
