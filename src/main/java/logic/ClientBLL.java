package logic;

import java.util.List;
import java.util.NoSuchElementException;

import access.ClientDAO;
import model.Client;

public class ClientBLL {

    private ClientDAO clientDAO;

    public ClientBLL() {
        this.clientDAO = new ClientDAO();
    }

    public List<Client> findAllClients() {
        List<Client> clients = clientDAO.findAll();
        if (clients == null) {
            throw new NoSuchElementException("No clients were found!");
        }
        return clients;
    }

    public Client findClientById(int id) {
        Client client = clientDAO.findById(id);
        if (client == null) {
            throw new NoSuchElementException("The client with id =" + id + " was not found!");
        }
        return client;
    }

    public Client insertClient(Client client) {
        Client clientInserted = clientDAO.insert(client);
        if (clientInserted == null) {
            throw new NoSuchElementException("The client was not inserted!");
        }
        return clientInserted;
    }

    public Client deleteClient(Client client) {
        Client clientDeleted = clientDAO.delete(client);
        if (clientDeleted == null) {
            throw new NoSuchElementException("The client was not deleted!");
        }
        return clientDeleted;
    }

    public Client updateClientField(Client client, String fieldName, Object newValue) {
        Client clientUpdated = clientDAO.updateField(client, fieldName, newValue);
        if (clientUpdated == null) {
            throw new NoSuchElementException("The client was not updated!");
        }
        return clientUpdated;
    }

}
