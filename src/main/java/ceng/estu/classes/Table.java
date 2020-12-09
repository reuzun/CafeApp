package ceng.estu.classes;

/**
 * @author reuzun
 */
public class Table {

    protected String nameOfTable;
    static int id = 0;
    private Bill bill;

    public Table(){
        nameOfTable = "table " + id++;
        bill = new Bill();
        bill.setTable(this);
    }

    public Bill getBill() {
        return bill;
    }

    @Override
    public String toString() {
        return this.nameOfTable +" ==> "+ this.getBill().totalPrice;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }



    public void reset(){
        this.bill.getBill().clear();
        this.bill.totalPrice = 0;
    }

    /*public void addProduct(Product product){
        bill.addToBill(product);
    }*/


}
