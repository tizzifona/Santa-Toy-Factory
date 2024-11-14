package projects.f5.toys_java.bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {

    private static final String URL = "jdbc:sqlite:toys.db";
    private static Connection connection = null;

    public static Connection connect() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(URL);
                System.out.println("Database connected successfully.");
            } catch (SQLException e) {
                System.out.println("Error connecting to the database.");
                e.printStackTrace();
            }
        }
        return connection;
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
        try (Statement stmt = connect().createStatement()) {
            stmt.execute(sql);
            System.out.println("Table created or already exists.");
        } catch (SQLException e) {
            System.out.println("Error creating the table.");
            e.printStackTrace();
        }
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Database connection closed.");
            } catch (SQLException e) {
                System.out.println("Failed to close the connection.");
                e.printStackTrace();
            }
        }
    }
}
