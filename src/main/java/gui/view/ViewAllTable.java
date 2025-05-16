package gui.view;

import javax.swing.*;
import java.awt.*;

/**
 * A scrollable window for displaying any list of items in a JTable.
 */
public class ViewAllTable extends JFrame {
    private final JTable table;

    public ViewAllTable(String title) {
        super(title);
        this.table = new JTable();
        initGui();
    }

    private void initGui() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(800, 400);
        setLocationRelativeTo(null);

        Container cp = getContentPane();
        cp.setLayout(new BorderLayout(10, 10));

        JScrollPane scroll = new JScrollPane(table);
        cp.add(scroll, BorderLayout.CENTER);

        JButton btnClose = new JButton("Close");
        btnClose.addActionListener(e -> dispose());
        JPanel south = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        south.add(btnClose);
        cp.add(south, BorderLayout.SOUTH);
    }

    public JTable getTable() {
        return table;
    }
}
