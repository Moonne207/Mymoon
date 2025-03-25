/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.procot.Model;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Admin
 */
public class Student {
    int studentID;          //Mã sinh viên
    String fullName;        //Tên sinh viên
    Date dateOfBirth;       //Ngày sinh
    int gender;             //Giới tính
    String address;         //Địa chỉ
    String phoneNumber;     //Số điện thoại
    String email;           //Email
    int classID;            //Mã lớp
    String className;       //Tên lớp

    
    //Constructor không tham số
    public Student() {
    }

    //Constructor không có ClassID
    //Lấy dữ liệu từ database để hiển thị
    public Student(int studentID, String fullName, Date dateOfBirth, int gender, String address, String phoneNumber, String email, String className) {
        this.studentID = studentID;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.className = className;
    }
    
    //Constructor không có StudentID , ClassName
    //Insert
    public Student(String fullName, Date dateOfBirth, int gender, String address, String phoneNumber, String email, int classID) {
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.classID = classID;
    }

    //Constructor không có className
    //Update
    public Student(int studentID, String fullName, Date dateOfBirth, int gender, String address, String phoneNumber, String email, int classID) {
        this.studentID = studentID;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.classID = classID;
    }

    //getter & setter
    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public int getClassID() {
        return classID;
    }

    public void setClassID(int classID) {
        this.classID = classID;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    //Định dạng ngày sinh theo định dạng dd/MM/yyyy
    public String getFormattedDateOfBirth() {
        SimpleDateFormat fm = new SimpleDateFormat("dd/MM/yyyy");
        return fm.format(dateOfBirth);
    }
}
