/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.procot.DAO;

/**
 *
 * @author anh74
 */
import com.procot.Model.roleModel;
import com.procot.Util.jdbcHelper;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class roleDAO {
    private static roleDAO instance;

    public static roleDAO getInstance() {
        if (instance == null) {
            instance = new roleDAO();
        }
        return instance;
    }

    // Lấy danh sách tất cả vai trò
   public List<String> getAllRoleNames() {
    List<String> roleNames = new ArrayList<>();
    String query = "SELECT RoleName FROM Role"; // Chỉ lấy tên
    try (ResultSet rs = jdbcHelper.executeQuery(query)) {
        while (rs.next()) {
            roleNames.add(rs.getString("RoleName")); // Lấy RoleName
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return roleNames;
}


   
   public List<roleModel> getAllRoless() {
    List<roleModel> roleList = new ArrayList<>();
    String query = "SELECT RoleID, RoleName FROM Role"; // Lấy cả ID và tên
    try (ResultSet rs = jdbcHelper.executeQuery(query)) {
        while (rs.next()) {
            int roleID = rs.getInt("RoleID");
            String roleName = rs.getString("RoleName");
            roleList.add(new roleModel(roleID, roleName));
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return roleList;
}



    // Lấy RoleID từ RoleName
    public int getRoleIDByName(String roleName) {
    String query = "SELECT RoleID FROM Role WHERE RoleName = ?";
    try (ResultSet rs = jdbcHelper.executeQuery(query, roleName)) {
        if (rs.next()) {
            return rs.getInt("RoleID"); // Trả về số nguyên RoleID
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return -1; // Trả về -1 nếu không tìm thấy
}

    
    

}
