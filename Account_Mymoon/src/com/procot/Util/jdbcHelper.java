package com.procot.Util;

import java.sql.*;

public class jdbcHelper {

    private static final String host = "localhost";          // Địa chỉ CSDL
    private static final String database = "StudentManagement";        // Tên Database
    private static final String user = "sa";                    // Tên tài khoản SQL Server
    private static final String password = "123456789";    // Mật khẩu SQL Server
    private static final String url = "jdbc:sqlserver://" + host + ";databaseName=" + database + ";trustServerCertificate=true";

    // Phương thức kết nối database
    public static Connection getConnection() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver"); // Sử dụng cho những phiên bản thấp hơn
            return DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            throw new RuntimeException("Lỗi kết nối: " + e.getMessage(), e);
        }
    }

    // Phương thức thực thi INSERT, UPDATE, DELETE (Sử dụng try-with-resources để tự động đóng kết nối)
    public static int executeUpdate(String sql, Object... args) {
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            for (int i = 0; i < args.length; i++) {
                stmt.setObject(i + 1, args[i]);
            }
            return stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi SQL: " + e.getMessage(), e);
        }
    }

    // Phương thức thực thi SELECT danh sách
    public static ResultSet executeQuery(String sql, Object... args) {
        try {
            Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                stmt.setObject(i + 1, args[i]);
            }
            return stmt.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi SQL: " + e.getMessage(), e);
        }
    }

}
