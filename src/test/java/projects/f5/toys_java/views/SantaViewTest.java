package projects.f5.toys_java.views;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import projects.f5.toys_java.models.BadToy;
import projects.f5.toys_java.models.GoodToy;
import projects.f5.toys_java.models.Toy;

public class SantaViewTest {

    private SantaView santaView;
    private Scanner mockScanner;

    @BeforeEach
    void setUp() {
        mockScanner = mock(Scanner.class);
        santaView = new SantaView() {
            @Override
            public void closeScanner() {
            }
        };
        santaView.scanner = mockScanner;
    }

    @Test
    void testShowSantaMenu() {
        when(mockScanner.nextInt()).thenReturn(2);
        int choice = santaView.showSantaMenu();
        assertThat(choice, is(2));
    }

    @Test
    void testGetCsvFileName() {
        when(mockScanner.nextLine()).thenReturn("toys.csv");
        String filename = santaView.getCsvFilename();
        assertThat(filename, is("toys.csv"));
    }

    @Test
    void testDisplayToys_WithToys() {
        List<Toy> toys = List.of(
                new GoodToy("1", "G1", "Title1", "Brand1", 8, "Category1"),
                new BadToy("2", "B2", "Title2", "Content2"));

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        try {
            santaView.displayToys(toys);

            String expectedOutput = """
                    \033[34mToys List:\033[0m

                    \u001B[36m❄️G1
                    Title: Title1
                    Brand: Brand1
                    Recommended Age: 8
                    Category: Category1
                    -----------------------------\u001B[0m

                    \u001B[36m❄️B2
                    Title: Title2
                    Content: Content2
                    -----------------------------\u001B[0m
                    """;

            String actualOutput = outContent.toString();

            expectedOutput = expectedOutput.replaceAll("\\s+", " ").trim();
            actualOutput = actualOutput.replaceAll("\\s+", " ").trim();

            assertThat(actualOutput, is(expectedOutput));
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    void testDisplayToys_NoToys() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        try {
            santaView.displayToys(List.of());

            String expectedOutput = "\033[31mNo toys available.\033[0m\n";
            assertThat(outContent.toString().trim(), is(expectedOutput.trim()));
        } finally {
            System.setOut(originalOut);
        }
    }

}
