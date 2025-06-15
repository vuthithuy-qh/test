package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import model.entity.Students;
import model.entity.StudentInfo;
import org.mindrot.jbcrypt.BCrypt;

public class StudentDAO {

    private final DBContext dbContext;

    public StudentDAO() {
        this.dbContext = DBContext.getInstance();
    }

    /**
     * Kiểm tra xem đã có sinh viên nào trong bảng Student chưa
     */
    public boolean checkIfStudentExists() throws SQLException {
        String sql = "SELECT COUNT(*) FROM Student";
        try (Connection conn = dbContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            System.out.println("StudentDAO: Kiểm tra số lượng sinh viên trong database...");
            if (rs.next()) {
                int count = rs.getInt(1);
                System.out.println("StudentDAO: Số lượng sinh viên: " + count);
                return count > 0;
            }
        } catch (SQLException e) {
            System.err.println("StudentDAO: Lỗi khi kiểm tra sinh viên tồn tại: " + e.getMessage());
            throw e;
        }
        return false;
    }

    /**
     * Tạo sinh viên mặc định với các trường tối thiểu
     */
    public void createDefaultStudent(String studentID, String plainPassword) throws SQLException {
        if (studentID == null || plainPassword == null) {
            throw new IllegalArgumentException("StudentID và password không được null.");
        }
        String sql = "INSERT INTO Student (StudentID, Username, Password, FullName, Email, PhoneNumber) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = dbContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            String hashedPassword = BCrypt.hashpw(plainPassword, BCrypt.gensalt());

            stmt.setString(1, studentID);
            stmt.setString(2, studentID + "_user");
            stmt.setString(3, hashedPassword);
            stmt.setString(4, "Sinh Vien Mac Dinh");
            stmt.setString(5, studentID + "@example.com");
            stmt.setString(6, "0900000000");

            int rowsAffected = stmt.executeUpdate();
            System.out.println("StudentDAO: Đã tạo sinh viên mặc định, rows affected: " + rowsAffected);

            if (rowsAffected == 0) {
                throw new SQLException("Không thể chèn tài khoản sinh viên mặc định.");
            }
        } catch (SQLException e) {
            System.err.println("StudentDAO: Lỗi khi tạo sinh viên mặc định: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Tạo sinh viên và thông tin chi tiết (StudentInfo) cùng lúc
     */
    public void createStudentWithInfo(Students student, StudentInfo studentInfo) throws SQLException {
        if (student == null || student.getStudentID() == null || student.getUsername() == null || student.getPassword() == null ||
            studentInfo == null || studentInfo.getStudentID() == null) {
            throw new IllegalArgumentException("Student, student ID, username, password hoặc studentInfo không được null.");
        }

        Connection conn = null;
        try {
            conn = dbContext.getConnection();
            conn.setAutoCommit(false);

            // Thêm vào bảng Student
            String sqlStudent = "INSERT INTO Student (StudentID, Username, Password, FullName, Email, PhoneNumber) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sqlStudent)) {
                String password = student.getPassword();
                if (password != null && !password.startsWith("$2a$") && !password.startsWith("$2b$")) {
                    password = BCrypt.hashpw(password, BCrypt.gensalt());
                }
                student.setPassword(password);

                stmt.setString(1, student.getStudentID());
                stmt.setString(2, student.getUsername());
                stmt.setString(3, student.getPassword());
                stmt.setString(4, student.getFullName());
                stmt.setString(5, student.getEmail());
                stmt.setString(6, student.getPhoneNumber());

                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected == 0) {
                    throw new SQLException("Không thể tạo sinh viên.");
                }
            }

            // Thêm vào bảng StudentInfo
            String sqlStudentInfo = "INSERT INTO StudentInfo (StudentID, FirstName, LastName, Gender, DateOfBirth, PhoneNumber, Email, CCCD, Address, AdminID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sqlStudentInfo)) {
                stmt.setString(1, studentInfo.getStudentID());
                stmt.setString(2, studentInfo.getFirstName());
                stmt.setString(3, studentInfo.getLastName());
                stmt.setString(4, studentInfo.getGender());
                stmt.setDate(5, studentInfo.getDateOfBirth());
                stmt.setString(6, studentInfo.getPhoneNumber());
                stmt.setString(7, studentInfo.getEmail());
                stmt.setString(8, studentInfo.getCccd());
                stmt.setString(9, studentInfo.getAddress());
                stmt.setInt(10, studentInfo.getAdminID() != null ? studentInfo.getAdminID() : null);

                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected == 0) {
                    throw new SQLException("Không thể tạo thông tin sinh viên.");
                }
            }

