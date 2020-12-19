package ceng.estu.classes;

import java.util.ArrayList;

/**
 * This class is used for instantiating and grouping products via using Type Enum.
 *
 * @author reuzun
 */
public class Product {
    /**
     * The global menu.
     */
    public static ArrayList<Product> menu = new ArrayList<>();
    /**
     * Name of product.
     */
    private String name;
    /**
     * Price of product.
     */
    private double price;
    /**
     * Count of product.
     */
    private int count;
    /**
     * The Type of product.
     */
    public Type type;

    /**
     * Instantiates a new Product.
     *
     * @param name  the name
     * @param price the price
     * @param type  the type
     */
    public Product(String name, double price, Type type) {
        this.name = name;
        this.price = price;
        count = 1;
        this.type = type;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets price.
     *
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets price.
     *
     * @param price the price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Increase count.
     *
     * @param a the a
     */
    public void increaseCount(int a){this.count += a;}

    /**
     * Get count int.
     *
     * @return the int
     */
    public int getCount(){return this.count;}

    /**
     * Returns a new product with same values.
     * <>p</>
     * This is for holding true counts for each table along each product.
     *
     * @return the product
     */
    public Product newInstance(){
        return new Product(this.name,this.price,this.type);
    }

    @Override
    public String toString() {
        return this.name;
    }

}
