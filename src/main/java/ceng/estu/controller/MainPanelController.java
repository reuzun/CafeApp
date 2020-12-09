package ceng.estu.controller;

import ceng.estu.main.Main;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author reuzun
 */
public class MainPanelController implements Initializable {

    static Scene tableScene;
    static Scene settingScene;

    @javafx.fxml.FXML
    private AnchorPane mainPanel;
    private double xOffset = 0;
    private double yOffset = 0;
    @javafx.fxml.FXML
    private BorderPane panel;
    @javafx.fxml.FXML
    private JFXButton closeButton1;
    @javafx.fxml.FXML
    private JFXButton closeButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        makeStageDrageable();
        try {
            tableScene = new Scene(Main.loadFXML("TableSection"));
            settingScene = new Scene(Main.loadFXML("SettingSection"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @javafx.fxml.FXML
    public void getSettingsSection(ActionEvent actionEvent) {
        panel.setCenter(settingScene.getRoot());
    }

    @javafx.fxml.FXML
    public void getHowToUse(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void getTablesSection(ActionEvent actionEvent) {
        panel.setCenter(tableScene.getRoot());
    }

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

    @javafx.fxml.FXML
    public void handleCloseAction(ActionEvent actionEvent) {
        System.exit(-1);
    }
}