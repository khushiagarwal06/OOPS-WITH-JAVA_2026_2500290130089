import java.sql.*;
import java.util.Scanner;

public class InventoryManagement {

    static final String URL = "jdbc:mysql://localhost:3306/retail_db";
    static final String USER = "root";
    static final String PASSWORD = "Khushi@MySQL2026!";   // apna MySQL password

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in);
             Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {

            int choice;
            do {
                System.out.println("\n1.Insert three products");
                System.out.println("2.Search category: quantity < 20, price descending");
                System.out.println("3.Add stock");
                System.out.println("4.Find product and update price");
                System.out.println("5.Total inventory value and conditional delete");
                System.out.println("0.Exit");
                System.out.print("Choice: ");

                while (!sc.hasNextInt()) {
                    sc.next();
                    System.out.print("Enter a number: ");
                }
                choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {
                    case 1: insertThree(con); break;
                    case 2: searchCategory(con, sc); break;
                    case 3: addStock(con, sc); break;
                    case 4: updatePrice(con, sc); break;
                    case 5: totalAndDelete(con, sc); break;
                    case 0: System.out.println("Goodbye."); break;
                    default: System.out.println("Invalid choice.");
                }
            } while (choice != 0);

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }

    // (a) Insert three records and handle duplicate IDs.
    static void insertThree(Connection con) {
        String sql = "INSERT INTO Product "
                + "(product_id, product_name, category, quantity, price) "
                + "VALUES (?, ?, ?, ?, ?)";

        Product[] data = {
            new Product(101, "Keyboard", "Electronics", 15, 799.0),
            new Product(102, "Notebook", "Stationery", 30, 55.0),
            new Product(103, "Mouse", "Electronics", 8, 499.0)
        };

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            for (Product p : data) {
                ps.setInt(1, p.getProductId());
                ps.setString(2, p.getProductName());
                ps.setString(3, p.getCategory());
                ps.setInt(4, p.getQuantity());
                ps.setDouble(5, p.getPrice());
                try {
                    ps.executeUpdate();
                    System.out.println("Inserted product ID " + p.getProductId());
                } catch (SQLException e) {
                    if (e.getErrorCode() == 1062) {
                        System.out.println("Duplicate product ID "
                                + p.getProductId() + "; skipped.");
                    } else {
                        System.out.println("Insert failed: " + e.getMessage());
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // (b) Filter category and stock, sorted by descending price.
    static void searchCategory(Connection con, Scanner sc) {
        System.out.print("Category: ");
        String category = sc.nextLine();

        String sql = "SELECT * FROM Product "
                + "WHERE category = ? AND quantity < 20 "
                + "ORDER BY price DESC";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, category);
            try (ResultSet rs = ps.executeQuery()) {
                boolean found = false;
                while (rs.next()) {
                    found = true;
                    printProduct(rs);
                }
                if (!found) {
                    System.out.println("No matching products.");
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // (c) Add to the existing stock.
    static void addStock(Connection con, Scanner sc) {
        System.out.print("Product ID: ");
        int id = readInt(sc);
        System.out.print("Quantity to add: ");
        int qty = readInt(sc);

        if (qty <= 0) {
            System.out.println("Quantity must be positive.");
            return;
        }

        String sql = "UPDATE Product SET quantity = quantity + ? WHERE product_id = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, qty);
            ps.setInt(2, id);
            if (ps.executeUpdate() == 1) {
                System.out.println("Stock updated.");
            } else {
                System.out.println("Product does not exist.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // (d) Retrieve details; update price only if product exists and price > 0.
    static void updatePrice(Connection con, Scanner sc) {
        System.out.print("Product ID: ");
        int id = readInt(sc);

        String select = "SELECT * FROM Product WHERE product_id = ?";
        try (PreparedStatement ps = con.prepareStatement(select)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    System.out.println("Product does not exist.");
                    return;
                }
                System.out.println("Current details:");
                printProduct(rs);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return;
        }

        System.out.print("New price (> 0): ");
        double price = readDouble(sc);
        if (price <= 0) {
            System.out.println("Price must be greater than zero.");
            return;
        }

        try (PreparedStatement ps = con.prepareStatement(
                "UPDATE Product SET price = ? WHERE product_id = ?")) {
            ps.setDouble(1, price);
            ps.setInt(2, id);
            if (ps.executeUpdate() == 1) {
                System.out.println("Updated details:");
                try (PreparedStatement check = con.prepareStatement(select)) {
                    check.setInt(1, id);
                    try (ResultSet rs = check.executeQuery()) {
                        if (rs.next()) {
                            printProduct(rs);
                        }
                    }
                }
            } else {
                System.out.println("Product was not updated.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // (e) Aggregate inventory value and delete only when quantity = 0.
    static void totalAndDelete(Connection con, Scanner sc) {
        String total = "SELECT COALESCE(SUM(quantity * price), 0) "
                + "AS inventory_value FROM Product";
        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(total)) {
            if (rs.next()) {
                System.out.printf("Total inventory value: %.2f%n",
                        rs.getDouble("inventory_value"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return;
        }

        System.out.print("Product ID to delete: ");
        int id = readInt(sc);

        String sql = "DELETE FROM Product WHERE product_id = ? AND quantity = 0";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            if (ps.executeUpdate() == 1) {
                System.out.println("Product deleted.");
            } else {
                System.out.println("Cannot delete: product does not exist "
                        + "or quantity is not zero.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Convert ResultSet row into a Product object and display it.
    static void printProduct(ResultSet rs) throws SQLException {
        Product p = new Product(
            rs.getInt("product_id"),
            rs.getString("product_name"),
            rs.getString("category"),
            rs.getInt("quantity"),
            rs.getDouble("price")
        );
        p.display();
    }

    static int readInt(Scanner sc) {
        while (!sc.hasNextInt()) {
            sc.next();
            System.out.print("Enter an integer: ");
        }
        int n = sc.nextInt();
        sc.nextLine();
        return n;
    }

    static double readDouble(Scanner sc) {
        while (!sc.hasNextDouble()) {
            sc.next();
            System.out.print("Enter a number: ");
        }
        double n = sc.nextDouble();
        sc.nextLine();
        return n;
    }
}
