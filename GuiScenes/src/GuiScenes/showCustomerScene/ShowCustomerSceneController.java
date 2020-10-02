package GuiScenes.showCustomerScene;

import GuiScenes.appScene.AppSceneController;

import Logic.Logic;
import Logic.My_CLASS.MyCustomer;
import Logic.My_CLASS.MySuperMarket;
import Logic.SDM_CLASS.Location;
import Logic.SDM_CLASS.SDMCustomer;
import Logic.SDM_CLASS.SDMCustomers;
import Logic.SuperMarket;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class ShowCustomerSceneController {

    @FXML private TableView<MyCustomer> customerTableView;
    @FXML private TableColumn<MyCustomer, Integer> idCulmn;
    @FXML private TableColumn<MyCustomer, String> nameCulmn;
    @FXML private TableColumn<MyCustomer, String> locationCulmn;
    @FXML private TableColumn<MyCustomer, Integer> ordersQuantityculmn;
    @FXML private TableColumn<MyCustomer, String> avgOrderPriceCulmn;
    @FXML private TableColumn<MyCustomer, String> avgorderDeliveryculmn;
    AppSceneController appSceneController;

    @FXML
    void StatDynmComboBoxOnAction(ActionEvent event) {
        System.out.println("good");
    }


    public void setAppSceneController(AppSceneController appSceneController) {
        this.appSceneController = appSceneController;
        this.initialize();
    }

    @FXML
    private void initialize() {
        if(this.appSceneController != null){

            this.idCulmn.setCellValueFactory(new PropertyValueFactory<>("id"));
            this.nameCulmn.setCellValueFactory(new PropertyValueFactory<>("name"));
            this.locationCulmn.setCellValueFactory(new PropertyValueFactory<>("location"));
            this.ordersQuantityculmn.setCellValueFactory(new PropertyValueFactory<>("howManyOrdersHasMade"));
            this.avgOrderPriceCulmn.setCellValueFactory(new PropertyValueFactory<>("avgOrderPriceString"));
            this.avgorderDeliveryculmn.setCellValueFactory(new PropertyValueFactory<>("avgDeliveryPriceString"));


            //load data from sdm
            Logic logic = appSceneController.getLogic();

            MySuperMarket superMarket = logic.getMySupermarket();

            ObservableList<MyCustomer> sdmCustomerObservableList = superMarket.getCustomers()
                    .getCustomerObesrvableList();
            customerTableView.setItems(sdmCustomerObservableList);
        }
    }


}
