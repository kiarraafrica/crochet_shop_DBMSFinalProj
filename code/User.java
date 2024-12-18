import java.util.Scanner;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.DriverManager;

public class User {
    private String currentUsername;
    private UserManager userManager;
    private Scanner input = new Scanner(System.in);

    public User() {
        this.currentUsername = null;
        this.userManager = new UserManager();
    }

    public String getCurrentUsername() {
        return currentUsername;
    }
    
    public void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public void signUp() {
        clearScreen();
        System.out.println("+----------------------------------+");
        System.out.println("|               SIGN UP            |");
        System.out.println("+----------------------------------+");

        String username;
        while (true) {
            username = userManager.setUsernameInput();
            if (username.equals("0")) {
                clearScreen();
                return;
            }
            if (!userManager.usernameExists(username)) break;
            System.out.print("Username already exists. Choose a different one.");
        }

        String password = userManager.setPasswordInput();
        String email = userManager.setEmailInput();
        String address = userManager.setAddressInput();
        String phoneNumber = userManager.setPhoneNumberInput();

        userManager.saveUser(username, password, email, address, phoneNumber);

        clearScreen();
        System.out.println("Sign-up successful!");
        System.out.println();
    }

    public boolean logIn() {
        clearScreen();
        System.out.println("+----------------------------------+");
        System.out.println("|               LOG IN             |");
        System.out.println("+----------------------------------+");

        String username = userManager.setUsernameInput();
        if (username.equals("0")) {
            clearScreen();
            return false;
        }

        System.out.print("Enter password: ");
        String password = input.nextLine();

        if (userManager.loadUser(username, password)) {
            clearScreen();
            System.out.println("Login successful!");
            this.currentUsername = username;
            return true;
        } else {
            clearScreen();
            System.out.println("Invalid username or password. Please try again.");
            return false;
        }
    }

    private class UserManager {
        private Scanner input = new Scanner(System.in);
        private Connection conn;

        public UserManager() {
            try {
                String url = "jdbc:mysql://localhost:3306/FinalProj_DBMS";
                String user = "root";
                String password = "MySQL0109030705";

                conn = DriverManager.getConnection(url, user, password);
                System.out.println("Database connection established successfully.");
            }
            catch (SQLException e) {
                System.out.println("Database connection error: " + e.getMessage());
                conn = null;
            }
        }

        public String setUsernameInput() {
            String username;

            while (true) {
                System.out.print("Enter a username: ");
                username = input.nextLine();
                if (username.equalsIgnoreCase("0")) return "0";
                if (username.length() >= 6) break;
                System.out.println("Username must be at least 6 characters long.");
            }
            return username;
        }

        public boolean usernameExists(String username) {
            if (conn == null) {
                System.out.println("Database connection is not initialized.");
                return false;
            }

            String query = "SELECT COUNT(*) FROM users WHERE username = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1,username);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
            catch (SQLException e) {
                System.out.println("Error checking username existence: " + e.getMessage());
            }
            return false;
        }

        public String setPasswordInput() {
            String password;

            while (true) {
                System.out.print("Enter a password: ");
                password = input.nextLine();
                if (password.length() >= 8) break;
                System.out.println("Password must be at least 8 characters long.");
            }
            return password;
        }

        public String setEmailInput() {
            String email;

            while (true) {
                System.out.print("Enter your email: ");
                email = input.nextLine();
                if (email.contains("@") && email.contains(".")) break;
                System.out.println("Invalid email format. Please try again.");
            }
            return email;
        }

        public String setAddressInput() {
            System.out.print("Enter your address: ");
            return input.nextLine();
        }

        public String setPhoneNumberInput() {
            String phoneNumber;

            while (true) {
                System.out.print("Enter your phone number: ");
                phoneNumber = input.nextLine();
                if (phoneNumber.matches("\\d{11}")) break;
                System.out.println("Phone number must be 11 digits. Please try again.");
            }
            return phoneNumber;
        }

        public void saveUser(String username, String password, String email, String address, String phoneNumber) {
            try {
                String customerID = generateCustomerID();
                String query = "INSERT INTO users (customer_id, username, password, email, address, phone_number) VALUES (?, ?, ?, ?, ?, ?)";
                try (PreparedStatement stmt = conn.prepareStatement(query)) {
                    stmt.setString(1, customerID);
                    stmt.setString(2, username);
                    stmt.setString(3, password);
                    stmt.setString(4, email);
                    stmt.setString(5, address);
                    stmt.setString(6, phoneNumber);
                    stmt.executeUpdate();
                }
            }
            catch (SQLException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        private String generateCustomerID() {
            try {
                String query = "SELECT MAX(customer_id) FROM users";
                try(Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
                    if (rs.next()) {
                        String lastID = rs.getString(1);
                        if (lastID != null) {
                            int nextID = Integer.parseInt(lastID) + 1;
                            return String.format("%03d", nextID);
                        }
                    }
                }
            }
            catch (SQLException e) {
                System.out.println("Error generating customer ID: " + e.getMessage());
            }
            return "001";
        }

        public boolean loadUser(String username, String password) {
            try {
                String query = "SELECT * FROM users WHERE username = ? AND password = ?";
                try (PreparedStatement stmt = conn.prepareStatement(query)) {
                    stmt.setString(1, username);
                    stmt.setString(2, password);
                    ResultSet rs = stmt.executeQuery();
                    if (rs.next()) {
                        return true;
                    }
                }
            }
            catch (SQLException e) {
                System.out.println("Error: " + e.getMessage());
            }
            return false;
        }
    }
}
