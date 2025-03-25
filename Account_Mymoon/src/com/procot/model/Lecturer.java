/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.procot.Model;

/**
 *
 * @author Admin
 */
public class Lecturer {
    int lecturerID;          //Mã giảng viên
    String fullName;         //Tên giảng viên
    String phoneNumber;      //Số điện thoại
    String email;            //Email
    String address;          //Địa chỉ
    int majorID;             //Mã chuyên ngành
    String majorName;        //Tên chuyên ngành
    String status;           //Trạng thái

    //Constructor không có tham số
    public Lecturer() {
    }
    
    //Constructor không có majorID
    //Lấy dữ liệu từ database để hiển thị
    public Lecturer(int lecturerID, String fullName, String phoneNumber, String email, String address, String majorName, String status) {
        this.lecturerID = lecturerID;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.majorName = majorName;
        this.status = status;
    }

    //Constructor không có lecturerID , majorName
    //Insert
    public Lecturer(String fullName, String phoneNumber, String email, String address, int majorID, String status) {
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.majorID = majorID;
        this.status = status;
    }
    
    //Constructor không có majorName
    //Update
    public Lecturer(int lecturerID, String fullName, String phoneNumber, String email, String address, int majorID, String status) {
        this.lecturerID = lecturerID;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.majorID = majorID;
        this.status = status;
    }

    // getter & setter
    public int getLecturerID() {
        return lecturerID;
    }

    public void setLecturerID(int lecturerID) {
        this.lecturerID = lecturerID;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getMajorID() {
        return majorID;
    }

    public void setMajorID(int majorID) {
        this.majorID = majorID;
    }

    public String getMajorName() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
