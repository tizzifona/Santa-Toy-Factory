package projects.f5.toys_java.views;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HomeViewTest {

    private HomeView homeView;
    private Scanner mockScanner;

    @BeforeEach
    void setUp() {
        mockScanner = mock(Scanner.class);
        homeView = new HomeView() {
            @Override
            public void closeScanner() {
            }
        };
        homeView.scanner = mockScanner;
    }

    @Test
    void testShowMainMenu() {
        when(mockScanner.nextInt()).thenReturn(1);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        try {
            int choice = homeView.showMainMenu();
            assertThat(choice, is(1));

            String expectedOutput = """
                    ❄️ ❄️ ❄️ ❄️ ❄️ ❄️ ❄️ ❄️ ❄️ ❄️ ❄️
                    Start work session as:
                    \033[32m1. Elf\033[0m
                    \033[34m2. Santa Claus\033[0m
                    Select an option:
                    ❄️ ❄️ ❄️ ❄️ ❄️ ❄️ ❄️ ❄️ ❄️ ❄️ ❄️
                    """;

            String actualOutput = outContent.toString();

            expectedOutput = expectedOutput.replaceAll("\\s+", " ").trim();
            actualOutput = actualOutput.replaceAll("\\s+", " ").trim();

            assertThat(actualOutput, is(expectedOutput));
        } finally {
            System.setOut(originalOut);
        }
    }

}
