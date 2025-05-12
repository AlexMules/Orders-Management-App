package gui.view;

import gui.Controller;
import model.Client;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ClientView extends JFrame {
    private JTextField textName;
    private JTextField textAddress;
    private JTextField textEmail;
    private JButton     btnAddClient;

    private JComboBox<Client> clientComboBox;
    private JButton           btnDeleteClient;

    private JComboBox<Client> clientEditComboBox;
    private JButton           btnEditClient;

    private JButton btnViewAll;

    private Controller controller;

    public ClientView(String title, Controller controller) {
        super(title);
        this.controller = controller;
        prepareGui();
    }

    private void prepareGui() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 500);
        setLocationRelativeTo(null);

        // Use a vertical BoxLayout on the content pane
        Container cp = getContentPane();
        cp.setLayout(new BoxLayout(cp, BoxLayout.Y_AXIS));

        // Row 1: Name / Address / Email / Add Client
        JPanel row1 = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JLabel labelName    = new JLabel("Name:");
        textName            = new JTextField(12);
        JLabel labelAddress = new JLabel("Address:");
        textAddress         = new JTextField(20);
        JLabel labelEmail   = new JLabel("Email:");
        textEmail           = new JTextField(18);
        btnAddClient        = new JButton("Add Client");
        btnAddClient.setFont(btnAddClient.getFont().deriveFont(Font.BOLD, 16f));
        btnAddClient.addActionListener(e -> {
            controller.handleAddClient(
                    textName.getText(),
                    textAddress.getText(),
                    textEmail.getText(),
                    this
            );
            textName.setText("");
            textAddress.setText("");
            textEmail.setText("");
            refreshClients();
        });

        row1.add(labelName);
        row1.add(textName);
        row1.add(labelAddress);
        row1.add(textAddress);
        row1.add(labelEmail);
        row1.add(textEmail);
        row1.add(btnAddClient);

        // Row 2: Select Client / Delete Client
        JPanel row2 = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JLabel lblSelect = new JLabel("Select Client:");
        clientComboBox    = new JComboBox<>();
        btnDeleteClient   = new JButton("Delete Client");
        btnDeleteClient.setFont(btnDeleteClient.getFont().deriveFont(Font.BOLD, 16f));
        btnDeleteClient.addActionListener(e -> {
            Client sel = (Client) clientComboBox.getSelectedItem();
            if (sel != null) {
                controller.handleDeleteClient(sel, this);
                refreshClients();
            }
        });

        row2.add(lblSelect);
        row2.add(clientComboBox);
        row2.add(btnDeleteClient);

        // Row 3: Edit Client Combo + Button
        JPanel row3 = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JLabel lblEdit   = new JLabel("Edit Client:");
        clientEditComboBox = new JComboBox<>();
        btnEditClient      = new JButton("Edit Client");
        btnEditClient.setFont(btnEditClient.getFont().deriveFont(Font.BOLD, 16f));
        btnEditClient.addActionListener(e -> {
            Client selected = (Client) clientEditComboBox.getSelectedItem();
            if (selected != null) {
                controller.handleOpenEditClientWindow(selected, this);
                refreshClients();
            }
        });

        row3.add(lblEdit);
        row3.add(clientEditComboBox);
        row3.add(btnEditClient);

        JPanel row4 = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        btnViewAll = new JButton("View All Clients");
        btnViewAll.setFont(btnViewAll.getFont().deriveFont(Font.BOLD, 16f));
        btnViewAll.addActionListener(e -> controller.handleOpenViewAllTable());
        row4.add(btnViewAll);
        cp.add(row4);

        // Add all rows to the content pane
        cp.add(row1);
        cp.add(row2);
        cp.add(row3);
        cp.add(row4);

        // finally populate the combo-boxes
        refreshClients();
    }

    protected void refreshClients() {
        clientComboBox.removeAllItems();
        clientEditComboBox.removeAllItems();
        List<Client> clients = controller.getAllClients();
        for (Client c : clients) {
            clientComboBox.addItem(c);
            clientEditComboBox.addItem(c);
        }
    }
}
