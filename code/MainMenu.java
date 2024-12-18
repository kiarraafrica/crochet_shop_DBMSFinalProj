import java.util.InputMismatchException;
import java.util.Scanner;

public class MainMenu {
    private Scanner input = new Scanner(System.in);
    private User user = new User();
    private CrochetShop crochetShop = new CrochetShop();
    private AdminMenu adminMenu = new AdminMenu();

    public void mainMenu() {
        boolean exit = false;

        while (!exit) {
            System.out.println("+----------------------------------+");
            System.out.println("              Main Menu");
            System.out.println("+----------------------------------+");
            System.out.println("            1. Sign Up");
            System.out.println("             2. Log In");
            System.out.println("          3. Admin Log In");
            System.out.println("-----------------------------------");
            System.out.println("              4. Exit");
            System.out.println("+----------------------------------+");
            System.out.print("  > Enter your choice (1-4): ");
            int choice = 0;

            try {
                choice = input.nextInt();
            }
            catch (InputMismatchException e) {
                System.out.println("Please enter a valid choice from the menu.");
                input.next();
                continue;
            }

            switch(choice) {
                case 1:
                    user.signUp();
                    break;
                case 2:
                    if (user.logIn()) {
                        crochetShop.crochetShop(user.getCurrentUsername());
                    }
                    break;
                case 3: 
                    adminLogin();
                    break;
                case 4:
                    System.out.println("Exiting...");
                    exit = true;
                    break;
                default:
                    System.out.println("Please enter a valid choice from the menu.");
            }
            input.nextLine();
        }
        input.close();
    }

    private void adminLogin() {
        String adminUsername = "admin";
        String adminPassword = "admin123";

        System.out.println("+----------------------------------+");
        System.out.println("             ADMIN LOGIN");
        System.out.println("+----------------------------------+");
        System.out.print(" > Enter admin username: ");
        String username = input.next();
        System.out.print(" > Enter admin password: ");
        String password = input.next();

        if (username.equals(adminUsername) && password.equals(adminPassword)) {
            System.out.println("Admin login successful!");
            adminMenu.adminMenu();
        }
        else {
            System.out.println("Invalid admin credentials.");
        }
    }
}