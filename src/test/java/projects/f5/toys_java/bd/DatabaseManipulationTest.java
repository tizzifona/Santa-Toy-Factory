package projects.f5.toys_java.bd;

import static org.mockito.Mockito.*;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

public class DatabaseManipulationTest {

    private Connection mockConnection;
    private Statement mockStatement;

    @BeforeEach
    void setUp() {
        mockConnection = mock(Connection.class);
        mockStatement = mock(Statement.class);
    }

    @Test
    void testCreateTableIfNotExists() throws SQLException {
        try (MockedStatic<DatabaseConnection> mockedDbConnection = mockStatic(DatabaseConnection.class)) {
            mockedDbConnection.when(DatabaseConnection::connect).thenReturn(mockConnection);

            when(mockConnection.createStatement()).thenReturn(mockStatement);

            DatabaseManipulation.createTableIfNotExists();

            String expectedSql = "CREATE TABLE IF NOT EXISTS toys (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "custom_id TEXT UNIQUE, " +
                    "title TEXT NOT NULL, " +
                    "brand TEXT, " +
                    "recommended_age INTEGER, " +
                    "category TEXT, " +
                    "content TEXT, " +
                    "child_type INTEGER NOT NULL)";
            verify(mockStatement).execute(expectedSql);
        }
    }
}
