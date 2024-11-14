package projects.f5.toys_java.dtos;

public class ToyDTO {
    private String title;
    private String brand;
    private int recommendedAge;
    private String category;
    private String content;
    private int childType;

    public ToyDTO(String title, String brand, int recommendedAge, String category, String content, int childType) {
        this.title = title;
        this.brand = brand;
        this.recommendedAge = recommendedAge;
        this.category = category;
        this.content = content;
        this.childType = childType;
    }

    public String getTitle() {
        return title;
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

    public String getContent() {
        return content;
    }

    public int getChildType() {
        return childType;
    }

}
