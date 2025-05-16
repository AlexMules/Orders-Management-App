package logic;

import access.BillDAO;
import model.Bill;

import java.util.List;

/**
 * Business‐logic layer for {@link Bill} records.
 * <p> No update or delete operations are supported, since bills are append-only once created. </p>
 */
public class BillBLL {
    private final BillDAO dao = new BillDAO();

    /**
     * Inserts the given bill into the log.
     *
     * @param bill the Bill to insert (its id field is ignored)
     * @return a new Bill instance with its database-generated id, or {@code null} if the insert failed
     */
    public Bill insertBill(Bill bill) {
        return dao.insert(bill);
    }

    /**
     * Fetches every bill from the log table.
     *
     * @return a List of all Bill records; may be empty but never null
     */
    public List<Bill> findAllBills() {
        return dao.findAll();
    }
}
