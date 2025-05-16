package access;

import connection.ConnectionFactory;
import model.Bill;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BillDAO {
    protected static final Logger LOGGER = Logger.getLogger(BillDAO.class.getName());
    private final Class<Bill> type = Bill.class;

    private String createInsertQuery() {
        StringBuilder columns = new StringBuilder();
        StringBuilder values = new StringBuilder();
        boolean first = true;
        for (Field f : type.getDeclaredFields()) {
            String name = f.getName();
            if (name.equalsIgnoreCase("id")){
                continue;
            }
            if (!first) {
                columns.append(", ");
                values.append(", ");
            }
            columns.append(name);
            values.append("?");
            first = false;
        }
        return "INSERT INTO `log` (" + columns + ") VALUES (" + values + ")";
    }

    private String createFindAllQuery() {
        return "SELECT * FROM `log`";
    }

    private void closeAll(ResultSet rs, PreparedStatement ps, Connection conn) {
        ConnectionFactory.close(rs);
        ConnectionFactory.close(ps);
        ConnectionFactory.close(conn);
    }

    public Bill insert(Bill bill) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionFactory.getConnection();
            String query = createInsertQuery();
            preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            int idx = 1;
            for (Field f : type.getDeclaredFields()) {
                String name = f.getName();
                if ("id".equalsIgnoreCase(name)){
                    continue;
                }
                Method accessor = type.getMethod(name);
                Object value = accessor.invoke(bill);
                preparedStatement.setObject(idx++, value);
            }

            int updated = preparedStatement.executeUpdate();
            if (updated == 0) {
                LOGGER.warning("Inserting bill failed, no rows affected.");
                return null;
            }

            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                int newId = resultSet.getInt(1);
                return new Bill(newId, bill.idOrder(), bill.clientName(), bill.productName(), bill.quantity(), bill.price());
            } else {
                LOGGER.warning("Inserting bill failed, no ID obtained.");
            }

        } catch (SQLException | NoSuchMethodException | IllegalAccessException | InvocationTargetException ex) {
            LOGGER.log(Level.SEVERE, "Error inserting Bill", ex);
        } finally {
            closeAll(resultSet, preparedStatement, connection);
        }
        return null;
    }


    public List<Bill> findAll() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Bill> bills = new ArrayList<>();

        try {
            connection = ConnectionFactory.getConnection();
            String sql = createFindAllQuery();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            Constructor<Bill> ctor = type.getDeclaredConstructor(
                    int.class, int.class, String.class, String.class, int.class, double.class);

            while (resultSet.next()) {
                Bill b = ctor.newInstance(
                        resultSet.getInt("id"),
                        resultSet.getInt("idOrder"),
                        resultSet.getString("clientName"),
                        resultSet.getString("productName"),
                        resultSet.getInt("quantity"),
                        resultSet.getDouble("price")
                );
                bills.add(b);
            }
        } catch (SQLException | NoSuchMethodException | InstantiationException | IllegalAccessException
                                                                        | InvocationTargetException ex) {
            LOGGER.log(Level.SEVERE, "Error reading Bills", ex);
        } finally {
            closeAll(resultSet, preparedStatement, connection);
        }

        return bills;
    }
}
