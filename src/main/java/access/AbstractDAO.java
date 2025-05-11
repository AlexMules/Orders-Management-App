package access;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import connection.ConnectionFactory;


public class AbstractDAO<T> {
    protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());

    private final Class<T> type;

    @SuppressWarnings("unchecked")
    public AbstractDAO() {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    private String createFindByIDSelectQuery() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" * ");
        sb.append(" FROM ");
        sb.append(type.getSimpleName());
        sb.append(" WHERE " + "id" + " =?");
        return sb.toString();
    }

    private String createFindAllSelectQuery() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" * ");
        sb.append(" FROM ");
        sb.append(type.getSimpleName());
        return sb.toString();
    }

    private String createInsertQuery(String table) {
        List<String> cols = new ArrayList<>();
        StringBuilder sb = new StringBuilder();

        for (Field f : type.getDeclaredFields()) {
            String name = f.getName();
            if ("id".equalsIgnoreCase(name)) continue;
            cols.add(name);
            if (!sb.isEmpty()) sb.append(", ");
            sb.append("?");
        }

        String colsPart = String.join(", ", cols);
        return "INSERT INTO " + table +
                " (" + colsPart + ") VALUES (" + sb + ")";
    }

    private void closeAll(ResultSet resultSet, PreparedStatement preparedStatement, Connection connection) {
        ConnectionFactory.close(resultSet);
        ConnectionFactory.close(preparedStatement);
        ConnectionFactory.close(connection);
    }

    //find object
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

    //find object
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

            return createObjects(resultSet).get(0);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
        } finally {
            closeAll(resultSet, statement, connection);
        }
        return null;
    }

    private List<T> createObjects(ResultSet resultSet) {
        List<T> list = new ArrayList<>();
        Constructor[] ctors = type.getDeclaredConstructors();
        Constructor ctor = null;
        for (int i = 0; i < ctors.length; i++) {
            ctor = ctors[i];
            if (ctor.getGenericParameterTypes().length == 0)
                break;
        }
        try {
            while (resultSet.next()) {
                ctor.setAccessible(true);
                T instance = (T)ctor.newInstance();
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

    //insert object
    public T insert(T t) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String raw   = type.getSimpleName().toLowerCase(); // "order"
        String table = "`" + raw + "`";                     // "`order`"
        String query = createInsertQuery(table);

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            int idx = 1;
            for (Field field : type.getDeclaredFields()) {
                String name = field.getName();
                if ("id".equalsIgnoreCase(name)) {
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

    public T update(T t) {
        // TODO:
        return t;
    }
}
