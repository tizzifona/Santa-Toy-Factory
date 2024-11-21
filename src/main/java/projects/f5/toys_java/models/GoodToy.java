package projects.f5.toys_java.models;

public class GoodToy extends Toy {
    private String brand;
    private int recommendedAge;
    private String category;

    public GoodToy(String id, String customId, String title, String brand, int recommendedAge, String category) {
        super(id, customId, title, 1);
        this.brand = brand;
        this.recommendedAge = recommendedAge;
        this.category = category;
    }

    public String getBrand() {
        return brand;
    }

    public int getRecommendedAge() {
        return recommendedAge;
    }

    public String getCategory() {
        return category;
    }

    @Override
    public String toString() {
        String toyDetails = "❄️";
        toyDetails += customId + ".\n";

        String cyanColor = "\u001B[36m";
        String resetColor = "\u001B[0m";

        toyDetails += cyanColor + "Title: " + title + "\n" + resetColor;
        toyDetails += cyanColor + "Brand: " + brand + "\n" + resetColor;
        toyDetails += cyanColor + "Recommended Age: " + recommendedAge + "\n" + resetColor;
        toyDetails += cyanColor + "Category: " + category + "\n" + resetColor;

        return toyDetails;
    }

}
