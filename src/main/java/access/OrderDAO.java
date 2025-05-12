package access;

import model.Order;

public class OrderDAO extends AbstractDAO<Order> {

    // uses basic CRUD methods from superclass

    // TODO: create only order specific queries
    public Order delete(Order order){
        throw new UnsupportedOperationException("Delete not allowed for Order");
    }

    public Order updateField(Order order, String fieldName, Object newValue){
        throw new UnsupportedOperationException("Update not allowed for Order");
    }
}
