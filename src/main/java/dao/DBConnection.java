package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConnection {
// protected static Connection connection;

    public static Connection DBConnection() {
        Connection conn=null;
        String user = "sa";
        String pass = "sa";
        String url = "jdbc:sqlserver://localhost:1433;databaseName=CarShop;encrypt=true;trustServerCertificate=true";
            
        try {

            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection(url, user, pass);
            System.out.println(conn);
            return conn;
            
        } catch (ClassNotFoundException | SQLException e) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, e);
        }
         return null;
    }
    public static void main(String[] args) {
//        DBConnection.DBConnection();
        try (Connection conn = DBConnection.DBConnection()) {
            System.out.println("Ket noi thanh cong: " + conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

