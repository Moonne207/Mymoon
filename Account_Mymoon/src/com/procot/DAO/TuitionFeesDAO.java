/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.procot.DAO;

import com.procot.Model.TuitionFeesModel;
import com.procot.Util.jdbcHelper;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.management.Query;
/**
 *
 * @author anh74
 */
// DAO cho bảng TuitionFees


public class TuitionFeesDAO implements interfaceDAO<TuitionFeesModel>{
    private Connection conn;
    
        public static TuitionFeesDAO getInstance() {
            return new TuitionFeesDAO();
        }



        @Override
        public int insert(TuitionFeesModel model) {
            String sql = "INSERT INTO TuitionFees (student_id, amount, due_date, payment_status) VALUES (?, ?, ?, ?)";
            return jdbcHelper.executeUpdate(sql, 
                    model.getStudentId(), 
                    model.getAmount(), 
                    model.getDueDate(), 
                    model.isPaymentStatus());
        }

        @Override
        public int update(TuitionFeesModel model) {
            String query = "UPDATE TuitionFees SET student_id=?, amount=?, due_date=?, payment_status=? WHERE fee_id=?";
            return jdbcHelper.executeUpdate(query, 
                           model.getStudentId(), 
                           model.getAmount(), 
                           model.getDueDate(), 
                           model.isPaymentStatus(), 
                           model.getFeeId());    
        }

        @Override
        public int delete(TuitionFeesModel model) {
            String query = "DELETE FROM TuitionFees WHERE fee_id=?";
            return jdbcHelper.executeUpdate(query, model.getFeeId());    
        }

        @Override
    public ArrayList<TuitionFeesModel> selectAll() {
        ArrayList<TuitionFeesModel> list = new ArrayList<>();
        String query = "SELECT tf.fee_id, tf.student_id,  tf.amount, tf.due_date, tf.payment_status "
                     + "FROM TuitionFees tf "
                     + "LEFT JOIN Student s ON tf.student_id = s.student_id"; // JOIN để lấy tên sinh viên
        try (ResultSet rs = jdbcHelper.executeQuery(query)) {
            while (rs.next()) {
                list.add(new TuitionFeesModel(
                    rs.getInt(1),   // fee_id
                    rs.getInt(2),   // student_id
                    rs.getDouble(3),// amount
                    rs.getDate(4),  // due_date
                    rs.getBoolean(5)// payment_status
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public ArrayList<TuitionFeesModel> search(String column, String keyword) {
        ArrayList<TuitionFeesModel> list = new ArrayList<>();
        String sql = "SELECT tf.fee_id, tf.student_id,tf.amount, tf.due_date, tf.payment_status "
                   + "FROM TuitionFees tf "
                   + "LEFT JOIN Student s ON tf.student_id = s.student_id "
                   + "WHERE " + column + " LIKE ?";
        try (ResultSet rs = jdbcHelper.executeQuery(sql, "%" + keyword + "%")) {
            while (rs.next()) {
                list.add(new TuitionFeesModel(
                    rs.getInt(1),   
                    rs.getInt(2),   
                    rs.getDouble(3),
                    rs.getDate(4),
                    rs.getBoolean(5)
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public ArrayList<TuitionFeesModel> searchAll(String keyword) {
        ArrayList<TuitionFeesModel> list = new ArrayList<>();
        String sql = "SELECT tf.fee_id, tf.student_id, tf.amount, tf.due_date, tf.payment_status "
                   + "FROM TuitionFees tf "
                   + "LEFT JOIN Student s ON tf.student_id = s.student_id "
                   + "OR tf.student_id LIKE ? "
                   + "OR tf.amount LIKE ? "
                   + "OR tf.due_date LIKE ? "
                   + "OR tf.payment_status LIKE ?";

        try (ResultSet rs = jdbcHelper.executeQuery(sql,
                "%" + keyword + "%",
                "%" + keyword + "%",
                "%" + keyword + "%",
                "%" + keyword + "%",
                "%" + keyword + "%")) {
            while (rs.next()) {
                list.add(new TuitionFeesModel(
                    rs.getInt(1),   // fee_id
                    rs.getInt(2),   // student_id
                    rs.getDouble(3),// amount
                    rs.getDate(4),  // due_date
                    rs.getBoolean(5)// payment_status
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

}
