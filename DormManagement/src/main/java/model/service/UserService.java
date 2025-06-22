package model.service;

import model.dao.StudentDAO;
import model.entity.Students;
import java.sql.SQLException;
import java.sql.Date;
import java.util.Calendar;

public class UserService {
    private StudentDAO studentDAO;
    
    public UserService() {
        studentDAO = new StudentDAO();
    }
    
    public boolean registerUser(String username, String email, String password, String fullName, String studentCode, String cccd) throws SQLException {
        // Kiểm tra xem username hoặc email đã tồn tại chưa
        if (studentDAO.checkUserExists(username, email)) {
            return false;
        }
        
        // Tạo đối tượng Students mới
        Students student = new Students();
        student.setUsername(username);
        student.setEmail(email);
        student.setPassword(password);
        student.setFullName(fullName);
        student.setStudentCode(studentCode);
        student.setCccd(cccd);
        
        // Set ngày tạo
        Calendar cal = Calendar.getInstance();
        student.setCreatedAt(new Date(cal.getTimeInMillis()));
        student.setUpdatedAt(new Date(cal.getTimeInMillis()));
        
        // Set trạng thái mặc định
        student.setStatusRoom("Chưa có phòng");
        
        // Lưu vào database
        return studentDAO.insertStudent(student);
    }
    
    public boolean checkEmailFormat(String email) {
        // Kiểm tra định dạng email
        if (email == null || !email.contains("@")) {
            return false;
        }
        
        // Kiểm tra email phải kết thúc bằng @gmail.com hoặc @fpt.edu.vn
        return email.endsWith("@gmail.com") || email.endsWith("@fpt.edu.vn");
    }
    
    public boolean validateStudentCode(String studentCode) {
        // Kiểm tra mã sinh viên (có thể có cả chữ và số, độ dài 6-12 ký tự)
        if (studentCode == null || studentCode.trim().isEmpty()) {
            return false;
        }
        // Chấp nhận chữ cái, số và dấu gạch ngang, độ dài 6-12 ký tự
        return studentCode.matches("^[A-Za-z0-9-]{6,12}$");
    }
    
    public boolean validateCCCD(String cccd) {
        // Kiểm tra CCCD (phải có đúng 12 số)
        if (cccd == null || cccd.trim().isEmpty()) {
            return false;
        }
        return cccd.matches("\\d{12}");
    }
} 