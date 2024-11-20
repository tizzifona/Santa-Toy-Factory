package projects.f5.toys_java.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import projects.f5.toys_java.bd.DatabaseConnection;

public class CustomId {
    public static String generateCustomId(String prefix) {
        String query = "SELECT MAX(custom_id) AS max_id FROM toys WHERE custom_id LIKE ?";
        String maxId = null;

        try (Connection conn = DatabaseConnection.connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, prefix + "%");
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                maxId = rs.getString("max_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (maxId != null) {
            int currentNumber = Integer.parseInt(maxId.substring(1));
            return prefix + (currentNumber + 1);
        } else {
            return prefix + "1";
        }
    }

}
