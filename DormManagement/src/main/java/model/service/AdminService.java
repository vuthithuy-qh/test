package model.service;

import java.sql.Date;
import java.sql.SQLException;
import model.dao.AdminDAO;
import model.entity.Admin;
import org.mindrot.jbcrypt.BCrypt;

public class AdminService {

    private final AdminDAO adminDAO;

    public AdminService() {
        this.adminDAO = new AdminDAO();
    }

    public Admin authenticateAdmin(String emailOrUsername, String password) throws SQLException {
        Admin admin = adminDAO.findAdminByEmailOrUsername(emailOrUsername);
        if (admin != null && BCrypt.checkpw(password, admin.getPassword())) {
            return admin;
        }
        return null;
    }

    public boolean createAdmin(Admin admin) throws SQLException {
        if (admin.getPassword() == null || admin.getPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("Mật khẩu không được để trống.");
        }

        admin.setCreatedAt(new Date(System.currentTimeMillis()));
        admin.setUpdatedAt(new Date(System.currentTimeMillis()));

        int result = adminDAO.createAdmin(admin);
        return result > 0;
    }

    public boolean checkIfAdminExists() throws SQLException {
        return adminDAO.checkIfAdminExists();
    }

    public Admin getAdminById(int adminId) throws SQLException {
        return adminDAO.getAdminById(adminId);
    }
}
