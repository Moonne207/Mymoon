/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.procot.DAO;

import com.procot.Model.studentModel;
import com.procot.Util.jdbcHelper;
import java.sql.*;
import java.util.ArrayList;
/**
 *
 * @author anh74
 */
public class studentDAO implements interfaceDAO<studentModel>{

    
    public static studentDAO getInstance() {
            return new studentDAO();
        }

    @Override
    public int insert(studentModel model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int update(studentModel model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int delete(studentModel model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<studentModel> selectAll() {
    ArrayList<studentModel> list = new ArrayList<>();
    String query = "SELECT sv.StudentID, sv.FullName, sv.DateOfBirth, sv.Gender, sv.Address, "
                 + "sv.PhoneNumber, sv.Email, l.ClassName "
                 + "FROM Student sv "
                 + "LEFT JOIN Class l ON l.ClassID = sv.ClassID"; // Sửa lỗi thiếu dấu cách

    try (ResultSet rs = jdbcHelper.executeQuery(query)) {
        while (rs.next()) {
            list.add(new studentModel(
                    rs.getInt("StudentID"),       // ID sinh viên
                    rs.getString("FullName"),    // Họ và tên
                    rs.getDate("DateOfBirth"),   // Ngày sinh
                    rs.getBoolean("Gender"),     // Giới tính
                    rs.getString("Address"),     // Địa chỉ
                    rs.getString("PhoneNumber"), // Số điện thoại
                    rs.getString("Email"),       // Email
                    rs.getString("ClassName")    // Thay vì ClassID, ta lấy ClassName
            ));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return list;
}


    @Override
    public ArrayList<studentModel> search(String column, String keyword) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<studentModel> searchAll(String keyword) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
    

    