            conn.commit();
            System.out.println("StudentDAO: Đã tạo sinh viên và thông tin chi tiết thành công, StudentID: " + student.getStudentID());
        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback();
            }
            System.err.println("StudentDAO: Lỗi khi tạo sinh viên và thông tin chi tiết: " + e.getMessage());
            throw e;
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }

    /**
     * Tìm sinh viên dựa trên email hoặc student ID
     */
    public Students findStudentByEmailOrStudentID(String emailOrStudentID) throws SQLException {
        if (emailOrStudentID == null) {
            throw new IllegalArgumentException("Email hoặc student ID không được null.");
        }
        String sql = "SELECT * FROM Student WHERE Email = ? OR StudentID = ?";
        try (Connection conn = dbContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, emailOrStudentID);
            stmt.setString(2, emailOrStudentID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Students student = new Students();
                    student.setStudentID(rs.getString("StudentID"));
                    student.setUsername(rs.getString("Username"));
                    student.setPassword(rs.getString("Password"));
                    student.setFullName(rs.getString("FullName"));
                    student.setEmail(rs.getString("Email"));
                    student.setPhoneNumber(rs.getString("PhoneNumber"));
                    System.out.println("StudentDAO: Tìm thấy sinh viên: " + student.getStudentID());
                    return student;
                }
            }
        } catch (SQLException e) {
            System.err.println("StudentDAO: Lỗi khi tìm sinh viên: " + e.getMessage());
            throw e;
        }
        System.out.println("StudentDAO: Không tìm thấy sinh viên với email/student ID: " + emailOrStudentID);
        return null;
    }

    /**
     * Lấy thông tin chi tiết của sinh viên dựa trên StudentID
     */
    public StudentInfo getStudentInfoByStudentID(String studentID) throws SQLException {
        if (studentID == null) {
            throw new IllegalArgumentException("studentID không hợp lệ.");
        }
        String sql = "SELECT * FROM StudentInfo WHERE StudentID = ?";
        try (Connection conn = dbContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, studentID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    StudentInfo studentInfo = new StudentInfo();
                    studentInfo.setStudentID(rs.getString("StudentID"));
                    studentInfo.setFirstName(rs.getString("FirstName"));
                    studentInfo.setLastName(rs.getString("LastName"));
                    studentInfo.setFullName(rs.getString("Fullname")); // Computed column
                    studentInfo.setGender(rs.getString("Gender"));
                    studentInfo.setDateOfBirth(rs.getDate("DateOfBirth"));
                    studentInfo.setPhoneNumber(rs.getString("PhoneNumber"));
                    studentInfo.setEmail(rs.getString("Email"));
                    studentInfo.setCccd(rs.getString("CCCD"));
                    studentInfo.setAddress(rs.getString("Address"));
                    studentInfo.setAdminID(rs.getInt("AdminID"));
                    return studentInfo;
                }
            }
        }
        return null;
    }

    /**
     * Cập nhật thông tin sinh viên
     */
    public void updateStudent(Students student) throws SQLException {
        if (student == null || student.getStudentID() == null) {
            throw new IllegalArgumentException("Student hoặc studentID không hợp lệ.");
        }
        String sql = "UPDATE Student SET Username = ?, Password = ?, FullName = ?, Email = ?, PhoneNumber = ? WHERE StudentID = ?";
        try (Connection conn = dbContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, student.getUsername());
            String password = student.getPassword();
            if (password != null && !password.startsWith("$2a$") && !password.startsWith("$2b$")) {
                password = BCrypt.hashpw(password, BCrypt.gensalt());
            }
            student.setPassword(password);
            stmt.setString(2, student.getPassword());
            stmt.setString(3, student.getFullName());
            stmt.setString(4, student.getEmail());
            stmt.setString(5, student.getPhoneNumber());
            stmt.setString(6, student.getStudentID());

            int rows = stmt.executeUpdate();
            if (rows == 0) {
                throw new SQLException("Không tìm thấy sinh viên để cập nhật.");
            }
            System.out.println("StudentDAO: Cập nhật sinh viên thành công, rows affected: " + rows);
        } catch (SQLException e) {
            System.err.println("StudentDAO: Lỗi khi cập nhật sinh viên: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Cập nhật thông tin chi tiết của sinh viên
     */
    public void updateStudentInfo(StudentInfo studentInfo) throws SQLException {
        if (studentInfo == null || studentInfo.getStudentID() == null) {
            throw new IllegalArgumentException("StudentInfo hoặc studentID không hợp lệ.");
        }
        String sql = "UPDATE StudentInfo SET FirstName = ?, LastName = ?, Gender = ?, DateOfBirth = ?, PhoneNumber = ?, Email = ?, CCCD = ?, Address = ?, AdminID = ? WHERE StudentID = ?";
        try (Connection conn = dbContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, studentInfo.getFirstName());
            stmt.setString(2, studentInfo.getLastName());
            stmt.setString(3, studentInfo.getGender());
            stmt.setDate(4, studentInfo.getDateOfBirth());
            stmt.setString(5, studentInfo.getPhoneNumber());
            stmt.setString(6, studentInfo.getEmail());
            stmt.setString(7, studentInfo.getCccd());
            stmt.setString(8, studentInfo.getAddress());
            stmt.setInt(9, studentInfo.getAdminID() != null ? studentInfo.getAdminID() : null);
            stmt.setString(10, studentInfo.getStudentID());

            int rows = stmt.executeUpdate();
            if (rows == 0) {
                throw new SQLException("Không tìm thấy thông tin sinh viên để cập nhật.");
            }
            System.out.println("StudentDAO: Cập nhật thông tin sinh viên thành công, rows affected: " + rows);
        } catch (SQLException e) {
            System.err.println("StudentDAO: Lỗi khi cập nhật thông tin sinh viên: " + e.getMessage());
            throw e;
        }
    }
}