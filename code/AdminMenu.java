import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class AdminMenu {
    private Scanner input = new Scanner(System.in);
    private Connection conn;

        public void adminMenu() {
        boolean back = false;
        ProductCatalog productCatalog = new ProductCatalog();

        while (!back) {
            System.out.println("+---------------------------------+");
            System.out.println("            Admin Menu");
            System.out.println("+---------------------------------+");
            System.out.println("         1. View Products");
            System.out.println("          2. Add Product");
            System.out.println("         3. Remove Product");
            System.out.println("         4. Update Stock");
            System.out.println("-----------------------------------");
            System.out.println("            5. Log Out");
            System.out.println("+---------------------------------+");
            System.out.print(" > Enter your choice (1-5): ");
            int choice;

            try {
                choice = input.nextInt();
            }
            catch (Exception e) {
                System.out.println("Invalid input.");
                input.nextLine();
                continue;
            }

            switch (choice) {
                case 1:
                    conn = getConnection();
                    viewProducts(conn, productCatalog);
                    break;
                case 2:
                    addProducts();
                    break;
                case 3:
                    removeProducts();
                    break;
                case 4:
                    updateStock();
                    break;
                case 5:
                    System.out.println("Logging out...");
                    back = true;
                    break;
                default:
                    System.out.println("Please enter a valid choice from the menu.");
            }
        }
    }

    public void viewProducts(Connection connection, ProductCatalog productCatalog) {
        System.out.println("+---------------------------------+");
        System.out.println("           View Products");
        System.out.println("+---------------------------------+");
        System.out.println("         1. Crochet Tools");
        System.out.println("             2. Yarns");
        System.out.println("        3. Crocheted Items");
        System.out.println("-----------------------------------");
        System.out.println("             4. Exit");
        System.out.println("+---------------------------------+");
        System.out.print(" > Enter your choice (1-4): ");
        int choice = input.nextInt();

        switch(choice) {
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
                return;
            default:
                System.out.println("Please enter a valid choice from the menu.");
                break;
        }
    }

    public void addProducts() {
        System.out.println("+---------------------------------+");
        System.out.println("             Add Product");
        System.out.println("+---------------------------------+");
        System.out.println(" Select product type to add:");
        System.out.println("    1. Crochet Tools");
        System.out.println("    2. Yarns");
        System.out.println("    3. Crocheted Items");
        System.out.println("-----------------------------------");
        System.out.println("    4. Exit");
        System.out.println("+---------------------------------+");
        System.out.print(" > Enter your choice (1-4): ");
        int choice = input.nextInt();
        input.nextLine();

        switch (choice) {
            case 1:
                System.out.println("+---------------------------------+");
                System.out.println("         Add Crochet Tools");
                System.out.println("+---------------------------------+");
                System.out.print(" > Enter tool name: ");
                String toolName = input.nextLine();
                System.out.print(" > Enter description: ");
                String toolDescription = input.nextLine();
                System.out.print(" > Enter price: ");
                double toolPrice = input.nextDouble();
                System.out.print(" > Enter stock: ");
                int toolStock = input.nextInt();

                try (Connection connection = getConnection();
                    PreparedStatement stmt = connection.prepareStatement(
                        "INSERT INTO crochet_tools (name, description, price, stock) VALUES (?, ?, ?, ?)")) {
                    stmt.setString(1, toolName);       
                    stmt.setString(2, toolDescription);        
                    stmt.setDouble(3, toolPrice);        
                    stmt.setInt(4, toolStock);     
                    
                    int rows = stmt.executeUpdate();
                    if (rows > 0) {
                        System.out.println("Crochet tool added successfully.");
                    }
                }
                catch (Exception e) {
                    System.out.println("Error adding crochet tool: " + e.getMessage());
                }
                break;

            case 2:
                System.out.println("+---------------------------------+");
                System.out.println("             Add Yarns");
                System.out.println("+---------------------------------+");
                System.out.print(" > Enter yarn name: ");
                String yarnName = input.nextLine();
                System.out.print(" > Enter material: ");
                String yarnMaterial = input.nextLine();
                System.out.print(" > Enter hook size: ");
                double hookSize = input.nextDouble();
                System.out.print(" > Enter price: ");
                double yarnPrice = input.nextDouble();
                System.out.print(" > Enter stock: ");
                int yarnStock = input.nextInt();

                try (Connection connection = getConnection();
                    PreparedStatement stmt = connection.prepareStatement(
                        "INSERT INTO yarns (name, material, hook_size, price, stock) VALUES (?, ?, ?, ?, ?)")) {
                    stmt.setString(1, yarnName);
                    stmt.setString(2, yarnMaterial);
                    stmt.setDouble(3, hookSize);
                    stmt.setDouble(4, yarnPrice);
                    stmt.setInt(5, yarnStock);

                    int rows = stmt.executeUpdate();
                    if (rows > 0) {
                        System.out.println("Yarn added successfully.");
                    }
                }
                catch (Exception e) {
                    System.out.println("Error adding yarn: " + e.getMessage());
                }
                break;

            case 3:
                System.out.println("+---------------------------------+");
                System.out.println("        Add Crocheted Items");
                System.out.println("+---------------------------------+");
                System.out.print(" > Enter item name: ");
                String itemName = input.nextLine();
                System.out.print(" > Enter color: ");
                String itemColor = input.nextLine();
                System.out.print(" > Enter price: ");
                double itemPrice = input.nextDouble();
                System.out.print(" > Enter stock: ");
                int itemStock = input.nextInt();

                try (Connection connection = getConnection();
                    PreparedStatement stmt= connection.prepareStatement(
                        "INSERT INTO crocheted_items (name, color, price, stock) VALUES (?, ?, ?, ?)")) {
                    stmt.setString(1, itemName);
                    stmt.setString(2, itemColor);
                    stmt.setDouble(3, itemPrice);
                    stmt.setInt(4, itemStock);

                    int rows = stmt.executeUpdate();
                    if (rows > 0) {
                        System.out.println("Crocheted item added successfully.");
                    }
                }
                catch (Exception e) {
                    System.out.println("Error adding crocheted item: " + e.getMessage());
                }
                break;
            
            default:
                System.out.println("Please enter a valid choice from the menu.");
        }
    }

    public void removeProducts() {
        System.out.println("+---------------------------------+");
        System.out.println("          Remove Product");
        System.out.println("+---------------------------------+");
        System.out.print(" > Enter product ID to remove: ");
        String productId = input.nextLine();

        try (Connection connection = getConnection();
                PreparedStatement stmt = connection.prepareStatement("DELETE FROM crochet_tools WHERE product_id = ?")) {
            stmt.setString(1, productId);

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Product removed successfully.");
            } else {
                System.out.println("Product not found.");
            }
        } catch (Exception e) {
            System.out.println("Error removing product: " + e.getMessage());
        }
    }

    public void updateStock() {
        System.out.println("+---------------------------------+");
        System.out.println("           Update Product");
        System.out.println("+---------------------------------+");
        System.out.print(" > Enter product ID to update: ");
        String productId = input.nextLine();
        System.out.print(" > Enter new stock quantity: ");
        int newStock = input.nextInt();

        try (Connection connection = getConnection();
                PreparedStatement stmt = connection.prepareStatement("UPDATE crochet_tools SET stock = ? WHERE product_id = ?")) {
            stmt.setInt(1, newStock);
            stmt.setString(2, productId);

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Stock updated successfully.");
            } else {
                System.out.println("Product not found.");
            }
        } catch (Exception e) {
            System.out.println("Error updating stock: " + e.getMessage());
        }
    }

    private Connection getConnection() {
        try {
            String url = "jdbc:mysql://localhost:3306/FinalProj_DBMS";
            String user = "root";
            String password = "MySQL0109030705";

            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Database connection established successfully.");
            return conn;
        }
        catch (SQLException e) {
            System.out.println("Database connection error: " + e.getMessage());
            return null;
        }
    }       
}