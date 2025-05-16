package logic;

import java.util.List;
import java.util.NoSuchElementException;

import access.ClientDAO;
import model.Client;

/**
 * Business‐logic layer for {@link Client} entities.
 */
public class ClientBLL {

    private ClientDAO clientDAO;

    public ClientBLL() {
        this.clientDAO = new ClientDAO();
    }

    /**
     * Retrieves all clients from the database.
     *
     * @return a non‐empty {@link List} of {@link Client}
     * @throws NoSuchElementException if no clients were found
     */
    public List<Client> findAllClients() {
        List<Client> clients = clientDAO.findAll();
        if (clients == null) {
            throw new NoSuchElementException("No clients were found!");
        }
        return clients;
    }

    /**
     * Retrieves a client by its ID.
     *
     * @param id the client’s database primary key
     * @return the matching {@link Client}
     * @throws NoSuchElementException if no client with the given ID exists
     */
    public Client findClientById(int id) {
        Client client = clientDAO.findById(id);
        if (client == null) {
            throw new NoSuchElementException("The client with id =" + id + " was not found!");
        }
        return client;
    }

    /**
     * Inserts a new client into the database.
     *
     * @param name    the client’s name
     * @param address the client’s address
     * @param email   the client’s email
     * @throws NoSuchElementException if insertion failed
     */
    public void insertClient(String name, String address, String email) {
        Client client = new Client(name, address, email);
        Client clientInserted = clientDAO.insert(client);
        if (clientInserted == null) {
            throw new NoSuchElementException("The client was not inserted!");
        }
    }

    /**
     * Deletes the given client from the database.
     *
     * @param client the {@link Client} to delete
     * @return the deleted {@link Client}
     * @throws NoSuchElementException if deletion failed
     */
    public Client deleteClient(Client client) {
        Client clientDeleted = clientDAO.delete(client);
        if (clientDeleted == null) {
            throw new NoSuchElementException("The client was not deleted!");
        }
        return clientDeleted;
    }

    /**
     * Updates a single field on an existing client.
     *
     * @param client    the {@link Client} to update
     * @param fieldName the name of the field/column to change
     * @param newValue  the new value for that field
     * @throws NoSuchElementException  if the update failed
     * @throws IllegalArgumentException if the field name is invalid
     */
    public void updateClientField(Client client, String fieldName, Object newValue) {
        Client clientUpdated = clientDAO.updateField(client, fieldName, newValue);
        if (clientUpdated == null) {
            throw new NoSuchElementException("The client was not updated!");
        }
    }
}
