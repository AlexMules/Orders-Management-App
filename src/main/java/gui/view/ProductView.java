package gui.view;

import gui.Controller;

import javax.swing.*;
import java.awt.*;

public class ProductView extends JFrame {
    private final Controller controller;

    private JTextField textName;
    private JTextField textPrice;
    private JTextField textQuantity;
    private JButton    btnAddProduct;

    public ProductView(String title, Controller controller) {
        super(title);
        this.controller = controller;
        prepareGui();
    }

    private void prepareGui() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(1000, 500);
        setLocationRelativeTo(null);

        // Vom folosi același stil: un singur rând cu FlowLayout
        Container cp = getContentPane();
        cp.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));

        // Name
        JLabel lblName = new JLabel("Name:");
        lblName.setFont(lblName.getFont().deriveFont(Font.BOLD, 16f));
        textName = new JTextField(20);
        textName.setFont(textName.getFont().deriveFont(Font.PLAIN, 14f));

        // Price
        JLabel lblPrice = new JLabel("Price:");
        lblPrice.setFont(lblPrice.getFont().deriveFont(Font.BOLD, 16f));
        textPrice = new JTextField(10);
        textPrice.setFont(textPrice.getFont().deriveFont(Font.PLAIN, 14f));

        // Quantity
        JLabel lblQuantity = new JLabel("Quantity:");
        lblQuantity.setFont(lblQuantity.getFont().deriveFont(Font.BOLD, 16f));
        textQuantity = new JTextField(10);
        textQuantity.setFont(textQuantity.getFont().deriveFont(Font.PLAIN, 14f));

        // Add Product button
        btnAddProduct = new JButton("Add Product");
        btnAddProduct.setFont(btnAddProduct.getFont().deriveFont(Font.BOLD, 16f));
        btnAddProduct.addActionListener(e -> {
            String nameStr = textName.getText();
            String priceStr = textPrice.getText();
            String qtyStr = textQuantity.getText();

            controller.handleAddProduct(nameStr, priceStr, qtyStr, this);

            // clear fields on success
            textName.setText("");
            textPrice.setText("");
            textQuantity.setText("");
        });

        // Adăugăm componentele în rând
        cp.add(lblName);
        cp.add(textName);
        cp.add(lblPrice);
        cp.add(textPrice);
        cp.add(lblQuantity);
        cp.add(textQuantity);
        cp.add(btnAddProduct);
    }
}
