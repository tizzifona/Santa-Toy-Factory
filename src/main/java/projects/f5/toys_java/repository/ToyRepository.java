package projects.f5.toys_java.repository;

import projects.f5.toys_java.models.Toy;
import projects.f5.toys_java.models.GoodToy;
import projects.f5.toys_java.models.BadToy;
import projects.f5.toys_java.bd.DatabaseConnection;
import projects.f5.toys_java.dtos.ToyDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ToyRepository {

    public ToyRepository() {
        DatabaseConnection.createTableIfNotExists();
    }

    public void saveToy(ToyDTO toyDTO, String customId) {
        String query = "INSERT INTO toys (custom_id, title, brand, recommended_age, category, content, child_type) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, customId);
            stmt.setString(2, toyDTO.getTitle());

            if (toyDTO.getChildType() == 1) {
                stmt.setString(3, toyDTO.getBrand());
                stmt.setInt(4, toyDTO.getRecommendedAge());
                stmt.setString(5, toyDTO.getCategory());
                stmt.setString(6, null);
            } else {
                stmt.setString(3, null);
                stmt.setNull(4, Types.INTEGER);
                stmt.setString(5, null);
                stmt.setString(6, toyDTO.getContent());
            }

            stmt.setInt(7, toyDTO.getChildType());
            stmt.executeUpdate();
            System.out.println("\033[33mToy added successfully!\033[0m");

        } catch (SQLException e) {
            System.out.println("Error saving toy!");
            e.printStackTrace();
        }
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

                if (rs.getInt("child_type") == 1) {
                    String brand = rs.getString("brand");
                    int recommendedAge = rs.getInt("recommended_age");
                    String category = rs.getString("category");
                    toys.add(new GoodToy(id, customId, title, brand, recommendedAge, category));
                } else {
                    String content = rs.getString("content");
                    toys.add(new BadToy(id, customId, title, content));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return toys;
    }

    public String getMaxCustomId(String prefix) {
        String query = "SELECT MAX(custom_id) AS max_id FROM toys WHERE custom_id LIKE ?";
        try (Connection conn = DatabaseConnection.connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, prefix + "%");
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("max_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
