import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class CrochetShop {
    private Scanner input = new Scanner(System.in);
    private User user = new User();
    private ProductCatalog productCatalog = new ProductCatalog();
    private ShoppingCart shoppingCart = new ShoppingCart();
    private Wallet wallet = new Wallet();
    
    public void crochetShop(String username) {
        boolean exit = false;

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/FinalProj_DBMS",
                "root",
                "MySQL0109030705"
            );
        }
        catch (SQLException e) {
            System.out.println("Error connecting to the database: " + e.getMessage());
            return;
        }

        while (!exit) {
            user.clearScreen();
            System.out.println("+---------------------------------+");
            System.out.println("          Crochet Shop Menu");
            System.out.println("+---------------------------------+");
            System.out.println("         1. Product Catalog");
            System.out.println("          2. Shopping Cart");
            System.out.println("             3. Wallet");
            System.out.println("-----------------------------------");
            System.out.println("           4. Log Out");
            System.out.println("+---------------------------------+");
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
                    productCatalogMenu(connection);
                    break;
                case 2:
                    shoppingCartMenu(connection, username);
                    break;
                case 3:
                    walletMenu();
                    break;
                case 4:
                    user.clearScreen();
                    System.out.println("Logging out...");
                    System.out.println();
                    return;
                default:
                    System.out.println("Please enter a valid choice from the menu.");
            }
            input.nextLine();
        }
        
        try {
            connection.close();
        }
        catch (SQLException e) {
            System.out.println("Error closing the database connection: " + e.getMessage());
        }
        input.close();
    }

    private void productCatalogMenu(Connection connection) {
        boolean back = false;
        
        while (!back) {
            user.clearScreen();
            System.out.println("+---------------------------------+");
            System.out.println("        Product Catalog Menu");
            System.out.println("+---------------------------------+");
            System.out.println("      1. View Crochet Tools");
            System.out.println("          2. View Yarns");
            System.out.println("     3. View Crocheted Items");
            System.out.println("-----------------------------------");
            System.out.println("            4. Exit");
            System.out.println("+---------------------------------+");
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

            switch (choice) {
                case 1:
                    productCatalog.viewCrochetTools(connection);
                    break;
                case 2:
                    productCatalog.viewYarns(connection);
                    break;
                case 3:
                    productCatalog.viewCrochetedItems(connection);
                    break;
                case 4:
                    back = true;
                    break;
                default:
                    System.out.println("Please enter a valid choice from the menu.");
            }
            input.nextLine();
        }
    }
    
    private void shoppingCartMenu(Connection connection, String username) {
        boolean back = false;

        while (!back) {
            user.clearScreen();
            System.out.println("+---------------------------------+");
            System.out.println("         Shopping Cart Menu");
            System.out.println("+---------------------------------+");
            System.out.println("            1. View Cart");
            System.out.println("           2. Add to Cart");
            System.out.println("         3. Remove from Cart");
            System.out.println("             4. Checkout");
            System.out.println("-----------------------------------");
            System.out.println("               5. Exit");
            System.out.println("+---------------------------------+");
            System.out.print("  > Enter your choice (1-5): ");
            int choice = 0;

            try {
                choice = input.nextInt();
            }
            catch (InputMismatchException e) {
                System.out.println("Please enter a valid choice from the menu.");
                input.next();
                continue;
            }

            switch (choice) {
                case 1:
                    shoppingCart.displayCart();
                    break;
                case 2:
                    addToCart(connection);
                    break;
                case 3:
                    removeFromCart(connection);
                    break;
                case 4:
                    checkoutCart(connection, username);
                    break;
                case 5:
                    back = true;
                    break;
                default:
                    System.out.println("Please enter a valid choice from the menu.");
            }
            input.nextLine();
        }
    }

    private void addToCart(Connection connection) {
        user.clearScreen();
        System.out.println("+---------------------------------+");
        System.out.println("             Add to Cart");
        System.out.println("+---------------------------------+");
    
        System.out.print(" > Enter product ID to add to cart: ");
        input.nextLine();
        String productID = input.nextLine();
    
        System.out.print(" > Enter quantity: ");
        int productQuantity = input.nextInt();
    
        shoppingCart.addItem(connection, productID, productQuantity);
    }
    

    private void removeFromCart(Connection connection) {
        user.clearScreen();
        System.out.println("+---------------------------------+");
        System.out.println("          Remove from Cart");
        System.out.println("+---------------------------------+");
        System.out.print(" > Enter product ID to remove from cart: ");
        String productID = input.nextLine();

        shoppingCart.removeItem(productID);
    }

    private void checkoutCart(Connection connection, String username) {
        if (shoppingCart.cartItems.isEmpty()) {
            System.out.println("Your cart is empty.");
            return;
        }

        try {
            String customerID = null;
            String userQuery = "SELECT customer_id FROM users WHERE username = ?";
            try (PreparedStatement userStmt = connection.prepareStatement(userQuery)) {
                userStmt.setString(1, username);
                ResultSet rs = userStmt.executeQuery();
                if (rs.next()) {
                    customerID = rs.getString("customer_id");
                }
            }

            if (customerID == null) {
                System.out.println("Unable to retrieve customer ID.");
                return;
            }

            String query = "INSERT INTO orders (customer_id, username, product_id, product_type, name, price, quantity, total_price, date_time) VALUES (?, ?, ?, ?, ?, ?, ?, ?, NOW())";

            for (ShoppingCart.CartItem item : shoppingCart.cartItems) {
                double totalPrice = item.getPrice() * item.getQuantity();

                String productType = determineProductType(item.getProductID(), connection);

                try (PreparedStatement stmt = connection.prepareStatement(query)) {
                    stmt.setString(1, customerID);
                    stmt.setString(2, username); 
                    stmt.setString(3, item.getProductID());
                    stmt.setString(4, productType);
                    stmt.setString(5, item.getName());   
                    stmt.setDouble(6, item.getPrice()); 
                    stmt.setInt(7, item.getQuantity());
                    stmt.setDouble(8, totalPrice);  
                    stmt.executeUpdate();
                }
            }
            System.out.println("Checkout successful! Your order has been placed.");
            shoppingCart.cartItems.clear();
        }
        catch (SQLException e) {
            System.out.println("Error saving checkout details: " + e.getMessage());
        }
    }

    private String determineProductType(String productID, Connection connection) {
        try (PreparedStatement stmt = connection.prepareStatement(
            "SELECT 'tool' AS type FROM crochet_tools WHERE product_id = ? UNION ALL "
          + "SELECT 'yarn' AS type FROM yarns WHERE product_id = ? UNION ALL "
          + "SELECT 'item' AS type FROM crocheted_items WHERE product_id = ?")) {
            stmt.setString(1, productID);
            stmt.setString(2, productID);
            stmt.setString(3, productID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("type");
            }
        } catch (SQLException e) {
            System.out.println("Error determining product type: " + e.getMessage());
        }
        return null;
    }
    

    private void walletMenu() {
        boolean back = false;

        while (!back) {
            user.clearScreen();
            System.out.println("+---------------------------------+");
            System.out.println("               Wallet");
            System.out.println("+---------------------------------+");
            System.out.println("          1. View Balance");
            System.out.println("'        2. Deposit Money");
            System.out.println("            3. Withdraw");
            System.out.println("-----------------------------------");
            System.out.println("              4. Exit");
            System.out.println("+---------------------------------+");
            System.out.print(" > Enter your choice (1-4): ");
            int choice = 0;

            try {
                choice = input.nextInt();
            }
            catch (InputMismatchException e) {
                System.out.println("Please enter a valid choice from the menu.");
                input.next();
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.printf("Current Balance: %.2f%n", wallet.getBalance());
                    System.out.println("---------------------------------");
                    System.out.println();
                    System.out.print(" > Press 'enter' to return to shop menu.");
                    input.nextLine();
                    break;
                case 2:
                    user.clearScreen();
                    System.out.println("+---------------------------------+");
                    System.out.println("             DEPOSIT");              
                    System.out.println("+---------------------------------+");     
                    System.out.print(" > Enter amount to deposit: ");
                    double depositAmount = input.nextDouble();
                    wallet.deposit(depositAmount);
                    break;
                case 3:
                    user.clearScreen();
                    System.out.println("+---------------------------------+");
                    System.out.println("             WITHDRAW");
                    System.out.println("+---------------------------------+");
                    System.out.print(" > Enter amount to withdraw: ");
                    double withdrawAmount = input.nextDouble();
                    wallet.withdraw(withdrawAmount);
                    break;
                case 4:
                    back = true;
                    break;
                default:
                    System.out.println("Please enter a valid choice from the menu.");
            }
            input.nextLine();
        }
    }
} 