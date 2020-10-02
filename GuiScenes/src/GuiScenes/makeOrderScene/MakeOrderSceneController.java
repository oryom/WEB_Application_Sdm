package GuiScenes.makeOrderScene;

import GuiScenes.appScene.AppSceneController;
import GuiScenes.makeOrderScene.dynamicScene.DynamicSceneController;
import GuiScenes.makeOrderScene.staticScene.StaticSceneController;
import Logic.Logic;
import Logic.My_CLASS.MyCustomer;
import Logic.My_CLASS.MyStore;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.List;

public class MakeOrderSceneController {

    @FXML private ComboBox<MyStore> storeComboBox;
    @FXML private ComboBox<String> statDyneComboBox;
    @FXML private DatePicker OrderDatePicker;
    @FXML private ComboBox<MyCustomer> customerSelectionComboBox;
    @FXML private AnchorPane bottomAnchorPane;


    private AppSceneController appSceneController;
    private SimpleBooleanProperty isCustomerSelected;
    private SimpleBooleanProperty isStaticOrder ;
    private SimpleBooleanProperty isDateChoosed;
    private SimpleStringProperty staticString;
    private SimpleStringProperty dynamicString;


    public void setAppSceneController(AppSceneController appSceneController) {
        this.appSceneController = appSceneController;
        isStaticOrder = new SimpleBooleanProperty(false);
        isDateChoosed = new SimpleBooleanProperty(false);
        staticString = new SimpleStringProperty("static order");
        dynamicString = new SimpleStringProperty("dynamic order");
        isCustomerSelected = new SimpleBooleanProperty(false);
        this.initialize();
    }

    @FXML
    void StatDynmComboBoxOnAction(ActionEvent event) throws IOException {
        if(statDyneComboBox.getValue().equals(staticString.get())){
            isStaticOrder.set(true);
        }
        else{
            isStaticOrder.set(false);
            changhToDynamicScene();
        }

    }

    private void changhToDynamicScene() throws IOException {
        FXMLLoader loader = appSceneController.
                createLoader("/GuiScenes/makeOrderScene/dynamicScene/DyanamicScene.fxml");
        ScrollPane root = loader.load();
        bottomAnchorPane.getChildren().setAll(root);

        //wire up controllers:
        DynamicSceneController controller = loader.getController();
        controller.set(this);
    }

    private void changeToStaticTableView() throws IOException {
        FXMLLoader loader = appSceneController.
                createLoader("/GuiScenes/makeOrderScene/staticScene/staticScene.fxml");
        SplitPane root = loader.load();
        bottomAnchorPane.getChildren().setAll(root);

        //wire up controllers:
        StaticSceneController controller = loader.getController();
        controller.set(this);

    }



    @FXML
    void StoreComboBoxOnAction(ActionEvent event) throws IOException {
        //present the table
        changeToStaticTableView();
        //create order
        //add items to the order via logic.

    }

    @FXML
    void OrderDatePickerOnAction(ActionEvent event) {
        isDateChoosed.set(true);
    }

    @FXML
    void customerComboBoxOnAction(ActionEvent event) {
        isCustomerSelected.set(true);
    }

    @FXML
    private void initialize() {

        if(this.appSceneController != null) {
            //wire up combo boxes
            storeComboBox.disableProperty().bind(isStaticOrder.not());
            statDyneComboBox.disableProperty().bind(isCustomerSelected.not());
            customerSelectionComboBox.disableProperty().bind(isDateChoosed.not());

            //set  titles
            setTitels();
            //set store names in StoreComboBox
            setStoreNames();
            //set customer names in customer Selection ComboBox
            setCustomerNames();
            //set Static Dynamic combobox
            statDyneComboBox.getItems().addAll(staticString.get() ,dynamicString.get());

        }
    }

    private void setCustomerNames() {
        Logic logic = this.appSceneController.getLogic();
        List<MyCustomer> customerNames = logic.getMySupermarket().getCustomers().getCustomerList();
        customerSelectionComboBox.getItems().addAll(customerNames);

    }

    private void setStoreNames() {
        Logic logic = this.appSceneController.getLogic();
        List<MyStore> storesName = logic.getMySupermarket().getStores().getStoreList();

        storeComboBox.getItems().addAll(storesName);
    }

    private void setTitels() {
        storeComboBox.setPromptText("Choose store");
        statDyneComboBox.setPromptText("Static/Dynamic");
        customerSelectionComboBox.setPromptText("Choose customer");
        OrderDatePicker.setPromptText("Choose Date");
    }


    public AppSceneController getAppControler() {
        return appSceneController;
    }

    public ComboBox<MyStore> getStoreComboBox() {
        return storeComboBox;
    }

    public ComboBox<String> getStatDyneComboBox() {
        return statDyneComboBox;
    }

    public DatePicker getOrderDatePicker() {
        return OrderDatePicker;
    }

    public ComboBox<MyCustomer> getCustomerSelectionComboBox() {
        return customerSelectionComboBox;
    }

}
