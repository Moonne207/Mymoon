/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.procot.Model;

/**
 *
 * @author skmyg
 */
public class accountModel {
    private String fullname;
    private String email;
    private String userName;
    private String password;
    private int roleID;
    private int studentID;
    private int lecturerID;
    private String studentName;
    private String lecturerName;
    private String roleName;

    public accountModel(String fullname, String email, String userName, String password, int roleID, int studentID, int lecturerID) {
        this.fullname = fullname;
        this.email = email;
        this.userName = userName;
        this.password = password;
        this.roleID = roleID;
        this.studentID = studentID;
        this.lecturerID = lecturerID;
    }

    public accountModel(String fullname, String email, String password, int roleID, int studentID, int lecturerID) {
        this.fullname = fullname;
        this.email = email;
        this.password = password;
        this.roleID = roleID;
        this.studentID = studentID;
        this.lecturerID = lecturerID;
    }
    
    public accountModel(String fullname, String password, int roleID, int studentID, int lecturerID) {
        this.fullname = fullname;
        this.password = password;
        this.roleID = roleID;
        this.studentID = studentID;
        this.lecturerID = lecturerID;
    }

    public accountModel(String fullname, String email, String userName, String password, String roleName, String studentName, String lecturerName) {
        this.fullname = fullname;
        this.email = email;
        this.userName = userName;
        this.password = password;
        this.roleName = roleName;
        this.studentName = studentName;
        this.lecturerName = lecturerName;
    }
    
    
    
    public accountModel() {
    }

    public String getFullnamee() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRoleID() {
        return roleID;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }
    
    public String getroleName() {
        return roleName;
    }

    public void setroleName(String roleName) {
        this.roleName = roleName;
    }
    
    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public int getLecturerID() {
        return lecturerID;
    }

    public void setLecturerID(int lecturerID) {
        this.lecturerID = lecturerID;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getLecturerName() {
        return lecturerName;
    }

    public void setLecturerName(String lecturerName) {
        this.lecturerName = lecturerName;
    }
    
    
}
