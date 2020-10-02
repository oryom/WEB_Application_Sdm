package GuiScenes.updateStoreItem;

import GuiScenes.appScene.AppSceneController;
import Logic.Logic;
import Logic.My_CLASS.MyItem;
import Logic.My_CLASS.MyStore;
import Logic.My_CLASS.MyStoreItem;
import Logic.My_CLASS.MySuperMarket;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class UpdateStoreItemsSceneController {

    @FXML private ComboBox<MyStore> storeComboBox;
    @FXML private ComboBox<String> menuComboBox;
    @FXML private Label massageLabel;
    @FXML private ComboBox<MyStoreItem> deleteComboBox;
    @FXML private ComboBox<MyItem> addItemToStoreComboBox;
    @FXML private ComboBox<MyStoreItem> updateItemPriceComboBox;
    @FXML private Spinner<Integer> updatePriceSpinner;
    @FXML private Spinner<Integer> addItemPriceSpinner;
    @FXML private Button addItemBtn;
    @FXML private Button updateItemBtn;

    private AppSceneController appSceneController;
    private Logic logic;
    private MySuperMarket superMarket;
    private BooleanProperty isStoreChose;
    private BooleanProperty isDeleteSelected;
    private BooleanProperty isAddItemSelected;
    private BooleanProperty isUpdateItemSelected;
    private MyStore prevStore;
    private MyStore currentStore;
    private Alert massageAlert;


    @FXML
    void addItemBtnOnAction(ActionEvent event) throws IOException {
        MyItem item = addItemToStoreComboBox.getValue();
        if(item == null) {
            massageAlert.getDialogPane().setContentText("Choose Item first!");
            massageAlert.showAndWait();
        }
        else {
            if (currentStore.addItemToStore(item, addItemPriceSpinner.getValue()))
                massageAlert.getDialogPane().setContentText("Added Successfully");
            else
                massageAlert.getDialogPane().setContentText("Can not add item: The store already sells this item.");
            massageAlert.showAndWait();
            resetScene();
        }
    }

    @FXML
    void deleteItemOnAction(ActionEvent event) throws IOException {
        MyStoreItem storeItem = deleteComboBox.getValue();
        if(this.superMarket.isItemSoldInOtherStorBeforeDelating(storeItem)){
            if(currentStore.getStoreItems().getItemsList().size() > 1) {
                currentStore.deleteItemFromStore(storeItem);
                massageAlert.getDialogPane().setContentText("Deleted Successfully");
            }
            else
                massageAlert.getDialogPane().setContentText("Can not delete item: The store sells only this item.");
        }
        else
            massageAlert.getDialogPane().setContentText("Can not delete item:The item sold only by this store.");

        massageAlert.showAndWait();
        resetScene();
    }

    private Alert createAlert() {
        Stage massageStage = (Stage) appSceneController.getDisplayScenes().getScene().getWindow();
        Alert.AlertType type = Alert.AlertType.INFORMATION;
        Alert alert = new Alert(type, "");
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(massageStage);
        alert.getDialogPane().setContentText("try");
        return alert;
    }

    private void resetScene() throws IOException {
        FXMLLoader loader = appSceneController.
                createLoader("/GuiScenes/updateStoreItem/UpdateStoreItemsScene.fxml");
        SplitPane root = loader.load();
        appSceneController.getDisplayScenes().getChildren().setAll(root);
        UpdateStoreItemsSceneController updateStoreItemsSceneController = loader.getController();
        updateStoreItemsSceneController.set(this.appSceneController);


    }

    @FXML
    void menuChoiceOnAction(ActionEvent event) {
       String selected = menuComboBox.getValue();

       if(selected.equals("Delete Item")) {
           isDeleteSelected.set(true);
           isAddItemSelected.set(false);
           isUpdateItemSelected.set(false);
           isStoreChose.set(false);

           //load data to store items comboBox
       }
       else
           if(selected.equals("Add Item To Store")) {
               isDeleteSelected.set(false);
               isAddItemSelected.set(true);
               isUpdateItemSelected.set(false);
               isStoreChose.set(false);
           }
           else
               if (selected.equals("Update Stores Item Price")) {
                   isDeleteSelected.set(false);
                   isAddItemSelected.set(false);
                   isUpdateItemSelected.set(true);
                   isStoreChose.set(false);
               }

    }

    @FXML
    void storeChooseOnAction(ActionEvent event) {
        isStoreChose.set(true);
         currentStore = storeComboBox.getValue();
        if(prevStore == null){
            prevStore = storeComboBox.getValue();
            deleteComboBox.getItems().addAll(currentStore.getStoreItems().getItemsList());
            updateItemPriceComboBox.getItems().addAll(currentStore.getStoreItems().getItemsList());
        }

        else{
            deleteComboBox.getItems().removeAll(prevStore.getStoreItems().getItemsList());
            updateItemPriceComboBox.getItems().removeAll(prevStore.getStoreItems().getItemsList());
            deleteComboBox.getItems().addAll(currentStore.getStoreItems().getItemsList());
            updateItemPriceComboBox.getItems().addAll(currentStore.getStoreItems().getItemsList());
            prevStore = currentStore;
        }
    }

    @FXML
    void updateItemBtnOnAction(ActionEvent event) throws IOException {
        MyStoreItem storeItem = updateItemPriceComboBox.getValue();
        int newPrice = updatePriceSpinner.getValue();
        if(storeItem == null) {
            massageAlert.getDialogPane().setContentText("Choose Item first!");
            massageAlert.showAndWait();
        }
        else {
            currentStore.updatePrice(storeItem, newPrice);
            massageAlert.getDialogPane().setContentText("Update price successfully");
            massageAlert.showAndWait();
            resetScene();
        }
    }

    @FXML
    private void initialize() {
        if(this.appSceneController != null) {
            isStoreChose = new SimpleBooleanProperty(false);
            isDeleteSelected = new SimpleBooleanProperty(false);
            isAddItemSelected = new SimpleBooleanProperty(false);
            isUpdateItemSelected = new SimpleBooleanProperty(false);

            this.logic = appSceneController.getLogic();
            this.superMarket = logic.getMySupermarket();
            massageAlert = createAlert();

            //setComboBoxes titles
            setComboBoxTitels();
            //add data to combo boxes
            loadDataToComboBoxes();
            //set spinners
            setSpinners();
            // wire up boolean properties
            wireUpBooleanProperties();

        }
    }

    private void setSpinners() {
        SpinnerValueFactory<Integer> addItemFactory = new SpinnerValueFactory.
                IntegerSpinnerValueFactory(0, 500, 0, 1);
        addItemPriceSpinner.setValueFactory(addItemFactory);
        addItemPriceSpinner.setEditable(true);

        SpinnerValueFactory<Integer> updateItemFactory = new SpinnerValueFactory.
                IntegerSpinnerValueFactory(0, 500, 0, 1);
        updatePriceSpinner.setValueFactory(updateItemFactory);
        updatePriceSpinner.setEditable(true);


    }

    private void wireUpBooleanProperties() {
        // menu option section
        menuComboBox.disableProperty().bind(isStoreChose.not());

        //delete section
        deleteComboBox.disableProperty().bind(isDeleteSelected.not());

        //add item section
        addItemToStoreComboBox.disableProperty().bind(isAddItemSelected.not());
        addItemPriceSpinner.disableProperty().bind(isAddItemSelected.not());
        addItemBtn.disableProperty().bind(isAddItemSelected.not());

        //update section
        updateItemPriceComboBox.disableProperty().bind(isUpdateItemSelected.not());
        updatePriceSpinner.disableProperty().bind(isUpdateItemSelected.not());
        updateItemBtn.disableProperty().bind(isUpdateItemSelected.not());

    }

    private void loadDataToComboBoxes() {
        storeComboBox.getItems().addAll(superMarket.getStores().getStoreList());
        menuComboBox.getItems().addAll("Delete Item" , "Add Item To Store" , "Update Stores Item Price");
        addItemToStoreComboBox.getItems().addAll(superMarket.getItems().getItemList());
    }

    private void setComboBoxTitels() {
        storeComboBox.setPromptText("Choose Store");
        menuComboBox.setPromptText("Choose Action");
        deleteComboBox.setPromptText("Choose Item");
        addItemToStoreComboBox.setPromptText("Choose Item To Add");
        updateItemPriceComboBox.setPromptText("Choose Item");
    }

    public void set(AppSceneController appSceneController){
        this.appSceneController = appSceneController;
        initialize();
    }



}
