package projects.f5.toys_java.models;

public class BadToy extends Toy {
    private String content;

    public BadToy(String id, String customId, String title, String content) {
        super(id, customId, title, 2);
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        String toyDetails = "❄️";
        toyDetails += customId + ".\n";

        String cyanColor = "\u001B[36m";
        String resetColor = "\u001B[0m";

        toyDetails += cyanColor + "Title: " + title + "\n" + resetColor;
        toyDetails += cyanColor + "Content: " + content + "\n" + resetColor;

        return toyDetails;
    }

}
