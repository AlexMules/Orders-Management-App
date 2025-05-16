package model;

/**
 * <p> Represents an order in the database, with a unique identifier, the name of the client, the name of the product,
 * and the quantity of the ordered product.</p>
 */
public class Order {
    private int id;
    private String clientName;
    private String productName;
    private int quantity;

    public Order() {
    }

    public Order(String clientName, String productName, int quantity) {
        super();
        this.quantity = quantity;
        this.clientName = clientName;
        this.productName = productName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Order [id=" + id + ", clientName=" + clientName + ", productName=" + productName + ", quantity="
                + quantity + "]";
    }
}
