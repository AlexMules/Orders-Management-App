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
        RecordComponent[] recComps = type.isRecord() ? type.getRecordComponents() : null;

        if (recComps != null) {
            colNames = Arrays.stream(recComps).map(RecordComponent::getName).toArray(String[]::new);
        } else {
            colNames = Arrays.stream(type.getDeclaredFields()).map(Field::getName).toArray(String[]::new);
        }

        Object[][] data = items.stream()
                .map(obj -> Arrays.stream(colNames)
                        .map(col -> {
                            try {
                                if (recComps != null) {
                                    return recComps[Arrays.asList(colNames).indexOf(col)].getAccessor().invoke(obj);
                                } else {
                                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(col, type);
                                    return propertyDescriptor.getReadMethod().invoke(obj);
                                }
                            } catch (Exception e) {
                                return null;
                            }
                        })
                        .toArray(Object[]::new)
                )
                .toArray(Object[][]::new);

        DefaultTableModel model = new DefaultTableModel(data, colNames) {
            @Override public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        table.setModel(model);
    }
}
