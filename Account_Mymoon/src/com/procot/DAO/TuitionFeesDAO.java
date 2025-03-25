/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.procot.DAO;

import com.procot.Model.TuitionFeesModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author anh74
 */
// DAO cho bảng TuitionFees


public class TuitionFeesDAO {
    private Connection conn;
    
    public TuitionFeesDAO(Connection conn) {
        this.conn = conn;
    }

    public void insertTuitionFee(TuitionFeesModel fee) throws SQLException {
        String sql = "INSERT INTO TuitionFees (student_id, amount, due_date, payment_status) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, fee.getStudentId());
            stmt.setDouble(2, fee.getAmount());
            stmt.setDate(3, new java.sql.Date(fee.getDueDate().getTime()));
            stmt.setBoolean(4, fee.isPaymentStatus());
            stmt.executeUpdate();
        }
    }

    public List<TuitionFeesModel> getAllTuitionFees() throws SQLException {
        List<TuitionFeesModel> fees = new ArrayList<>();
        String sql = "SELECT * FROM TuitionFees";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                TuitionFeesModel fee = new TuitionFeesModel(
                    rs.getInt("fee_id"),
                    rs.getInt("student_id"),
                    rs.getDouble("amount"),
                    rs.getDate("due_date"),
                    rs.getBoolean("payment_status")
                );
                fees.add(fee);
            }
        }
        return fees;
    }

    public void updatePaymentStatus(int feeId, boolean status) throws SQLException {
        String sql = "UPDATE TuitionFees SET payment_status = ? WHERE fee_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setBoolean(1, status);
            stmt.setInt(2, feeId);
            stmt.executeUpdate();
        }
    }

    public void deleteTuitionFee(int feeId) throws SQLException {
        String sql = "DELETE FROM TuitionFees WHERE fee_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, feeId);
            stmt.executeUpdate();
        }
    }
}
