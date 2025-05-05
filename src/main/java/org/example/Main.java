package org.example;

import logic.ClientBLL;
import model.Client;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    protected static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) throws SQLException {

        ClientBLL clientBll = new ClientBLL();

        try {
            List<Client> clients = clientBll.findAllClients();

            for (Client client : clients) {
                //System.out.println(client.toString());
                ReflectionExample.retrieveProperties(client);
                System.out.println("----");
            }

        } catch (Exception ex) {
            LOGGER.log(Level.INFO, ex.getMessage());
        }
    }

}
