package ceng.estu.controller;

import ceng.estu.classes.Bill;
import ceng.estu.classes.Product;
import ceng.estu.classes.Table;
import ceng.estu.main.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

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
    private double xOffset = 0;
    private double yOffset = 0;

    private void makeStageDrageable() {
        mainPanel.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        mainPanel.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Main.stage.setX(event.getScreenX() - xOffset);
                Main.stage.setY(event.getScreenY() - yOffset);
                Main.stage.setOpacity(0.7f);
            }
        });
        mainPanel.setOnDragDone((e) -> {
            Main.stage.setOpacity(1.0f);
        });
        mainPanel.setOnMouseReleased((e) -> {
            Main.stage.setOpacity(1.0f);
        });
        mainPanel.setOnMouseClicked(e->{
            if(e.getClickCount() == 2){
                Main.stage.setIconified(true);
            }
        });
        mainPanel.setOnZoom(e->{
            Main.stage.setIconified(true);
        });

    }

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
        makeStageDrageable();
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
