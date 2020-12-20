package ceng.estu.classes;

import java.util.ArrayList;

/**
 * Table class is like a real table in the life. It have bills.
 * it handled in bill class.
 *
 * @author reuzun
 */
public class Table {
    /**
     * The global tableList.
     */
    public static ArrayList<Table> tableList = new ArrayList<>();
    /**
     * The Name of table.
     */
    protected String nameOfTable;
    /**
     * The Id of table.
     */
    public static int id = 0;
    private Bill bill;

    /**
     * Instantiates a new Table.
     */
    public Table(){
        nameOfTable = "table " + id++;
        bill = new Bill();
        bill.setTable(this);
    }

    /**
     * Gets bill.
     *
     * @return the bill
     */
    public Bill getBill() {
        return bill;
    }

    @Override
    public String toString() {
        return this.nameOfTable +" ==> "+ this.getBill().totalPrice;
    }

    /**
     * Sets bill.
     *
     * @param bill the bill
     */
    public void setBill(Bill bill) {
        this.bill = bill;
    }

    /**
     * Reset.
     */
    public void reset(){
        this.bill.getBill().clear();
        this.bill.totalPrice = 0;
    }
}
