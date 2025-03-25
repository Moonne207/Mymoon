/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.procot.Model;

/**
 *
 * @author skmyg
 */
public class MajorModel {
    int majorID;
    String majorName;
    // Dùng để hiện danh sách
    public MajorModel(int majorID, String majorName) {
        this.majorID = majorID;
        this.majorName = majorName;
    }
    // Dùng để thêm dự liệu mới
    public MajorModel(String majorName) {
        this.majorName = majorName;
    }

    public MajorModel() {
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
    
    
}
