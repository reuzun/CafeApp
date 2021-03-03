package ceng.estu.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


import java.io.IOException;

/**
 * @author reuzun
 */
public class Main extends Application {

    public static FXMLLoader loader;
    public static Stage stage;
    private static Scene scene;

    @Override
    public void start(Stage primaryStage) throws Exception {
        scene = new Scene(loadFXML("MainPanel"));
        stage = primaryStage;
        //stage.setOpacity(0.2);
        stage.setOpacity(0);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setResizable(false);
        stage.setTitle("cafeApp");
        stage.setScene(scene);
        stage.show();
    }

    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxml + ".fxml"));
        loader = fxmlLoader;
        return fxmlLoader.load();
    }

    public static Object loadController(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxml + ".fxml"));
        Parent root = (Parent) fxmlLoader.load();
        Object controller = fxmlLoader.getController();
        return fxmlLoader.getController();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
