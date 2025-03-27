/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.procot.DAO;

import com.procot.Model.tuitionFeesModel;
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


public class tuitionFeesDAO implements interfaceDAO<tuitionFeesModel>{
    private Connection conn;
    
        public static tuitionFeesDAO getInstance() {
            return new tuitionFeesDAO();
        }



    @Override
public int insert(tuitionFeesModel model) {
    Integer studentID = null;
    Integer semesterID = null;

    // Truy vấn ID sinh viên từ tên sinh viên
    String studentIdQuery = "SELECT StudentID FROM Student WHERE FullName = ?";
    try (PreparedStatement ps = jdbcHelper.getConnection().prepareStatement(studentIdQuery)) {
        ps.setString(1, model.getFullName());
        ResultSet rs = ps.executeQuery(); // ✅ Dùng executeQuery()
        if (rs.next()) {
            studentID = rs.getInt("StudentID");
        }
    } catch (SQLException e) {
        e.printStackTrace();
        return -1;
    }

    // Truy vấn ID học kỳ từ tên học kỳ
    String semesterIdQuery = "SELECT SemesterID FROM Semester WHERE SemesterName = ?";
    try (PreparedStatement ps = jdbcHelper.getConnection().prepareStatement(semesterIdQuery)) {
        ps.setString(1, model.getSemesterName());
        ResultSet rs = ps.executeQuery(); // ✅ Dùng executeQuery()
        if (rs.next()) {
            semesterID = rs.getInt("SemesterID");
        }
    } catch (SQLException e) {
        e.printStackTrace();
        return -1;
    }

    // Kiểm tra nếu không tìm thấy StudentID hoặc SemesterID
    if (studentID == null || semesterID == null) {
        System.out.println("Không tìm thấy StudentID hoặc SemesterID phù hợp!");
        return -1;
    }

    // Thực hiện INSERT
    String sql = "INSERT INTO TuitionFees (StudentID, SemesterID, Amount, DueDate, PaymentStatus) VALUES (?, ?, ?, ?, ?)";
    return jdbcHelper.executeUpdate(sql,
            studentID,  // Chuyển từ FullName thành StudentID
            semesterID, // Chuyển từ SemesterName thành SemesterID
            model.getAmount(),
            model.getDueDate(),
            model.isPaymentStatus());
}


    @Override
    public int update(tuitionFeesModel model) {
    // Truy vấn ID sinh viên từ tên sinh viên
    String studentIdQuery = "SELECT StudentID FROM Student WHERE FullName = ?";
    Integer studentID = jdbcHelper.executeUpdate(studentIdQuery, model.getFullName());

    // Truy vấn ID học kỳ từ tên học kỳ
    String semesterIdQuery = "SELECT SemesterID FROM Semester WHERE SemesterName = ?";
    Integer semesterID = jdbcHelper.executeUpdate(semesterIdQuery, model.getSemesterName());

    if (studentID == null || semesterID == null) {
        System.out.println("Không tìm thấy StudentID hoặc SemesterID phù hợp!");
        return -1; // Trả về -1 nếu không tìm thấy
    }

    // Thực hiện UPDATE
    String sql = "UPDATE TuitionFees SET StudentID = ?, SemesterID = ?, Amount = ?, DueDate = ?, PaymentStatus = ? WHERE FeeID = ?";
    return jdbcHelper.executeUpdate(sql,
            studentID,   // Chuyển từ FullName thành StudentID
            semesterID,  // Chuyển từ SemesterName thành SemesterID
            model.getAmount(),
            model.getDueDate(),
            model.isPaymentStatus(),
            model.getFeeID());  // Điều kiện WHERE
}


    @Override
    public int delete(tuitionFeesModel model) {
        String query = "DELETE FROM TuitionFees WHERE FeeIDd=?";
        return jdbcHelper.executeUpdate(query, model.getFeeID());
    }

    @Override
public ArrayList<tuitionFeesModel> selectAll() {
    ArrayList<tuitionFeesModel> list = new ArrayList<>();
    String query = "SELECT tf.FeeIDd, s.FullName, sem.SemesterName, tf.Amount, tf.DueDate, tf.PaymentStatus "
                 + "FROM TuitionFees tf "
                 + "LEFT JOIN Student s ON tf.StudentID = s.StudentID "
                 + "LEFT JOIN Semester sem ON tf.SemesterID = sem.SemesterID";
    
    try (ResultSet rs = jdbcHelper.executeQuery(query)) {
        while (rs.next()) {
            list.add(new tuitionFeesModel(
                rs.getInt(1),     // fee_id
                rs.getString(2),  // full_name
                rs.getString(3),  // semester_name
                rs.getDouble(4),  // amount
                rs.getDate(5),    // due_date
                rs.getBoolean(6)  // payment_status
            ));
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return list;
}


    @Override
    public ArrayList<tuitionFeesModel> search(String column, String keyword) {
        ArrayList<tuitionFeesModel> list = new ArrayList<>();
        String sql = "SELECT tf.fee_id, tf.student_id, tf.semester_id, tf.amount, tf.due_date, tf.payment_status "
                + "FROM TuitionFees tf "
                + "LEFT JOIN Student s ON tf.student_id = s.student_id "
                + "WHERE " + column + " LIKE ?";
        try (ResultSet rs = jdbcHelper.executeQuery(sql, "%" + keyword + "%")) {
            while (rs.next()) {
                list.add(new tuitionFeesModel(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getInt(3),
                        rs.getDouble(4),
                        rs.getDate(5),
                        rs.getBoolean(6)
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public ArrayList<tuitionFeesModel> searchAll(String keyword) {
        ArrayList<tuitionFeesModel> list = new ArrayList<>();
        String sql = "SELECT tf.fee_id, tf.student_id, tf.semester_id, tf.amount, tf.due_date, tf.payment_status "
                + "FROM TuitionFees tf "
                + "LEFT JOIN Student s ON tf.student_id = s.student_id "
                + "WHERE tf.fee_id LIKE ? "
                + "OR tf.student_id LIKE ? "
                + "OR tf.semester_id LIKE ? "
                + "OR tf.amount LIKE ? "
                + "OR tf.due_date LIKE ? "
                + "OR tf.payment_status LIKE ?";

        try (ResultSet rs = jdbcHelper.executeQuery(sql,
                "%" + keyword + "%",
                "%" + keyword + "%",
                "%" + keyword + "%",
                "%" + keyword + "%",
                "%" + keyword + "%",
                "%" + keyword + "%")) {
            while (rs.next()) {
                list.add(new tuitionFeesModel(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getInt(3),
                        rs.getDouble(4),
                        rs.getDate(5),
                        rs.getBoolean(6)
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public boolean isFeeExists(int studentID, int semesterID) {
    String query = "SELECT COUNT(*) FROM TuitionFees WHERE StudentID = ? AND SemesterID = ?";
    
    try (ResultSet rs = jdbcHelper.executeQuery(query, studentID, semesterID)) {
        if (rs.next()) {
            return rs.getInt(1) > 0; // Nếu COUNT > 0 nghĩa là đã tồn tại học phí
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    
    return false; // Trả về false nếu có lỗi hoặc không tìm thấy dữ liệu
}
}
