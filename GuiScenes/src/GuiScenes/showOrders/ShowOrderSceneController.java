package GuiScenes.showOrders;

import GuiScenes.appScene.AppSceneController;
import GuiScenes.makeOrderScene.orderSemmery.OrderSummeryController;
import Logic.Logic;
import Logic.My_CLASS.MyOrder;
import Logic.My_CLASS.MySuperMarket;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;

import java.io.IOException;

public class ShowOrderSceneController {

    @FXML private FlowPane summeryFlowPane;
    @FXML private Label orderCostLabel;
    @FXML private Label totalDeliveryCostLabel;
    @FXML private Label totalOrderAndDeliveryCostLabel;
    @FXML private Label dateLabel;
    @FXML private Label orderIdLabel;
    @FXML private Label customerNameLabel;

    @FXML private ComboBox<MyOrder> orderComboBox;

    private AppSceneController appSceneController;
    private Logic logic;
    private MySuperMarket superMarket;


    @FXML
    void orderComboBoxOnAction(ActionEvent event) throws IOException {
        //init order
        MyOrder order = orderComboBox.getValue();

        //set top labels
        this.dateLabel.setText(order.getDateAsString());
        this.orderIdLabel.setText(String.valueOf(order.getOrderId()));
        this.customerNameLabel.setText(order.getCustomer().getName());

        //building orders scene -> bottom labels set here
        appSceneController.getOrderSummeryController().
                presentOrderToGui(summeryFlowPane,order.getStoreSingleOrderItemsMap()
                ,orderCostLabel,totalDeliveryCostLabel,totalOrderAndDeliveryCostLabel);

    }

    public void setAppSceneController(AppSceneController appSceneController) {
        this.appSceneController = appSceneController;
        this.initialize();
    }

    @FXML
    private void initialize() {
        if(this.appSceneController != null) {
            this.logic = appSceneController.getLogic();
            this.superMarket = logic.getMySupermarket();
            orderComboBox.setPromptText("Choose store");
            orderComboBox.getItems().addAll(superMarket.getOrders().getOrderList());
        }


    }
}
