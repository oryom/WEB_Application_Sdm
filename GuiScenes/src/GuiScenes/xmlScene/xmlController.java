package GuiScenes.xmlScene;

import GuiScenes.appScene.AppSceneController;
import Logic.Logic;
import Logic.XmlLoaderTask;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sun.rmi.runtime.Log;

import java.io.File;
import java.util.concurrent.ExecutionException;

public class xmlController {

    @FXML private Button openFileBtn;
    @FXML private Button loadBtn;
    @FXML private ProgressBar fileLoaderProgress;
    @FXML private Label pathLabel;
    @FXML private Label showStatusLabel;
    @FXML private Label precentLabel;

   private SimpleBooleanProperty isFileSelected;
    private SimpleStringProperty filePath;
    AppSceneController appSceneController;

    public xmlController() { // the c'tor creates  the instances of properties.
        this.isFileSelected = new SimpleBooleanProperty(false);
        this.filePath = new SimpleStringProperty();
}

    @FXML
    void loadOnAction(ActionEvent event) throws InterruptedException, ExecutionException {
        Logic logic = appSceneController.getLogic();
        logic.newLoadXml(filePath.get(),this);
    }

    @FXML
    void openFileOnAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select .xml file");
        fileChooser.getExtensionFilters().
                add(new FileChooser.ExtensionFilter(".xml files", "*.xml"));
        File selectedFile = fileChooser.showOpenDialog(appSceneController.getPrimaryStage());
        if (selectedFile == null) {
            return;
        }
        String absolutePath = selectedFile.getAbsolutePath();
        this.isFileSelected.set(true);
        this.filePath.set(absolutePath);
    }

    @FXML
    private void initialize() {
        this.loadBtn.disableProperty().bind(isFileSelected.not());
        this.pathLabel.textProperty().bind(filePath);
    }

    public void set(AppSceneController appSceneController) {
        this.appSceneController = appSceneController;
    }

    public void bindToXmlLoaderGui(XmlLoaderTask aTask) {
        // task message
        showStatusLabel.textProperty().bind(aTask.messageProperty());
       


        // task progress bar
        fileLoaderProgress.progressProperty().bind(aTask.progressProperty());

        // task percent label
        precentLabel.textProperty().bind(
                Bindings.concat(
                        Bindings.format(
                                "%.0f",
                                Bindings.multiply(
                                        aTask.progressProperty(),
                                        100)),
                        " %"));

    }
}

