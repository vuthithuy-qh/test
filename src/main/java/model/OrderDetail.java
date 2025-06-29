/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author ADMIN
 */
@Entity
@Table(name ="order_detail")
public class OrderDetail implements Serializable{
    
    @EmbeddedId
    private OrderDetailId id; 
    
    // quan he nhieu 1 toi order
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("orderId")  // anh xa thuoc tinh "orderId" trong lop OrderDetailId
    @JoinColumn(name = "order_id")
    private Order order; 
    
    // quan he nhieu 1 toi car
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("carId")
    @JoinColumn(name ="car_id")
    private Car car; 
    
    @Column(name ="single_price", precision = 15, scale = 2)
    private BigDecimal singlePrice; 
    
    @Column(name ="discount", precision = 5, scale = 2)
    private BigDecimal discount; 

    public OrderDetailId getId() {
        return id;
    }

    public void setId(OrderDetailId id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public BigDecimal getSinglePrice() {
        return singlePrice;
    }

    public void setSinglePrice(BigDecimal singlePrice) {
        this.singlePrice = singlePrice;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }
    
    
}
