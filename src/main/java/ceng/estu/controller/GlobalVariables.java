package ceng.estu.controller;

import ceng.estu.classes.Product;
import ceng.estu.classes.Table;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

/**
 * @author reuzun
 */
public interface GlobalVariables {
    //not implemented since it will not used for a while.
    File configFile = new File("config.cfg");

    ArrayList<Table> tableList = new ArrayList<>();
    ArrayList<Product> menu = new ArrayList<>();

    static void updateCFG() {
        //boolean flag = true;
        try (FileWriter writer = new FileWriter(configFile,false)) {
                //if(flag){}
                    writer.write(tableList.size()+System.lineSeparator());
                for(int i = 0 ; i < menu.size() ; i++){
                    writer.write(menu.get(i).getName()+" "+menu.get(i).getPrice()+System.lineSeparator());
                }
        }
        catch (Exception e) {

        }
    }

}
