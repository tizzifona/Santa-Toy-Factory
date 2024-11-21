package projects.f5.toys_java.repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import projects.f5.toys_java.bd.DatabaseConnection;
import projects.f5.toys_java.dtos.ToyDTO;
import projects.f5.toys_java.models.BadToy;
import projects.f5.toys_java.models.GoodToy;
import projects.f5.toys_java.models.Toy;
import projects.f5.toys_java.utils.CSVSaver;
import projects.f5.toys_java.utils.CustomId;

public class ToyRepository {

    public ToyRepository() {
    }

    public void addToy(ToyDTO toyDTO, int childType) {

        if (toyDTO == null) {
            System.out.println("Invalid toy data! Object is null.");
            return;
        }

        if (childType == 1) {
            if (isNullOrEmpty(toyDTO.getTitle()) ||
                    isNullOrEmpty(toyDTO.getBrand()) ||
                    isNullOrEmpty(toyDTO.getCategory()) ||
                    toyDTO.getRecommendedAge() <= 0) {
                System.out.println("Invalid toy data for a good toy! Please ensure all fields are filled correctly.");
                return;
            }
        } else if (childType == 2) {
            if (isNullOrEmpty(toyDTO.getTitle()) || isNullOrEmpty(toyDTO.getContent())) {
                System.out.println("Invalid toy data for a bad toy! Please ensure title and content are filled.");
                return;
            }
        } else {
            System.out.println("Invalid child type! Must be 1 (Good) or 2 (Bad).");
            return;
        }

        String prefix = childType == 1 ? "G" : "B";
        String customId = CustomId.generateCustomId(prefix);

        while (CustomId.isCustomIdExists(customId)) {
            System.out.println("Duplicate custom_id found: " + customId + ". Regenerating...");
            customId = CustomId.generateCustomId(prefix);
        }

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
        CSVSaver.saveToysToCSV(filename, toys);
    }

    private boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

}
