/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author skmyg
 */
public class lecturerModel {
    private int lecturerID;
    private String lecturerName;
    private String gender;
    private String address;
    private String email;
    private String phoneNumber;
    private int majorID;
    private String majorName;
    private String status;

    public lecturerModel(int lecturerID, String lecturerName, String gender, String address, String email, String phoneNumber, String majorName, String status) {
        this.lecturerID = lecturerID;
        this.lecturerName = lecturerName;
        this.gender = gender;
        this.address = address;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.majorName = majorName;
        this.status = status;
    }

    public lecturerModel(String lecturerName, String gender, String address, String email, String phoneNumber, int majorID, String status) {
        this.lecturerName = lecturerName;
        this.gender = gender;
        this.address = address;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.majorID = majorID;
        this.status = status;
    }

    public lecturerModel() {
    }

    public int getLecturerID() {
        return lecturerID;
    }

    public void setLecturerID(int lecturerID) {
        this.lecturerID = lecturerID;
    }

    public String getLecturerName() {
        return lecturerName;
    }

    public void setLecturerName(String lecturerName) {
        this.lecturerName = lecturerName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
     public int getmajorID() {
        return majorID;
    }

    public void setmajorID(int majorID) {
        this.majorID = majorID;
    }
     public String getmajorName() {
        return majorName;
    }

    public void setmajorName(String majorName) {
        this.majorName = majorName;
    }
     public String getstatus() {
        return status;
    }

    public void setstatus(String status) {
        this.status = status;
    }
}
