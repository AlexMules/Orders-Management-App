package gui.view;

import gui.Controller;
import model.Product;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ProductView extends JFrame {
    private final Controller controller;

    private JTextField textName;
    private JTextField textPrice;
    private JTextField textQuantity;
    private JButton    btnAddProduct;

    private JComboBox<Product> productComboBox;
    private JButton            btnDeleteProduct;

    private JComboBox<Product> productEditComboBox;
    private JButton            btnEditProduct;
    private JButton btnViewAll;

    public ProductView(String title, Controller controller) {
        super(title);
        this.controller = controller;
        prepareGui();
    }

    private void prepareGui() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(1000, 500);
        setLocationRelativeTo(null);

        Container cp = getContentPane();
        cp.setLayout(new BoxLayout(cp, BoxLayout.Y_AXIS));

        JPanel row1 = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        row1.add(new JLabel("Name:"));
        textName = new JTextField(12);
        row1.add(textName);

        row1.add(new JLabel("Price:"));
        textPrice = new JTextField(8);
        row1.add(textPrice);

        row1.add(new JLabel("Quantity:"));
        textQuantity = new JTextField(5);
        row1.add(textQuantity);

        btnAddProduct = new JButton("Add Product");
        btnAddProduct.setFont(btnAddProduct.getFont().deriveFont(Font.BOLD, 16f));
        btnAddProduct.addActionListener(e -> {
            controller.handleAddProduct(
                    textName.getText(),
                    textPrice.getText(),
                    textQuantity.getText(),
                    this
            );
            textName.setText("");
            textPrice.setText("");
            textQuantity.setText("");
            refreshProducts();
        });
        row1.add(btnAddProduct);

        JPanel row2 = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        row2.add(new JLabel("Select Product:"));
        productComboBox = new JComboBox<>();
        productComboBox.setFont(productComboBox.getFont().deriveFont(Font.PLAIN, 14f));
        row2.add(productComboBox);

        btnDeleteProduct = new JButton("Delete Product");
        btnDeleteProduct.setFont(btnDeleteProduct.getFont().deriveFont(Font.BOLD, 16f));
        btnDeleteProduct.addActionListener(e -> {
            Product selected = (Product) productComboBox.getSelectedItem();
            if (selected != null) {
                controller.handleDeleteProduct(selected, this);
                refreshProducts();
            }
        });
        row2.add(btnDeleteProduct);

        JPanel row3 = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        row3.add(new JLabel("Edit Product:"));
        productEditComboBox = new JComboBox<>();
        productEditComboBox.setFont(productEditComboBox.getFont().deriveFont(Font.PLAIN, 14f));
        row3.add(productEditComboBox);

        btnEditProduct = new JButton("Edit Product");
        btnEditProduct.setFont(btnEditProduct.getFont().deriveFont(Font.BOLD, 16f));
        btnEditProduct.addActionListener(e -> {
            Product selected = (Product) productEditComboBox.getSelectedItem();
            if (selected != null) {
                controller.handleOpenEditProductWindow(selected, this);
            }
        });
        row3.add(btnEditProduct);

        JPanel row4 = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        btnViewAll = new JButton("View All Products");
        btnViewAll.setFont(btnViewAll.getFont().deriveFont(Font.BOLD, 16f));
        btnViewAll.addActionListener(e ->
                controller.showAllWindow(
                        "All Products",
                        controller::getAllProducts
                )
        );

        row4.add(btnViewAll);

        // add rows
        cp.add(row1);
        cp.add(row2);
        cp.add(row3);
        cp.add(row4);

        refreshProducts();
    }

    public void refreshProducts() {
        List<Product> all = controller.getAllProducts();
        productComboBox.removeAllItems();
        productEditComboBox.removeAllItems();
        for (Product p : all) {
            productComboBox.addItem(p);
            productEditComboBox.addItem(p);
        }
    }
}