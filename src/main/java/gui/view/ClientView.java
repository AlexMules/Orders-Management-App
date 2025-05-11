package gui.view;

import gui.Controller;
import model.Client;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ClientView extends JFrame{
    private JTextField nameField;
    private JTextField addressField;
    private JTextField emailField;
    private JButton btnAddClient;
    private JLabel labelName;
    private JLabel labelAddress;
    private JLabel labelEmail;

    private JComboBox<Client> clientComboBox;
    private JButton btnDeleteClient;

    private Controller controller = new Controller();

    public ClientView(String title) {
        super(title);
        prepareGui();
    }

    private void prepareGui() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);

        Container cp = getContentPane();
        cp.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));

        labelName = new JLabel("Name:");
        labelName.setFont(labelName.getFont().deriveFont(Font.BOLD, 16f));
        nameField = new JTextField(12);
        nameField.setFont(nameField.getFont().deriveFont(Font.PLAIN, 14f));

        labelAddress = new JLabel("Address:");
        labelAddress.setFont(labelAddress.getFont().deriveFont(Font.BOLD, 16f));
        addressField = new JTextField(20);
        addressField.setFont(addressField.getFont().deriveFont(Font.PLAIN, 14f));

        labelEmail = new JLabel("Email:");
        labelEmail.setFont(labelEmail.getFont().deriveFont(Font.BOLD, 16f));
        emailField = new JTextField(18);
        emailField.setFont(emailField.getFont().deriveFont(Font.PLAIN, 14f));

        btnAddClient = new JButton("Add Client");
        btnAddClient.setFont(btnAddClient.getFont().deriveFont(Font.BOLD, 16f));
        btnAddClient.addActionListener(e -> {
            String name = nameField.getText();
            String address = addressField.getText();
            String email = emailField.getText();
            controller.handleAddClient(name, address, email, this);
            nameField.setText("");
            addressField.setText("");
            emailField.setText("");
            refreshClients();
        });

        // ComboBox and Delete Button
        clientComboBox = new JComboBox<>();
        refreshClients();

        btnDeleteClient = new JButton("Delete Client");
        btnDeleteClient.setFont(btnDeleteClient.getFont().deriveFont(Font.BOLD, 16f));
        btnDeleteClient.addActionListener(e -> {
            Client selected = (Client) clientComboBox.getSelectedItem();
            if (selected != null) {
                controller.handleDeleteClient(selected, this);
                refreshClients();
            }
        });

        cp.add(labelName);
        cp.add(nameField);
        cp.add(labelAddress);
        cp.add(addressField);
        cp.add(labelEmail);
        cp.add(emailField);
        cp.add(btnAddClient);

        JLabel selectClientLabel = new JLabel("Select Client:");
        selectClientLabel.setFont(selectClientLabel.getFont().deriveFont(Font.BOLD, 16f));
        cp.add(selectClientLabel);
        clientComboBox.setFont(clientComboBox.getFont().deriveFont(Font.PLAIN, 14f));
        cp.add(clientComboBox);
        cp.add(btnDeleteClient);
    }

    private void refreshClients() {
        clientComboBox.removeAllItems();
        List<Client> clients = controller.getAllClients();
        for (Client c : clients) {
            clientComboBox.addItem(c);
        }
    }
}
