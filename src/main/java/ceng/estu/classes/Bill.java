package ceng.estu.classes;

import ceng.estu.controller.GlobalVariables;

import java.util.LinkedList;

/**
 * @author reuzun
 */
public class Bill {
    private LinkedList<Product> bill;
    private Table table;


    public Bill(){
        bill = new LinkedList<>();
    }

    public LinkedList<Product> getBill() {
        return bill;
    }

    public void setBill(LinkedList<Product> bill) {
        this.bill = bill;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }



    /**
     * Adds the specified item to bill.
     *
     * @param product the product
     */
    public void addToBill(Product product){
        bill.add(product);
    }

    /**
     * Removes the specified item to bill.
     *
     * @param product the product
     */
    public void removeFromBill(Product product){
        bill.remove(product);
    }

    public void pay(){
        table.reset();
    }

}
