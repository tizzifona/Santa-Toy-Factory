package projects.f5.toys_java.views;

import projects.f5.toys_java.models.BadToy;
import projects.f5.toys_java.models.GoodToy;
import projects.f5.toys_java.models.Toy;
import java.util.List;

public class SantaView extends View {

    public int showSantaMenu() {
        System.out.println();
        System.out.println("\033[34mToy Manager (Session Type: Santa)\033[0m\n");
        System.out.println("\033[34m1. View Good Children Toys\033[0m");
        System.out.println("\033[34m2. View Bad Children Toys\033[0m");
        System.out.println("\033[34m3. Save All Toys to CSV\033[0m");
        System.out.println("\033[31m4. Log Out\033[0m");
        System.out.print("Select an option: ");
        return scanner.nextInt();
    }

    public String getCsvFilename() {
        scanner.nextLine();
        System.out.print("Enter the filename to save: ");
        return scanner.nextLine();
    }

    public void displayToys(List<Toy> toys) {
        String cyanColor = "\u001B[36m";
        String resetColor = "\u001B[0m";

        if (toys.isEmpty()) {
            System.out.println("\033[31mNo toys available.\033[0m");
        } else {
            System.out.println();
            System.out.println("\033[34mToys List:\033[0m\n");

            for (Toy toy : toys) {
                System.out.println(cyanColor + "❄️" + toy.getCustomId());
                System.out.println("Title: " + toy.getTitle());

                if (toy instanceof GoodToy) {
                    GoodToy goodToy = (GoodToy) toy;
                    System.out.println("Brand: " + goodToy.getBrand());
                    System.out.println("Recommended Age: " + goodToy.getRecommendedAge());
                    System.out.println("Category: " + goodToy.getCategory());
                } else if (toy instanceof BadToy) {
                    BadToy badToy = (BadToy) toy;
                    System.out.println("Content: " + badToy.getContent());
                }

                System.out.println("-----------------------------" + resetColor);
            }
        }
    }

}
