package ceng.estu.classes;


import java.util.LinkedList;

/**
 * This class bounds tables and bills to each other Also
 * provide some functionality about adding products to bill.
 *
 *
 * @author reuzun
 */
public class Bill {
    /**
     * A list that holds products for identifed Table in the class.
     */
    private LinkedList<Product> bill;
    /**
     * Table that has the bill.
     */
    private Table table;
    /**
     * The Total price.
     */
    public double totalPrice = 0;


    /**
     * Instantiates a new Bill.
     */
    public Bill(){
        bill = new LinkedList<>();
    }

    /**
     * Gets bill.
     *
     * @return the bill
     */
    public LinkedList<Product> getBill() {
        return bill;
    }


    /**
     * Sets table of the bill.
     *
     * @param table the table
     */
    public void setTable(Table table) {
        this.table = table;
    }


    /**
     * Adds the specified item in specified count to bill.
     *
     * @param product the Product
     * @param count   the Count of product to add.
     */
    public void addToBill(Product product, int count){
        totalPrice += product.getPrice()*count;
        for(int i = 0 ; i < bill.size() ; i++){
            if(bill.get(i).getName().equals(product.getName())){
                bill.get(i).increaseCount(count);
                return;
            }
        }
        product.increaseCount(count-1);
        bill.add(product);
    }

}
