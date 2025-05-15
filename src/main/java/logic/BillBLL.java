package logic;

import access.BillDAO;
import model.Bill;

import java.util.List;

public class BillBLL {
    private final BillDAO dao = new BillDAO();

    public Bill insertBill(Bill bill) {
        return dao.insert(bill);
    }

    public List<Bill> findAllBills() {
        return dao.findAll();
    }
}
