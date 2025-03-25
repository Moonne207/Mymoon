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
 * @author skmyg
 */
public class studentDAO implements interfaceDAO<studentModel> {

    public static studentDAO getInstance() {
        return new studentDAO();
    }

    @Override
    public int insert(studentModel model) {
        String sql = "INSERT INTO Student (FullName,Gender,DateOfBirth,Address,Email,PhoneNumber,ClassID,MajorID) VALUES (?,?,?,?,?,?,?)";
        return jdbcHelper.executeUpdate(sql, 
                model.getStudentName(), 
                model.getGender(),
                model.getDateOfBirth(),
                model.getAddress(),
                model.getEmail(),
                model.getPhoneNumber(),
                model.getClassID(),
                model.getMajorID()
                );
    }

    @Override
    public int update(studentModel model) {
        String sql = "UPDATE Student SET FullName=?,Gender=?,DateOfBirth=?,Address=?,Email=?,PhoneNumber=?,ClassID=?,MajorID=? WHERE MaSinhVien=?";
        return jdbcHelper.executeUpdate(sql, 
                model.getStudentName(), 
                model.getGender(),
                model.getDateOfBirth(),
                model.getAddress(),
                model.getEmail(),
                model.getPhoneNumber(),
                model.getClassID(),
                model.getStudentID(),
                model.getMajorID()
                );
    }

    @Override
    public int delete(studentModel model) {
        String sql = "DELETE FROM Student WHERE StudentID=?";
        return jdbcHelper.executeUpdate(sql, model.getStudentID());
    }

    @Override
public ArrayList<studentModel> selectAll() {
    ArrayList<studentModel> list = new ArrayList<>();
    String sql = "SELECT sv.StudentID, sv.FullName, sv.Gender, sv.DateOfBirth, sv.Address, sv.Email, sv.PhoneNumber, l.ClassName, m.MajorName "
               + "FROM Student sv "
               + "LEFT JOIN Class l ON l.ClassID = sv.ClassID "
               + "LEFT JOIN Major m ON sv.MajorID = m.MajorID";
    try (ResultSet rs = jdbcHelper.executeQuery(sql)) {
        while (rs.next()) {
            list.add(new studentModel(
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getDate(4),
                    rs.getString(5),
                    rs.getString(6),
                    rs.getString(7),
                    rs.getString(8),
                    rs.getString(9)
            ));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return list;
}


    @Override
    public ArrayList<studentModel> search(String column, String keyword) {
        ArrayList<studentModel> list = new ArrayList<>();

        String sql = "SELECT sv.StudentID,sv.FullName,sv.Gender,sv.DateOfBirth,sv.Address,sv.Email,sv.PhoneNumber,l.ClassName,m.MajorName "
                + "FROM Student sv "
                + "LEFT JOIN Class l ON l.ClassID = sv.ClassID "
                + "LEFT JOIN Major m ON sv.MajorID = m.MajorID"
                + "WHERE " + column + " LIKE ?";
        try (ResultSet rs = jdbcHelper.executeQuery(sql, "%" + keyword + "%")) {
            while (rs.next()) {
                list.add(new studentModel(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getDate(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getString(9)
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public ArrayList<studentModel> searchAll(String keyword) {
        ArrayList<studentModel> list = new ArrayList<>();
        String sql = "SELECT sv.StudentID,sv.FullName,sv.Gender,sv.DateOfBirth,sv.Address,sv.Email,sv.PhoneNumber,l.ClassName,m.MajorName "
                + "FROM Student sv "
                + "LEFT JOIN Class l ON l.ClassID = sv.ClassID "
                + "LEFT JOIN Major m ON sv.MajorID = m.MajorID"
                + "WHERE sv.StudentID LIKE ? "
                + "OR sv.FullName LIKE ? "
                + "OR sv.Gender LIKE ? "
                + "OR sv.DateOfBirth LIKE ? "
                + "OR sv.Address LIKE ? "
                + "OR sv.Email LIKE ? "
                + "OR sv.PhoneNumber LIKE ? "
                + "OR l.ClassName LIKE ? "
                + "OR m.MajorName LIKE ? ";

        try (ResultSet rs = jdbcHelper.executeQuery(sql,
                "%" + keyword + "%",
                "%" + keyword + "%",
                "%" + keyword + "%",
                "%" + keyword + "%",
                "%" + keyword + "%",
                "%" + keyword + "%",
                "%" + keyword + "%",
                "%" + keyword + "%",
                "%" + keyword + "%")) {
            while (rs.next()) {
                list.add(new studentModel(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getDate(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getString(9)
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public studentModel selectByEmail(String email) {
        studentModel sm = null;
        String sql = "SELECT * FROM Student WHERE Email=?";
        try (ResultSet rs = jdbcHelper.executeQuery(sql, email)) {
            while (rs.next()) {
                return new studentModel(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getDate(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getInt(8),
                        rs.getInt(9)
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sm;
    }
    
    public studentModel selectByPhoneNumber(String phoneNumber) {
        studentModel sm = null;
        String sql = "SELECT * FROM Student WHERE PhoneNumber=?";
        try (ResultSet rs = jdbcHelper.executeQuery(sql, phoneNumber)) {
            while (rs.next()) {
                return new studentModel(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getDate(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getInt(8),
                        rs.getInt(9)
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sm;
    }
    
    public String selectClassNameByID(int studentId) {
        String sql = "SELECT l.ClassName "
                + "FROM Student sv "
                + "LEFT JOIN Class l ON l.ClassID = sv.ClassID "
                + "WHERE sv.StudentID = ?";
        try (ResultSet rs = jdbcHelper.executeQuery(sql, studentId)) {
            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Không có thông tin";
    }
    
    public String selectNameByID(int studentId) {
        String sql = "SELECT FullName FROM Student WHERE StudentID = ?";
        try (ResultSet rs = jdbcHelper.executeQuery(sql, studentId)) {
            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Không có thông tin";
    }
}
