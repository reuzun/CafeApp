package ceng.estu.controller;


import static ceng.estu.classes.Product.*;
import ceng.estu.classes.Product;
import ceng.estu.classes.Type;
import static ceng.estu.classes.Table.*;
import ceng.estu.classes.Table;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

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
    private Text priceText;
    @FXML
    private ChoiceBox<Integer> countBox;
    @FXML
    private ChoiceBox<Type> typeBox;

    static boolean tableFlag = false;


    @FXML
    public void addToBill(ActionEvent actionEvent) {

        try {
            tableListView.getSelectionModel().getSelectedItem().getBill().addToBill(menuChoieceBox.getSelectionModel().getSelectedItem().newInstance(), Integer.parseInt(countBox.getSelectionModel().getSelectedItem().toString()));
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "You did not select any table Please select one before. ", ButtonType.OK);
            alert.showAndWait();
            return;
            /* further updates.
            if (alert.getResult() == ButtonType.OK) {
                //do stuff
            }*/
        }
        updateBill();
        refreshHelper();
    }

    @FXML
    public void payBill(ActionEvent actionEvent) {
        try {
            tableListView.getSelectionModel().getSelectedItem().reset();
            updateBill();
            updateTableList();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "You did not select any table Please select one before. ", ButtonType.OK);
            alert.showAndWait();
            return;
        }
        updateMenuChoiceBox();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tableListView.itemsProperty().bind(SettingSectionController.globalListView.itemsProperty());

/*
        typeBox.getItems().add(TYPE.Food);
        typeBox.getItems().add(TYPE.Drink);
*/
        //for more types.
        Type[] List = Type.values();
        for(Type T : List){
            typeBox.getItems().add(T);
        }

        typeBox.getSelectionModel().selectFirst();

        typeBox.setOnMouseClicked(e->{
            menuChoieceBox.getSelectionModel().select(null);
            countBox.getSelectionModel().select(0);
            updateMenuChoiceBox();
        });

        menuChoieceBox.setOnMouseClicked(e->{
            updateMenuChoiceBox();
            //updateTableList();
        });

        if (GlobalVariables.configFile.exists()) {
            try (Scanner scan = new Scanner(GlobalVariables.configFile)) {
                boolean flag = true;
                while(scan.hasNext()){
                    String line = scan.nextLine();
                    if(flag){
                        for (int i = 0; i < Integer.parseInt(line); i++) {
                            Table table = new Table();
                            tableList.add(table);
                            tableListView.getItems().add(table);
                        }
                        flag = false;
                        continue;
                    }
                    StringTokenizer tokenizer = new StringTokenizer(line);
                    menu.add(new Product(tokenizer.nextToken(), Double.parseDouble(tokenizer.nextToken()), Type.valueOf(tokenizer.nextToken())));
                }
            } catch (Exception ignored) {
            }
        } else {
            for (int i = 0; i < 5; i++) {
                Table table = new Table();
                tableList.add(table);
                tableListView.getItems().add(table);
            }
            menu.add(new Product("Su", 10.50, Type.Drink));
            menu.add(new Product("Hamburger", 120.50, Type.Food));
            try(FileWriter writer = new FileWriter(GlobalVariables.configFile,true)){
                writer.write(5+System.lineSeparator());
                for (Product p : menu) {
                    writer.write(p.getName() + " " + p.getPrice() + " " + p.type);
                    writer.write(System.lineSeparator());
                }
            }catch (Exception ignored){}
            updateMenuChoiceBox();
        }

        tableListView.setOnMouseClicked(e -> {
            if(e.getButton() == MouseButton.SECONDARY&& e.getClickCount() == 2 ){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure about removing this table ?",ButtonType.APPLY,ButtonType.CANCEL);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.APPLY) {
                    tableListView.getItems().remove(tableListView.getSelectionModel().getSelectedItem());
                }else{
                    return;
                }

            }

            /*if(tableFlag){
                updateTableList();
                tableFlag = false;
            }*/
            try {
                updateBill();
                updateMenuChoiceBox();
            } catch (Exception ex) {
                System.out.println("Something unusual happened.");
            }
        });
        for (int i = 1; i < 11; i++) {
            countBox.getItems().add(i);
        }
        countBox.getSelectionModel().selectFirst();
    }


    private void updateBill() {
        BillVBox.getChildren().clear();
        double totalPrice = 0;
        Table temp = tableListView.getSelectionModel().getSelectedItem();
        for (Product p : temp.getBill().getBill()) {
            String str = String.format("%-17s %-8.2f x%-6d", p.getName(), p.getPrice(), p.getCount());
            Label label = new Label(str);
            label.setMinWidth(600);
            label.setPrefWidth(1200);
            label.setContentDisplay(ContentDisplay.RIGHT);
            Button btn = new Button("Remove x1");
            label.setGraphic(btn);
            label.setStyle("-fx-font-family: monospaced");
            label.setFont(new Font(15.5));
            BillVBox.getChildren().add(label);
            totalPrice += p.getPrice() * p.getCount();

            btn.setOnAction(e -> {
                StringTokenizer tokenizer = new StringTokenizer(label.getText());
                String str2 = tokenizer.nextToken();
                for (int i = 0; i < temp.getBill().getBill().size(); i++) {
                    if (temp.getBill().getBill().get(i).getName().equals(str2)) {
                        temp.getBill().getBill().get(i).increaseCount(-1);
                        temp.getBill().totalPrice -= temp.getBill().getBill().get(i).getPrice();
                        if (temp.getBill().getBill().get(i).getCount() == 0)
                            temp.getBill().getBill().remove(i);
                        updateBill();
                        refreshHelper();
                    }
                }
            });
        }
        String pricea = String.format("%-10.2f", totalPrice);
        temp.getBill().totalPrice = Double.parseDouble(pricea.replace(",","."));
        String price = String.format("%,-10.2f", totalPrice);
        priceText.setText(price);
    }

    private void updateMenuChoiceBox() {
        menuChoieceBox.getItems().clear();
        for (Product product : menu) {
            if (typeBox.getSelectionModel().getSelectedItem().equals(product.type))
                menuChoieceBox.getItems().add(product);
        }
    }


    public static void updateTableList() {
        SettingSectionController.globalListView.getItems().clear();
        for (Table table : tableList) {
            SettingSectionController.globalListView.getItems().add(table);
        }
    }



    private void refreshHelper() {
        int a = tableListView.getSelectionModel().getSelectedIndex();
        int b = menuChoieceBox.getSelectionModel().getSelectedIndex();
        updateMenuChoiceBox();
        updateTableList();
        tableListView.getSelectionModel().select(a);
        menuChoieceBox.getSelectionModel().select(b);
    }

    @FXML
    void changeMusic(ActionEvent event) throws IOException {
        /*
        File file = new File("C:\\Users\\Efe\\Documents\\IntelliJProjects\\CafeApp\\src\\main\\resources\\ceng\\estu\\main\\MusicPlayerPage.fxml");
        URL url = file.toURI().toURL();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(""));
        loader.setLocation(url);
        Parent root = (Parent) loader.load();
        Object controller = loader.getController();

*/



        if(MainPanelController.controller instanceof MusicPlayerController){
            ((MusicPlayerController) MainPanelController.controller).listenNextSong(new ActionEvent());
        }
    }

}
