package GuiScenes.makeOrderScene.sellScene;

import GuiScenes.appScene.AppSceneController;
import GuiScenes.makeOrderScene.orderSemmery.OrderSummeryController;
import Logic.My_CLASS.MyOrder;
import Logic.My_CLASS.MyStore;
import Logic.My_CLASS.MyStoreItem;
import Logic.My_CLASS.MySuperMarket;
import Logic.SDM_CLASS.IfYouBuy;
import Logic.SDM_CLASS.SDMDiscount;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.FlowPane;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class AllSellsSceneControlller {

    @FXML private FlowPane sellsFlowPane;
    @FXML private Button confirmBtn;
    private AppSceneController controller;
    private MySuperMarket superMarket;
    private MyOrder order;
    private HashMap<ToggleGroup, SDMDiscount> salesChosen = new HashMap<>();


    @FXML
    private void initialize() throws IOException {
        if(this.controller != null){
            superMarket = controller.getLogic().getMySupermarket();
            showSells();
        }
    }

    @FXML
    void confirmOnAction(ActionEvent event) throws IOException {
        for (Map.Entry<ToggleGroup, SDMDiscount> entry : salesChosen.entrySet()) {
            RadioButton selectedRadioButton = (RadioButton) entry.getKey().getSelectedToggle();
            SDMDiscount discount = entry.getValue();
            int storeId = superMarket.getDiscountStoreId(discount);

            if(selectedRadioButton.getText().equals("None"))
                continue;
            else{
                if(discount.getThenYouGet().getOperator().equals("ALL-OR-NOTHING"))
                   order.addDiscountsItemsdToOrder(superMarket,discount.getThenYouGet(),storeId);
                else{ // ONE-OF
                    Integer radioIndex = entry.getKey().getToggles().indexOf(entry.getKey().getSelectedToggle());

                    //creates instance of MyStoreItem and ads to orders quantityMap.
                    order.addDiscountSingleIemToOrder(superMarket,discount.getThenYouGet().getSDMOffer().get(radioIndex) ,storeId);
                }

            }
        }

        //need to update avg and ,update store and items(need to think about it...)
        showOrderSummery();
    }

    private void showOrderSummery() throws IOException {
        // presents scene on app.
        presenetScene();

    }

    private void presenetScene() throws IOException {
        FXMLLoader loader = controller.
                createLoader("/GuiScenes/makeOrderScene/orderSemmery/orderSummeryScene.fxml");
        ScrollPane root = loader.load();
        controller.getDisplayScenes().getChildren().setAll(root);

        //wire up controllers:
        OrderSummeryController orderSummeryController = loader.getController();
        orderSummeryController.set(this.controller , order);
    }

    private void showSells() throws IOException {
        Set<MyStoreItem> storeItemSet = order.getQuantityMap().keySet();
        for (MyStoreItem storeItem:storeItemSet) {
            MyStore store = superMarket.getStores().getStoreMap().get(storeItem.getStoreId());
            if(store.getSdmStore().getSDMDiscounts() != null) { // if the store has discounters.
                for (SDMDiscount discount : store.getSdmStore().getSDMDiscounts().getSDMDiscount()) {
                    IfYouBuy ifYouBuy = discount.getIfYouBuy();
                    if(storeItem.getMyItem().getSdmItem().getId() == ifYouBuy.getItemId() &&
                  order.getQuantityMap().get(storeItem) >= ifYouBuy.getQuantity())
                        for (double i=0;
                             i<Math.floor(order.getQuantityMap().get(storeItem)/ifYouBuy.getQuantity());i++){
                            createSellTile(discount);
                        }
                }
            }
        }
    }

    private void createSellTile(SDMDiscount discount) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL url = getClass().getResource("/GuiScenes/makeOrderScene/sellScene/SingleSellScene.fxml");
        fxmlLoader.setLocation(url);
        Node singleSaleTile = fxmlLoader.load();
        SingleSellController sellController = fxmlLoader.getController();
        sellController.init(discount,order,this);
        sellsFlowPane.getChildren().add(singleSaleTile);
    }

    public void set(AppSceneController appControler, MyOrder order) throws IOException {
        this.controller = appControler;
        this.order = order;
        this.initialize();

    }

    public MySuperMarket getSuperMarket() {
        return superMarket;
    }

    public HashMap<ToggleGroup, SDMDiscount> getSalesChosen() {
        return salesChosen;
    }
}
