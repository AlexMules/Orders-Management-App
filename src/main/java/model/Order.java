package model;

public class Order {
    private int id;
    private String clientName;
    private String productName;
    private int quantity;

    public Order() {
    }

    public Order(int quantity, String clientName, String productName) {
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
