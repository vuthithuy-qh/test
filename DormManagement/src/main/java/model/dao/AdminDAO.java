package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.entity.Admins;
import org.mindrot.jbcrypt.BCrypt;

public class AdminDAO {

    private final DBContext dbContext;

    public AdminDAO() {
        this.dbContext = DBContext.getInstance();
    }

    /**
     * Kiểm tra xem đã có admin nào trong bảng Admin chưa
     */
    public boolean checkIfAdminExists() throws SQLException {
        String sql = "SELECT COUNT(*) FROM Admin";
        try (Connection conn = dbContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            System.out.println("AdminDAO: Kiểm tra số lượng admin trong database...");
            if (rs.next()) {
                int count = rs.getInt(1);
                System.out.println("AdminDAO: Số lượng admin: " + count);
                return count > 0;
            }
        } catch (SQLException e) {
            System.err.println("AdminDAO: Lỗi khi kiểm tra admin tồn tại: " + e.getMessage());
            throw e;
        }
        return false;
    }

    /**
     * Tạo admin mặc định với các trường tối thiểu
     */
    public void createDefaultAdmin(String username, String plainPassword) throws SQLException {
        if (username == null || plainPassword == null) {
            throw new IllegalArgumentException("Username và password không được null.");
        }
        String sql = "INSERT INTO Admin (Username, APassword, FullName, Email, PhoneNumber, ImageProfile) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = dbContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            String hashedPassword = BCrypt.hashpw(plainPassword, BCrypt.gensalt());

            stmt.setString(1, username);
            stmt.setString(2, hashedPassword);
            stmt.setString(3, "Admin Mặc Định");
            stmt.setString(4, username + "@example.com");
            stmt.setString(5, "0900000000");
            stmt.setString(6, null);

            int rowsAffected = stmt.executeUpdate();
            System.out.println("AdminDAO: Đã tạo admin mặc định, rows affected: " + rowsAffected);

            if (rowsAffected == 0) {
                throw new SQLException("Không thể chèn tài khoản admin mặc định.");
            }
        } catch (SQLException e) {
            System.err.println("AdminDAO: Lỗi khi tạo admin mặc định: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Tìm admin dựa trên email hoặc username
     */
    public Admins findAdminByEmailOrUsername(String emailOrUsername) throws SQLException {
        if (emailOrUsername == null) {
            throw new IllegalArgumentException("Email hoặc username không được null.");
        }
        String sql = "SELECT * FROM Admin WHERE Email = ? OR Username = ?";
        try (Connection conn = dbContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, emailOrUsername);
            stmt.setString(2, emailOrUsername);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Admins admin = new Admins();
                    admin.setAdminID(rs.getInt("AdminID"));
                    admin.setUsername(rs.getString("Username"));
                    admin.setaPassword(rs.getString("APassword"));
                    admin.setFullName(rs.getString("FullName"));
                    admin.setEmail(rs.getString("Email"));
                    admin.setPhoneNumber(rs.getString("PhoneNumber"));
                    admin.setImageProfile(rs.getString("ImageProfile"));
                    System.out.println("AdminDAO: Tìm thấy admin: " + admin.getUsername());
                    return admin;
                }
            }
        } catch (SQLException e) {
            System.err.println("AdminDAO: Lỗi khi tìm admin: " + e.getMessage());
            throw e;
        }
        System.out.println("AdminDAO: Không tìm thấy admin với email/username: " + emailOrUsername);
        return null;
    }

    /**
     * Tạo tài khoản admin mới
     */
    public int createAdmin(Admins admin) throws SQLException {
        if (admin == null || admin.getUsername() == null || admin.getAPassword() == null) {
            throw new IllegalArgumentException("Admin, username hoặc password không được null.");
        }
        String sql = "INSERT INTO Admin (Username, APassword, FullName, Email, PhoneNumber, ImageProfile) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = dbContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            String password = admin.getAPassword();
            if (password != null && !password.startsWith("$2a$") && !password.startsWith("$2b$")) {
                password = BCrypt.hashpw(password, BCrypt.gensalt());
            }
            admin.setaPassword(password);

            stmt.setString(1, admin.getUsername());
            stmt.setString(2, admin.getAPassword());
            stmt.setString(3, admin.getFullName());
            stmt.setString(4, admin.getEmail());
            stmt.setString(5, admin.getPhoneNumber());
            stmt.setString(6, admin.getImageProfile());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        admin.setAdminID(rs.getInt(1));
                        System.out.println("AdminDAO: Đã tạo admin mới, ID: " + rs.getInt(1));
                        return rs.getInt(1);
                    }
                }
            } else {
                throw new SQLException("Không thể tạo admin mới.");
            }
        } catch (SQLException e) {
            System.err.println("AdminDAO: Lỗi khi tạo admin mới: " + e.getMessage());
            throw e;
        }
        return -1;
    }

    /**
     * Cập nhật thông tin admin
     */
    public void updateAdmin(Admins admin) throws SQLException {
        if (admin == null || admin.getAdminID() <= 0) {
            throw new IllegalArgumentException("Admin hoặc adminID không hợp lệ.");
        }
        String sql = "UPDATE Admin SET Username = ?, APassword = ?, FullName = ?, Email = ?, PhoneNumber = ?, ImageProfile = ? WHERE AdminID = ?";
        try (Connection conn = dbContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, admin.getUsername());
            String password = admin.getAPassword();
            if (password != null && !password.startsWith("$2a$") && !password.startsWith("$2b$")) {
                password = BCrypt.hashpw(password, BCrypt.gensalt());
            }
            admin.setaPassword(password);
            stmt.setString(2, admin.getAPassword());
            stmt.setString(3, admin.getFullName());
            stmt.setString(4, admin.getEmail());
            stmt.setString(5, admin.getPhoneNumber());
            stmt.setString(6, admin.getImageProfile());
            stmt.setInt(7, admin.getAdminID());

            int rows = stmt.executeUpdate();
            if (rows == 0) {
                throw new SQLException("Không tìm thấy admin để cập nhật.");
            }
            System.out.println("AdminDAO: Cập nhật admin thành công, rows affected: " + rows);
        } catch (SQLException e) {
            System.err.println("AdminDAO: Lỗi khi cập nhật admin: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Lấy thông tin admin theo ID
     */
    public Admins getAdminById(int adminID) throws SQLException {
        if (adminID <= 0) {
            throw new IllegalArgumentException("adminID không hợp lệ.");
        }
        String sql = "SELECT * FROM Admin WHERE AdminID = ?";
        try (Connection conn = dbContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, adminID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Admins admin = new Admins();
                    admin.setAdminID(rs.getInt("AdminID"));
                    admin.setUsername(rs.getString("Username"));
                    admin.setaPassword(rs.getString("APassword"));
                    admin.setFullName(rs.getString("FullName"));
                    admin.setEmail(rs.getString("Email"));
                    admin.setPhoneNumber(rs.getString("PhoneNumber"));
                    admin.setImageProfile(rs.getString("ImageProfile"));
                    return admin;
                }
            }
        }
        return null;
    }
}