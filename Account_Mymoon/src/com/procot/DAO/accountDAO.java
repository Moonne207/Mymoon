/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.accountModel;
import Util.jdbcHelper;
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

    public accountModel checkLogin(String username, String password) {
    String query = "SELECT PasswordHash, Fullname, RoleID, StudentID, lecturerID FROM Account WHERE Username = ?";
    try (ResultSet rs = jdbcHelper.executeQuery(query, username)) {
        if (rs.next()) {
            String storedHash = rs.getString("PasswordHash"); // Lấy mật khẩu đã mã hóa
            if (BCrypt.checkpw(password, storedHash)) { // So sánh mật khẩu nhập vào và mật khẩu đã mã hóa
                String accountName = rs.getString("Fullname");
                int roleID = rs.getInt("RoleID");
                int studentID = rs.getObject("StudentID") != null ? rs.getInt("StudentID") : -1;
                int lecturerID = rs.getObject("lecturerID") != null ? rs.getInt("lecturerID") : -1;

                return new accountModel(accountName, username, "", roleID, studentID, lecturerID);
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return null;
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
    String updateQuery = "UPDATE Account SET PasswordHash = ? WHERE Email = ?";

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

    String query;
    if (model.getStudentID() == -1 && model.getLecturerID() == -1) {
        query = "INSERT INTO Account (Fullname, Email, Username, PasswordHash, RoleID, StudentID, lecturerID) " +
                "VALUES (?, ?, ?, ?, ?, NULL, NULL)";
        return jdbcHelper.executeUpdate(query, model.getFullnamee(), model.getEmail(),
                model.getUserName(), hashedPassword, model.getRoleID());
    } else if (model.getLecturerID() == -1) {
        query = "INSERT INTO Account (Fullname, Email, Username, PasswordHash, RoleID, StudentID, lecturerID) " +
                "VALUES (?, ?, ?, ?, ?, ?, NULL)";
        return jdbcHelper.executeUpdate(query, model.getFullnamee(), model.getEmail(),
                model.getUserName(), hashedPassword, model.getRoleID(), model.getStudentID());
    } else {
        query = "INSERT INTO Account (Fullname, Email, Username, PasswordHash, RoleID, StudentID, lecturerID) " +
                "VALUES (?, ?, ?, ?, ?, NULL, ?)";
        return jdbcHelper.executeUpdate(query, model.getFullnamee(), model.getEmail(),
                model.getUserName(), hashedPassword, model.getRoleID(), model.getLecturerID());
    }
}


    @Override
    public int update(accountModel model) {
        String hashedPassword = BCrypt.hashpw(model.getPassword(), BCrypt.gensalt(12)); // Mã hóa mật khẩu
        if (model.getStudentID() == -1 && model.getLecturerID() == -1) {
            String query = "UPDATE Account SET Fullname=?, Email=?,PasswordHash=?,RoleID=?,StudentID=NULL,lecturerID=NULL WHERE Username=?";
            return jdbcHelper.executeUpdate(query, model.getFullnamee(), model.getEmail(), hashedPassword, model.getRoleID(),
                    model.getUserName());
        } else if (model.getLecturerID() == -1) {
            String query = "UPDATE Account SET Fullname=?, Email=?,PasswordHash=?,RoleID=?,StudentID=?,lecturerID=NULL WHERE Username=?";
            return jdbcHelper.executeUpdate(query, model.getFullnamee(), model.getEmail(), hashedPassword, model.getRoleID(),
                    model.getStudentID(), model.getUserName());
        } else {
            String query = "UPDATE Account SET Fullname=?, Email=?,PasswordHash=?,RoleID=?,StudentID=NULL,lecturerID=? WHERE Username=?";
            return jdbcHelper.executeUpdate(query, model.getFullnamee(), model.getEmail(), hashedPassword, model.getRoleID(),
                    model.getLecturerID(), model.getUserName());
        }
    }

    @Override
    public int delete(accountModel model) {
        String query = "DELETE FROM Account WHERE Username=?";
        return jdbcHelper.executeUpdate(query, model.getUserName());
    }

    @Override
    public ArrayList<accountModel> selectAll() {
        ArrayList<accountModel> list = new ArrayList<>();
        String query = "SELECT tk.Fullname,tk.Email,tk.Username,tk.Email,tk.PasswordHash,tk.RoleID,sv.Fullname,gv.full_name "
                + "FROM Account tk "
                + "LEFT JOIN Student sv ON sv.StudentID = tk.StudentID "
                + "LEFT JOIN Lecturer gv ON gv.lecturerID = tk.lecturerID ";
        try (ResultSet rs = jdbcHelper.executeQuery(query)) {
            while (rs.next()) {
                list.add(new accountModel(
                        rs.getString(1), 
                        rs.getString(2), 
                        rs.getString(3), 
                        rs.getString(4), 
                        rs.getString(5), 
                        rs.getString(6), 
                        rs.getString(7)
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
                        rs.getInt(5), 
                        rs.getInt(6),
                        rs.getInt(7)
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
        String sql = "SELECT * FROM Account WHERE Fullname LIKE ? "
                + "OR Email LIKE ? "
                + "OR RoleID LIKE ? "
                + "OR StudentID LIKE ? "
                + "OR lecturerID LIKE ? "
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
                        rs.getInt(5), 
                        rs.getInt(6),
                        rs.getInt(7)
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
                        rs.getInt(5),  
                        rs.getInt(6), 
                        rs.getInt(7)
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
