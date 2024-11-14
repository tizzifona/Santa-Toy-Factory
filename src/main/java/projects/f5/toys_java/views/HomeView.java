package projects.f5.toys_java.views;

public class HomeView extends View {
    public int showMainMenu() {
        System.out.println();
        System.out.println("❄️ ❄️ ❄️ ❄️ ❄️ ❄️ ❄️ ❄️ ❄️ ❄️ ❄️");
        System.out.println("Start work session as:");
        System.out.println("\033[32m1. Elf\033[0m");
        System.out.println("\033[34m2. Santa Claus\033[0m");
        System.out.print("Select an option: ");
        return scanner.nextInt();
    }
}
