/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author ADMIN
 */

@Embeddable   // danh dau lop nay la 1 thanh phan co the nhung vao Entity
public class OrderDetailId implements Serializable{
    @Column(name = "order_id")
    private int orderId;
    
    @Column(name ="car_id")
    private int carId; 

    public OrderDetailId() {
    }

    public OrderDetailId(int orderId, int carId) {
        this.orderId = orderId;
        this.carId = carId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    @Override
    public int hashCode() {
       return Objects.hash(orderId, carId); 
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final OrderDetailId other = (OrderDetailId) obj;
   
        return this.carId == other.carId && this.orderId == other.orderId;
    }
    
    
    
}
