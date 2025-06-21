
package util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 *
 * @author ADMIN
 */
public class JPAUtil {
    // EntityManagerFactory giống như một "nhà máy sản xuất" EntityManager.
    // Nó rất tốn tài nguyên để tạo, vì vậy chúng ta chỉ tạo nó một lần duy nhất cho toàn bộ ứng dụng.
    private static final EntityManagerFactory factory;

    // Khối static này sẽ được chạy một lần duy nhất khi lớp được tải
    static {
        try {
            // Đọc file persistence.xml và tạo factory dựa trên persistence-unit-name="CarShopPU"
            factory = Persistence.createEntityManagerFactory("CarShopPU");
        } catch (Throwable ex) {
            System.err.println("Lỗi khởi tạo EntityManagerFactory: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    /**
     * Cung cấp một đối tượng EntityManager để thực hiện các thao tác CSDL.
     * EntityManager là "công nhân" thực hiện công việc, nó nhẹ và được tạo ra cho mỗi tác vụ.
     * @return một đối tượng EntityManager.
     */
    public static EntityManager getEntityManager() {
        return factory.createEntityManager();
    }
}
