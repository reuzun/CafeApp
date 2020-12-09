package ceng.estu.controller;

import ceng.estu.classes.Bill;
import ceng.estu.classes.Product;
import ceng.estu.classes.Table;
import ceng.estu.main.Main;
import com.jfoenix.controls.JFXButton;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author reuzun
 */
public class TableSectionController implements Initializable {
    @FXML
    private AnchorPane mainPanel;
    @FXML
    private ListView<Table> tableListView;
    @FXML
    private ChoiceBox<Product> menuChoieceBox;
    @FXML
    private VBox BillVBox;
    @FXML
    private Text priceText;
    @FXML
    private ChoiceBox countBox;


    @FXML
    public void addToBill(ActionEvent actionEvent) {
        try {
            System.out.println(Integer.parseInt(countBox.getSelectionModel().getSelectedItem().toString()));
            tableListView.getSelectionModel().getSelectedItem().getBill().addToBill(menuChoieceBox.getSelectionModel().getSelectedItem().newInstance(),Integer.parseInt(countBox.getSelectionModel().getSelectedItem().toString()));
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.WARNING, "You did not select anytable Please select one before. ", ButtonType.OK);
            alert.showAndWait();
            return;
            /* further updates.
            if (alert.getResult() == ButtonType.OK) {
                //do stuff
            }*/
        }
        updateBill();
    }

    @FXML
    public void payBill(ActionEvent actionEvent) {
        tableListView.getSelectionModel().getSelectedItem().reset();
        updateBill();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for(int i = 0 ; i < 5 ;i++){
            Table table = new Table();
            GlobalVariables.tableList.add(table);
            tableListView.getItems().add(table);
        }

        tableListView.setOnMouseClicked(e->{
            updateBill();
            updateMenuChoiceBox();
        });


        for(int i = 1 ; i < 11 ;i++){
            countBox.getItems().add(i);
        }
        countBox.getSelectionModel().selectFirst();

        GlobalVariables.menu.add(new Product("Su",10.50));
        GlobalVariables.menu.add(new Product("Hamburger",120.50));
        updateMenuChoiceBox();


        //A system to get items that we add once.
        /*menuChoieceBox.getItems().add(new Product("Su",10.50));
        menuChoieceBox.getItems().add(new Product("Hamburger",120.50));*/

    }


    private void updateBill(){
        BillVBox.getChildren().clear();
        double totalPrice = 0;
        Table temp = tableListView.getSelectionModel().getSelectedItem();
        for(Product p : temp.getBill().getBill()){
            String str = String.format("%-15s %-6.2f x%-4d",p.getName(),p.getPrice(),p.getCount());
            Label label = new Label(str);
            label.setStyle("-fx-font-family: monospaced");
            label.setFont(new Font(15));
            BillVBox.getChildren().add(label);
            totalPrice += p.getPrice()*p.getCount();
        }
        String price = String.format("%,-10.2f",totalPrice);
        priceText.setText(price);
    }

    private void updateMenuChoiceBox(){
        menuChoieceBox.getItems().clear();
        for(int i = 0 ; i < GlobalVariables.menu.size() ; i++){
            menuChoieceBox.getItems().add(GlobalVariables.menu.get(i));
        }
    }

    private int flag = 5;
    private void updateTableList(){
        tableListView.getItems().clear();
        for (int i = 0; i < GlobalVariables.tableList.size(); i++) {
            tableListView.getItems().add(GlobalVariables.tableList.get(i));
        }
    }

    @FXML
    public void refresh(ActionEvent actionEvent) {
        updateMenuChoiceBox();
        updateTableList();
    }
}
