package logic;

import access.OrderDAO;
import model.Order;

import java.util.List;
import java.util.NoSuchElementException;

public class OrderBLL {

    private OrderDAO orderDAO;

    public OrderBLL() {
        this.orderDAO = new OrderDAO();
    }

    public List<Order> findAllOrders() {
        List<Order> orders = orderDAO.findAll();
        if (orders == null) {
            throw new NoSuchElementException("No orders were found!");
        }
        return orders;
    }

    public Order findOrderById(int id) {
        Order order = orderDAO.findById(id);
        if (order == null) {
            throw new NoSuchElementException("The order with id =" + id + " was not found!");
        }
        return order;
    }

    public void insertOrder(Order order) {
        Order orderInserted = orderDAO.insert(order);
        if (orderInserted == null) {
            throw new NoSuchElementException("The order was not inserted!");
        }
    }
}
