/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import jakarta.enterprise.concurrent.Asynchronous;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.User;

public class UserDAO extends DBConnection {

    public User FindUserByUserName(String userName) {

        String sql = "SELECT * FROM users WHERE username = ?";
        try {
//            PreparedStatement stmt =DBConnection..prepareStatement(sql);
            PreparedStatement stmt = DBConnection.DBConnection().prepareStatement(sql);
            stmt.setString(1, userName);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                User u = new User();
                u.setId(rs.getInt("id"));
                u.setUsername(rs.getString("username"));
                u.setPassword(rs.getString("password"));
                u.setEmail(rs.getString("email"));
                u.setRole(rs.getString("role"));
                u.setCreatedAt(rs.getString("created_at"));
                return u;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null; // không tìm thấy
    }

    public boolean Login(String userName, String passWord) {
        String sql = "SELECT * FROM account WHERE username = ? AND password_hash = ?";
        try {
            PreparedStatement stmt = DBConnection.DBConnection().prepareStatement(sql);
            stmt.setString(1, userName);
            stmt.setString(2, passWord);
            ResultSet rs = stmt.executeQuery();

            return rs.next(); // nếu có bản ghi -> đăng nhập thành công

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false; // lỗi hoặc không đúng thông tin
    }

    public String getRole(String userName) {
        String sql = "SELECT role FROM account WHERE username = ?";
        try {
//        PreparedStatement stmt = connection.prepareStatement(sql);
            PreparedStatement stmt = DBConnection.DBConnection().prepareStatement(sql);
            stmt.setString(1, userName);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getString("role");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null; // không tìm thấy user hoặc lỗi
    }

    public boolean signUp(String email, String userName, String passWord) {
        String sql = "INSERT INTO account (email, username, password_hash) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = DBConnection.DBConnection().prepareStatement(sql);) {
            stmt.setString(1, email);
            stmt.setString(2, userName);
            stmt.setString(3, passWord);

            System.out.println("Thực thi câu lệnh SQL: " + sql);
            int rows = stmt.executeUpdate();
            System.out.println("Số dòng ảnh hưởng: " + rows);

            return rows == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
//    public static void main(String[] args) {
//        UserDAO u = new UserDAO();
//        u.signUp(email, userName, passWord)
//    }

   public ArrayList<User> getAllUser() {
    ArrayList<User> userList = new ArrayList<>();
    String sql = "SELECT TOP 3 id, username, email, password_hash, role FROM account";

    try (Connection conn = DBConnection.DBConnection();
         PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            int id = rs.getInt("id");
            String username = rs.getString("username");
            String email = rs.getString("email");
            String password = rs.getString("password_hash");
            String role = rs.getString("role");

            User user = new User(id, username, password, email, role, "");
            userList.add(user);
        }

    } catch (Exception e) {
        e.printStackTrace(); 
    }

    return userList;
}
    public static void main(String[] args) {
        UserDAO ud = new UserDAO();
        System.out.println(ud.getAllUser());
    }

    public boolean DeleteUserByUserName(String userName) {
        String sql = "Delete from account where username = ?";
        try {
            PreparedStatement stmt = DBConnection.DBConnection().prepareStatement(sql);
            stmt.setString(1, userName);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
