// src/gui/view/ViewAllTable.java
package gui.view;

import javax.swing.*;
import java.awt.*;

/**
 * A reusable window that contains only a JTable (in a scroll pane)
 * and a Close button.  Data is populated externally via getTable().
 */
public class ViewAllTable<T> extends JFrame {
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

        // Use BorderLayout: table in CENTER, Close in SOUTH
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout(10, 10));

        // 1) scrollable table
        JScrollPane scroll = new JScrollPane(table);
        cp.add(scroll, BorderLayout.CENTER);

        // 2) close button
        JButton btnClose = new JButton("Close");
        btnClose.addActionListener(e -> dispose());
        JPanel south = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        south.add(btnClose);
        cp.add(south, BorderLayout.SOUTH);
    }

    /**
     * Exposes the JTable so callers can do:
     *   controller.populateTable(view.getTable(), dataList);
     */
    public JTable getTable() {
        return table;
    }
}
