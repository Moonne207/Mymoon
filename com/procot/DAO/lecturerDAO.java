/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.lecturerModel;
import Util.jdbcHelper;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author skmyg
 */
public class lecturerDAO implements interfaceDAO<lecturerModel> {

    public static lecturerDAO getInstance() {
        return new lecturerDAO();
    }

    @Override
    public int insert(lecturerModel model) {
        String sql = "INSERT INTO Lecturer (full_name,Gender,address,Email,phone_number,majorID,Status) VALUES (?,?,?,?,?)";
        return jdbcHelper.executeUpdate(sql,
                model.getLecturerName(),
                model.getGender(),
                model.getAddress(),
                model.getEmail(),
                model.getPhoneNumber(),
                model.getmajorName(),
                model.getstatus()
        );
    }

    @Override
    public int update(lecturerModel model) {
        String sql = "UPDATE Lecturer SET full_name=?,Gender=?,address=?,Email=?,phone_number=?,majorID=?,Status=? WHERE lecturerID=?";
        return jdbcHelper.executeUpdate(sql,
                model.getLecturerName(),
                model.getGender(),
                model.getAddress(),
                model.getEmail(),
                model.getPhoneNumber(),
                model.getmajorID(),
                model.getstatus(),
                model.getLecturerID()
        );
    }

    @Override
    public int delete(lecturerModel model) {
        String sql = "DELETE FROM Lecturer WHERE lecturerID=?";
        return jdbcHelper.executeUpdate(sql, model.getLecturerID());
    }

    @Override
    public ArrayList<lecturerModel> selectAll() {
        ArrayList<lecturerModel> list = new ArrayList<>();
        String sql = "SELECT * FROM Lecturer sv ";
        try (ResultSet rs = jdbcHelper.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new lecturerModel(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getString(8)
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public ArrayList<lecturerModel> search(String column, String keyword) {
        ArrayList<lecturerModel> list = new ArrayList<>();

        String sql = "SELECT * FROM Lecturer WHERE " + column + " LIKE ?";
        try (ResultSet rs = jdbcHelper.executeQuery(sql, "%" + keyword + "%")) {
            while (rs.next()) {
                list.add(new lecturerModel(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getString(8)
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public ArrayList<lecturerModel> searchAll(String keyword) {
        ArrayList<lecturerModel> list = new ArrayList<>();
        String sql = "SELECT *"
                + "FROM Lecturer "
                + "WHERE lecturerID LIKE ? "
                + "OR full_name LIKE ? "
                + "OR Gender LIKE ? "
                + "OR address LIKE ? "
                + "OR Email LIKE ? "
                + "OR phone_number LIKE ? "
                + "OR majorID LIKE ?"
                + "OR Status LIKE ?";

        try (ResultSet rs = jdbcHelper.executeQuery(sql,
                "%" + keyword + "%",
                "%" + keyword + "%",
                "%" + keyword + "%",
                "%" + keyword + "%",
                "%" + keyword + "%",
                "%" + keyword + "%",
                "%" + keyword + "%",
                "%" + keyword + "%")) {
            while (rs.next()) {
                list.add(new lecturerModel(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getString(8)
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public lecturerModel selectByEmail(String email) {
        lecturerModel sm = null;
        String sql = "SELECT * FROM Lecturer WHERE Email=?";
        try (ResultSet rs = jdbcHelper.executeQuery(sql, email)) {
            while (rs.next()) {
                return new lecturerModel(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getString(8)
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sm;
    }

    public lecturerModel selectByPhoneNumber(String phoneNumber) {
        lecturerModel sm = null;
        String sql = "SELECT * FROM Lecturer WHERE phone_number=?";
        try (ResultSet rs = jdbcHelper.executeQuery(sql, phoneNumber)) {
            while (rs.next()) {
                return new lecturerModel(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getString(8)
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sm;
    }

    public String selectNameByID(int lecturerId) {
        String sql = "SELECT full_name FROM Lecturer WHERE lecturerID = ?";
        try (ResultSet rs = jdbcHelper.executeQuery(sql, lecturerId)) {
            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Không có thông tin";
    }
}
