package logic;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import access.ClientDAO;
import model.Client;

public class ClientBLL {

    private ClientDAO clientDAO;

    public ClientBLL() {
        clientDAO = new ClientDAO();
    }

    public Client findClientById(int id) {
        Client client = clientDAO.findById(id);
        if (client == null) {
            throw new NoSuchElementException("The student with id =" + id + " was not found!");
        }
        return client;
    }

    public List<Client> findAllClients() {
        List<Client> clients = clientDAO.findAll();
        if (clients == null) {
            throw new NoSuchElementException("No clients were found!");
        }
        return clients;
    }

}
