/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.order;

import dao.car.CarDAO;
import dao.car.CarStatus;
import dao.order.OrderDAO;
import dto.OrderHistoryPageDTO;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import model.Account;
import model.Billing;
import model.Car;
import model.Order;
import model.OrderDetail;
import model.OrderStatus;
import model.PaymentMethod;
import model.PaymentStatus;
import util.JPAUtil;
import util.ValidationException;

/**
 *
 * @author ADMIN
 */
public class OrderService {
    private final OrderDAO orderDAO; 
    private final CarDAO carDAO; 

    public OrderService() {
        this.orderDAO = new OrderDAO();
        this.carDAO = new CarDAO();
    }
    
    public Order createOrder (Account customer, List<Integer> carIds) throws ValidationException{
        if (carIds == null || carIds.isEmpty()){
            throw new ValidationException("Your cart is empty", null); 
        }
        
        //goi carDAO de lay thong tin va kiem tra cac xe xem co hop le hay khong
        List<Car> carsInCart = carDAO.findCarsByIds(carIds); 
        if(carsInCart.size() != carIds.size()){
            //TH 1 ID xe trong gio hang khong co trong database
            throw new ValidationException("One or more cars in cart is not valid", null); 
        }
        
        //b3: kiem tra logic nghiep vu(xe phai co san) va tinh tong tien
        BigDecimal totalAmount = BigDecimal.ZERO; 
        for (Car car : carsInCart) {
            if(car.getStatus() != CarStatus.AVAILABLE){
                throw new ValidationException("Sorry, the car is " + car.getCarModel().getName() + " (VIN: " + car.getVin() + " ) " + car.getStatus(), null);    
            }
            totalAmount= totalAmount.add(car.getSellingPrice()); 
        }  
            //b4: tao doi tuong Entity de chuan bi luu
            Order order = new Order(); 
            order.setCustomer(customer);
            order.setSaleDate(LocalDate.now()); // ngay tao don hang la ngay hien tai
            order.setStatus(OrderStatus.PROCCESSING);
            order.setOrderDetails(new ArrayList<>());  //khoi tao 1 danh sach chi tiet
            
            Billing billing = new Billing(); 
            billing.setOrder(order);
            billing.setAmount(totalAmount);
            billing.setPaymentStatus(PaymentStatus.PENDING);//mac dinh
            billing.setPaymentMethod(PaymentMethod.CASH); //mac dinh
            billing.setPaymentDate(LocalDate.now());
            order.setBilling(billing);
            
            for (Car car : carsInCart) {
                OrderDetail detail  = new OrderDetail(); 
                detail.setCar(car);
                detail.setOrder(order);
                detail.setSinglePrice(car.getSellingPrice());
                
                //Vi order chua dc luu nen chua co id, ta se thiet lap quan he 2 chieu
                //va de CascadeType.ALL cua JPA tu xu li
                order.getOrderDetails().add(detail); 
                
                //cap nhat trang thai cua xe
                car.setStatus(CarStatus.SOLD);
                
            }
            
            //b5: goi DAO de luu don hang vao DB
            orderDAO.save(order);
            
            //b6: cap nhat trang thai cu xe da ban
            for (Car car : carsInCart) {
                carDAO.update(car); 
            }
        
        return order; 
    }
    
    /**
 * Lấy một trang trong lịch sử mua hàng của khách hàng.
 * @param accountId ID của tài khoản khách hàng đang đăng nhập.
 * @param page Số trang hiện tại mà người dùng muốn xem (bắt đầu từ 1).
 * @param pageSize Số lượng đơn hàng hiển thị trên mỗi trang.
 * @return Một đối tượng DTO chứa danh sách đơn hàng và thông tin phân trang.
 */
    public OrderHistoryPageDTO getOrderHistoryForCustomer(int accountId, int page, int pageSize){
        if (pageSize < 1){
            page = 1; 
        }
        
        if(pageSize <= 0){
            pageSize = 10; 
        }
        
        //goi DAO de lay tong danh sach don hang cho trang hien tai
        List<Order> ordersOnCurrentPage = orderDAO.findAllByAccountIdWithPagnination(accountId, page, pageSize);
        //Goi dao de lay tong so don hang khach hang 
        long totalOrders = orderDAO.countOrdersByAccountId(accountId);
        //tinh toan tong so trang can co
        int totalPages =(int) Math.ceil((double)totalOrders/pageSize);
        //dong goi tat ca cac thong tin de tra ve controller
        OrderHistoryPageDTO dto = new OrderHistoryPageDTO(); 
        dto.setOrders(ordersOnCurrentPage);
        dto.setCurrentPage(page);
        dto.setTotalPages(totalPages);
        dto.setTotalOrders(totalOrders);
        
        return dto; 
    }
    
    
    public Optional<Order> getOrderDetailForCustomer(int orderId, int accountId){
        //Goi DAO de tim don hang
        Optional<Order> orderOpt = orderDAO.findById(orderId); 
        
        if(orderOpt.isPresent()){
            Order order = orderOpt.get(); 
            
            //so sanh ID cua hang hang so huu don hang voi ID cua nguoi dang yeu cau
            if(order.getCustomer().getId() == accountId){
                return orderOpt; 
            }
            
        }
        
        return Optional.empty(); 
    }
    
    
    public boolean cancelOrderForCustomer(int orderId, int accountId) throws Exception{
        EntityManager em = JPAUtil.getEntityManager(); 
        
        try {
            em.getTransaction().begin();
            
            Order order = em.find(Order.class, orderId); 
            //1.Kiem tra don hang co ton tai va dung chu so huu hay khong
            if(order == null || order.getCustomer().getId() != accountId){
                em.getTransaction().rollback();
                return false; 
            }
            
            // kiem tra trang thai don hang co cho phep huy hay khong
            if(order.getStatus() != OrderStatus.PROCCESSING && order.getStatus() != OrderStatus.PENDING){
                em.getTransaction().rollback();
                return false; 
            }
            
            //moi thu hop le
            //1 doi trang thai don hang
            
            order.setStatus(OrderStatus.CANCELLED);
            
            //2. hoan tra san phan ve kho(cap nhat trang thai cua xe)
            
            for (OrderDetail orderDetail : order.getOrderDetails()) {
                Car car = orderDetail.getCar(); 
                
                if (car != null){
                    car.setStatus(CarStatus.AVAILABLE);
                    em.merge(car) ; // cap nhat lai xe
                }
                
            }
            
            em.merge(order); //cap nhat lai don hang
            em.getTransaction().commit();
            return true; 
            
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e; // nem lai exception de tang tren xu li
        }finally{
            if(em.isOpen()){
                em.close();
            }
        }
    }
    
    
    
    public long countOrdersByAccountId(int accountId){
        return orderDAO.countOrdersByAccountId(accountId); 
    }
    
    public Order getLatestOrder(int accountId){
        return orderDAO.findLastestOrderByAccountId(accountId); 
    }
    
     
}
