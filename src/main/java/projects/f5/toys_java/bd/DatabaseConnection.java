package projects.f5.toys_java.bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {

    private static final String URL = "jdbc:sqlite:toys.db";
    private static Connection connection = null;

    public static Connection connect() {
        if (connection == null || !isValidConnection()) {
            try {
                connection = DriverManager.getConnection(URL);
            } catch (SQLException e) {
                System.out.println("Error connecting to the database.");
                e.printStackTrace();
            }
        }
        return connection;
    }

    private static boolean isValidConnection() {
        try {
            return connection != null && !connection.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }

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
        Connection conn = connect();

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println("Error creating the table.");
            e.printStackTrace();
        }
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                if (!connection.isClosed()) {
                    connection.close();
                    System.out.println("Database connection closed.");
                }
            } catch (SQLException e) {
                System.out.println("Failed to close the connection.");
                e.printStackTrace();
            }
        }
    }
}
