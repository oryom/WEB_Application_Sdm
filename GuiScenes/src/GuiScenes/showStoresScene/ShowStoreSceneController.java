package GuiScenes.showStoresScene;

import GuiScenes.appScene.AppSceneController;
import Logic.Logic;
import Logic.My_CLASS.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.Date;

public class ShowStoreSceneController {
    // Top anchor
    @FXML private ComboBox<MyStore> showStoreComboBox;

    @FXML private Label storeIdLabel;
    @FXML private Label storeNameLabel;
    @FXML private Label ppkLabel;
    @FXML private Label deliveriesPaymentLabel;


    //Low anchor
         // Items tab
    @FXML private Tab itemsTab;
    @FXML private TableView<StoreItemView> itemsTableView;
    @FXML private TableColumn<StoreItemView, Integer> itemsTab_itemId_col;
    @FXML private TableColumn<StoreItemView, String> itemsTab_itemName_col;
    @FXML private TableColumn<StoreItemView, String> itemsTab_itemCategory_col;
    @FXML private TableColumn<StoreItemView,Integer> itemsTab_itemPrice_col;
    @FXML private TableColumn<StoreItemView, StoreItemView> itemsTab_howMuchItemSold_col;

        //Orders Tab
    @FXML private Tab ordersTab;
    @FXML private TableView<StoreOrdersView> OrdersTableView;
    @FXML private TableColumn<StoreOrdersView, Integer> ordersTab_orderId_col;
    @FXML private TableColumn<StoreOrdersView, String> ordersTab_orderKind_col;
    @FXML private TableColumn<StoreOrdersView, String> ordersTab_date_col;
    @FXML private TableColumn<StoreOrdersView, Integer> ordersTab_howManyItemsSold_col;
    @FXML private TableColumn<StoreOrdersView, String> ordersTab_deliveryPrice_col;
    @FXML private TableColumn<StoreOrdersView, String> ordersTab_totalPrice_col;

       //Offers Tab
    @FXML private Tab offersTab;
    @FXML private TableView<StoreOffersView> offersTableView;
    @FXML private TableColumn<StoreOffersView, String> offerName_col;
    @FXML private TableColumn<StoreOffersView, String > ifYouBuy_col;
    @FXML private TableColumn<StoreOffersView, Double> quantity_col;
    @FXML private TableColumn<StoreOffersView, String> operator_col;
    @FXML private TableColumn<StoreOffersView, ChoiceBox<String>> thenYouGet_col;

    //data members
    private AppSceneController appSceneController;
    private Logic logic;
    private MySuperMarket superMarket;


    @FXML
    private void initialize() {
        if(this.appSceneController != null){
            showStoreComboBox.setPromptText("Choose store");
            showStoreComboBox.getItems().addAll(superMarket.getStores().getStoreList());
        }
    }

    @FXML
    void chooseStoreOnAction(ActionEvent event) {
        MyStore chosenStore = showStoreComboBox.getValue();
        //init Store labels.
        presentStoreDryDetails(chosenStore);
        //init item table view
        presentItemsTable(chosenStore);
        //init Orders table view
        presentOrderTable(chosenStore);
        //init offer table view
        presentOffersTable(chosenStore);

    }

    private void presentOffersTable(MyStore chosenStore) {

        offerName_col.setCellValueFactory(new PropertyValueFactory<>("offerName"));
        ifYouBuy_col.setCellValueFactory(new PropertyValueFactory<>("ifYouBuy"));
        quantity_col.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        operator_col.setCellValueFactory(new PropertyValueFactory<>("operator"));
        thenYouGet_col.setCellValueFactory(new PropertyValueFactory<>("thenYouGet"));


        ObservableList<StoreOffersView>  storeOffersViews = chosenStore.createObservableListForOffersView();
        offersTableView.setItems(storeOffersViews);
    }

    private void presentOrderTable(MyStore chosenStore) {
        ordersTab_orderId_col.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        ordersTab_orderKind_col.setCellValueFactory(new PropertyValueFactory<>("orderKind"));
        ordersTab_date_col.setCellValueFactory(new PropertyValueFactory<>("date"));
        ordersTab_howManyItemsSold_col.setCellValueFactory(new PropertyValueFactory<>("howManyItemsSold"));
        ordersTab_deliveryPrice_col.setCellValueFactory(new PropertyValueFactory<>("deliveryPrice"));
        ordersTab_totalPrice_col.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));

        ObservableList<StoreOrdersView> storeOrdersViews = chosenStore.createObservableListForOrdersView();
        OrdersTableView.setItems(storeOrdersViews);
    }

    private void presentItemsTable(MyStore chosenStore) {
        this.itemsTab_itemId_col.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        this.itemsTab_itemName_col.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        this.itemsTab_itemCategory_col.setCellValueFactory(new PropertyValueFactory<>("parchesCategory"));
        this.itemsTab_itemPrice_col.setCellValueFactory(new PropertyValueFactory<>("price"));
        this.itemsTab_howMuchItemSold_col.setCellValueFactory(new PropertyValueFactory<>("howManyTimesSold"));

        ObservableList<StoreItemView> storeItemViews = chosenStore.createObservableListForItemsView();
        itemsTableView.setItems(storeItemViews);

    }

    private void presentStoreDryDetails(MyStore chosenStore) {
        storeIdLabel.setText(String.valueOf(chosenStore.getId()));
        storeNameLabel.setText(chosenStore.getName());
        ppkLabel.setText(String.valueOf(chosenStore.getSdmStore().getDeliveryPpk()));
        deliveriesPaymentLabel.setText(String.format("%.2f",chosenStore.calculateTotalDelivryEarn()));

    }


    public void set(AppSceneController appSceneController) {
        this.appSceneController = appSceneController;
        this.logic = appSceneController.getLogic();
        this.superMarket = logic.getMySupermarket();
        initialize();
    }


}
