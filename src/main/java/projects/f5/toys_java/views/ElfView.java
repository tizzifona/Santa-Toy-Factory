package projects.f5.toys_java.views;

import java.util.List;

import projects.f5.toys_java.dtos.ToyDTO;
import projects.f5.toys_java.models.Toy;

public class ElfView extends View {

    public int showElfMenu() {
        System.out.println();
        System.out.println("\033[32mToy Manager (Session Type: Elf)\033[0m\n");
        System.out.println("\033[32m1. Add Toy\033[0m");
        System.out.println("\033[32m2. View All Toys\033[0m");
        System.out.println("\033[32m3. Delete Toy\033[0m");
        System.out.println("\033[31m4. Log Out\033[0m");
        System.out.print("Select an option: ");
        return scanner.nextInt();
    }

    public int askForChildType() {
        System.out.println();
        System.out.print("\033[32mFor Child...:\033[0m");
        System.out.println();
        System.out.println("\033[32m1. Good\033[0m");
        System.out.println("\033[32m2. Naughty\033[0m");
        return scanner.nextInt();
    }

    public ToyDTO getToyInput(int childType) {
        scanner.nextLine();
        System.out.print("\033[32mEnter title: \033[0m");
        String title = scanner.nextLine();
        if (childType == 1) {
            System.out.print("\033[32mEnter brand: \033[0m");
            String brand = scanner.nextLine();
            System.out.print("\033[32mEnter recommended age: \033[0m");
            int recommendedAge = scanner.nextInt();
            scanner.nextLine();
            System.out.print("\033[32mEnter category: \033[0m");
            String category = scanner.nextLine();
            return new ToyDTO(title, brand, recommendedAge, category, null, childType);
        } else {
            System.out.print("\033[32mEnter content: \033[0m");
            String content = scanner.nextLine();
            return new ToyDTO(title, "N/A", 0, null, content, childType);
        }
    }

    public String getToyIdForDeletion() {
        scanner.nextLine();
        System.out.print("Enter the toy ID to delete: ");
        return scanner.nextLine();
    }

    public void displayToys(List<Toy> toys) {
        for (Toy toy : toys) {
            System.out.println(toy);
        }
    }
}
