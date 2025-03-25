/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;
import java.sql.Date;
import java.text.SimpleDateFormat;
/**
 *
 * @author skmyg
 */
public class studentModel {
    private int studentID;
    private String studentName;
    private String gender;
    private Date dateOfBirth;
    private String address;
    private String email;
    private String phoneNumber;
    private int classID;
    private int majorID;
    private String className;
    private String majorName;
    // Xuất dự liệu
    public studentModel(int studentID, String studentName, String gender, Date dateOfBirth, String address, String email, String phoneNumber, String className, String majorName) {
        this.studentID = studentID;
        this.studentName = studentName;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.className = className;
        this.majorName = majorName;
    }
    // Insert dự liệu
    public studentModel(String studentName, String gender, Date dateOfBirth, String address, String email, String phoneNumber, int classID, int majorID) {
        this.studentName = studentName;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.classID = classID;
        this.majorID = majorID;
    }
    // Update dự liệu
    public studentModel(int studentID, String studentName, String gender, Date dateOfBirth, String address, String email, String phoneNumber, int classID, int majorID) {
        this.studentID = studentID;
        this.studentName = studentName;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.classID = classID;
        this.majorID = majorID;
    }

    

    public studentModel() {
    }
    
    public int getMajorID() {
        return majorID;
    }

    public void setMajorID(int majorID) {
        this.majorID = majorID;
    }

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
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
    
    public String getFormattedDateOfBirth() {
        if (dateOfBirth != null) {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            return formatter.format(dateOfBirth);
        }
        return null;
    }
}
