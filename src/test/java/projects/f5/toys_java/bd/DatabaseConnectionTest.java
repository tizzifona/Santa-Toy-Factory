package projects.f5.toys_java.bd;

import static org.mockito.Mockito.*;

import java.sql.Connection;
import java.sql.SQLException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import java.sql.DriverManager;

public class DatabaseConnectionTest {

    private Connection mockConnection;

    @BeforeEach
    void setUp() {
        mockConnection = mock(Connection.class);
    }

    @AfterEach
    void tearDown() {

        DatabaseConnection.closeConnection();
    }

    @Test
    void testCloseConnection() throws SQLException {

        try (MockedStatic<DriverManager> mockedDriverManager = mockStatic(DriverManager.class)) {

            mockedDriverManager.when(() -> DriverManager.getConnection("jdbc:sqlite:toys.db"))
                    .thenReturn(mockConnection);

            DatabaseConnection.connect();

            when(mockConnection.isClosed()).thenReturn(false);

            DatabaseConnection.closeConnection();

            verify(mockConnection).close();
        }
    }

}
