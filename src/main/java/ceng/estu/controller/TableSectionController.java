package ceng.estu.controller;

import ceng.estu.classes.Bill;
import ceng.estu.classes.Product;
import ceng.estu.classes.Table;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author reuzun
 */
public class TableSectionController implements Initializable {
    @FXML
    private ListView<Table> tableListView;
    @FXML
    private ChoiceBox<Product> menuChoieceBox;
    @FXML
    private VBox BillVBox;

    @FXML
    public void addToBill(ActionEvent actionEvent) {
        tableListView.getSelectionModel().getSelectedItem().getBill().addToBill(menuChoieceBox.getSelectionModel().getSelectedItem());
        updateBill();
    }

    @FXML
    public void payBill(ActionEvent actionEvent) {
        tableListView.getSelectionModel().getSelectedItem().reset();
        updateBill();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for(int i = 0 ; i < GlobalVariables.TABLECOUNT ;i++){
            Table table = new Table("table"+i);
            GlobalVariables.tableList.add(table);
            tableListView.getItems().add(table);
        }

        tableListView.setOnMouseClicked(e->{
            updateBill();
        });

        menuChoieceBox.getItems().add(new Product("Su",1.50));

    }


    private void updateBill(){
        BillVBox.getChildren().clear();
        Table temp = tableListView.getSelectionModel().getSelectedItem();
        for(Product p : temp.getBill().getBill()){
            Label label = new Label(p.getName()+"\t"+p.getPrice());
            BillVBox.getChildren().add(label);
        }
    }

}
