package org.example;

import access.ClientDAO;
import access.OrderDAO;
import access.ProductDAO;
import logic.ClientBLL;
import logic.OrderBLL;
import logic.ProductBLL;
import model.Client;
import model.Order;
import model.Product;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    protected static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) throws SQLException {

        ClientBLL clientBll   = new ClientBLL();
        ProductBLL prodBll    = new ProductBLL();
        OrderBLL orderBll     = new OrderBLL();

        System.out.println("=== CLIENTS ===");
        // 1) Insert a new Client
        Client newClient = new Client("Gabriel Pop", "Str. Lalelelor 20, Cluj", "gabriel.pop@example.com");
        Client insertedClient = clientBll.insertClient(newClient);
        System.out.println("Inserted:   " + insertedClient);

        // 2) Update a single field
        Client updatedClient = clientBll.updateClientField(
                insertedClient,      // the object returned from insert
                "address",           // field to update
                "Bd. Libertății 7, București"
        );
        System.out.println("Updated:    " + updatedClient);

        // 3) Find by ID
        Client foundClient = clientBll.findClientById(updatedClient.getId());
        System.out.println("Found by id:" + foundClient);

        // 4) List all
        List<Client> allClients = clientBll.findAllClients();
        System.out.println("All clients:");
        allClients.forEach(System.out::println);

        // 5) Delete
        Client deletedClient = clientBll.deleteClient(foundClient);
        System.out.println("Deleted:    " + deletedClient);
        System.out.println();

        System.out.println("=== PRODUCTS ===");
        // 1) Insert a new Product
        Product newProd = new Product("Boxă Portabilă", 129.99, 15);
        Product insertedProd = prodBll.insertProduct(newProd);
        System.out.println("Inserted:   " + insertedProd);

        // 2) Update single field
        Product updatedProd = prodBll.updateProductField(
                insertedProd,
                "price",
                109.50
        );
        System.out.println("Updated:    " + updatedProd);

        // 3) Find by ID
        Product foundProd = prodBll.findProductById(updatedProd.getId());
        System.out.println("Found by id:" + foundProd);

        // 4) List all
        List<Product> allProds = prodBll.findAllProducts();
        System.out.println("All products:");
        allProds.forEach(System.out::println);

        // 5) Delete
        Product deletedProd = prodBll.deleteProduct(foundProd);
        System.out.println("Deleted:    " + deletedProd);
        System.out.println();

        System.out.println("=== ORDERS ===");
        // 1) Insert a new Order
        Order newOrder = new Order(2, "Gabriel Pop", "Boxă Portabilă");
        Order insertedOrder = orderBll.insertOrder(newOrder);
        System.out.println("Inserted:   " + insertedOrder);

        // 2) Find by ID
        Order foundOrder = orderBll.findOrderById(insertedOrder.getId());
        System.out.println("Found by id:" + foundOrder);

        // 3) List all
        List<Order> allOrders = orderBll.findAllOrders();
        System.out.println("All orders:");
        allOrders.forEach(System.out::println);

        // 4) Delete
        Order deletedOrder = orderBll.deleteOrder(foundOrder);
        System.out.println("Deleted:    " + deletedOrder);
    }
}

