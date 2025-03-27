/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.procot.Model;

import java.sql.Date;

/**
 *
 * @author anh74
 */
// Model cho bảng TuitionFees
public class tuitionFeesModel {
    private int feeID;           // Mã học phí
    private int studentID;       // Mã sinh viên
    private int semesterID;      // Mã học kỳ
    private double amount;       // Số tiền học phí
    private Date dueDate;        // Hạn đóng học phí
    private boolean paymentStatus; // Trạng thái thanh toán
    private String fullName;     // Tên sinh viên (chỉ để hiển thị)
    private String semesterName; // Tên học kỳ (chỉ để hiển thị)

    public tuitionFeesModel() {}

    public tuitionFeesModel(int feeID ,String fullName, String semesterName, double amount, Date dueDate, boolean paymentStatus) {
        this.feeID = feeID;
        this.fullName = fullName;
        this.semesterName = semesterName;
        this.amount = amount;
        this.dueDate = dueDate;
        this.paymentStatus = paymentStatus;
        
    }
    public tuitionFeesModel(int feeID, int studentID, int semesterID, double amount, Date dueDate, boolean paymentStatus) {
        this.feeID = feeID;
        this.studentID = studentID;
        this.semesterID = semesterID;
        this.amount = amount;
        this.dueDate = dueDate;
        this.paymentStatus = paymentStatus;
        
    }

    public tuitionFeesModel(int studentID, int semesterID, double amount, Date dueDate, boolean paymentStatus) {
        this.studentID = studentID;
        this.semesterID = semesterID;
        this.amount = amount;
        this.dueDate = dueDate;
        this.paymentStatus = paymentStatus;
        
    }
    public int getFeeID() {
        return feeID;
    }

    public void setFeeID(int feeID) {
        this.feeID = feeID;
    }

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public int getSemesterID() {
        return semesterID;
    }

    public void setSemesterID(int semesterID) {
        this.semesterID = semesterID;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(boolean paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getSemesterName() {
        return semesterName;
    }

    public void setSemesterName(String semesterName) {
        this.semesterName = semesterName;
    }
}