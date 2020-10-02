package GuiScenes.makeOrderScene.staticScene;

import GuiScenes.makeOrderScene.MakeOrderSceneController;
import GuiScenes.makeOrderScene.sellScene.AllSellsSceneControlller;
import Logic.Logic;
import Logic.My_CLASS.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.control.*;
import javafx.scene.control.TableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


//import javax.security.auth.callback.Callback;

public class StaticSceneController {

    @FXML private Label deliveryLabel;
    @FXML private Label orderCostLabel;
    @FXML private Button submitButton;
    @FXML private Button cancelButton;
    @FXML private TableView<MyStoreItem> staticOrderTable;
    @FXML private TableColumn<MyStoreItem, String> col_name;
    @FXML private TableColumn<MyStoreItem, Integer  > col_price;
    @FXML private TableColumn<MyStoreItem, Button> col_add; // add button
    @FXML private TableColumn<MyStoreItem, Spinner<Double>> col_quantity;//quantity bar


   private MakeOrderSceneController orderController;
    private double deliveryCost = 0;
    private double orderCost = 0 ;
    private Map<MyStoreItem,Double> selectedItemsMap;


    @FXML
    void cancelOnAction(ActionEvent event) throws IOException {
        //reset spinner to zero.
      orderController.getAppControler().getLogic().getMySupermarket().resetSpinners(selectedItemsMap.keySet());
        // need to call to makeOrderScene.
        FXMLLoader loader = orderController.getAppControler().
                createLoader("/GuiScenes/makeOrderScene/makeOrderScene.fxml");
        ScrollPane root = loader.load();
        orderController.getAppControler().getDisplayScenes().getChildren().setAll(root);
        MakeOrderSceneController orderCtrl = loader.getController();
        orderCtrl.setAppSceneController(this.orderController.getAppControler());

    }

    @FXML
    void submitOnAction(ActionEvent event) throws IOException {

        Logic logic = orderController.getAppControler().getLogic();
        MySuperMarket superMarket = logic.getMySupermarket();


        //need to clean the zeros from map.
        superMarket.cleanZerosFromSelcetedItemsMap(selectedItemsMap);

        //need to build the order.-> update super's order and update the customer orders
        MyOrder order = buildOrderFromSuperMarket(superMarket);

        //need to move to the offers scene
        // gets order instance.
        buildOfferScene(order);
    }

    private void buildOfferScene(MyOrder order) throws IOException {
        FXMLLoader loader = this.orderController.getAppControler().
                createLoader("/GuiScenes/makeOrderScene/sellScene/AllSellsScene.fxml");
        ScrollPane root = loader.load();

        this.orderController.getAppControler().getDisplayScenes().getChildren().setAll(root);

        //wire up controller
        AllSellsSceneControlller controlller = loader.getController();
        controlller.set(this.orderController.getAppControler() , order);

    }

    private MyOrder buildOrderFromSuperMarket(MySuperMarket superMarket) {
        int storeId = orderController.getStoreComboBox().getValue().getId();
        Map<Integer, Double> deliveryMap = new HashMap<>();
        deliveryMap.put(storeId,deliveryCost);
        String orderKind = orderController.getStatDyneComboBox().getValue();
        return superMarket.buildOrder(orderController.getOrderDatePicker() ,
                orderController.getCustomerSelectionComboBox().getValue(),selectedItemsMap,
                orderKind,orderCost,deliveryMap);
    }

    @FXML
    private void initialize() {
        if(this.orderController != null){

            //set up columns in the table
            this.col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
            this.col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
            this.col_quantity.setCellValueFactory(new PropertyValueFactory<>("spinner"));

           //load data
            loadDate();

            //add button to each raw
            addButtonToTable();

            //set up map
            selectedItemsMap = new HashMap<>();
        }
    }

    private void loadDate() {
        Logic logic = this.orderController.getAppControler().getLogic();
        MySuperMarket superMarket = logic.getMySupermarket();
        MyStore store = superMarket.getStores().getStoreMap().
                get(orderController.getStoreComboBox().getValue().getId());
        ObservableList<MyStoreItem> observeList = store.getStoreItems().buildObservList();
        staticOrderTable.setItems(observeList);
         deliveryCost =  superMarket.
                calculateDeliveryCost(orderController.getStoreComboBox().getValue(),
                        orderController.getStoreComboBox().getValue().getMyLocation()
                        ,orderController.getCustomerSelectionComboBox().getValue().getLocation());
        deliveryLabel.setText(String.format("%.2f",deliveryCost));
    }


    private void addButtonToTable() {
        Callback<TableColumn<MyStoreItem, Button>, TableCell<MyStoreItem, Button>>
                cellFactory = creatCallBackForButton();
        col_add.setCellFactory(cellFactory);
    }

    private Callback<TableColumn<MyStoreItem, Button>, TableCell<MyStoreItem, Button>> creatCallBackForButton() {
        Callback<TableColumn<MyStoreItem, Button>, TableCell<MyStoreItem, Button>> cellFactory =
                new Callback<TableColumn<MyStoreItem, Button>, TableCell<MyStoreItem, Button>>() {
            @Override
            public TableCell<MyStoreItem, Button> call(final TableColumn<MyStoreItem, Button> param) {
                final TableCell<MyStoreItem, Button> cell = new TableCell<MyStoreItem, Button>() {

                    private final Button btn = new Button("Add item");
                    {
                        btn.setOnAction((ActionEvent event) -> {
                            MyStoreItem item = getTableView().getItems().get(getIndex());
                            Logic logic = orderController.getAppControler().getLogic();
                            MySuperMarket superMarket = logic.getMySupermarket();
                            superMarket.addItemToMapFromGui(item ,selectedItemsMap );
                            orderCost = calcCost(selectedItemsMap);
                            orderCostLabel.setText(String.format("%.2f",orderCost));
                        });
                    }
                    @Override
                    public void updateItem(Button item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };
        return cellFactory;
    }

    private double calcCost(Map<MyStoreItem, Double> selectedItemsMap) {
        Logic logic = orderController.getAppControler().getLogic();
        MySuperMarket superMarket = logic.getMySupermarket();
        return superMarket.calculateItemCost(selectedItemsMap);
    }


    public void set(MakeOrderSceneController appSceneController) {
        this.orderController = appSceneController;
        this.initialize();
    }

    public void setOrderCost(double orderCost) {
        this.orderCost = orderCost;
    }
}
