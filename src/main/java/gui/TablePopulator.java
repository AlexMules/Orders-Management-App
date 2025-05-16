package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.beans.*;
import java.lang.reflect.Field;
import java.lang.reflect.RecordComponent;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

/**
 * A utility interface for populating a {@link JTable} from a list of Java objects or records.
 * <p> If the provided list is null or empty, the table’s model is reset to an empty DefaultTableModel. </p>
 */
public interface TablePopulator {

    /**
     * Populates the given {@link JTable} with the data from the provided list of items.
     * @param table the JTable to populate; its model will be replaced
     * @param items the list of domain objects or records to display; if null or empty, the table is cleared
     * @param <T> the type of the items in the list
     */
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

        Object[][] data = items.stream().map(item -> IntStream.range(0, colNames.length)
                .mapToObj(c -> {
                    try {
                        if (recComps != null) {
                            return recComps[c].getAccessor().invoke(item);
                        } else {
                            return new PropertyDescriptor(colNames[c], type).getReadMethod().invoke(item);
                        }
                    } catch (Exception e) {
                        return null;
                    }
                })
                .toArray(Object[]::new))
                .toArray(Object[][]::new);

        DefaultTableModel model = new DefaultTableModel(data, colNames) {
            @Override public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        table.setModel(model);
    }
}
