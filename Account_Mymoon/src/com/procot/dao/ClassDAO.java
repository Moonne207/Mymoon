/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.procot.DAO;

import com.procot.dao.InterfaceDAO;
import com.procot.dao.InterfaceDAO;
import com.procot.util.JDBCHelper;
import com.procot.model.Class;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author payga
 */
public class ClassDAO implements InterfaceDAO<Class> {

    public static ClassDAO getInstance() {
        return new ClassDAO();
    }

    @Override
    public int insert(Class model) {
        String sql = "INSERT INTO Class (ClassName, MajorID, LecturerID) VALUES (?, ?, ?)";
        return JDBCHelper.executeUpdate(sql, model.getClassName(), model.getMajorID(), model.getLecturerID());
    }

    @Override
    public int update(Class model) {
        String sql = "UPDATE Class SET ClassName=?, MajorID=?, LecturerID=? WHERE ClassID=?";
        return JDBCHelper.executeUpdate(sql, model.getClassName(), model.getMajorID(), model.getLecturerID(), model.getClassID());
    }

    @Override
    public int delete(Class model) {
        String sql = "DELETE FROM Class WHERE ClassID=?";
        return JDBCHelper.executeUpdate(sql, model.getClassID());   
    }

    @Override
    public ArrayList<Class> selectAll() {
        ArrayList<Class> list = new ArrayList<>();
        String sql = "SELECT * FROM Class";
        try (ResultSet rs = JDBCHelper.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Class(
                        rs.getInt("ClassID"),
                        rs.getString("ClassName"),
                        rs.getInt("MajorID"),
                        rs.getInt("LecturerID")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    //Lấy tên giảng viên theo ID
    public String getNameByID(int ClassID) {
        String sql = "SELECT ClassName FROM Class WHERE ClassID=?";
        try (ResultSet rs = JDBCHelper.executeQuery(sql, ClassID)){
            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "No information available";
    }
}

