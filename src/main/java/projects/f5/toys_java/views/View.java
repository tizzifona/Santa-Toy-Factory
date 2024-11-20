package projects.f5.toys_java.views;

import java.util.Scanner;

public abstract class View {
    protected Scanner scanner = new Scanner(System.in);

    public void closeScanner() {
        scanner.close();
    }
}
