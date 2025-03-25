/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.procot.DAO;

import com.procot.model.Major;
import com.procot.util.JDBCHelper;
import java.util.ArrayList;
import java.sql.*;

/**
 *
 * @author skmyg
 */
public class MajorDAO implements InterfaceDAO<Major> {

    public static MajorDAO getInstance() {
        return new MajorDAO();
    }

    @Override
    public int insert(Major model) {
        String sql = "INSERT INTO Major(MajorName) VALUES(?)";
        return JDBCHelper.executeUpdate(sql, model.getMajorName());
    }

    @Override
    public int update(Major model) {
        String sql = "UPDATE Major SET MajorName=? WHERE MajorID=?";
        return JDBCHelper.executeUpdate(sql, model.getMajorName(), model.getMajorID());
    }

    @Override
    public int delete(Major model) {
        String sql = "DELETE FROM Major WHERE MajorID=?";
        return JDBCHelper.executeUpdate(sql, model.getMajorID());
    }

    @Override
    public ArrayList<Major> selectAll() {
        ArrayList<Major> list = new ArrayList<>();
        String sql = "SELECT * FROM Major";
        try (ResultSet rs = JDBCHelper.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Major(
                        rs.getInt("MajorID"),
                        rs.getString("MajorName")
                ));
            };
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Lấy Id bằng tên major
    public int getIdByName(String name) {
        String query = "SELECT MajorID FROM Major WHERE MajorName = ?";
        try (ResultSet rs = JDBCHelper.executeQuery(query, name)) {
            if (rs.next()) {
                return rs.getInt("MajorID");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi SQL: " + e.getMessage(), e);
        }
        return -1; // Trả về -1 nếu không tìm thấy
    }

    // Lấy tên 
    public Major getName(String name) {
        Major model = null;
        String sql = "SELECT * FROM Major WHERE MajorName=?";
        try (ResultSet rs = JDBCHelper.executeQuery(sql, name)) {
            while (rs.next()) {
                return new Major(
                        rs.getInt("MajorID"),
                        rs.getString("MajorName")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return model;
    }
}
