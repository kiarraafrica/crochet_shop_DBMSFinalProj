import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class ProductCatalog {
    private User user = new User();
    private Scanner input = new Scanner(System.in);
    
    public void viewCrochetTools(Connection connection) {
        try (Statement stmt = connection.createStatement()) {
            String query = "SELECT * FROM crochet_tools";
            ResultSet rs = stmt.executeQuery(query);

            user.clearScreen();
            System.out.println("+---------------------------------+");
            System.out.println("            CROCHET TOOLS");
            System.out.println("+---------------------------------+");

            while (rs.next()) {
                System.out.println();
                System.out.println("-------------------------------------------------------------------------------");
                System.out.println("Product ID: " + rs.getInt("product_id"));
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("Description: " + rs.getString("description"));
                System.out.println("Price: " + rs.getDouble("price"));
                System.out.println("Stock: " + rs.getInt("stock"));
            }
        }
        catch (Exception e) {
            System.out.println("Error fetching crochet tools: " + e.getMessage());
        }

        System.out.println();
        System.out.print(" > Press 'enter' to return to catalog menu.");
        input.nextLine();
        return;
    }

    public void viewYarns(Connection connection) {
        try (Statement stmt = connection.createStatement()) {
            String query = "SELECT * FROM yarns";
            ResultSet rs = stmt.executeQuery(query);
            
            user.clearScreen();
            System.out.println("+---------------------------------+");
            System.out.println("              YARNS");
            System.out.println("+---------------------------------+");

            while (rs.next()) {
                System.out.println();
                System.out.println("-------------------------------------------------------------------------");
                System.out.println("Product ID: " + rs.getInt("product_id"));
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("Material: " + rs.getString("material"));
                System.out.println("Hook Size: " + rs.getString("hook_size"));
                System.out.println("Price: " + rs.getDouble("price"));
                System.out.println("Stock: " + rs.getInt("stock"));
            }
        }
        catch (Exception e) {
            System.out.println("Errors fetching yarns: " + e.getMessage());
        }

        System.out.println();
        System.out.print("> Press 'enter' to return to catalog menu.");
        input.nextLine();
        return;
    }

    public void viewCrochetedItems(Connection connection) {
        try (Statement stmt = connection.createStatement()) {
            String query = "SELECT * FROM crocheted_items";
            ResultSet rs = stmt.executeQuery(query);
            
            user.clearScreen();
            System.out.println("+---------------------------------+");
            System.out.println("         CROCHETED ITEMS");
            System.out.println("+---------------------------------+");

            while (rs.next()) {
                System.out.println();
                System.out.println("---------------------------------------------------------");
                System.out.println("Product ID: " + rs.getInt("product_id"));
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("Color: " + rs.getString("color"));
                System.out.println("Price: " + rs.getDouble("price"));
                System.out.println("Stock: " + rs.getInt("stock"));
            }
        }
        catch (Exception e) {
            System.out.println("Error fetching crocheted itens: " + e.getMessage());
        }

        System.out.println();
        System.out.print(" > Press 'enter' to return to catalog menu.");
        input.nextLine();
        return;
    }
}