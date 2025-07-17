package model;

import java.io.Serializable;
import java.util.Objects;

public class CartItemId implements Serializable {

    private Integer cart;
    private Integer car;

    public CartItemId() {
    }

    public CartItemId(Integer cart, Integer car) {
        this.cart = cart;
        this.car = car;
    }

    // Bắt buộc có getter/setter public

    public Integer getCart() {
        return cart;
    }

    public void setCart(Integer cart) {
        this.cart = cart;
    }

    public Integer getCar() {
        return car;
    }

    public void setCar(Integer car) {
        this.car = car;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CartItemId)) return false;
        CartItemId that = (CartItemId) o;
        return Objects.equals(cart, that.cart) && Objects.equals(car, that.car);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cart, car);
    }
}
