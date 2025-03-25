/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.procot.Model;

import java.util.Date;

/**
 *
 * @author anh74
 */
// Model cho bảng TuitionFees
public class TuitionFeesModel {
    private int feeId;
    private int studentId;
    private double amount;
    private Date dueDate;
    private boolean paymentStatus;

    public TuitionFeesModel() {}

    public TuitionFeesModel(int feeId, int studentId, double amount, Date dueDate, boolean paymentStatus) {
        this.feeId = feeId;
        this.studentId = studentId;
        this.amount = amount;
        this.dueDate = dueDate;
        this.paymentStatus = paymentStatus;
    }

    public int getFeeId() { return feeId; }
    public void setFeeId(int feeId) { this.feeId = feeId; }

    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public Date getDueDate() { return dueDate; }
    public void setDueDate(Date dueDate) { this.dueDate = dueDate; }

    public boolean isPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(boolean paymentStatus) { this.paymentStatus = paymentStatus; }
}
