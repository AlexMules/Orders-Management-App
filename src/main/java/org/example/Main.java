package org.example;

import access.ClientDAO;
import access.OrderDAO;
import access.ProductDAO;
import logic.ClientBLL;
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

        // 1. Instanţiem DAO-urile
        ClientDAO clientDao   = new ClientDAO();
        ProductDAO productDao = new ProductDAO();
        OrderDAO   orderDao   = new OrderDAO();

        // 2. Creăm şi inserăm un Client
        Client newClient = new Client("Maria Pop", "Str. Florilor 12", "maria.pop@example.com", 27);
        clientDao.insert(newClient);
        System.out.println("Inserted client: " + newClient);
        // ↳ va avea acum newClient.getId() setat

        // 3. Creăm şi inserăm un Product
        Product newProduct = new Product("Căști Wireless", 149.90, 20);
        productDao.insert(newProduct);
        System.out.println("Inserted product: " + newProduct);
        // ↳ newProduct.getId() populat automat

        // 4. Creăm şi inserăm un Order care leagă cele două
        Order newOrder = new Order(newClient.getId(), newClient.getId(), 3);
        orderDao.insert(newOrder);
        System.out.println("Inserted order: " + newOrder);

        /* / 5. (Opţional) Listează tot ce ai inserat
        System.out.println("\nAll clients:");
        clientDao.findAll().forEach(c -> System.out.println("  " + c));

        System.out.println("\nAll products:");
        productDao.findAll().forEach(p -> System.out.println("  " + p));

        System.out.println("\nAll orders:");
        orderDao.findAll().forEach(o -> System.out.println("  " + o)); */
    }

}
