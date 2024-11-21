package projects.f5.toys_java.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import projects.f5.toys_java.models.GoodToy;
import projects.f5.toys_java.models.Toy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class CSVSaverTest {

    private BufferedWriter writerMock;

    @BeforeEach
    void setUp() {
        writerMock = Mockito.mock(BufferedWriter.class);
    }

    @Test
    void testSaveToysToCSV_ExceptionHandling() throws IOException {
        List<Toy> toys = new ArrayList<>();
        toys.add(new GoodToy("id1", "customId1", "Toy1", "Brand1", 5, "Category1"));
        doThrow(new IOException("Test exception")).when(writerMock).write(anyString());
        CSVSaver.saveToysToCSV("test.csv", toys);
    }

    @Test
    void testCSVSaverConstructor() {
        new CSVSaver();
    }

}
