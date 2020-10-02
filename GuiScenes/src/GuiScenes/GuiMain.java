package GuiScenes;

import GuiScenes.appScene.AppSceneController;
import Logic.Logic;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;


import java.net.URL;

public class GuiMain extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader();

        // load main fxml
        URL mainFXML = getClass().getResource("/GuiScenes/appScene/appScene.fxml");
        loader.setLocation(mainFXML);
        ScrollPane root = loader.load();


        // wire up controller
        AppSceneController controller = loader.getController();
       // logic created at controller c'tor
        controller.setPrimaryStage(primaryStage);


        // set stage
        primaryStage.setTitle("Super Duper Market");
        Scene scene = new Scene(root, 1300, 800);
        primaryStage.setScene(scene);


        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
