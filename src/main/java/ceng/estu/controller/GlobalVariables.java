package ceng.estu.controller;

import static ceng.estu.classes.Product.*;
import static ceng.estu.classes.Table.*;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

/**
 * for storing global variables about that program.
 * @author reuzun
 */
public final class GlobalVariables {

    static File configFile = new File("config.cfg");
    static Boolean files = new File("musics\\").mkdirs();
    /*
    static ArrayList<Table> tableList = new ArrayList<>();
    static ArrayList<Product> menu = new ArrayList<>();
*/
    static void updateCFG() {
        try (FileWriter writer = new FileWriter(configFile,false)) {
                    writer.write(tableList.size()+System.lineSeparator());
                for(int i = 0 ; i < menu.size() ; i++){
                    writer.write(menu.get(i).getName()+" "+menu.get(i).getPrice()+" "+ menu.get(i).type+System.lineSeparator());
                }
        }
        catch (Exception e) {

        }
    }

    private GlobalVariables(){} //for not instantiating.

}
