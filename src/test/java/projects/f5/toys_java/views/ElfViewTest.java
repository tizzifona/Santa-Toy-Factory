package projects.f5.toys_java.views;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import projects.f5.toys_java.dtos.ToyDTO;
import projects.f5.toys_java.models.Toy;

public class ElfViewTest {
    private ElfView elfView;
    private Scanner mockScanner;

    @BeforeEach
    void setUp() {

        mockScanner = mock(Scanner.class);
        elfView = new ElfView() {
            @Override
            public void closeScanner() {
            }
        };
        elfView.scanner = mockScanner;
    }

    @Test
    void testShowElfMenu() {
        when(mockScanner.nextInt()).thenReturn(1);
        int choice = elfView.showElfMenu();
        assertThat(choice, is(1));
    }

    @Test
    void testAskForChildType() {
        when(mockScanner.nextInt()).thenReturn(1);
        int childType = elfView.askForChildType();
        assertThat(childType, is(1));
    }

    @Test
    void testGetToyInputForGoodChild() {
        when(mockScanner.nextLine())
                .thenReturn("")
                .thenReturn("Title")
                .thenReturn("Brand")
                .thenReturn("")
                .thenReturn("Category");

        when(mockScanner.nextInt())
                .thenReturn(5);

        ToyDTO toyDTO = elfView.getToyInput(1);

        assertThat(toyDTO.getTitle(), is("Title"));
        assertThat(toyDTO.getBrand(), is("Brand"));
        assertThat(toyDTO.getRecommendedAge(), is(5));
        assertThat(toyDTO.getCategory(), is("Category"));
        assertThat(toyDTO.getContent(), is(nullValue()));
    }

    @Test
    void testGetToyInputForNaughtyChild() {
        when(mockScanner.nextLine())
                .thenReturn("")
                .thenReturn("Title")
                .thenReturn("Content");

        ToyDTO toyDTO = elfView.getToyInput(2);

        assertThat(toyDTO.getTitle(), is("Title"));
        assertThat(toyDTO.getBrand(), is("N/A"));
        assertThat(toyDTO.getRecommendedAge(), is(0));
        assertThat(toyDTO.getCategory(), is(nullValue()));
        assertThat(toyDTO.getContent(), is("Content"));
    }

    @Test
    void testGetToyIdForDeletion() {
        when(mockScanner.nextLine()).thenReturn("B1");
        String toyId = elfView.getToyIdForDeletion();
        assertThat(toyId, is("B1"));
    }

    @Test
    void testDisplayToys() {
        List<Toy> toys = List.of(
                new Toy("1", "B1", "Title", 2),
                new Toy("2", "G1", "Title1", 1));

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        try {
            elfView.displayToys(toys);

            String expectedOutput = "Toy{id='1', customId='B1', title='Title', childType=2}\n" +
                    "Toy{id='2', customId='G1', title='Title1', childType=1}\n";

            assertThat(outContent.toString().trim(), is(expectedOutput.trim()));
        } finally {

            System.setOut(originalOut);
        }
    }

}
