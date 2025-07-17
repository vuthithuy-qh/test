package util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import model.Car;

public class CarValidation {

    public static Map<String, String> validateCar(Car car, String action) {
        Map<String, String> errors = new HashMap<>();

        if (car == null) {
            errors.put("general", "Car data is missing.");
            return errors;
        }

        // Kiểm tra khi cập nhật
        if (action.equalsIgnoreCase("update")) {
            if (car.getId() == 0) {
                errors.put("id", "Car ID is required for update.");
                return errors;
            }

            if (!isCarExist(null, car.getId())) {
                errors.put("id", "Car with this ID does not exist.");
                return errors;
            }
        }

        // Kiểm tra VIN
        if (car.getVin() == null || car.getVin().trim().isEmpty()) {
            errors.put("vin", "VIN must not be empty.");
        } else if (action.equalsIgnoreCase("create") && isVinExists(car.getVin(), 0)) {
            errors.put("vin", "VIN already exists.");
        } else if (action.equalsIgnoreCase("update") && isVinExists(car.getVin(), car.getId())) {
            errors.put("vin", "VIN already exists.");
        }

        // Giá nhập
        if (car.getImportPrice() == null || car.getImportPrice().compareTo(BigDecimal.ZERO) <= 0) {
            errors.put("importPrice", "Import price must be greater than 0.");
        }

        // Giá bán
        if (car.getSellingPrice() == null || car.getSellingPrice().compareTo(BigDecimal.ZERO) <= 0) {
            errors.put("sellingPrice", "Selling price must be greater than 0.");
        }

        // Ngày nhập
        if (car.getImportDate() == null) {
            errors.put("importDate", "Import date must not be null.");
        } else if (car.getImportDate().after(new Date())) {
            errors.put("importDate", "Import date cannot be in the future.");
        }

        // Trạng thái
        if (car.getStatus() == null) {
            errors.put("status", "Car status must be selected.");
        }

        // Model xe
        if (car.getCarModel() == null || car.getCarModel().getId() == 0) {
            errors.put("carModel", "Car model must be selected.");
        }

        return errors;
    }

    // Kiểm tra VIN đã tồn tại chưa (loại trừ theo ID)
    private static boolean isVinExists(String vin, int excludeId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            String jpql = "SELECT COUNT(c) FROM Car c WHERE c.vin = :vin";
            if (excludeId > 0) {
                jpql += " AND c.id <> :excludeId";
            }

            TypedQuery<Long> query = em.createQuery(jpql, Long.class);
            query.setParameter("vin", vin);
            if (excludeId > 0) {
                query.setParameter("excludeId", excludeId);
            }

            return query.getSingleResult() > 0;
        } finally {
            em.close();
        }
    }

    // Kiểm tra sự tồn tại của xe theo VIN hoặc ID
    public static boolean isCarExist(String vin, int carId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            String jpql = "SELECT COUNT(c) FROM Car c WHERE 1=1";
            if (vin != null) jpql += " AND c.vin = :vin";
            if (carId > 0) jpql += " AND c.id = :id";

            TypedQuery<Long> query = em.createQuery(jpql, Long.class);
            if (vin != null) query.setParameter("vin", vin);
            if (carId > 0) query.setParameter("id", carId);

            return query.getSingleResult() > 0;
        } finally {
            em.close();
        }
    }
}
