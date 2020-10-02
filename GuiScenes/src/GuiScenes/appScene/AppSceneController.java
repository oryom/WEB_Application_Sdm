package GuiScenes.appScene;

import GuiScenes.makeOrderScene.MakeOrderSceneController;
import GuiScenes.makeOrderScene.orderSemmery.OrderSummeryController;
import GuiScenes.mapScene.MapSceneController;
import GuiScenes.showCustomerScene.ShowCustomerSceneController;
import GuiScenes.showItemsScene.ShowItemsSceneController;
import GuiScenes.showOrders.ShowOrderSceneController;
import GuiScenes.showStoresScene.ShowStoreSceneController;
import GuiScenes.updateStoreItem.UpdateStoreItemsSceneController;
import GuiScenes.xmlScene.xmlController;
import Logic.Logic;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class AppSceneController {

    @FXML private Button loadXmlBtn;
    @FXML private Button showStoreBtn;
    @FXML private Button showItemsBtn;
    @FXML private Button showCustomerBtn;
    @FXML private Button makeOrderBtn;
    @FXML private Button updateInfoBtn;
    @FXML private Button showOrderBtn;
    @FXML private Button showMapBtn;
    @FXML private AnchorPane displayScenes;
    @FXML private ScrollPane scrollPane;
    @FXML private AnchorPane anchorPane;


    private SimpleBooleanProperty isXmlLoad;
    OrderSummeryController orderSummeryController;

    private Logic logic;
    private Stage primaryStage;

    public boolean isIsXmlLoad() {
        return isXmlLoad.get();
    }

    public SimpleBooleanProperty isXmlLoadProperty() {
        return isXmlLoad;
    }

    public Logic getLogic() {
        return logic;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public AppSceneController() {
        this.logic = new Logic(this);
        this.isXmlLoad = logic.isSuperMarketIsValidProperty();
    }

    public FXMLLoader createLoader(String path)
    {
        FXMLLoader loader = new FXMLLoader();
        URL mainFXML = getClass().
                getResource(path);
        loader.setLocation(mainFXML);
        return loader;
    }

    @FXML
    void loadXmlOnAction(ActionEvent event) throws IOException {

        FXMLLoader loader = createLoader("/GuiScenes/xmlScene/loadXmlScene.fxml");
        Pane root =  loader.load();
        displayScenes.getChildren().setAll(root);

        //wire up controllers:
        xmlController xmlCtrl = loader.getController();
        xmlCtrl.set(this);
    }

    @FXML
    void makeOrderOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = createLoader("/GuiScenes/makeOrderScene/makeOrderScene.fxml");
        ScrollPane root = loader.load();
        displayScenes.getChildren().setAll(root);
        MakeOrderSceneController orderCtrl = loader.getController();
        orderCtrl.setAppSceneController(this);
    }

    @FXML
    void showCustomerOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = createLoader("/GuiScenes/showCustomerScene/showCustomerScene.fxml");
        ScrollPane root = loader.load();

        displayScenes.getChildren().setAll(root);
        ShowCustomerSceneController showCust = loader.getController();
        showCust.setAppSceneController(this);
    }

    @FXML
    void showItemsOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = createLoader("/GuiScenes/showItemsScene/showItemesScene.fxml");
        ScrollPane root = loader.load();
        displayScenes.getChildren().setAll(root);
        ShowItemsSceneController showItems = loader.getController();
        showItems.setAppSceneController(this);
    }

    @FXML
    void showOrdersOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = createLoader("/GuiScenes/showOrders/ShowOrderScen.fxml");
        ScrollPane root = loader.load();
        displayScenes.getChildren().setAll(root);
        ShowOrderSceneController orderSceneController = loader.getController();
        orderSceneController.setAppSceneController(this);
    }

    @FXML
    void showMapOnAction(ActionEvent event) throws IOException {
        Stage stg = new MapSceneController().getMap(this);
        stg.show();


    }

    @FXML
    void showStoreOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = createLoader("/GuiScenes/showStoresScene/ShowStoreScene.fxml");
        SplitPane root = loader.load();
        displayScenes.getChildren().setAll(root);
        ShowStoreSceneController storeSceneController = loader.getController();
        storeSceneController.set(this);

    }

    @FXML
    void updateInfoOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = createLoader("/GuiScenes/updateStoreItem/UpdateStoreItemsScene.fxml");
        SplitPane root = loader.load();
        displayScenes.getChildren().setAll(root);
        UpdateStoreItemsSceneController updateStoreItemsSceneController = loader.getController();
        updateStoreItemsSceneController.set(this);


    }

    @FXML
    private void initialize() {

        showCustomerBtn.disableProperty().bind(isXmlLoad.not());
        showItemsBtn.disableProperty().bind(isXmlLoad.not());
        showMapBtn.disableProperty().bind(isXmlLoad.not());
        showOrderBtn.disableProperty().bind(isXmlLoad.not());
        showStoreBtn.disableProperty().bind(isXmlLoad.not());
        makeOrderBtn.disableProperty().bind(isXmlLoad.not());
        updateInfoBtn.disableProperty().bind(isXmlLoad.not());
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setLogic(Logic logic) {
        this.logic = logic;
    }

    public AnchorPane getDisplayScenes() {
        return displayScenes;
    }

    public OrderSummeryController getOrderSummeryController() {
        return orderSummeryController;
    }

    public void setOrderSummeryController(OrderSummeryController orderSummeryController) {
        this.orderSummeryController = orderSummeryController;
    }
}
