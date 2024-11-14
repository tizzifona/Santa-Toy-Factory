package projects.f5.toys_java.models;

public class Toy {
    protected String id;
    protected String customId;
    protected String title;
    protected int childType;

    public Toy(String id, String customId, String title, int childType) {
        this.id = id;
        this.customId = customId;
        this.title = title;
        this.childType = childType;
    }

    public String getId() {
        return id;
    }

    public String getCustomId() {
        return customId;
    }

    public String getTitle() {
        return title;
    }

    public int getChildType() {
        return childType;
    }

}
