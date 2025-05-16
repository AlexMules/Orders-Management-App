package logic;

import access.OrderDAO;
import model.Order;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Business‐logic layer for {@link Order} entities.
 * <p> No update or delete operations are exposed here; orders are append‐only once created.</p>
 */
public class OrderBLL {

    private OrderDAO orderDAO;

    public OrderBLL() {
        this.orderDAO = new OrderDAO();
    }

    /**
     * Retrieves all orders from the database.
     *
     * @return a non‐empty {@link List} of {@link Order}
     * @throws NoSuchElementException if no orders exist
     */
    public List<Order> findAllOrders() {
        List<Order> orders = orderDAO.findAll();
        if (orders == null) {
            throw new NoSuchElementException("No orders were found!");
        }
        return orders;
    }

    /**
     * Retrieves a single order by its primary key.
     *
     * @param id the order’s database ID
     * @return the matching {@link Order}
     * @throws NoSuchElementException if no order with the given ID exists
     */
    public Order findOrderById(int id) {
        Order order = orderDAO.findById(id);
        if (order == null) {
            throw new NoSuchElementException("The order with id =" + id + " was not found!");
        }
        return order;
    }

    /**
     * Inserts a new order into the database.
     *
     * @param clientName the name of the client who made the order
     * @param productName the name of the ordered product
     * @param quantity the ordered quantity
     * @return the inserted {@link Order}, with its generated ID populated
     * @throws NoSuchElementException if insertion fails
     */
    public Order insertOrder(String clientName, String productName, int quantity) {
        Order order = new Order(clientName, productName, quantity);
        Order inserted = orderDAO.insert(order);
        if (inserted == null) {
            throw new NoSuchElementException("The order was not inserted!");
        }
        return inserted;
    }
}
