// src/gui/view/OrderView.java
package gui.view;

import gui.Controller;
import model.Client;
import model.Product;

import javax.swing.*;
import java.awt.*;

public class OrderView extends JFrame {
    private final Controller controller;

    private JComboBox<Client>  clientCombo;
    private JComboBox<Product> productCombo;
    private JTextField         qtyField;
    private JButton            btnPlaceOrder;

    public OrderView(String title, Controller controller) {
        super(title);
        this.controller = controller;
        prepareGui();
    }

    private void prepareGui() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(1000, 500);
        setLocationRelativeTo(null);

        Container cp = getContentPane();
        cp.setLayout(new BorderLayout(10, 10));

        JPanel center = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        center.add(new JLabel("Client:"));
        clientCombo = new JComboBox<>();
        center.add(clientCombo);

        center.add(new JLabel("Product:"));
        productCombo = new JComboBox<>();
        center.add(productCombo);

        center.add(new JLabel("Quantity:"));
        qtyField = new JTextField(5);
        center.add(qtyField);

        btnPlaceOrder = new JButton("Place Order");
        btnPlaceOrder.setFont(btnPlaceOrder.getFont().deriveFont(Font.BOLD, 14f));
        btnPlaceOrder.addActionListener(e -> {
                    Client  client  = (Client)  clientCombo.getSelectedItem();
                    Product product = (Product) productCombo.getSelectedItem();
                    String  qtyStr  = qtyField.getText();

                    controller.handlePlaceOrder(client, product, qtyStr, this);
                    qtyField.setText("");
                    refreshLists();
                });
        center.add(btnPlaceOrder);

        cp.add(center, BorderLayout.CENTER);

        JPanel south = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnRefresh = new JButton("Refresh");
        btnRefresh.setFont(btnRefresh.getFont().deriveFont(Font.BOLD, 14f));
        btnRefresh.addActionListener(e -> refreshLists());
        south.add(btnRefresh);

        cp.add(south, BorderLayout.SOUTH);
        refreshLists();
    }

    public void refreshLists() {
        clientCombo.removeAllItems();
        for (Client c : controller.getAllClients()) {
            clientCombo.addItem(c);
        }
        productCombo.removeAllItems();
        for (Product p : controller.getAllProducts()) {
            productCombo.addItem(p);
        }
    }
}
