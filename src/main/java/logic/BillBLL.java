package logic;

import access.BillDAO;
import model.Bill;

import java.util.List;

/**
 * Business‐logic layer for {@link Bill} records.
 * <p> No update or delete operations are supported, since bills are append-only once created. </p>
 */
public class BillBLL {

    private BillDAO billDAO;

    public BillBLL() {
        this.billDAO = new BillDAO();
    }

    /**
     * Inserts the given bill into the log.
     *
     * @param id the bill's id
     * @param orderId the corresponding order's id
     * @param clientName the client's name
     * @param productName the product's name
     * @param qty the ordered quantity
     * @param totalPrice the total price of the order
     * @return a new Bill instance with its database-generated id, or {@code null} if the insert failed
     */
    public Bill insertBill(int id, int orderId, String clientName, String productName, int qty, double totalPrice) {
        Bill bill = new Bill(id, orderId, clientName, productName, qty, totalPrice);
        return billDAO.insert(bill);
    }

    /**
     * Fetches every bill from the log table.
     *
     * @return a List of all Bill records; may be empty but never null
     */
    public List<Bill> findAllBills() {
        return billDAO.findAll();
    }
}
