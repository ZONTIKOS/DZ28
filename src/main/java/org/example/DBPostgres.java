package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBPostgres {
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "olejka";

    public static Connection connect() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println("❌ Connection failed!");
            e.printStackTrace();
            return null;
        }
    }

    public static void save(String nickname, String message) {
        String sql = "INSERT INTO logstable (nickname, message) VALUES (?, ?)";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nickname);
            pstmt.setString(2, message);
            pstmt.executeUpdate();
            System.out.println("✅ Data saved successfully!");
        } catch (SQLException e) {
            System.out.println("❌ Error saving data: " + e.getMessage());
        }
    }

    public static List<String> read() {
        List<String> dataList = new ArrayList<>();
        String sql = "SELECT id, nickname, message, created_at FROM logstable ORDER BY created_at DESC";
        try (Connection conn = connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                dataList.add("[" + rs.getInt("id") + "] " + rs.getString("nickname") + ": " + rs.getString("message") + " (" + rs.getTimestamp("created_at") + ")");
            }
        } catch (SQLException e) {
            System.out.println("❌ Error reading data: " + e.getMessage());
        }
        return dataList;
    }

    public static void update(int id, String newMessage) {
        String sql = "UPDATE logstable SET message = ? WHERE id = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newMessage);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
            System.out.println("✅ Data updated successfully!");
        } catch (SQLException e) {
            System.out.println("❌ Error updating data: " + e.getMessage());
        }
    }

    public static void delete(int id) {
        String sql = "DELETE FROM logstable WHERE id = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("✅ Data deleted successfully!");
        } catch (SQLException e) {
            System.out.println("❌ Error deleting data: " + e.getMessage());
        }
    }
}
