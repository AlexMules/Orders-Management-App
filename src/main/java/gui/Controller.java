package gui;

import gui.view.ClientView;
import gui.view.EditClientView;
import gui.view.ProductView;
import logic.ClientBLL;
import logic.ProductBLL;
import model.Client;
import model.Product;
import utils.*;

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
    private final ProductBLL productBLL;

    public Controller() {
        clientBLL = new ClientBLL();
        productBLL = new ProductBLL();
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

    private void validateClientName(String name) throws IncorrectClientNameException {
        String nameStr = name.trim();
        if (nameStr.isEmpty()) {
            throw new IncorrectClientNameException("Name field cannot be empty!");
        }
        if (!NAME_PATTERN.matcher(nameStr).matches()) {
            throw new IncorrectClientNameException("Invalid name!");
        }
    }

    private void validateProductName(String name) throws IncorrectProductNameException {
        String nameStr = name.trim();
        if (nameStr.isEmpty()) {
            throw new IncorrectProductNameException("Name field cannot be empty!");
        }
    }

    private void validateProductPrice(String priceStr) throws IncorrectProductPriceException {
        String p = priceStr.trim();
        if (p.isEmpty()) {
            throw new IncorrectProductPriceException("Price field cannot be empty!");
        }
        try {
            double price = Double.parseDouble(p);
            if (price <= 0) {
                throw new IncorrectProductPriceException("Price must be greater than 0!");
            }
        } catch (NumberFormatException ex) {
            throw new IncorrectProductPriceException("Price must be a valid number!");
        }
    }

    private void validateProductQuantity(String qtyStr) throws IncorrectProductQuantityException {
        String q = qtyStr.trim();
        if (q.isEmpty()) {
            throw new IncorrectProductQuantityException("Quantity field cannot be empty!");
        }
        try {
            int qty = Integer.parseInt(q);
            if (qty <= 0) {
                throw new IncorrectProductQuantityException("Quantity must be greater than 0!");
            }
        } catch (NumberFormatException ex) {
            throw new IncorrectProductQuantityException("Quantity must be a valid integer!");
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

    public void handleOpenProductWindow() {
        ProductView productView = new ProductView("Product Menu", this);
        productView.setVisible(true);
    }

    public void handleAddClient(String name, String address, String email, Component parentComponent) {
        try {
            validateClientName(name);
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

    public void handleAddProduct(String name, String priceStr, String qtyStr, Component parent) {
        try {
            validateProductName(name);
            validateProductPrice(priceStr);
            validateProductQuantity(qtyStr);

            double price = Double.parseDouble(priceStr.trim());
            int    qty   = Integer.parseInt(qtyStr.trim());

            productBLL.insertProduct(new Product(name.trim(), price, qty));

            JOptionPane.showMessageDialog(parent,
                    "Product added successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch ( IncorrectProductNameException | IncorrectProductPriceException | IncorrectProductQuantityException ex) {
            JOptionPane.showMessageDialog(parent,
                    ex.getMessage(),
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (NoSuchElementException ex) {
        JOptionPane.showMessageDialog(parent,
                "Failed to insert product: " + ex.getMessage(),
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
                validateClientName(newName);
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
