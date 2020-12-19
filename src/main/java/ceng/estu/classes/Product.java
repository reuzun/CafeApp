package ceng.estu.classes;

import java.util.ArrayList;

/**
 * @author reuzun
 */
public class Product {
    public static ArrayList<Product> menu = new ArrayList<>();
    private String name;
    private double price;
    private int count;
    public Type type;

    public Product(String name, double price, Type type) {
        this.name = name;
        this.price = price;
        count = 1;
        this.type = type;
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
        return new Product(this.name,this.price,this.type);
    }

    @Override
    public String toString() {
        return this.name;
    }

}
