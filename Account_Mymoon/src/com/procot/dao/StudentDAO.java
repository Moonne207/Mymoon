/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.procot.DAO;

import com.procot.Model.StudentModel;
import com.procot.Util.jdbcHelper;
import java.sql.*;
import java.util.ArrayList;
/**
 *
 * @author Admin
 */
public class StudentDAO implements interfaceDAO<StudentModel> {          //Triển khai interface
    
    public static StudentDAO getInstance(){     
        return new StudentDAO();   
    }
    
    //Thêm sinh viên
    @Override
    public int insert(StudentModel model) {
        String sql = "INSERT INTO Student (FullName , DateOfBirth , Gender , Address , PhoneNumber , Email , ClassID) VALUES (?,?,?,?,?,?,?)";
        return jdbcHelper.executeUpdate(sql, model.getFullName(),
                                             model.getDateOfBirth(),
                                             model.getGender(),
                                             model.getAddress(),
                                             model.getPhoneNumber(),
                                             model.getEmail(),
                                             model.getClassID() );
    }

    //Cập nhật thông tin sinh viên
    @Override
    public int update(StudentModel model) {
        String sql = "UPDATE Student SET FullName=? , DateOfBirth=? , Gender=? , Address=? , PhoneNumber=? , Email=? , ClassID=? WHERE StudentID=?";
        return jdbcHelper.executeUpdate(sql, model.getFullName(),
                                             model.getDateOfBirth(),
                                             model.getGender(),
                                             model.getAddress(),
                                             model.getPhoneNumber(),
                                             model.getEmail(),
                                             model.getClassID(),
                                             model.getStudentID() );
    }

    //Xóa thông tin sinh viên
    @Override
    public int delete(StudentModel model) {
        String sql = "DELETE FROM STUDENT WHERE StudentID=?";
        return jdbcHelper.executeUpdate(sql, model.getStudentID());
    }

    // Lấy danh sách tất cả sinh viên
    @Override
    public ArrayList<StudentModel> selectAll() {
        ArrayList<StudentModel> list = new ArrayList<>();
        String sql = " SELECT st.StudentID,st.FullName,st.DateOfBirth,st.Gender,st.Address,st.PhoneNumber,st.Email,cl.className "
                   + " FROM Student st "
                   + " LEFT JOIN Class cl ON cl.ClassID = st.ClassID";
        try (ResultSet rs = jdbcHelper.executeQuery(sql)){
            while (rs.next()){
            list.add(new StudentModel( rs.getInt(1),
                                  rs.getString(2),
                                  rs.getDate(3),
                                  rs.getInt(4),
                                  rs.getString(5),
                                  rs.getString(6),
                                  rs.getString(7),
                                  rs.getString(8) ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    //Tìm kiếm sinh viên theo email
    public StudentModel selectByEmail(String email) {
        StudentModel st = null;
        String sql = "SELECT * FROM Student WHERE Email=?";
        try (ResultSet rs = jdbcHelper.executeQuery(sql,email)) {
            while (rs.next()) {
                return new StudentModel(  rs.getInt(1),
                                     rs.getString(2),
                                     rs.getDate(3),
                                     rs.getInt(4),
                                     rs.getString(5),
                                     rs.getString(6),
                                     rs.getString(7),
                                     rs.getInt(8)
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return st;
    }
    
    //Tìm kiếm sinh viên theo số điện thoại
    public StudentModel selectByPhoneNumber(String phoneNumber) {
        StudentModel st = null;
        String sql = "SELECT * FROM Student WHERE PhoneNumber=?";
        try (ResultSet rs = jdbcHelper.executeQuery(sql,phoneNumber)) {
            while (rs.next()) {
                return new StudentModel(  rs.getInt(1),
                                     rs.getString(2),
                                     rs.getDate(3),
                                     rs.getInt(4),
                                     rs.getString(5),
                                     rs.getString(6),
                                     rs.getString(7),
                                     rs.getInt(8)
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return st;
    }
}
