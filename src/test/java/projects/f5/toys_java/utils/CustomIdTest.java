package projects.f5.toys_java.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import projects.f5.toys_java.bd.DatabaseConnection;

public class CustomIdTest {

    private Connection mockConnection;
    private PreparedStatement mockStatement;
    private ResultSet mockResultSet;

    @BeforeEach
    void setUp() throws SQLException {
        mockConnection = mock(Connection.class);
        mockStatement = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
    }

    @Test
    void testGenerateCustomId() throws SQLException {
        try (MockedStatic<DatabaseConnection> mockedDbConnection = mockStatic(DatabaseConnection.class)) {
            mockedDbConnection.when(DatabaseConnection::connect).thenReturn(mockConnection);

            ResultSet resultSetForGenerateNewId = mock(ResultSet.class);
            when(mockStatement.executeQuery())
                    .thenReturn(resultSetForGenerateNewId)
                    .thenReturn(mockResultSet);

            when(resultSetForGenerateNewId.next()).thenReturn(true);
            when(resultSetForGenerateNewId.getString("max_id")).thenReturn("T100");
            when(mockResultSet.next()).thenReturn(true);
            when(mockResultSet.getInt(1)).thenReturn(0);

            String customId = CustomId.generateCustomId("T");

            assertNotNull(customId);
            assertEquals("T101", customId);

            verify(mockStatement, times(2)).executeQuery();
            verify(mockStatement).setString(1, "T%");
            verify(mockStatement).setString(1, "T101");
        }
    }

    @Test
    void testIsCustomIdExists() throws SQLException {
        try (MockedStatic<DatabaseConnection> mockedDbConnection = mockStatic(DatabaseConnection.class)) {
            mockedDbConnection.when(DatabaseConnection::connect).thenReturn(mockConnection);

            when(mockResultSet.next()).thenReturn(true);
            when(mockResultSet.getInt(1)).thenReturn(1);

            boolean exists = CustomId.isCustomIdExists("T101");

            assertTrue(exists);

            verify(mockStatement).setString(eq(1), eq("T101"));
            verify(mockStatement).executeQuery();

            when(mockResultSet.getInt(1)).thenReturn(0);

            exists = CustomId.isCustomIdExists("T999");

            assertFalse(exists);

            verify(mockStatement).setString(eq(1), eq("T999"));
            verify(mockStatement, times(2)).executeQuery();
        }
    }
}
