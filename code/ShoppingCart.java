import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Scanner;

public class ShoppingCart {
    public ArrayList<CartItem> cartItems;
    private Scanner input = new Scanner(System.in);
    private User user = new User();

    public ShoppingCart() {
        this.cartItems = new ArrayList<>();
    }

    public void addItem(Connection connection, String productID, int quantity) {
        try (PreparedStatement stmt = connection.prepareStatement(
            "SELECT product_id, name, price, stock FROM " +
            "(SELECT product_id, name, price, stock FROM crochet_tools UNION ALL " +
            "SELECT product_id, name, price, stock FROM yarns UNION ALL " +
            "SELECT product_id, name, price, stock FROM crocheted_items) AS products " +
            "WHERE product_id = ?")) {
    
            stmt.setString(1, productID);
            ResultSet rs = stmt.executeQuery();
    
            if (rs.next()) {
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                int stock = rs.getInt("stock");
    
                if (quantity > stock) {
                    System.out.println("Insufficient Stock. Available stock: " + stock);
                    return;
                }
    
                for (CartItem cartItem : cartItems) {
                    if (cartItem.getProductID().equals(productID)) { // Fixing comparison
                        cartItem.setQuantity(cartItem.getQuantity() + quantity);
                        System.out.println("Updated quantity for " + name + " to " + cartItem.getQuantity());
                        return;
                    }
                }
    
                CartItem item = new CartItem(productID, name, price, quantity);
                cartItems.add(item);
                System.out.println(name + " added to the cart.");
            } else {
                System.out.println("Product not found.");
            }
        } catch (Exception e) {
            System.out.println("Error adding to cart: " + e.getMessage());
        }
    }
    

    public void removeItem(String productID) {
        boolean found = false;

        for (int k = 0; k < cartItems.size(); k++) {
            if (cartItems.get(k).getProductID().equals(productID)) {
                System.out.println(cartItems.get(k).getName() + " remove from the cart.");
                cartItems.remove(k);
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Product not found in the cart.");
        }
    }

    public void displayCart() {
        if (cartItems.isEmpty()) {
            user.clearScreen();
            System.out.println("---Your cart is empty.");

            System.out.println();
            System.out.print("Press 'enter' to return to shopping cart menu.");
            input.nextLine();
        }
        else {
            System.out.println("+---------------------------------+");
            System.out.println("            Shopping Cart");
            System.out.println("+---------------------------------+");
            double total = 0;

            for (CartItem item : cartItems) {
                System.out.println("Product: " + item.getName());
                System.out.println("Quantity: " + item.getQuantity());
                System.out.println("Price: $" + item.getPrice());
                System.out.println("-----------------------------------");
                total += item.getPrice() * item.getQuantity();
            }
            System.out.println("Total: $" + total);
        }
    }

    public double checkout(double payment) {
        double total = 0;
        for (CartItem item : cartItems) {
            total += item.getPrice() * item.getQuantity();
        }

        if (payment >= total) {
            cartItems.clear();
            return payment - total;
        }
        else {
            return -1;
        }
    }

    public ArrayList<CartItem> getCartItems() {
        return cartItems;
    }

    public double calculateCost() {
        double cost = 0;

        for(CartItem item : cartItems) {
            cost += item.getPrice() * item.getQuantity();
        }
        return cost;
    }

    public void clearCart() {
        cartItems.clear();
    }

    public static class CartItem {
        private String productID;
        private String name;
        private double price;
        private int quantity;

        public CartItem(String productID, String name, double price, int quantity) {
            this.productID = productID;
            this.name = name;
            this.price = price;
            this.quantity = quantity;
        }

        public String getProductID() {
            return productID;
        }

        public String getName() {
            return name;
        }

        public double getPrice() {
            return price;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
    }
}