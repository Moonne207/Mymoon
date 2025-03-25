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
    private String accountName;
    private String email;
    private String userName;
    private String password;
    private int roleID;
    private String roleName;

    public accountModel(String accountName, String email, String userName, String password, int roleID) {
        this.accountName = accountName;
        this.email = email;
        this.userName = userName;
        this.password = password;
        this.roleID = roleID;
    }

    public accountModel(String accountName, String email, String password, int roleID) {
        this.accountName = accountName;
        this.email = email;
        this.password = password;
        this.roleID = roleID;
    }
    
    public accountModel(String accountName, String password, int roleID) {
        this.accountName = accountName;
        this.password = password;
        this.roleID = roleID;
    }

    public accountModel(String accountName, String email, String userName, String password, String roleName) {
        this.accountName = accountName;
        this.email = email;
        this.userName = userName;
        this.password = password;
        this.roleName = roleName;
    }
    
    public accountModel(String userName,String email, String password, String roleName) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.roleName = roleName;
    }
    public accountModel() {
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
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
  
}
