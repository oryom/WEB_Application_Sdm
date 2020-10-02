package GuiScenes.makeOrderScene.orderSemmery;

import GuiScenes.appScene.AppSceneController;
import GuiScenes.makeOrderScene.MakeOrderSceneController;
import GuiScenes.showItemsScene.ShowItemsSceneController;
import Logic.Logic;
import Logic.My_CLASS.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class OrderSummeryController {

    @FXML private Button sendOrderBtn;
    @FXML private Button cancelBtn;
    @FXML private FlowPane summeryFlowPane;
    @FXML private Label orderCostLabel;
    @FXML private Label totalDeliveryCostLabel;
    @FXML private Label totalOrderAndDeliveryCostLabel;

    AppSceneController appSceneController;
    private MyOrder order;
    private MySuperMarket superMarket;
    private Logic logic;
    private Map<Integer, MyStoreSingleOrderItems> storeSingleOrderItemsMap;






    @FXML void cancelOnAction(ActionEvent event) throws IOException {
        // present make order scene

        // need to call to makeOrderScene.
        FXMLLoader loader = appSceneController.
                createLoader("/GuiScenes/makeOrderScene/makeOrderScene.fxml");
        ScrollPane root = loader.load();
        appSceneController.getDisplayScenes().getChildren().setAll(root);
        MakeOrderSceneController orderCtrl = loader.getController();
        orderCtrl.setAppSceneController(appSceneController);


    }

    @FXML
    void sendOrderOnAction(ActionEvent event) throws IOException {
        // update supermarket database.
        superMarket.updateOrder(this.order , this.storeSingleOrderItemsMap);
        presentThankYouMassage();
    }

    private void presentThankYouMassage() throws IOException {
        FXMLLoader loader = this.appSceneController.
                createLoader("/GuiScenes/makeOrderScene/orderSemmery/ThankYouScene.fxml");
        AnchorPane root = loader.load();
        this.appSceneController.getDisplayScenes().getChildren().setAll(root);
        ShowItemsSceneController showItems = loader.getController();

    }

    @FXML
    private void initialize() throws IOException {
        if(this.appSceneController != null){
            // initialize logic & supermarket & storeSingleOrderItemsMap
            this.logic = appSceneController.getLogic();
            this.superMarket= logic.getMySupermarket();
            this.storeSingleOrderItemsMap = new HashMap<>();
            logic.createStoreSingleOrderInstance(order,storeSingleOrderItemsMap);

            // creates tiles and presents to app
            presentOrderToGui(this.summeryFlowPane,this.storeSingleOrderItemsMap,
                    orderCostLabel, totalDeliveryCostLabel, totalOrderAndDeliveryCostLabel);

            //saves OrderSummery controller in appScene -> for the showOrders
            this.appSceneController.setOrderSummeryController(this);
        }
    }



    public void presentOrderToGui(FlowPane summeryFlowPane, Map<Integer,
            MyStoreSingleOrderItems> storeSingleOrderItemsMap,
                                  Label orderCostLabel, Label totalDeliveryCostLabel, Label totalOrderAndDeliveryCostLabel) throws IOException {

        for (Integer i: storeSingleOrderItemsMap.keySet()) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            URL url = getClass().getResource("/GuiScenes/makeOrderScene/orderSemmery" +
                    "/SingleStoreSummeryScene.fxml");
            fxmlLoader.setLocation(url);
            Node root = fxmlLoader.load();
            SingleStoreSummeryController singleStoreSummeryController = fxmlLoader.getController();
            singleStoreSummeryController.set(this,storeSingleOrderItemsMap.get(i));
            summeryFlowPane.getChildren().add(root);
        }
        // set order Cost labels
        setLabels(orderCostLabel, totalDeliveryCostLabel, totalOrderAndDeliveryCostLabel);

    }

    public void setLabels(Label orderCostLabel, Label totalDeliveryCostLabel, Label totalOrderAndDeliveryCostLabel) {
        orderCostLabel.setText(String.format("%.2f",order.getOrderCost()));
        totalDeliveryCostLabel.setText(String.format("%.2f",order.getDeliveryCost()));
        totalOrderAndDeliveryCostLabel.setText(String.format("%.2f",order.getTotalCost()));
    }

    public void set(AppSceneController appSceneController, MyOrder order) throws IOException {
        // imports appController & MyOrder
        this.appSceneController = appSceneController;

        this.order = order;

        initialize();
    }

    public MySuperMarket getSuperMarket() {
        return superMarket;
    }

    public Logic getLogic() {
        return logic;
    }
}
