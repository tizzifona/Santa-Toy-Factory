package projects.f5.toys_java.controllers;

import projects.f5.toys_java.dtos.ToyDTO;
import projects.f5.toys_java.models.Toy;
import projects.f5.toys_java.repository.ToyRepository;
import projects.f5.toys_java.views.ElfView;
import projects.f5.toys_java.views.SantaView;

import java.util.List;

public class ToyController {
    private final ToyRepository toyRepository;
    private final ElfView elfView;
    private final SantaView santaView;

    public ToyController(ToyRepository toyRepository, ElfView elfView, SantaView santaView) {
        this.toyRepository = toyRepository;
        this.elfView = elfView;
        this.santaView = santaView;
    }

    public void runElfSession() {
        boolean running = true;
        while (running) {
            int option = elfView.showElfMenu();

            switch (option) {
                case 1:
                    handleAddToy();
                    break;
                case 2:
                    displayAllToys();
                    break;
                case 3:
                    handleDeleteToy();
                    break;
                case 4:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private void handleAddToy() {
        int childType = elfView.askForChildType();
        ToyDTO toyDTO = elfView.getToyInput(childType);
        toyRepository.addToy(toyDTO, childType);
    }

    private void displayAllToys() {
        List<Toy> toys = toyRepository.getAllToys();
        elfView.displayToys(toys);
    }

    private void handleDeleteToy() {
        String toyId = elfView.getToyIdForDeletion();
        toyRepository.deleteToy(toyId);
    }

    public void runSantaSession() {
        boolean running = true;
        while (running) {
            int option = santaView.showSantaMenu();

            switch (option) {
                case 1:
                    viewToysForGoodChildren();
                    break;
                case 2:
                    viewToysForBadChildren();
                    break;
                case 3:
                    saveToysToCSV();
                    break;
                case 4:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private void viewToysForGoodChildren() {
        List<Toy> goodToys = toyRepository.getToysForGoodChildren();
        santaView.displayToys(goodToys);
    }

    private void viewToysForBadChildren() {
        List<Toy> badToys = toyRepository.getToysForBadChildren();
        santaView.displayToys(badToys);
    }

    private void saveToysToCSV() {
        String filename = santaView.getCsvFilename();
        toyRepository.saveToysToCSV(filename);
    }
}
