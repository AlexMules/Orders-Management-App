package access;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import connection.ConnectionFactory;

/**
 * <p> A generic Data Access Object (DAO) base class that provides
 * common CRUD operations for any domain entity T, using reflection
 * and introspection to map fields to table columns. </p>
 * @param <T>
 */
public class AbstractDAO<T> {
    protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());
    private final Class<T> type;

    @SuppressWarnings("unchecked")
    public AbstractDAO() {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /**
     * <p> Creates the query for finding the object using his ID.</p>
     * @return the query as a string
     */
    private String createFindByIDSelectQuery() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" * ");
        sb.append(" FROM ");
        sb.append("`" + type.getSimpleName() + "`");
        sb.append(" WHERE " + "id" + " =?");
        return sb.toString();
    }

    /**
     * <p> Creates the query for finding all objects.</p>
     * @return the query as a string
     */
    private String createFindAllSelectQuery() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" * ");
        sb.append(" FROM ");
        sb.append("`" + type.getSimpleName() + "`");
        return sb.toString();
    }

    /**
     * <p> Creates the query for inserting an object in the DB. </p>
     * @param table name as a string
     * @return the query as a string
     */
    private String createInsertQuery(String table) {
        var cols = Arrays.stream(type.getDeclaredFields())
                .map(Field::getName)
                .filter(n -> !n.equalsIgnoreCase("id"))
                .collect(Collectors.toList());

        String colsPart = String.join(", ", cols);

        String placeholders = cols.stream().map(c -> "?").collect(Collectors.joining(", "));

        return "INSERT INTO " + table + " (" + colsPart + ") VALUES (" + placeholders + ")";
    }

    /**
     * <p> Closes all resources(resultSet, preparedStatement and connection) used by the DAO. </p>
     * @param resultSet
     * @param preparedStatement
     * @param connection
     */
    private void closeAll(ResultSet resultSet, PreparedStatement preparedStatement, Connection connection) {
        ConnectionFactory.close(resultSet);
        ConnectionFactory.close(preparedStatement);
        ConnectionFactory.close(connection);
    }

    /**
     * <p> Closes all resources(statement and connection) used by the DAO. </p>
     * @param statement
     * @param connection
     */
    private void closeStatementAndConnection(PreparedStatement statement, Connection connection) {
        ConnectionFactory.close(statement);
        ConnectionFactory.close(connection);
    }

    /**
     * <p> Finds all objects of type T. </p>
     * @return a list of objects of type T
     */
    public List<T> findAll() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createFindAllSelectQuery();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            return createObjects(resultSet);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findAll " + e.getMessage());
        } finally {
            closeAll(resultSet, statement, connection);
        }
        return null;
    }

    /**
     * <p> Finds an object of type T using his ID. </p>
     * @param id
     * @return the object of type T
     */
    public T findById(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createFindByIDSelectQuery();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            List<T> list = createObjects(resultSet);
            return list.stream().findFirst().orElse(null);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
        } finally {
            closeAll(resultSet, statement, connection);
        }
        return null;
    }

    /**
     * <p> Creates a list of objects of type T. </p>
     * @param resultSet
     * @return the list of objects of type T
     */
    private List<T> createObjects(ResultSet resultSet) {
        List<T> list = new ArrayList<>();
        @SuppressWarnings("unchecked")
        Constructor<T> ctor = (Constructor<T>) Arrays.stream(type.getDeclaredConstructors())
                .filter(c -> c.getParameterCount() == 0)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No-arguments constructor not found for " + type.getName()));
        try {
            while (resultSet.next()) {
                ctor.setAccessible(true);
                T instance = ctor.newInstance();
                for (Field field : type.getDeclaredFields()) {
                    String fieldName = field.getName();
                    Object value = resultSet.getObject(fieldName);
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, type);
                    Method method = propertyDescriptor.getWriteMethod();
                    method.invoke(instance, value);
                }
                list.add(instance);
            }
        } catch (InstantiationException | IllegalAccessException | SecurityException | IllegalArgumentException |
                 InvocationTargetException | SQLException | IntrospectionException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * <p> Inserts an object of type T in the DB.</p>
     * @param t
     * @return the inserted object
     */
    public T insert(T t) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String rawTableName = type.getSimpleName().toLowerCase();
        String table = "`" + rawTableName + "`";
        String query = createInsertQuery(table);

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            int idx = 1;
            for (Field field : type.getDeclaredFields()) {
                String name = field.getName();
                if (name.equalsIgnoreCase("id")) {
                    continue;
                }
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(name, type);
                Object value = propertyDescriptor.getReadMethod().invoke(t);
                statement.setObject(idx++, value);
            }

            statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                int newId = resultSet.getInt(1);
                new PropertyDescriptor("id", type).getWriteMethod().invoke(t, newId);
            }
            return t;

        } catch (SQLException | IntrospectionException | IllegalAccessException | InvocationTargetException ex) {
            LOGGER.log(Level.WARNING, type.getName() + " insert error", ex);
        } finally {
            closeAll(resultSet, statement, connection);
        }
        return null;
    }

    /**
     * <p> Deletes an object from the DB. </p>
     * @param t
     * @return the deleted object
     */
    public T delete(T t) {
        Connection connection   = null;
        PreparedStatement statement  = null;
        String rawTableName   = type.getSimpleName().toLowerCase();
        String table = "`" + rawTableName + "`";

        try {
            PropertyDescriptor pd = new PropertyDescriptor("id", type);
            Object idValueBeforeDelete = pd.getReadMethod().invoke(t);
            T obj = findById((Integer) idValueBeforeDelete);
            if (obj == null) {
                return null;
            }

            String query   = "DELETE FROM " + table + " WHERE id = ?";
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);

            PropertyDescriptor propertyDescriptor = new PropertyDescriptor("id", type);
            Object idValue = propertyDescriptor.getReadMethod().invoke(t);
            statement.setObject(1, idValue);

            statement.executeUpdate();
            return obj;

        } catch (SQLException | IntrospectionException | IllegalAccessException | InvocationTargetException ex) {
            LOGGER.log(Level.WARNING, type.getName() + " delete error", ex);
        } finally {
            closeStatementAndConnection(statement, connection);
        }
        return null;
    }

    /**
     * <p> Modifies the specified field of an existing object from the DB. </p>
     * @param t
     * @param fieldName
     * @param newValue
     * @return the modified object
     */
    public T updateField(T t, String fieldName, Object newValue) {
        if (fieldName.equalsIgnoreCase("id")) {
            throw new IllegalArgumentException("Cannot update the 'id' field");
        }

        String table = "`" + type.getSimpleName().toLowerCase() + "`";
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            PropertyDescriptor pdId = new PropertyDescriptor("id", type);
            Integer idValue = (Integer) pdId.getReadMethod().invoke(t);

            boolean validField = Arrays.stream(type.getDeclaredFields())
                    .map(Field::getName)
                    .anyMatch(f -> f.equals(fieldName));
            if (!validField) {
                throw new IllegalArgumentException("Invalid field: " + fieldName);
            }

            PropertyDescriptor pdField = new PropertyDescriptor(fieldName, type);
            Method write = pdField.getWriteMethod();
            write.invoke(t, newValue);

            String query = "UPDATE " + table + " SET `" + fieldName + "` = ? WHERE `id` = ?";
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setObject(1, newValue);
            statement.setObject(2, idValue);
            int updated = statement.executeUpdate();

            if (updated > 0) {
                return findById(idValue);
            }
        } catch (SQLException | IntrospectionException | IllegalAccessException | InvocationTargetException ex) {
            LOGGER.log(Level.WARNING, type.getName() + " updateField error", ex);
        } finally {
            closeStatementAndConnection(statement, connection);
        }
        return null;
    }
}
