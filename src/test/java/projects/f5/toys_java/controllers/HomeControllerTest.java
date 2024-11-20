package projects.f5.toys_java.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import projects.f5.toys_java.views.HomeView;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class HomeControllerTest {
    @Mock
    private HomeView homeView;
    @Mock
    private ToyController toyController;

    private HomeController homeController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        homeController = new HomeController(homeView, toyController);
    }

    @Test
    void testShowMainMenu() {
        when(homeView.showMainMenu()).thenReturn(1);

        int result = homeController.showMainMenu();

        verify(homeView, times(1)).showMainMenu();
        assertEquals(1, result);
    }

    @Test
    void testRun_ElfSession() {
        when(homeView.showMainMenu()).thenReturn(1, 0);

        homeController.run();

        verify(homeView, times(2)).showMainMenu();
        verify(toyController, times(1)).runElfSession();
        verify(toyController, never()).runSantaSession();
    }

    @Test
    void testRun_SantaSession() {
        when(homeView.showMainMenu()).thenReturn(2, 0);

        homeController.run();

        verify(homeView, times(2)).showMainMenu();
        verify(toyController, times(1)).runSantaSession();
        verify(toyController, never()).runElfSession();
    }

}
