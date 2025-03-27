/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.procot.DAO;

import com.procot.Model.semesterModel;
import com.procot.Util.jdbcHelper;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author anh74
 */
public class semesterDAO implements interfaceDAO<semesterModel>{

    public static semesterDAO getInstance() {
            return new semesterDAO();
        }
    
    @Override
    public int insert(semesterModel model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int update(semesterModel model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int delete(semesterModel model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<semesterModel> selectAll() {
        ArrayList<semesterModel> list = new ArrayList<>();
        String query = "SELECT s.SemesterID, s.SemesterName, s.AcademicYear, s.StartDate, s.EndDate, s.SemesterStatus "
                     + "FROM Semester s";

         try (ResultSet rs = jdbcHelper.executeQuery(query)) {
        while (rs.next()) {
                list.add(new semesterModel(
                    rs.getInt(1),    // SemesterID
                    rs.getString(2), // SemesterName
                    rs.getString(3), // AcademicYear
                    rs.getDate(4),   // StartDate
                    rs.getDate(5),   // EndDate
                    rs.getBoolean(6) // SemesterStatus
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    
    }
    

    @Override
    public ArrayList<semesterModel> search(String column, String keyword) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<semesterModel> searchAll(String keyword) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
