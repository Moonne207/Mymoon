/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.procot.DAO;

import com.procot.Model.accountModel;
import com.procot.Util.jdbcHelper;
import java.sql.*;
import java.util.ArrayList;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author skmyg
 */
public class accountDAO implements interfaceDAO<accountModel> {

    public static accountDAO getInstance() {
        return new accountDAO();
    }

    public accountModel checkLogin(String email, String password) {
    String query = "SELECT Username, Password, RoleID FROM Account WHERE Email=?";
    ResultSet rs = jdbcHelper.executeQuery(query, email);
    
    try {
        if (rs.next()) {
            String hashedPassword = rs.getString("Password");
            if (BCrypt.checkpw(password, hashedPassword)) { // Kiểm tra mật khẩu
                int roleID = rs.getInt("RoleID");
                return new accountModel(rs.getString("Username"), email, roleID);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null; // Đăng nhập thất bại
}



    public boolean forgetPassword(String email) {
        String query = "SELECT * FROM Account WHERE Email = ?";
        try (ResultSet rs = jdbcHelper.executeQuery(query, email);) {
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updatePassword(String email, String newPassword) {
    String checkQuery = "SELECT 1 FROM Account WHERE Email = ?";
    String updateQuery = "UPDATE Account SET Password = ? WHERE Email = ?";

    try (Connection conn = jdbcHelper.getConnection();
         PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {
        
        checkStmt.setString(1, email);
        try (ResultSet rs = checkStmt.executeQuery()) {
            if (!rs.next()) {
                //System.out.println("Email không tồn tại trong hệ thống.");
                return false;
            }
        }

        try (PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {
            String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt(12)); // Mã hóa mật khẩu
            updateStmt.setString(1, hashedPassword);
            updateStmt.setString(2, email);

            int rowsAffected = updateStmt.executeUpdate();
            return rowsAffected > 0;
        }
    } catch (SQLException e) {
        System.err.println("Lỗi khi cập nhật mật khẩu: " + e.getMessage());
    }
    return false;
}


    @Override
   public int insert(accountModel model) {
    String hashedPassword = BCrypt.hashpw(model.getPassword(), BCrypt.gensalt(12)); // Mã hóa mật khẩu

    // Kiểm tra RoleID hợp lệ
    if (!isRoleIDValid(model.getRoleID())) {
        System.out.println("Lỗi: RoleID không tồn tại!");
        return -1; // Trả về -1 nếu RoleID không hợp lệ
    }

    // Xây dựng câu lệnh SQL
    String query = "INSERT INTO Account (FullName, Email, Username, Password, RoleID) " +
                   "VALUES (?, ?, ?, ?, ?)";
    
    return jdbcHelper.executeUpdate(query, model.getAccountName(), model.getEmail(),
                                    model.getUserName(), hashedPassword, model.getRoleID());
}



    @Override
    public int update(accountModel model) {
    String hashedPassword = BCrypt.hashpw(model.getPassword(), BCrypt.gensalt(12)); // Mã hóa mật khẩu

    // Xây dựng câu lệnh SQL
    String query = "UPDATE Account SET FullName=?, Email=?, Password=?, RoleID=? WHERE Username=?";
    
    return jdbcHelper.executeUpdate(query, model.getAccountName(), model.getEmail(),
                                    hashedPassword, model.getRoleID(), model.getUserName());
}

    @Override
//public int delete(accountModel model) {
//    String query = "DELETE FROM Account WHERE Username = ?";
//    return jdbcHelper.executeUpdate(query, model.getCorrectUsername());
//}

public int delete(accountModel model) {
    String query = "DELETE FROM Account WHERE Email = ?";
    return jdbcHelper.executeUpdate(query, model.getUserName()); 
}





    @Override
    public ArrayList<accountModel> selectAll() {
    ArrayList<accountModel> list = new ArrayList<>();
    String query = "SELECT tk.FullName, tk.Email, tk.Username, tk.Password, tk.RoleID, r.RoleName "
                 + "FROM Account tk "
                 + "LEFT JOIN Role r ON tk.RoleID = r.RoleID";
    try (ResultSet rs = jdbcHelper.executeQuery(query)) {
        while (rs.next()) {
            list.add(new accountModel(
                rs.getString(1), // FullName
                rs.getString(2), // Email
                rs.getString(3), // Username
                rs.getString(4), // Password
                rs.getString(6)  // RoleName (Sửa từ 5 -> 6)
            ));
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return list;
}


    @Override
    public ArrayList<accountModel> search(String column, String keyword) {
        ArrayList<accountModel> list = new ArrayList<>();

        String sql = "SELECT * FROM Account WHERE " + column + " LIKE ?";
        try (ResultSet rs = jdbcHelper.executeQuery(sql, "%" + keyword + "%")) {
            while (rs.next()) {
                list.add(new accountModel(
                        rs.getString(1), 
                        rs.getString(2), 
                        rs.getString(3), 
                        rs.getString(4), 
                        rs.getInt(5) 
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public ArrayList<accountModel> searchAll(String keyword) {
        ArrayList<accountModel> list = new ArrayList<>();
        String sql = "SELECT * FROM Account WHERE FullName LIKE ? "
                + "OR Email LIKE ? "
                + "OR RoleID LIKE ? "
                + "OR Username LIKE ?";

        try (ResultSet rs = jdbcHelper.executeQuery(sql,
                "%" + keyword + "%",
                "%" + keyword + "%",
                "%" + keyword + "%",
                "%" + keyword + "%",
                "%" + keyword + "%",
                "%" + keyword + "%")) {
            while (rs.next()) {
                list.add(new accountModel(
                        rs.getString(1), 
                        rs.getString(2), 
                        rs.getString(3), 
                        rs.getString(4), 
                        rs.getInt(5)
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public accountModel selectByEmail(String email) {
        accountModel sm = null;
        String sql = "SELECT * FROM Account WHERE Email=?";
        try (ResultSet rs = jdbcHelper.executeQuery(sql, email)) {
            while (rs.next()) {
                return new accountModel(
                        rs.getString(1), 
                        rs.getString(2), 
                        rs.getString(3), 
                        rs.getString(4), 
                        rs.getInt(5)
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sm;
    }
    
    public boolean isUsernameExists(String username) {
    String sql = "SELECT COUNT(*) FROM Account WHERE Username = ?";
    try (Connection conn = jdbcHelper.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, username);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getInt(1) > 0; // Trả về true nếu username đã tồn tại
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}
    
    public boolean isEmailExists(String email) {
    String query = "SELECT COUNT(*) FROM Account WHERE Email = ?";
    try (ResultSet rs = jdbcHelper.executeQuery(query, email)) {
        if (rs.next() && rs.getInt(1) > 0) {
            return true; // Email đã tồn tại
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return false; // Email chưa tồn tại
}
    
        public boolean isRoleIDValid(int roleID) {
    String query = "SELECT COUNT(*) FROM Role WHERE RoleID = ?";
    try (Connection conn = jdbcHelper.getConnection();
         PreparedStatement ps = conn.prepareStatement(query)) {
        ps.setInt(1, roleID);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}
    
    
}
