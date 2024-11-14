package projects.f5.toys_java.controllers;

import projects.f5.toys_java.views.HomeView;

public class HomeController {
    private final HomeView homeView;
    private final ToyController toyController;

    public HomeController(HomeView homeView, ToyController toyController) {
        this.homeView = homeView;
        this.toyController = toyController;
    }

    public void run() {
        boolean running = true;
        while (running) {
            int choice = homeView.showMainMenu();
            switch (choice) {
                case 1:
                    toyController.runElfSession();
                    break;
                case 2:
                    toyController.runSantaSession();
                    break;
                default:
                    System.out.println("Invalid choice.");
                    running = false;
                    break;
            }
        }
    }
}
