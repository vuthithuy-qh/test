package model.entity;

import java.sql.Date;

public class StudentInfo {
    private String studentID;
    private String firstName;
    private String lastName;
    private String fullName; // Computed column
    private String gender;
    private Date dateOfBirth;
    private String phoneNumber;
    private String email;
    private String cccd;
    private String address;
    private Integer adminID;

    public StudentInfo() {
    }

    public StudentInfo(String studentID, String firstName, String lastName, String gender, Date dateOfBirth, String phoneNumber, String email, String cccd, String address, Integer adminID) {
        this.studentID = studentID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.fullName = (firstName != null ? firstName + " " : "") + (lastName != null ? lastName : ""); // Tính toán fullName
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.cccd = cccd;
        this.address = address;
        this.adminID = adminID;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
        this.fullName = (firstName != null ? firstName + " " : "") + (lastName != null ? lastName : "");
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
        this.fullName = (firstName != null ? firstName + " " : "") + (lastName != null ? lastName : "");
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCccd() {
        return cccd;
    }

    public void setCccd(String cccd) {
        this.cccd = cccd;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getAdminID() {
        return adminID;
    }

    public void setAdminID(Integer adminID) {
        this.adminID = adminID;
    }

    @Override
    public String toString() {
        return "StudentInfo{" +
                "studentID='" + studentID + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", fullName='" + fullName + '\'' +
                ", gender='" + gender + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", cccd='" + cccd + '\'' +
                ", address='" + address + '\'' +
                ", adminID=" + adminID +
                '}';
    }
}