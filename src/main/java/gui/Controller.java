package gui;

import gui.view.*;
import logic.BillBLL;
import logic.ClientBLL;
import logic.OrderBLL;
import logic.ProductBLL;
import model.Bill;
import model.Client;
import model.Order;
import model.Product;
import utils.*;

import javax.swing.*;
import java.awt.*;
import java.util.NoSuchElementException;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import java.util.List;

/**
 * <p> Central GUI controller that mediates between Swing views and the
 * business-logic layer (ClientBLL, ProductBLL, OrderBLL, BillBLL). </p>
 *
 * <p> This class is responsible for:
 *  <ul>
 *     <li>Opening and wiring up all windows (ClientView, ProductView, OrderView, EditClientView, EditProductView, ViewAllTable).</li>
 *     <li>Validating user input (names, emails, addresses, prices, quantities) and throwing domain-specific exceptions.</li>
 *     <li>Delegating CRUD operations to the BLL classes.</li>
 *     <li>Populating {@link JTable}s via the {@link TablePopulator} interface.</li>
 *  </ul>
 */
public class Controller implements TablePopulator {

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$");
    private static final Pattern NAME_PATTERN = Pattern.compile("^[A-Za-z ]+$");

    private final ClientBLL clientBLL;
    private final ProductBLL productBLL;
    private final OrderBLL orderBLL;
    private final BillBLL billBLL;

    public Controller() {
        clientBLL = new ClientBLL();
        productBLL = new ProductBLL();
        orderBLL = new OrderBLL();
        billBLL = new BillBLL();
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

    public List<Client> getAllClients() {
        return clientBLL.findAllClients();
    }

    public List<Product> getAllProducts() {
        return productBLL.findAllProducts();
    }

    public List<Order> getAllOrders() {
        return orderBLL.findAllOrders();
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
            int qty = Integer.parseInt(qtyStr.trim());

            productBLL.insertProduct(new Product(name.trim(), price, qty));

            JOptionPane.showMessageDialog(parent,
                    "Product added successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (IncorrectProductNameException | IncorrectProductPriceException | IncorrectProductQuantityException ex) {
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

    public void handleDeleteClient(Client selectedClient, Component parentComponent) {
        try {
            Client deleted = clientBLL.deleteClient(selectedClient);
            JOptionPane.showMessageDialog(parentComponent,
                    "Client \"" + deleted.getName() + "\" has been deleted successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(parentComponent,
                    "Delete error: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void handleDeleteProduct(Product sel, Component parent) {
        try {
            Product deleted = productBLL.deleteProduct(sel);
            JOptionPane.showMessageDialog(parent,
                    "Product \"" + deleted.getName() + "\" deleted successfully!",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(parent,
                    "Failed to delete product: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void handleOpenEditClientWindow(Client selected, ClientView clientView) {
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
                    "Client has been updated successfully!",
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

    public void handleOpenEditProductWindow(Product selected, ProductView parent) {
        EditProductView editProductView = new EditProductView(selected, this, parent);
        editProductView.setVisible(true);
    }

    public void handleEditProduct(Product product, String newName, String newPrice, String newQuantity, Component parent) {
        boolean anyUpdated = false;
        try {
            if (newName != null && !newName.trim().isEmpty()) {
                validateProductName(newName);
                productBLL.updateProductField(product, "name", newName.trim());
                anyUpdated = true;
            }

            if (newPrice != null && !newPrice.trim().isEmpty()) {
                validateProductPrice(newPrice);
                double priceVal = Double.parseDouble(newPrice.trim());
                productBLL.updateProductField(product, "price", priceVal);
                anyUpdated = true;
            }

            if (newQuantity != null && !newQuantity.trim().isEmpty()) {
                validateProductQuantity(newQuantity);
                int qtyVal = Integer.parseInt(newQuantity.trim());
                productBLL.updateProductField(product, "quantity", qtyVal);
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
                    "Product has been updated successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (IncorrectProductNameException | IncorrectProductPriceException | IncorrectProductQuantityException ex) {
                JOptionPane.showMessageDialog(parent,
                    ex.getMessage(),
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE);

        } catch (NoSuchElementException ex) {
            JOptionPane.showMessageDialog(parent,
                    "Error updating product: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void handleOpenCreateOrderWindow() {
        OrderView orderView = new OrderView("Order Menu", this);
        orderView.setVisible(true);
    }

    public void handlePlaceOrder(Client client, Product product, String qtyStr, Component parent) {
        try {
            validateProductQuantity(qtyStr);
            int qty = Integer.parseInt(qtyStr.trim());

            if (product.getQuantity() < qty) {
                JOptionPane.showMessageDialog(parent,
                        "Under-stock! Only " + product.getQuantity() + " items available.",
                        "Under-stock Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            Order insertedOrder = orderBLL.insertOrder(new Order(client.getName(), product.getName(), qty));
            int orderId = insertedOrder.getId();

            int newStock = product.getQuantity() - qty;
            productBLL.updateProductField(product, "quantity", newStock);

            double totalPrice = product.getPrice() * qty;
            Bill bill = new Bill(0, orderId, client.getName(), product.getName(), qty, totalPrice);
            Bill savedBill = billBLL.insertBill(bill);

            JOptionPane.showMessageDialog(parent,
                    "Order placed successfully!  Stock for \""
                            + product.getName() + "\" is now " + newStock +
                            "\nBill #" + savedBill.id()
                            + " generated for order #" + orderId,
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (IncorrectProductQuantityException ex) {
            JOptionPane.showMessageDialog(parent,
                    "Quantity must be a valid integer!",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(parent,
                    "Failed to place order: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public <T> void showAllWindow(String title, Supplier<List<T>> dataSupplier) {
        ViewAllTable view = new ViewAllTable(title);
        List<T> data = dataSupplier.get();
        populateTable(view.getTable(), data);
        view.setVisible(true);
    }

    public void handleViewBillsWindow() {
        List<Bill> bills = billBLL.findAllBills();
        ViewAllTable view = new ViewAllTable("All Bills");
        populateTable(view.getTable(), bills);
        view.setVisible(true);
    }
}