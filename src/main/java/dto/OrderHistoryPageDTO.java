/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import java.io.Serializable;
import java.util.List;
import model.Order;

/**
 *
 * @author ADMIN
 */

/**
 * Data Transfer Object (DTO) dùng để gói dữ liệu cho trang lịch sử đơn hàng có phân trang.
 * Nó chứa cả danh sách đơn hàng cho trang hiện tại và thông tin về các trang.
 */
public class OrderHistoryPageDTO implements Serializable{
    
    private List<Order> orders; 
    
    private int currentPage; 
    /**
     * Tổng số trang có thể có.
     * Được tính toán ở tầng Service.
     */
    private int totalPages; 
    private long totalOrders; 

    public OrderHistoryPageDTO() {
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    

    public long getTotalOrders() {
        return totalOrders;
    }

    public void setTotalOrders(long totalOrders) {
        this.totalOrders = totalOrders;
    }
    
    
    
    
}
