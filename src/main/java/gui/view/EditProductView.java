package gui.view;

import gui.Controller;
import model.Product;

import javax.swing.*;
import java.awt.*;

/**
 * Swing GUI window for editing an existing product’s details.
 */
public class EditProductView extends JFrame {
    private final Product product;
    private final Controller controller;
    private final ProductView parent;

    private JTextField nameField;
    private JTextField priceField;
    private JTextField quantityField;
    private JButton btnSave;

    public EditProductView(Product product, Controller controller, ProductView parent) {
        super("Editing product");
        this.product    = product;
        this.controller = controller;
        this.parent     = parent;
        prepareGui();
    }

    private void prepareGui() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(500, 250);
        setLocationRelativeTo(null);

        Container cp = getContentPane();
        cp.setLayout(new BoxLayout(cp, BoxLayout.Y_AXIS));
        cp.add(Box.createVerticalStrut(10));

        JLabel title = new JLabel("Editing product");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 18f));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        cp.add(title);
        cp.add(Box.createVerticalStrut(20));

        JPanel fields = new JPanel(new GridLayout(3, 2, 10, 10));
        fields.setMaximumSize(new Dimension(480, 120));

        fields.add(new JLabel("Name:"));
        nameField = new JTextField(product.getName());
        fields.add(nameField);

        fields.add(new JLabel("Price:"));
        priceField = new JTextField(String.valueOf(product.getPrice()));
        fields.add(priceField);

        fields.add(new JLabel("Quantity:"));
        quantityField = new JTextField(String.valueOf(product.getQuantity()));
        fields.add(quantityField);

        cp.add(fields);
        cp.add(Box.createVerticalStrut(20));

        btnSave = new JButton("Save");
        btnSave.setFont(btnSave.getFont().deriveFont(Font.BOLD, 16f));
        btnSave.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnSave.addActionListener(e -> {
            String newName     = nameField.getText();
            String newPrice    = priceField.getText();
            String newQuantity = quantityField.getText();
            controller.handleEditProduct(product, newName, newPrice, newQuantity, this);
            parent.refreshProducts();
        });

        cp.add(btnSave);
        cp.add(Box.createVerticalStrut(10));
    }
}
