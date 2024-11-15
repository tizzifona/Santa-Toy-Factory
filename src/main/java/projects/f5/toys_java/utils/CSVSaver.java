package projects.f5.toys_java.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import projects.f5.toys_java.models.BadToy;
import projects.f5.toys_java.models.GoodToy;
import projects.f5.toys_java.models.Toy;

public class CSVSaver {
    public static void saveToysToCSV(String filename, List<Toy> toys) {
        String userHome = System.getProperty("user.home");
        String downloadPath = Paths.get(userHome, "Downloads", filename).toString();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(downloadPath))) {
            if (toys.isEmpty()) {
                writer.write("No toys found\n");
                System.out
                        .println("\033[33mNo toys found. File saved to Downloads folder: " + downloadPath + "\033[0m");
                return;
            }

            writer.write("List of All Toys\n\n");

            writer.write("Good Toys:\n");
            boolean hasGoodToys = false;
            int goodToyIndex = 1;
            for (Toy toy : toys) {
                if (toy instanceof GoodToy) {
                    hasGoodToys = true;
                    GoodToy goodToy = (GoodToy) toy;
                    writer.write(
                            goodToyIndex++ + ". "
                                    + "Toy id:" + goodToy.getCustomId() + ", " + "Title:" + goodToy.getTitle() + ", "
                                    + "Brand:"
                                    + goodToy.getBrand() + ", " + "Recommended age:" +
                                    goodToy.getRecommendedAge() + ", " + "Category:" + goodToy.getCategory() + "\n");
                }
            }

            if (!hasGoodToys) {
                writer.write("No good toys found\n");
            }

            writer.write("\nBad Toys:\n");
            boolean hasBadToys = false;
            int badToyIndex = 1;
            for (Toy toy : toys) {
                if (toy instanceof BadToy) {
                    hasBadToys = true;
                    BadToy badToy = (BadToy) toy;
                    writer.write(
                            badToyIndex++ + ". "
                                    + "Toy id:" + badToy.getCustomId() + ", " + "Title:" + badToy.getTitle() + ", "
                                    + "Content:"
                                    + badToy.getContent()
                                    + "\n");
                }
            }

            if (!hasBadToys) {
                writer.write("No bad toys found\n");
            }

            System.out.println("\033[32mFile saved to Downloads folder: " + downloadPath + "\033[0m");
        } catch (IOException e) {
            System.out.println("\033[31mError saving file!\033[0m");
            e.printStackTrace();
        }
    }
}
