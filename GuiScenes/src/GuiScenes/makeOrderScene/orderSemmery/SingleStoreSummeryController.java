package GuiScenes.makeOrderScene.orderSemmery;

import Logic.Logic;
import Logic.My_CLASS.MySingleOrderTableView;
import Logic.My_CLASS.MyStore;
import Logic.My_CLASS.MyStoreSingleOrderItems;
import Logic.My_CLASS.MySuperMarket;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;

public class SingleStoreSummeryController {

    @FXML private ScrollPane storeRootScrolePain;
    @FXML private Label storeIdLabel;
    @FXML private Label storeNameLabel;
    @FXML private Label ppkLabel;
    @FXML private Label distanceTocustomerLabel;
    @FXML private Label deliveryCostLabel;
    @FXML private TableView<MySingleOrderTableView> table; // creating observeList from myStoreSingleOrder

    @FXML private TableColumn<MySingleOrderTableView, Integer> itemId_col;
    @FXML private TableColumn<MySingleOrderTableView, String> itemName_col;
    @FXML private TableColumn<MySingleOrderTableView, String> purchesCatagory_col;
    @FXML private TableColumn<MySingleOrderTableView, Double> quantity_col;
    @FXML private TableColumn<MySingleOrderTableView, Integer> singleItemPrice_col;
    @FXML private TableColumn<MySingleOrderTableView, String> price_col;
    @FXML private TableColumn<MySingleOrderTableView, String> storOrOffer_col;

    private OrderSummeryController orderSummeryController;
    private MyStoreSingleOrderItems myStoreSingleOrderItems;
    private Logic logic;
    private MySuperMarket superMarket;

    @FXML
    private void initialize() throws IOException {
        if(this.orderSummeryController != null){
            this.logic = orderSummeryController.getLogic();
            this.superMarket = orderSummeryController.getSuperMarket();

            // initial labels
            initLabels();

            //initial table
            initTable();
        }
    }

    private void initLabels() {
        int storeId = myStoreSingleOrderItems.getStoreId();
        MyStore store = superMarket.getStores().getStoreMap().get(storeId);
         storeIdLabel.setText(String.valueOf(storeId));
         storeNameLabel.setText(store.getName());
         ppkLabel.setText(String.valueOf(store.getSdmStore().getDeliveryPpk()) );
         distanceTocustomerLabel.setText(String.format("%.2f",myStoreSingleOrderItems.getDistanceFromCustomer()));
         deliveryCostLabel.setText(String.format("%.2f",myStoreSingleOrderItems.getDeliveryCost())) ;

    }

    private void initTable() {
        this.itemId_col.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        this.itemName_col.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        this.purchesCatagory_col.setCellValueFactory(new PropertyValueFactory<>("purchesCatagory"));
        this.quantity_col.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        this.singleItemPrice_col.setCellValueFactory(new PropertyValueFactory<>("singleItemPrice"));
        this.price_col.setCellValueFactory(new PropertyValueFactory<>("priceString"));
        this.storOrOffer_col.setCellValueFactory(new PropertyValueFactory<>("offerKind"));

        ObservableList<MySingleOrderTableView> orderTableViews =  myStoreSingleOrderItems.createObservableListForOrderSummery();
        table.setItems(orderTableViews);

    }



    public void set(OrderSummeryController orderSummeryController,
                    MyStoreSingleOrderItems myStoreSingleOrderItems) throws IOException {
        this.orderSummeryController = orderSummeryController;
        this.myStoreSingleOrderItems = myStoreSingleOrderItems;
        initialize();
    }
}
