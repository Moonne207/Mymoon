/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.procot.DAO;

import com.procot.model.Lecturer;
import com.procot.util.JDBCHelper;
import java.util.ArrayList;
import java.sql.*;

/**
 *
 * @author Admin
 */
public class LecturerDAO implements InterfaceDAO<Lecturer>{             //Triển khai InterfaceDAO
    
    public static LecturerDAO getInstance(){
        return new LecturerDAO();                 //Tạo ra đối tượng mới mỗi lần gọi phương thức
    }
    
    //Thêm giảng viên mới
    @Override
    public int insert(Lecturer model) {
        String sql = "INSERT INTO Lecturer(FullName , PhoneNumber , Email , Address , MajorID , LecturerStatus) VALUES (?,?,?,?,?,?)";
        return JDBCHelper.executeUpdate(sql, model.getFullName(),
                                             model.getPhoneNumber(),
                                             model.getEmail(),
                                             model.getAddress(),
                                             model.getMajorID(),
                                             model.getStatus() );
    }
    
    //Cập nhật thông tin giảng viên
    @Override
    public int update(Lecturer model) {
        String sql = "Update Lecturer set FullName=? , PhoneNumber=? , Email=? , Address=? , MajorID=? , LecturerStatus=? WHERE LecturerID=?";
        return JDBCHelper.executeUpdate(sql, model.getFullName(),
                                             model.getPhoneNumber(),
                                             model.getEmail(),
                                             model.getAddress(),
                                             model.getMajorID(),
                                             model.getStatus(),
                                             model.getLecturerID() );
    }

    //Xóa thông tin giảng viên
    @Override
    public int delete(Lecturer model) {
        String sql = "DELETE FROM Lecturer WHERE LecturerID=?";
        return JDBCHelper.executeUpdate(sql, model.getLecturerID());
    }
    
    //Lấy danh sách tất cả giảng viên
    @Override
    public ArrayList<Lecturer> selectAll() {
        ArrayList<Lecturer> list = new ArrayList<>();
        String sql = "SELECT lr.LecturerID , lr.FullName , lr.PhoneNumber , lr.Email , lr.Address , mj.MajorName , lr.Status "
                   + " FROM Lecturer lr "
                   + " LEFT JOIN Major mj on mj.MajorID = lr.MajorID";
        try (ResultSet rs = JDBCHelper.executeQuery(sql)){
            while (rs.next()){
                list.add(new Lecturer (rs.getInt(1),
                                       rs.getString(2),
                                       rs.getString(3),
                                       rs.getString(4),
                                       rs.getString(5),
                                       rs.getString(6),
                                       rs.getString(7) ));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return list;
    }
    
    //Tìm kiếm giảng viên theo email
    public Lecturer selectByEmail(String email) {
        Lecturer lr = null;
        String sql = "SELECT * FROM Lecturer WHERE Email=?";
        try (ResultSet rs = JDBCHelper.executeQuery(sql,email)) {
            while (rs.next()) {
                return new Lecturer( rs.getInt(1),
                                     rs.getString(2),
                                     rs.getString(3),
                                     rs.getString(4),
                                     rs.getString(5),
                                     rs.getInt(6),
                                     rs.getString(7)
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lr;
    }
    
    //Tìm kiếm giảng viên theo số điện thoại
    public Lecturer selectByPhoneNumber(String phoneNumber) {
        Lecturer lr = null;
        String sql = "SELECT * FROM Lecturer WHERE PhoneNumber=?";
        try (ResultSet rs = JDBCHelper.executeQuery(sql,phoneNumber)) {
            while (rs.next()) {
                return new Lecturer( rs.getInt(1),
                                     rs.getString(2),
                                     rs.getString(3),
                                     rs.getString(4),
                                     rs.getString(5),
                                     rs.getInt(6),
                                     rs.getString(7)
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lr;
    }
}
