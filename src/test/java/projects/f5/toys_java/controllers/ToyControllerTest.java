package projects.f5.toys_java.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import projects.f5.toys_java.dtos.ToyDTO;
import projects.f5.toys_java.models.Toy;
import projects.f5.toys_java.repository.ToyRepository;
import projects.f5.toys_java.views.ElfView;
import projects.f5.toys_java.views.SantaView;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class ToyControllerTest {

    @Mock
    private ToyRepository toyRepository;

    @Mock
    private ElfView elfView;

    @Mock
    private SantaView santaView;

    @InjectMocks
    private ToyController toyController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRunElfSession_AddToy() {
        when(elfView.showElfMenu()).thenReturn(1, 4);
        when(elfView.askForChildType()).thenReturn(1);
        ToyDTO toyDTO = new ToyDTO("Super Car", "BrandX", 5, "Vehicles", "", 1);
        when(elfView.getToyInput(1)).thenReturn(toyDTO);

        toyController.runElfSession();

        verify(elfView, times(2)).showElfMenu();
        verify(elfView, times(1)).askForChildType();
        verify(elfView, times(1)).getToyInput(1);
        verify(toyRepository, times(1)).addToy(toyDTO, 1);
    }

    @Test
    void testRunElfSession_DisplayAllToys() {
        when(elfView.showElfMenu()).thenReturn(2, 4);
        when(toyRepository.getAllToys()).thenReturn(List.of(
                new Toy("1", "custom1", "Car", 1),
                new Toy("2", "custom2", "Doll", 2)));

        toyController.runElfSession();

        verify(elfView, times(1)).displayToys(anyList());
        assertThat(toyRepository.getAllToys(), hasSize(2));
        assertThat(toyRepository.getAllToys(), contains(
                hasProperty("title", equalTo("Car")),
                hasProperty("title", equalTo("Doll"))));
    }

    @Test
    void testRunElfSession_DeleteToy() {
        when(elfView.showElfMenu()).thenReturn(3, 4);
        when(elfView.getToyIdForDeletion()).thenReturn("custom1");

        toyController.runElfSession();

        verify(elfView, times(1)).getToyIdForDeletion();
        verify(toyRepository, times(1)).deleteToy(eq("custom1"));
    }

    @Test
    void testRunSantaSession_ViewToysForGoodChildren() {
        when(santaView.showSantaMenu()).thenReturn(1, 4);
        when(toyRepository.getToysForGoodChildren()).thenReturn(List.of(
                new Toy("1", "custom1", "Super Car", 1)));

        toyController.runSantaSession();

        verify(santaView, times(1)).displayToys(anyList());
        assertThat(toyRepository.getToysForGoodChildren(), hasSize(1));
        assertThat(toyRepository.getToysForGoodChildren(), contains(
                hasProperty("title", equalTo("Super Car"))));
    }

    @Test
    void testRunSantaSession_ViewToysForBadChildren() {
        when(santaView.showSantaMenu()).thenReturn(2, 4);
        when(toyRepository.getToysForBadChildren()).thenReturn(List.of(
                new Toy("2", "custom2", "Broken Doll", 2)));

        toyController.runSantaSession();

        verify(santaView, times(1)).displayToys(anyList());
        assertThat(toyRepository.getToysForBadChildren(), hasSize(1));
        assertThat(toyRepository.getToysForBadChildren(), contains(
                hasProperty("title", equalTo("Broken Doll"))));
    }

    @Test
    void testRunSantaSession_SaveToysToCSV() {
        when(santaView.showSantaMenu()).thenReturn(3, 4);
        when(santaView.getCsvFilename()).thenReturn("toys.csv");

        toyController.runSantaSession();

        verify(santaView, times(1)).getCsvFilename();
        verify(toyRepository, times(1)).saveToysToCSV(eq("toys.csv"));
    }
}
