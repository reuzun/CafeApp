package ceng.estu.classes;

/**
 * @author reuzun
 */
public class Table {

    protected String nameOfTable;
    private Bill bill;

    public Table(String name){
        nameOfTable = name;
        bill = new Bill();
        bill.setTable(this);
    }

    public Bill getBill() {
        return bill;
    }

    @Override
    public String toString() {
        return this.nameOfTable;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }



    public void reset(){
        this.bill.getBill().clear();
    }

    public void addProduct(Product product){
        bill.addToBill(product);
    }


}
