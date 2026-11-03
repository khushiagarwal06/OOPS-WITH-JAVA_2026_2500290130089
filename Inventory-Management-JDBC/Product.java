public class Product {
    private int productId;
    private String productName;
    private String category;
    private int quantity;
    private double price;

    public Product(int productId, String productName, String category,
                   int quantity, double price) {
        this.productId = productId;
        this.productName = productName;
        this.category = category;
        this.quantity = quantity;
        this.price = price;
    }

    public int getProductId() { return productId; }
    public String getProductName() { return productName; }
    public String getCategory() { return category; }
    public int getQuantity() { return quantity; }
    public double getPrice() { return price; }

    public void display() {
        System.out.printf("ID: %d | Name: %s | Category: %s | Qty: %d | Price: %.2f%n",
                productId, productName, category, quantity, price);
    }
}
