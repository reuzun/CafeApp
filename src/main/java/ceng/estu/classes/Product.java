package ceng.estu.classes;

/**
 * @author reuzun
 */
public class Product {
    private String name;
    private double price;
    private int count;

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
        count = 1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void increaseCount(int a){this.count += a;}

    public int getCount(){return this.count;}

    public Product newInstance(){
        return new Product(this.name,this.price);
    }

    @Override
    public String toString() {
        return this.name;
    }

}
