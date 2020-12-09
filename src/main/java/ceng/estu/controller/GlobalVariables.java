package ceng.estu.controller;

import ceng.estu.classes.Product;
import ceng.estu.classes.Table;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;

import java.io.File;
import java.util.ArrayList;

/**
 * @author reuzun
 */
public interface GlobalVariables {
    //not implemented since it will not used for a while.
    //File configFile = new File("config.cfg");

    ArrayList<Table> tableList = new ArrayList<>();
    ArrayList<Product> menu = new ArrayList<>();

}
