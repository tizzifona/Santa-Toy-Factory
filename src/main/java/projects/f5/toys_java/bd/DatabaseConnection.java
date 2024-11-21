package projects.f5.toys_java.bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
