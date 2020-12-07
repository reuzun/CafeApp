package ceng.estu.controller;

import ceng.estu.classes.Product;
import ceng.estu.classes.Table;

import java.io.File;
import java.util.ArrayList;

/**
 * @author reuzun
 */
public interface GlobalVariables {
    int TABLECOUNT =  5;
    int MENUITEMCOUNT = 0;
    //not implemented since it will not used for a while.
    //File configFile = new File("config.cfg");

    ArrayList<Table> tableList = new ArrayList<>();
    ArrayList<Product> menu = new ArrayList<>();
}
