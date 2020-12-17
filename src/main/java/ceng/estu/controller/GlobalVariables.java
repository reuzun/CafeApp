package ceng.estu.controller;

import java.io.File;
import java.io.FileWriter;

import static ceng.estu.classes.Product.menu;
import static ceng.estu.classes.Table.tableList;

/**
 * for storing global variables about that program.
 *
 * @author reuzun
 */
public final class GlobalVariables {

    static File configFile = new File("config.cfg");

    static {
        new File("musics" + File.separator).mkdirs();
    }

    static void updateCFG() {
        try (FileWriter writer = new FileWriter(configFile, false)) {
            writer.write(tableList.size() + System.lineSeparator());
            for (ceng.estu.classes.Product product : menu) {
                writer.write(product.getName() + " " + product.getPrice() + " " + product.type + System.lineSeparator());
            }
        } catch (Exception ignored) {
        }
    }

    private GlobalVariables() {
    } //for not instantiating.

}
