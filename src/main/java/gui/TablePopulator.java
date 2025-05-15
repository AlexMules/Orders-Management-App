package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.beans.*;
import java.lang.reflect.Field;
import java.lang.reflect.RecordComponent;
import java.util.Arrays;
import java.util.List;

public interface TablePopulator {

    default <T> void populateTable(JTable table, List<T> items) {
        if (items == null || items.isEmpty()) {
            table.setModel(new DefaultTableModel());
            return;
        }

        Class<?> type = items.getFirst().getClass();
        String[] colNames;
        RecordComponent[] recComps = type.isRecord()
                ? type.getRecordComponents()
                : null;

        if (recComps != null) {
            colNames = Arrays.stream(recComps)
                    .map(RecordComponent::getName)
                    .toArray(String[]::new);
        } else {
            Field[] fields = type.getDeclaredFields();
            colNames = Arrays.stream(fields)
                    .map(Field::getName)
                    .toArray(String[]::new);
        }

        Object[][] data = new Object[items.size()][colNames.length];
        for (int r = 0; r < items.size(); r++) {
            T obj = items.get(r);

            for (int c = 0; c < colNames.length; c++) {
                try {
                    Object value;
                    if (recComps != null) {
                        // call recordName() accessor:
                        value = recComps[c].getAccessor().invoke(obj);
                    } else {
                        // JavaBean via PropertyDescriptor
                        var pd = new PropertyDescriptor(colNames[c], type);
                        value = pd.getReadMethod().invoke(obj);
                    }
                    data[r][c] = value;
                } catch (Exception e) {
                    data[r][c] = null;
                }
            }
        }

        DefaultTableModel model = new DefaultTableModel(data, colNames) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };
        table.setModel(model);
    }
}
