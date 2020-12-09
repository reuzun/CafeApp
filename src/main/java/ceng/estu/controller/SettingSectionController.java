package ceng.estu.controller;

import ceng.estu.classes.Product;
import ceng.estu.classes.Table;
import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.testapps.GlyphsBrowser;
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
public class SettingSectionController implements Initializable {

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
    public void addProduct(ActionEvent actionEvent) {
        StringTokenizer tokenizer = new StringTokenizer(nameAreaForAdding.getText());
        if(tokenizer.countTokens()>1){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Product name must be a single word. E.G : Whiskey70cl", ButtonType.OK);
            alert.setTitle("Product Name Error.");
            alert.showAndWait();
            return;
        }
        GlobalVariables.menu.add(new Product(nameAreaForAdding.getText(), Double.parseDouble(priceAreaForAdding.getText())));
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
        GlobalVariables.menu.get(GlobalVariables.menu.indexOf(changePriceOfSelectedProductNode.getSelectionModel().getSelectedItem())).setPrice(Double.parseDouble(priceAreaForChanging.getText()));
        Alert alert = new Alert(Alert.AlertType.NONE, "Price has been changed.", ButtonType.OK);
        alert.setTitle("Price has been changed.");
        alert.showAndWait();
        priceAreaForChanging.setText("");
        updateChoiceBoxes();
        GlobalVariables.updateCFG();
    }

    @javafx.fxml.FXML
    public void deleteProduct(ActionEvent actionEvent) {
        GlobalVariables.menu.remove(GlobalVariables.menu.indexOf(deleteFromMenuBoxNode.getSelectionModel().getSelectedItem()));
        Alert alert = new Alert(Alert.AlertType.NONE, "Item has been removed.", ButtonType.OK);
        alert.setTitle("Item has been removed.");
        alert.showAndWait();
        updateChoiceBoxes();
        GlobalVariables.updateCFG();
    }


    private void updateChoiceBoxes() {
        deleteFromMenuBoxNode.getItems().clear();
        changePriceOfSelectedProductNode.getItems().clear();
        for (int i = 0; i < GlobalVariables.menu.size(); i++) {
            deleteFromMenuBoxNode.getItems().add(GlobalVariables.menu.get(i));
            changePriceOfSelectedProductNode.getItems().add(GlobalVariables.menu.get(i));
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateChoiceBoxes();
    }


    @javafx.fxml.FXML
    public void adjustTableCount(ActionEvent actionEvent) {
        if (Integer.parseInt(tableCountArea.getText()) > GlobalVariables.tableList.size()) {
            for (int i = GlobalVariables.tableList.size(); i < Integer.parseInt(tableCountArea.getText()); i++) {
                GlobalVariables.tableList.add(new Table());
            }
        }
        else{
            int size =  GlobalVariables.tableList.size();
            int wanted = Integer.parseInt(tableCountArea.getText());
            for (int i = size-1 ; wanted <= i ; i--) {
                GlobalVariables.tableList.remove(i);
            }
        }
        GlobalVariables.updateCFG();
    }
}
