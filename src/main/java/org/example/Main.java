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

        ClientDAO clientDao = new ClientDAO();

        // 1) Insert folosind constructor
        Client c = new Client("Andrei Pop", "Str. Livezilor 5, Cluj", "andrei.pop@example.com");
        Client insertedClient = clientDao.insert(c);
        System.out.println("Inserted client: " + insertedClient);

        // 2) Schimbă doar un câmp: address
        //    (reţinem obiectul cu id populat în insertedClient)
        Client updatedClient = clientDao.updateField(insertedClient, "address", "Bulevardul Eroilor 10, București");
        System.out.println("Updated client (address): " + updatedClient);
        System.out.println();


        // ——— PRODUCT —————————————————————————

        ProductDAO productDao = new ProductDAO();

        // 1) Insert folosind constructor
        Product p = new Product("Boxă Bluetooth", 299.99, 25);
        Product insertedProduct = productDao.insert(p);
        System.out.println("Inserted product: " + insertedProduct);

        // 2) Schimbă doar un câmp: price
        Product updatedProduct = productDao.updateField(insertedProduct, "price", 249.50);
        System.out.println("Updated product (price): " + updatedProduct);
        System.out.println();


        // ——— ORDER ——————————————————————————

        OrderDAO orderDao = new OrderDAO();

        try {
            // 1) Insert cu constructor
            Order o = new Order(3, "Maria Ionescu", "Căști Wireless");
            Order insertedOrder = orderDao.insert(o);
            System.out.println("Inserted order: " + insertedOrder);

            // 2) Încearcă să schimbi quantity – nu e permis
            orderDao.updateField(insertedOrder, "quantity", 5);
        } catch (UnsupportedOperationException ex) {
            System.err.println("UpdateField pe Order nereușit: " + ex.getMessage());
        }
    }

}
