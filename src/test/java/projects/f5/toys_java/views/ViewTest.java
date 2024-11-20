package projects.f5.toys_java.views;

import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.Scanner;
import org.junit.jupiter.api.Test;

public class ViewTest {
    @Test
    void testCloseScanner() {
        View mockView = new View() {
        };
        mockView.scanner = new Scanner(System.in);
        mockView.closeScanner();

        assertThrows(IllegalStateException.class, () -> {
            mockView.scanner.nextLine();
        });
    }
}
