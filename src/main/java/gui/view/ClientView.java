package gui.view;

import javax.swing.*;
import java.awt.*;

public class ClientView extends JFrame{
    private JTextField txtName;
    private JTextField txtAddress;
    private JTextField txtEmail;
    private JButton btnAddClient;

    public ClientView(String title) {
        super(title);
        prepareGui();
    }

    private void prepareGui() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        // Increased window size
        setSize(1000, 200);
        setLocationRelativeTo(null);

        // FlowLayout with larger horizontal gap for spacing
        Container cp = getContentPane();
        cp.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));

        // Name label and field
        JLabel lblName = new JLabel("Name:");
        lblName.setFont(lblName.getFont().deriveFont(Font.BOLD, 16f));
        txtName = new JTextField(12);
        txtName.setFont(txtName.getFont().deriveFont(Font.PLAIN, 14f));

        // Address label and field
        JLabel lblAddress = new JLabel("Address:");
        lblAddress.setFont(lblAddress.getFont().deriveFont(Font.BOLD, 16f));
        txtAddress = new JTextField(20);
        txtAddress.setFont(txtAddress.getFont().deriveFont(Font.PLAIN, 14f));

        // Email label and field
        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setFont(lblEmail.getFont().deriveFont(Font.BOLD, 16f));
        txtEmail = new JTextField(18);
        txtEmail.setFont(txtEmail.getFont().deriveFont(Font.PLAIN, 14f));

        // Add Client button
        btnAddClient = new JButton("Add Client");
        btnAddClient.setFont(btnAddClient.getFont().deriveFont(Font.BOLD, 16f));

        // Add all components in one row
        cp.add(lblName);
        cp.add(txtName);
        cp.add(lblAddress);
        cp.add(txtAddress);
        cp.add(lblEmail);
        cp.add(txtEmail);
        cp.add(btnAddClient);
    }
}
