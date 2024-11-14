package projects.f5.toys_java.repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import projects.f5.toys_java.bd.DatabaseConnection;
import projects.f5.toys_java.dtos.ToyDTO;
import projects.f5.toys_java.models.BadToy;
import projects.f5.toys_java.models.GoodToy;
import projects.f5.toys_java.models.Toy;

import java.io.*;

public class ToyRepository {

    public ToyRepository() {
    }

    public void addToy(ToyDTO toyDTO, int childType) {
        String prefix = childType == 1 ? "G" : "B";
        String customId = generateCustomId(prefix);

        String query = "INSERT INTO toys (custom_id, title, brand, recommended_age, category, content, child_type) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.connect();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, customId);
            stmt.setString(2, toyDTO.getTitle());
            stmt.setString(3, toyDTO.getBrand());
            stmt.setInt(4, toyDTO.getRecommendedAge());
            stmt.setString(5, toyDTO.getCategory());
            stmt.setString(6, toyDTO.getContent());
            stmt.setInt(7, childType);

            stmt.executeUpdate();
            System.out.println("\033[33mToy added successfully!\033[0m");
        } catch (SQLException e) {
            System.out.println("Error adding toy!");
            e.printStackTrace();
        }
    }

    private String generateCustomId(String prefix) {
        String query = "SELECT MAX(custom_id) AS max_id FROM toys WHERE custom_id LIKE ?";
        String maxId = null;

        try (Connection conn = DatabaseConnection.connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, prefix + "%");
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                maxId = rs.getString("max_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (maxId != null) {
            int currentNumber = Integer.parseInt(maxId.substring(1));
            return prefix + (currentNumber + 1);
        } else {
            return prefix + "1";
        }
    }

    public List<Toy> getToysForGoodChildren() {
        return getToysByChildType(1);
    }

    public List<Toy> getToysForBadChildren() {
        return getToysByChildType(2);
    }

    public List<Toy> getAllToys() {
        return getToysByChildType(0);
    }

    public List<Toy> getToysByChildType(int childType) {
        List<Toy> toys = new ArrayList<>();
        String query = (childType == 0) ? "SELECT * FROM toys" : "SELECT * FROM toys WHERE child_type = ?";

        try (Connection conn = DatabaseConnection.connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            if (childType != 0) {
                stmt.setInt(1, childType);
            }

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String id = rs.getString("id");
                String customId = rs.getString("custom_id");
                String title = rs.getString("title") != null ? rs.getString("title") : "";
                String brand = rs.getString("brand") != null ? rs.getString("brand") : "";
                int recommendedAge = rs.getInt("recommended_age");
                String category = rs.getString("category") != null ? rs.getString("category") : "";
                String content = rs.getString("content") != null ? rs.getString("content") : "";
                int type = rs.getInt("child_type");

                if (type == 1) {
                    toys.add(new GoodToy(id, customId, title, brand, recommendedAge, category));
                } else if (type == 2) {
                    toys.add(new BadToy(id, customId, title, content));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return toys;
    }

    public void deleteToy(String toyId) {
        String query = "DELETE FROM toys WHERE custom_id = ?";
        try (Connection conn = DatabaseConnection.connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, toyId);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("\033[33mToy deleted successfully!\033[0m");
            } else {
                System.out.println("Toy not found with ID " + toyId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveToysToCSV(String filename) {
        List<Toy> toys = getAllToys();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write("ID,Title,Brand,Recommended Age,Category,Content\n");

            for (Toy toy : toys) {

                if (toy instanceof GoodToy) {
                    GoodToy goodToy = (GoodToy) toy;
                    writer.write(goodToy.getId() + "," + goodToy.getTitle() + "," + goodToy.getBrand() + "," +
                            goodToy.getRecommendedAge() + "," + goodToy.getCategory() + ",\n");
                }

                else if (toy instanceof BadToy) {
                    BadToy badToy = (BadToy) toy;
                    writer.write(badToy.getId() + "," + badToy.getTitle() + ",,," + badToy.getContent() + "\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
