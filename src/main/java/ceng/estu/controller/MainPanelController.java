package ceng.estu.controller;

import ceng.estu.main.Main;
import com.jfoenix.controls.JFXButton;
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import static ceng.estu.main.Main.stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author reuzun
 */
public class MainPanelController implements Initializable {

    static Scene tableScene;
    static Scene settingScene;
    static Scene howToUseScene;
    static Scene musicPlayerPage;

    @javafx.fxml.FXML
    private AnchorPane mainPanel;
    private double xOffset = 0;
    private double yOffset = 0;
    @javafx.fxml.FXML
    private BorderPane panel;
    @FXML
    private VBox panelBox;
    @FXML
    private Label logoName;
    static Label staticLogo;
    @FXML
    private ImageView img;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        staticLogo = new Label();
        makeStageDrageable();
        try {
            tableScene = new Scene(Main.loadFXML("TableSection"));
            settingScene = new Scene(Main.loadFXML("SettingSection"));
            howToUseScene = new Scene(Main.loadFXML("HowToUse"));
            musicPlayerPage = new Scene(Main.loadFXML("MusicPlayerPage"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        logoName.textFillProperty().bind(staticLogo.textFillProperty());

        /*Timeline timeline = new Timeline(60,
                new KeyFrame(Duration.millis(1250), e -> {
                    for(int i = 0 ; i < 100000 ; i++){
                            stage.setOpacity(stage.getOpacity() + 0.00001);
                    }
                })
        );*/



        FadeTransition fadeTransition = new FadeTransition(Duration.millis(3000), img);
        //fadeTransition.setDelay(Duration.millis(1750));
        fadeTransition.setFromValue(1.0f);
        fadeTransition.setToValue(0.1f);
        fadeTransition.setCycleCount(2);
        fadeTransition.setAutoReverse(true);
        fadeTransition.setRate(0.75);

        RotateTransition rotateTransition = new RotateTransition(Duration.millis(3000), img);
        //rotateTransition.setDelay(Duration.millis(1750));
        rotateTransition.setByAngle(360);
        rotateTransition.setCycleCount(1);

        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(3000), img);
        //scaleTransition.setDelay(Duration.millis(1750));
        scaleTransition.setToX(0.5f);
        scaleTransition.setToY(0.5f);
        scaleTransition.setCycleCount(2);
        scaleTransition.setAutoReverse(true);


        ParallelTransition pt = new ParallelTransition(scaleTransition, fadeTransition, rotateTransition);
        pt.play();
        //SequentialTransition seqT = new SequentialTransition (timeline, pt);
        //seqT.play();


    }

    @javafx.fxml.FXML
    public void getSettingsSection(ActionEvent actionEvent) {
        panel.setCenter(settingScene.getRoot());
        panelBox.setStyle("-fx-background-color: #a5628e");
    }

    @javafx.fxml.FXML
    public void getHowToUse(ActionEvent actionEvent) {
        panel.setCenter(howToUseScene.getRoot());
        panelBox.setStyle("-fx-background-color: #4c363a");
    }

    @javafx.fxml.FXML
    public void getTablesSection(ActionEvent actionEvent) {
        panel.setCenter(tableScene.getRoot());
        panelBox.setStyle("-fx-background-color: #ffffff");
    }

    @FXML
    public void getMusicPlayer(ActionEvent actionEvent) {
        panel.setCenter(musicPlayerPage.getRoot());
        panelBox.setStyle("-fx-background-color: #b48484");
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
        mainPanel.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                Main.stage.setIconified(true);
            }
        });
    }

    @javafx.fxml.FXML
    public void handleCloseAction(ActionEvent actionEvent) {
        System.exit(-1);
    }


}
