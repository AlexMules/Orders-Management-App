package gui.view;

import gui.Controller;
import model.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditClientView extends JFrame {
    private final Client client;
    private final Controller controller;
    private final ClientView parent;

    private JTextField nameField;
    private JTextField addressField;
    private JTextField emailField;
    private JButton btnSave;

    public EditClientView(Client client, Controller controller, ClientView clientView) {
        super("Editing client");
        this.client = client;
        this.controller = controller;
        this.parent = clientView;
        prepareGui();
    }

    private void prepareGui() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 250);
        setLocationRelativeTo(null);

        Container cp = getContentPane();
        cp.setLayout(new BoxLayout(cp, BoxLayout.Y_AXIS));

        // Title label
        JLabel title = new JLabel("Editing client");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 18f));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        cp.add(Box.createVerticalStrut(10));
        cp.add(title);
        cp.add(Box.createVerticalStrut(20));

        // Fields panel
        JPanel fields = new JPanel(new GridLayout(3, 2, 10, 10));
        fields.setMaximumSize(new Dimension(480, 120));

        // Name
        fields.add(new JLabel("Name:"));
        nameField = new JTextField(client.getName());
        fields.add(nameField);

        // Address
        fields.add(new JLabel("Address:"));
        addressField = new JTextField(client.getAddress());
        fields.add(addressField);

        // Email
        fields.add(new JLabel("Email:"));
        emailField = new JTextField(client.getEmail());
        fields.add(emailField);

        cp.add(fields);
        cp.add(Box.createVerticalStrut(20));

        // Save button
        btnSave = new JButton("Save");
        btnSave.setFont(btnSave.getFont().deriveFont(Font.BOLD, 16f));
        btnSave.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnSave.addActionListener(e -> {
            String newName    = nameField.getText();
            String newAddress = addressField.getText();
            String newEmail   = emailField.getText();
            // Delegate everything to controller
            controller.handleEditClient(client, newName, newAddress, newEmail, this);
            parent.refreshClients();
        });
        cp.add(btnSave);
        cp.add(Box.createVerticalStrut(10));
    }
}