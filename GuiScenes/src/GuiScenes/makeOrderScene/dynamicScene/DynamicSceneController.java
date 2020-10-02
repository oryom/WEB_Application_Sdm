package GuiScenes.makeOrderScene.dynamicScene;

import GuiScenes.makeOrderScene.MakeOrderSceneController;
import GuiScenes.makeOrderScene.sellScene.AllSellsSceneControlller;
import Logic.Logic;
import Logic.My_CLASS.MyItem;
import Logic.My_CLASS.MyOrder;
import Logic.My_CLASS.MyStoreItem;
import Logic.My_CLASS.MySuperMarket;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DynamicSceneController {

    @FXML private Button submitBtn;
    @FXML private Button cancelBtn;
    @FXML private TableView<MyItem> dynTableView;
    @FXML private TableColumn<MyItem,String> col_name;
    @FXML private TableColumn<MyItem, Spinner<Double>> col_quantity;
    @FXML private TableColumn<MyItem,Button> col_add;

    private MakeOrderSceneController orderController;
    private Map<MyStoreItem,Double> myStoreItemDoubleMap;
    private Map<MyItem,Double> selectedMyItemsMap = new HashMap();
    private Logic logic;
    private MySuperMarket superMarket;
    private double orderCost = 0 ;



    @FXML
    void cancelBtnOnAction(ActionEvent event) throws IOException {
        //reset spinner to zero.
        superMarket.resetSpinnersForDynamic(selectedMyItemsMap);
        // need to call to makeOrderScene.
        FXMLLoader loader = orderController.getAppControler().
                createLoader("/GuiScenes/makeOrderScene/makeOrderScene.fxml");
        ScrollPane root = loader.load();
        orderController.getAppControler().getDisplayScenes().getChildren().setAll(root);
        MakeOrderSceneController orderCtrl = loader.getController();
        orderCtrl.setAppSceneController(this.orderController.getAppControler());

    }

    @FXML
    void submitBtnOnAction(ActionEvent event) throws IOException {
        //reset spinner to zero.
        superMarket.resetSpinnersForDynamic(selectedMyItemsMap);

        myStoreItemDoubleMap =  superMarket.findBestPrice(selectedMyItemsMap);
        //need to clean the zeros from map.
        superMarket.cleanZerosFromSelcetedItemsMap(myStoreItemDoubleMap);

        MyOrder order = buildOrderFromSuperMarket(superMarket);

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
        Map<Integer, Double> deliveryMap = new HashMap<>();
        superMarket.calculateDeliveryCostMap(myStoreItemDoubleMap,deliveryMap , orderController.getCustomerSelectionComboBox().getValue());
        String orderKind = orderController.getStatDyneComboBox().getValue();
        orderCost = superMarket.calculateItemCost(myStoreItemDoubleMap);
        return superMarket.buildOrder(orderController.getOrderDatePicker() ,
                orderController.getCustomerSelectionComboBox().getValue(),myStoreItemDoubleMap,
                orderKind,orderCost,deliveryMap);
    }





    @FXML
    private void initialize() {
        if(this.orderController != null){
            //set up columns in the table
            this.col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
            this.col_quantity.setCellValueFactory(new PropertyValueFactory<>("spinner"));

            //load data
            loadDate();

            //add button to each raw
            addButtonToTable();

            //set up map
            myStoreItemDoubleMap = new HashMap<>();
        }

    }

    private void loadDate() {
        ObservableList<MyItem> observableList = this.superMarket.getItems().getItemsObesrvableList();
        dynTableView.setItems(observableList);
    }

    private void addButtonToTable() {
        Callback<TableColumn<MyItem, Button>, TableCell<MyItem, Button>>
                cellFactory = creatCallBackForButton();
        col_add.setCellFactory(cellFactory);
    }

    private Callback<TableColumn<MyItem, Button>, TableCell<MyItem, Button>> creatCallBackForButton() {
        Callback<TableColumn<MyItem, Button>, TableCell<MyItem, Button>> cellFactory =
                new Callback<TableColumn<MyItem, Button>, TableCell<MyItem, Button>>() {
                    @Override
                    public TableCell<MyItem, Button> call(final TableColumn<MyItem, Button> param) {
                        final TableCell<MyItem, Button> cell = new TableCell<MyItem, Button>() {

                            private final Button btn = new Button("Add item");
                            {

                                btn.setOnAction((ActionEvent event) -> {
                                 MyItem item = getTableView().getItems().get(getIndex());
                                 if(selectedMyItemsMap.containsKey(item))
                                     selectedMyItemsMap.replace(item,item.getSpinner().getValue());
                                 else
                                     selectedMyItemsMap.put(item,item.getSpinner().getValue());
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

    public void set(MakeOrderSceneController makeOrderSceneController) {
        this.orderController = makeOrderSceneController;
        this.logic = this.orderController.getAppControler().getLogic();
        this.superMarket = this.logic.getMySupermarket();
        this.initialize();
    }
}
