/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.procot.DAO;

import com.procot.Model.MajorModel;
import com.procot.Util.jdbcHelper;
import java.util.ArrayList;
import java.sql.*;

/**
 *
 * @author skmyg
 */
public class MajorDAO implements interfaceDAO<MajorModel> {

    public static MajorDAO getInstance() {
        return new MajorDAO();
    }

    @Override
    public int insert(MajorModel model) {
        String sql = "INSERT INTO Major(MajorName) VALUES(?)";
        return jdbcHelper.executeUpdate(sql, model.getMajorName());
    }

    @Override
    public int update(MajorModel model) {
        String sql = "UPDATE Major SET MajorName=? WHERE MajorID=?";
        return jdbcHelper.executeUpdate(sql, model.getMajorName(), model.getMajorID());
    }

    @Override
    public int delete(MajorModel model) {
        String sql = "DELETE FROM Major WHERE MajorID=?";
        return jdbcHelper.executeUpdate(sql, model.getMajorID());
    }

    @Override
    public ArrayList<MajorModel> selectAll() {
        ArrayList<MajorModel> list = new ArrayList<>();
        String sql = "SELECT * FROM Major";
        try (ResultSet rs = jdbcHelper.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new MajorModel(
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
        try (ResultSet rs = jdbcHelper.executeQuery(query, name)) {
            if (rs.next()) {
                return rs.getInt("MajorID");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi SQL: " + e.getMessage(), e);
        }
        return -1; // Trả về -1 nếu không tìm thấy
    }

    // Lấy tên 
    public MajorModel getName(String name) {
        MajorModel model = null;
        String sql = "SELECT * FROM Major WHERE MajorName=?";
        try (ResultSet rs = jdbcHelper.executeQuery(sql, name)) {
            while (rs.next()) {
                return new MajorModel(
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
