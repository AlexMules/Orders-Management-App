package access;

import model.Order;

/**
 * DAO (Data Access Object) for {@link Order} entities.
 */
public class OrderDAO extends AbstractDAO<Order> {
    public Order delete(Order order){
        throw new UnsupportedOperationException("Delete not allowed for Order!");
    }

    public Order updateField(Order order, String fieldName, Object newValue){
        throw new UnsupportedOperationException("Update not allowed for Order!");
    }
}
