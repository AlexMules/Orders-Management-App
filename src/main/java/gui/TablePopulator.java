// src/gui/util/TablePopulator.java
package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.beans.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Collectors;

public interface TablePopulator {

    default <T> void populateTable(JTable table, List<T> items) {
        if (items == null || items.isEmpty()) {
            table.setModel(new DefaultTableModel());
            return;
        }

        // inferăm tipul bean-ului din primul element
        Class<?> type = items.getFirst().getClass();

        // 1) toate câmpurile declarate din clasa
        Field[] fields = type.getDeclaredFields();
        String[] colNames = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            colNames[i] = fields[i].getName();
        }

        // 2) construim matricea de date
        Object[][] data = new Object[items.size()][fields.length];
        for (int row = 0; row < items.size(); row++) {
            T obj = items.get(row);
            for (int col = 0; col < fields.length; col++) {
                String fname = fields[col].getName();
                try {
                    PropertyDescriptor pd = new PropertyDescriptor(fname, type);
                    data[row][col] = pd.getReadMethod().invoke(obj);
                } catch (IntrospectionException
                         | IllegalAccessException
                         | InvocationTargetException ex) {
                    data[row][col] = null;
                }
            }
        }

        // 3) setăm un model non‐editabil
        DefaultTableModel model = new DefaultTableModel(data, colNames) {
            @Override public boolean isCellEditable(int r, int c) {
                return false;
            }
        };
        table.setModel(model);
    }
}
