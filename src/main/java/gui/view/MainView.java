package gui.view;

import gui.Controller;

import javax.swing.*;
import java.awt.*;

public class MainView extends JFrame {
    private JButton btnClient;
    private JButton btnProduct;
    private JButton btnOrder;

    private Controller controller;

    public MainView(String title) {
        super(title);
        this.controller = new Controller();
        prepareGui();
    }

    private void prepareGui() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        Container cp = getContentPane();
        cp.setLayout(new BoxLayout(cp, BoxLayout.Y_AXIS));
        cp.add(Box.createVerticalGlue());

        btnClient = makeButton("Open Client Window");
        btnClient.addActionListener(e -> controller.handleOpenClientWindow());
        btnProduct = makeButton("Open Product Window");
        btnProduct.addActionListener(e -> controller.handleOpenProductWindow());
        btnOrder = makeButton("Create Order");
        btnOrder.addActionListener(e -> controller.handleOpenCreateOrderWindow());

        cp.add(btnClient);
        cp.add(Box.createVerticalStrut(20));
        cp.add(btnProduct);
        cp.add(Box.createVerticalStrut(20));
        cp.add(btnOrder);
        cp.add(Box.createVerticalGlue());
    }

    private JButton makeButton(String text) {
        JButton b = new JButton(text);
        b.setAlignmentX(Component.CENTER_ALIGNMENT);
        Dimension size = new Dimension(250, 50);
        b.setPreferredSize(size);
        b.setMaximumSize(size);
        Font f = b.getFont().deriveFont(Font.BOLD, 14f);
        b.setFont(f);
        return b;
    }
}
