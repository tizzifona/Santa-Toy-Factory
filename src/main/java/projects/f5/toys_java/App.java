package projects.f5.toys_java;

import projects.f5.toys_java.bd.DatabaseConnection;
import projects.f5.toys_java.bd.DatabaseManipulation;
import projects.f5.toys_java.controllers.HomeController;
import projects.f5.toys_java.controllers.ToyController;
import projects.f5.toys_java.repository.ToyRepository;
import projects.f5.toys_java.views.ElfView;
import projects.f5.toys_java.views.HomeView;
import projects.f5.toys_java.views.SantaView;

public class App {
    public static void main(String[] args) {
        DatabaseManipulation.createTableIfNotExists();

        HomeView homeView = new HomeView();
        ElfView elfView = new ElfView();
        SantaView santaView = new SantaView();

        ToyRepository toyRepository = new ToyRepository();
        ToyController toyController = new ToyController(toyRepository, elfView, santaView);
        HomeController homeController = new HomeController(homeView, toyController);

        boolean appRunning = true;
        while (appRunning) {
            int roleOption = homeController.showMainMenu();
            switch (roleOption) {
                case 1:
                    toyController.runElfSession();
                    break;
                case 2:
                    toyController.runSantaSession();
                    break;
                default:
                    appRunning = false;
                    break;
            }
        }

        DatabaseConnection.closeConnection();
        homeView.closeScanner();
        elfView.closeScanner();
        santaView.closeScanner();
    }
}
