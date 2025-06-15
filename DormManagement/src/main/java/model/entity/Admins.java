package model.entity;

import java.sql.Date;

public class Admins {
    private int adminID;
    private String username;
    private String aPassword; // Sử dụng aPassword để khớp với cột APassword trong DB
    private String fullName;
    private String email;
    private String phoneNumber;
    private String imageProfile;

    public Admins() {
    }

    public Admins(int adminID, String username, String aPassword, String fullName, String email, String phoneNumber, String imageProfile) {
        this.adminID = adminID;
        this.username = username;
        this.aPassword = aPassword;
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.imageProfile = imageProfile;
    }

    public int getAdminID() {
        return adminID;
    }

    public void setAdminID(int adminID) {
        this.adminID = adminID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAPassword() { // Đổi từ getaPassword thành getAPassword
        return aPassword;
    }

    public void setaPassword(String aPassword) {
        this.aPassword = aPassword;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getImageProfile() {
        return imageProfile;
    }

    public void setImageProfile(String imageProfile) {
        this.imageProfile = imageProfile;
    }

    @Override
    public String toString() {
        return "Admins{" +
                "adminID=" + adminID +
                ", username='" + username + '\'' +
                ", aPassword='" + aPassword + '\'' +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", imageProfile='" + imageProfile + '\'' +
                '}';
    }
}