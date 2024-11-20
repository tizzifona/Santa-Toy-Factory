package projects.f5.toys_java.bd;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManipulation {

    public static void createTableIfNotExists() {
        String sql = "CREATE TABLE IF NOT EXISTS toys (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "custom_id TEXT UNIQUE, " +
                "title TEXT NOT NULL, " +
                "brand TEXT, " +
                "recommended_age INTEGER, " +
                "category TEXT, " +
                "content TEXT, " +
                "child_type INTEGER NOT NULL)";

        try (Connection conn = DatabaseConnection.connect();
                Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println("Error creating the table.");
            e.printStackTrace();
        }
    }
}
