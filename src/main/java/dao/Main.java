/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

//import static dao.DBConnection.connection;

/**
 *
 * @author Admin
 */
public class Main {
    public static void main(String[] args) {
        String email = "a";
        String userName ="a";
        String passWord = "a";
        UserDAO ud1 = new UserDAO();
        ud1.signUp(email, userName, passWord);
}

}
