package projects.f5.toys_java.views;

public class SantaView extends View {
    public int showSantaMenu() {
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
}
