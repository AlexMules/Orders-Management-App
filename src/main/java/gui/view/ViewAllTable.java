package gui.view;

import gui.Controller;
import model.Client;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ViewAllTable extends JFrame {
    private final Controller controller;
    private final JTable      table;

    public ViewAllTable(Controller controller) {
        super("All Clients");
        this.controller = controller;
        this.table      = new JTable();

        initGui();
        loadData();
    }

    private void initGui() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(800, 400);
        setLocationRelativeTo(null);

        // center: scrollable table
        getContentPane().setLayout(new BorderLayout(10,10));
        getContentPane().add(new JScrollPane(table), BorderLayout.CENTER);

        // bottom: a Close button
        JButton btnClose = new JButton("Close");
        btnClose.addActionListener(e -> dispose());
        JPanel south = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        south.add(btnClose);
        getContentPane().add(south, BorderLayout.SOUTH);
    }

    private void loadData() {
        List<Client> all = controller.getAllClients();
        controller.populateTable(table, all);
    }
}
