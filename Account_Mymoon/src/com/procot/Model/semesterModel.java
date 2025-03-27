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
public class semesterModel {
    private int semesterId;
    private String semesterName;
    private String academicYear;
    private Date startDate;
    private Date endDate;
    private boolean semesterStatus;
    
    public semesterModel() {
        
    }
    
    public semesterModel(int semesterId, String semesterName, String academicYear, Date startDate, Date endDate, boolean semesterStatus) {
        this.semesterId = semesterId;
        this.semesterName = semesterName;
        this.academicYear = academicYear;
        this.startDate = startDate;
        this.endDate = endDate;
        this.semesterStatus = semesterStatus;
    }

    // Getter và Setter
    public int getSemesterId() {
        return semesterId;
    }

    public void setSemesterId(int semesterId) {
        this.semesterId = semesterId;
    }

    public String getSemesterName() {
        return semesterName;
    }

    public void setSemesterName(String semesterName) {
        this.semesterName = semesterName;
    }

    public String getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(String academicYear) {
        this.academicYear = academicYear;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public boolean isSemesterStatus() {
        return semesterStatus;
    }

    public void setSemesterStatus(boolean semesterStatus) {
        this.semesterStatus = semesterStatus;
    }
    
}
