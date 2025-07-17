package model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "order_detail")
public class OrderDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    /*────────────── 1.  Khóa chính tổng hợp ──────────────*/
    @EmbeddedId
    private OrderDetailId id = new OrderDetailId();   // KHỞI TẠO SỚM

    /*────────────── 2.  Quan hệ với Order ───────────────*/
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("orderId")                                // ánh xạ tới field orderId trong id
    @JoinColumn(name = "order_id")
    private Order order;

    /*────────────── 3.  Quan hệ với Car ────────────────*/
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("carId")                                  // ánh xạ tới field carId trong id
    @JoinColumn(name = "car_id")
    private Car car;

    /*────────────── 4.  Thuộc tính chi tiết ─────────────*/
    @Column(name = "single_price", precision = 15, scale = 2, nullable = false)
    private BigDecimal singlePrice;

    @Column(name = "discount", precision = 5, scale = 2, nullable = false)
    private BigDecimal discount = BigDecimal.ZERO;

    /*──────────────────── 5.  Constructors ────────────────────*/
    public OrderDetail() {}

    public OrderDetail(Order order, Car car,
                       BigDecimal singlePrice, BigDecimal discount) {
        setOrder(order);
        setCar(car);
        this.singlePrice = singlePrice;
        this.discount    = discount;
    }

    /*──────────────────── 6.  Getter / Setter ─────────────────*/
    public OrderDetailId getId() {
        return id;
    }

    /** Không khuyến khích setId thủ công; để Hibernate lo.
        Nếu vẫn cần, hãy đảm bảo đồng bộ cả order/car. */
    public void setId(OrderDetailId id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    /** Tự đồng bộ orderId vào composite key */
    public void setOrder(Order order) {
        this.order = order;
        if (order != null) {
            this.id.setOrderId(order.getId());
        }
    }

    public Car getCar() {
        return car;
    }

    /** Tự đồng bộ carId vào composite key */
    public void setCar(Car car) {
        this.car = car;
        if (car != null) {
            this.id.setCarId(car.getId());
        }
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

    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderDetail)) return false;
        OrderDetail that = (OrderDetail) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
