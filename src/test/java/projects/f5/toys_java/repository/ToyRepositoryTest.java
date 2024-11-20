package projects.f5.toys_java.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import projects.f5.toys_java.bd.DatabaseConnection;
import projects.f5.toys_java.dtos.ToyDTO;
import projects.f5.toys_java.models.GoodToy;
import projects.f5.toys_java.models.BadToy;
import projects.f5.toys_java.models.Toy;
import projects.f5.toys_java.utils.CSVSaver;

import java.sql.*;
import java.util.List;

public class ToyRepositoryTest {

    private ToyRepository toyRepository;
    private Connection connection;

    @BeforeEach
    public void setUp() throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite::memory:");
        Statement stmt = connection.createStatement();
        stmt.execute("CREATE TABLE toys (id TEXT, custom_id TEXT, title TEXT, " +
                "brand TEXT, recommended_age INTEGER, category TEXT, content TEXT, child_type INTEGER)");

        stmt.execute(
                "INSERT INTO toys (id, custom_id, title, brand, recommended_age, category, content, child_type) VALUES "
                        +
                        "('1', 'mockedCustomId', 'Super Car', 'ToyBrand', 5, 'Vehicles', '', 1)");
        stmt.execute(
                "INSERT INTO toys (id, custom_id, title, brand, recommended_age, category, content, child_type) VALUES "
                        +
                        "('2', 'mockedCustomId2', 'Broken Doll', '', 0, '', 'This toy is for bad children', 2)");

        toyRepository = new ToyRepository();
    }

    @Test
    public void testGetToysForGoodChildren() throws SQLException {
        try (MockedStatic<DatabaseConnection> dbConnectionMock = mockStatic(DatabaseConnection.class)) {
            dbConnectionMock.when(DatabaseConnection::connect).thenReturn(connection);

            List<Toy> toys = toyRepository.getToysForGoodChildren();

            assertNotNull(toys);
            assertEquals(1, toys.size());
            assertTrue(toys.get(0) instanceof GoodToy);
            GoodToy goodToy = (GoodToy) toys.get(0);
            assertEquals("Super Car", goodToy.getTitle());
        }
    }

    @Test
    public void testGetToysForBadChildren() throws SQLException {
        try (MockedStatic<DatabaseConnection> dbConnectionMock = mockStatic(DatabaseConnection.class)) {
            dbConnectionMock.when(DatabaseConnection::connect).thenReturn(connection);

            List<Toy> toys = toyRepository.getToysForBadChildren();

            assertNotNull(toys);
            assertEquals(1, toys.size());
            assertTrue(toys.get(0) instanceof BadToy);
            BadToy badToy = (BadToy) toys.get(0);
            assertEquals("Broken Doll", badToy.getTitle());
        }
    }

    @Test
    public void testGetAllToys() throws SQLException {
        try (MockedStatic<DatabaseConnection> dbConnectionMock = mockStatic(DatabaseConnection.class)) {
            dbConnectionMock.when(DatabaseConnection::connect).thenReturn(connection);

            List<Toy> toys = toyRepository.getAllToys();

            assertNotNull(toys);
            assertEquals(2, toys.size());
        }
    }

    @Test
    public void testAddToyWithValidGoodToy() {
        ToyDTO toyDTO = new ToyDTO("New Toy", "BrandX", 3, "Vehicles", "", 1);
        try (MockedStatic<DatabaseConnection> dbConnectionMock = mockStatic(DatabaseConnection.class)) {
            dbConnectionMock.when(DatabaseConnection::connect).thenReturn(connection);
            toyRepository.addToy(toyDTO, 1);
        }
    }

    @Test
    public void testAddToyWithValidBadToy() {
        ToyDTO toyDTO = new ToyDTO("Bad Toy", "", 0, "", "For bad children", 2);
        try (MockedStatic<DatabaseConnection> dbConnectionMock = mockStatic(DatabaseConnection.class)) {
            dbConnectionMock.when(DatabaseConnection::connect).thenReturn(connection);
            toyRepository.addToy(toyDTO, 2);
        }
    }

    @Test
    public void testAddToyWithNullToyDTO() {
        toyRepository.addToy(null, 1);
    }

    @Test
    public void testDeleteToySuccessfully() throws SQLException {
        try (MockedStatic<DatabaseConnection> dbConnectionMock = mockStatic(DatabaseConnection.class)) {
            dbConnectionMock.when(DatabaseConnection::connect).thenReturn(connection);

            List<Toy> toysBefore = toyRepository.getAllToys();
            assertEquals(2, toysBefore.size());

            toyRepository.deleteToy("mockedCustomId");

            List<Toy> toysAfter = toyRepository.getAllToys();
            assertEquals(0, toysAfter.size());
        }
    }

    @Test
    public void testDeleteToyWithNonExistingId() throws SQLException {
        try (MockedStatic<DatabaseConnection> dbConnectionMock = mockStatic(DatabaseConnection.class)) {
            dbConnectionMock.when(DatabaseConnection::connect).thenReturn(connection);

            toyRepository.deleteToy("nonExistingId");
        }
    }

    @Test
    public void testSaveToysToCSV() {
        try (MockedStatic<CSVSaver> csvSaverMock = mockStatic(CSVSaver.class)) {
            csvSaverMock.when(() -> CSVSaver.saveToysToCSV(anyString(), anyList())).thenAnswer(invocation -> null);

            toyRepository.saveToysToCSV("toys.csv");

            csvSaverMock.verify(() -> CSVSaver.saveToysToCSV(eq("toys.csv"), anyList()));
        }
    }
}
