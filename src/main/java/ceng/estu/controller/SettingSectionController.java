package ceng.estu.controller;

import ceng.estu.classes.Product;
import ceng.estu.classes.TYPE;
import ceng.estu.classes.Table;

import static ceng.estu.classes.Product.*;
import static ceng.estu.classes.Table.*;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

/**
 * @author reuzun
 */
public class SettingSectionController implements Initializable{

    @javafx.fxml.FXML
    private TextField priceAreaForChanging;
    @javafx.fxml.FXML
    private ChoiceBox<Product> deleteFromMenuBoxNode;
    @javafx.fxml.FXML
    private ChoiceBox<Product> changePriceOfSelectedProductNode;
    @javafx.fxml.FXML
    private TextField nameAreaForAdding;
    @javafx.fxml.FXML
    private TextField priceAreaForAdding;
    @javafx.fxml.FXML
    private TextField tableCountArea;
    @javafx.fxml.FXML
    private ChoiceBox<TYPE> typeBox;


    @javafx.fxml.FXML
    public void addProduct(ActionEvent actionEvent) {
        try {
            Double.valueOf(priceAreaForAdding.getText().replace(",","."));//for checking is it number in the price area
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter a number to Product price area.", ButtonType.OK);
            alert.setTitle("Input Error.");
            alert.showAndWait();
            return;
        }

        for (Product product : menu) {
            if (nameAreaForAdding.getText().equals(product.getName())) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "This item is already occur in menu.", ButtonType.OK);
                alert.showAndWait();
                return;
            }
        }
        StringTokenizer tokenizer = new StringTokenizer(nameAreaForAdding.getText());
        if(tokenizer.countTokens()>1){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Product name must be a single word. E.G : Whiskey70cl", ButtonType.OK);
            alert.setTitle("Product Name Error.");
            alert.showAndWait();
            return;
        }
        menu.add(new Product(nameAreaForAdding.getText(), Double.parseDouble(priceAreaForAdding.getText().replaceAll(",",".")), typeBox.getSelectionModel().getSelectedItem()));
        Alert alert = new Alert(Alert.AlertType.NONE, "Product has been added.", ButtonType.OK);
        alert.setTitle("Product has been added.");
        alert.showAndWait();
        nameAreaForAdding.setText("");
        priceAreaForAdding.setText("");
        updateChoiceBoxes();
        GlobalVariables.updateCFG();
    }

    @javafx.fxml.FXML
    public void changePrice(ActionEvent actionEvent) {
        try {
            Double a = Double.valueOf(priceAreaForChanging.getText());
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter a number to Product price area.", ButtonType.OK);
            alert.setTitle("Input Error.");
            alert.showAndWait();
            return;
        }

        menu.get(menu.indexOf(changePriceOfSelectedProductNode.getSelectionModel().getSelectedItem())).setPrice(Double.parseDouble(priceAreaForChanging.getText()));
        Alert alert = new Alert(Alert.AlertType.NONE, "Price has been changed.", ButtonType.OK);
        alert.setTitle("Price has been changed.");
        alert.showAndWait();
        priceAreaForChanging.setText("");
        updateChoiceBoxes();
        GlobalVariables.updateCFG();
    }

    @javafx.fxml.FXML
    public void deleteProduct(ActionEvent actionEvent) {
        menu.remove(deleteFromMenuBoxNode.getSelectionModel().getSelectedItem());
        Alert alert = new Alert(Alert.AlertType.NONE, "Item has been removed.", ButtonType.OK);
        alert.setTitle("Item has been removed.");
        alert.showAndWait();
        updateChoiceBoxes();
        GlobalVariables.updateCFG();
    }


    private void updateChoiceBoxes() {
        deleteFromMenuBoxNode.getItems().clear();
        changePriceOfSelectedProductNode.getItems().clear();
        for (Product product : menu) {
            deleteFromMenuBoxNode.getItems().add(product);
            changePriceOfSelectedProductNode.getItems().add(product);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateChoiceBoxes();
        TYPE[] List = TYPE.values();
        for(TYPE T : List){
            typeBox.getItems().add(T);
        }
        typeBox.getSelectionModel().selectFirst();
    }


    @javafx.fxml.FXML
    public void adjustTableCount(ActionEvent actionEvent) {
        TableSectionController.tableFlag = true;
        try{
            Integer.valueOf(tableCountArea.getText());
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR,"Please enter a decimal number to tableCount area",ButtonType.OK);
            alert.showAndWait();
            return;
        }
        if (Integer.parseInt(tableCountArea.getText()) > tableList.size()) {
            for (int i = tableList.size(); i < Integer.parseInt(tableCountArea.getText()); i++) {
                tableList.add(new Table());
            }
        }
        else{
            int size =  tableList.size();
            int wanted = Integer.parseInt(tableCountArea.getText());
            for (int i = size-1 ; wanted <= i ; i--) {
                tableList.remove(i);
            }
        }
        GlobalVariables.updateCFG();
    }
}
