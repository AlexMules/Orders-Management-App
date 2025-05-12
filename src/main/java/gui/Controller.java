package gui;

import gui.view.ClientView;
import gui.view.EditClientView;
import logic.ClientBLL;
import model.Client;
import utils.IncorrectAddressException;
import utils.IncorrectClientNameException;
import utils.IncorrectEmailException;

import javax.swing.*;
import java.awt.*;
import java.util.NoSuchElementException;
import java.util.regex.Pattern;
import java.util.List;

public class Controller {

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$");
    private static final Pattern NAME_PATTERN = Pattern.compile("^[A-Za-z ]+$");

    private final ClientBLL clientBLL;

    public Controller() {
        clientBLL = new ClientBLL();
    }

    private void validateEmail(String email) throws IncorrectEmailException {
        String emailStr = email.trim();
        if (emailStr.isEmpty()) {
            throw new IncorrectEmailException("Email field cannot be empty!");
        }
        if (!EMAIL_PATTERN.matcher(emailStr).matches()) {
            throw new IncorrectEmailException("Invalid email!");
        }
    }

    private void validateName(String name) throws IncorrectClientNameException {
        String nameStr = name.trim();
        if (nameStr.isEmpty()) {
            throw new IncorrectClientNameException("Name field cannot be empty!");
        }
        if (!NAME_PATTERN.matcher(nameStr).matches()) {
            throw new IncorrectClientNameException("Invalid name!");
        }
    }

    private void validateAddress(String address) throws IncorrectAddressException {
        String addressStr = address.trim();
        if (addressStr.isEmpty()) {
            throw new IncorrectAddressException("Address field cannot be empty!");
        }
    }

    public void handleOpenClientWindow() {
        ClientView clientView = new ClientView("Client Menu", this);
        clientView.setVisible(true);
    }

    public void handleAddClient(String name, String address, String email, Component parentComponent) {
        try {
            validateName(name);
            validateEmail(email);
            validateAddress(address);

            clientBLL.insertClient(name.trim(), address.trim(), email.trim());

            JOptionPane.showMessageDialog(parentComponent, "Client added successfully!", "Success",
                                                                            JOptionPane.INFORMATION_MESSAGE);
        } catch (IncorrectClientNameException | IncorrectEmailException | IncorrectAddressException ex) {
            JOptionPane.showMessageDialog(parentComponent, ex.getMessage(), "Validation Error",
                                                                            JOptionPane.ERROR_MESSAGE);
        } catch (NoSuchElementException ex) {
            JOptionPane.showMessageDialog(parentComponent,
                    "Failed to insert client: " + ex.getMessage(),
                    "Insertion Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public List<Client> getAllClients() {
        return clientBLL.findAllClients();
    }

    public void handleDeleteClient(Client selectedClient, Component parentComponent) {
        try {
            Client deleted = clientBLL.deleteClient(selectedClient);
            if (deleted != null) {
                JOptionPane.showMessageDialog(parentComponent,
                        "Client \"" + deleted.getName() + "\" has been deleted successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(parentComponent,
                        "Coudln't delete client!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(parentComponent,
                    "Delete error: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void handleOpenEditClientWindow(Client selected, ClientView clientView){
        EditClientView editClientView = new EditClientView(selected, this, clientView);
        editClientView.setVisible(true);
    }

    public void handleEditClient(Client client, String newName, String newAddress, String newEmail, Component parent) {
        boolean anyUpdated = false;
        try {
            if (newName != null && !newName.trim().isEmpty()) {
                validateName(newName);
                clientBLL.updateClientField(client, "name", newName.trim());
                anyUpdated = true;
            }

            if (newAddress != null && !newAddress.trim().isEmpty()) {
                clientBLL.updateClientField(client, "address", newAddress.trim());
                anyUpdated = true;
            }

            if (newEmail != null && !newEmail.trim().isEmpty()) {
                validateEmail(newEmail);
                clientBLL.updateClientField(client, "email", newEmail.trim());
                anyUpdated = true;
            }

            if (!anyUpdated) {
                JOptionPane.showMessageDialog(parent,
                        "You must complete at least one field!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            JOptionPane.showMessageDialog(parent,
                    "Clientul a fost actualizat cu succes!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (IncorrectClientNameException | IncorrectEmailException ex) {
            JOptionPane.showMessageDialog(parent,
                    ex.getMessage(),
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (NoSuchElementException ex) {
            JOptionPane.showMessageDialog(parent,
                    "Eroare la actualizare: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

}
