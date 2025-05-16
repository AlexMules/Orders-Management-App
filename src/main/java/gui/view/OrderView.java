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
    private JButton            btnViewAll;

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

        JPanel form = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        form.add(new JLabel("Client:"));
        clientCombo = new JComboBox<>();
        form.add(clientCombo);

        form.add(new JLabel("Product:"));
        productCombo = new JComboBox<>();
        form.add(productCombo);

        form.add(new JLabel("Quantity:"));
        qtyField = new JTextField(5);
        form.add(qtyField);

        btnPlaceOrder = new JButton("Place Order");
        btnPlaceOrder.setFont(btnPlaceOrder.getFont().deriveFont(Font.BOLD, 14f));
        btnPlaceOrder.addActionListener(e -> {
            controller.handlePlaceOrder(
                    (Client) clientCombo.getSelectedItem(),
                    (Product) productCombo.getSelectedItem(),
                    qtyField.getText(),
                    this
            );
            qtyField.setText("");
            refreshLists();
        });
        form.add(btnPlaceOrder);

        cp.add(form, BorderLayout.NORTH);

        JPanel viewAllPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        btnViewAll = new JButton("View All Orders");
        btnViewAll.setFont(btnViewAll.getFont().deriveFont(Font.BOLD, 16f));
        btnViewAll.addActionListener(e -> controller.showAllWindow("All Orders", controller::getAllOrders));
        viewAllPanel.add(btnViewAll);
        cp.add(viewAllPanel, BorderLayout.CENTER);

        JPanel south = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnRefresh = new JButton("Refresh");
        btnRefresh.setFont(btnRefresh.getFont().deriveFont(Font.BOLD, 14f));
        btnRefresh.addActionListener(e -> refreshLists());
        south.add(btnRefresh);
        cp.add(south, BorderLayout.SOUTH);

        refreshLists();
    }

    private void refreshLists() {
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
