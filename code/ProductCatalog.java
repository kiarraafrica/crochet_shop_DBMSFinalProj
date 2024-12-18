import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class ProductCatalog {
    private User user = new User();
    private Scanner input = new Scanner(System.in);

    public void viewCrochetTools(Connection connection) {
        user.clearScreen();
        System.out.println("+---------------------------------+");
        System.out.println("            CROCHET TOOLS");
        System.out.println("+---------------------------------+");

        String query = "SELECT * FROM crochet_tools";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            boolean hasResults = false;
            while (rs.next()) {
                hasResults = true;
                System.out.println("Product ID: " + rs.getString("product_id"));
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("Description: " + rs.getString("description"));
                System.out.println("Price: " + rs.getDouble("price"));
                System.out.println("Stock: " + rs.getInt("stock"));
                System.out.println("-----------------------------------");
            }

            if (!hasResults) {
                System.out.println("No products found in Crochet Tools.");
            }
        } catch (Exception e) {
            System.out.println();
            System.out.println("Error fetching Crochet Tools: " + e.getMessage());
        }

        System.out.println();
        System.out.print(" > Press 'Enter' to return to catalog menu.");
        input.nextLine();
    }

    public void viewYarns(Connection connection) {
        user.clearScreen();
        System.out.println("+---------------------------------+");
        System.out.println("              YARNS");
        System.out.println("+---------------------------------+");

        String query = "SELECT * FROM yarns";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            boolean hasResults = false;
            while (rs.next()) {
                hasResults = true;
                System.out.println("Product ID: " + rs.getString("product_id"));
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("Material: " + rs.getString("material"));
                System.out.println("Hook Size: " + rs.getDouble("hook_size"));
                System.out.println("Price: " + rs.getDouble("price"));
                System.out.println("Stock: " + rs.getInt("stock"));
                System.out.println("-----------------------------------");
            }

            if (!hasResults) {
                System.out.println("No products found in Yarns.");
            }
        } catch (Exception e) {
            System.err.println();
            System.out.println("Error fetching Yarns: " + e.getMessage());
        }

        System.err.println();
        System.out.print(" > Press 'Enter' to return to catalog menu.");
        input.nextLine();
    }

    public void viewCrochetedItems(Connection connection) {
        user.clearScreen();
        System.out.println("+---------------------------------+");
        System.out.println("         CROCHETED ITEMS");
        System.out.println("+---------------------------------+");

        String query = "SELECT * FROM crocheted_items";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            boolean hasResults = false;
            while (rs.next()) {
                hasResults = true;
                System.out.println("Product ID: " + rs.getString("product_id"));
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("Color: " + rs.getString("color"));
                System.out.println("Price: " + rs.getDouble("price"));
                System.out.println("Stock: " + rs.getInt("stock"));
                System.out.println("-----------------------------------");
            }

            if (!hasResults) {
                System.out.println();
                System.out.println("No products found in Crocheted Items.");
            }
        } catch (Exception e) {
            System.out.println("Error fetching Crocheted Items: " + e.getMessage());
        }

        System.out.println();
        System.out.print(" > Press 'Enter' to return to catalog menu.");
        input.nextLine();
    }
}
