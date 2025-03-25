/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.procot.Model;

/**
 *
 * @author payyga
 */
public class ClassModel {
    private int classID;
    private String className;
    private int majorID;
    private int lecturerID;

    // Constructor cho update và hiển thị dữ liệu
    public ClassModel(int classID, String className, int majorID, int lecturerID) {
        this.classID = classID;
        this.className = className;
        this.majorID = majorID;
        this.lecturerID = lecturerID;
    }

    // Constructor cho insert
    public ClassModel(String className, int majorID, int lecturerID) {
        this.className = className;
        this.majorID = majorID;
        this.lecturerID = lecturerID;
    }

    public ClassModel() {
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

    public int getMajorID() {
        return majorID;
    }

    public void setMajorID(int majorID) {
        this.majorID = majorID;
    }

    public int getLecturerID() {
        return lecturerID;
    }

    public void setLecturerID(int lecturerID) {
        this.lecturerID = lecturerID;
    }  
}
