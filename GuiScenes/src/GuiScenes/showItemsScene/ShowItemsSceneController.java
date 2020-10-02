package GuiScenes.showItemsScene;

import GuiScenes.appScene.AppSceneController;
import Logic.My_CLASS.MyItem;
import Logic.My_CLASS.MySuperMarket;
import Logic.Logic;
import Logic.SDM_CLASS.SDMItem;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ShowItemsSceneController {

    @FXML private TableView<MyItem> customerTableView;
    @FXML private TableColumn<MyItem, Integer> idCulmn;
    @FXML private TableColumn<MyItem, String> nameCulmn;
    @FXML private TableColumn<MyItem, String> purchaseCategoryCulmn;
    @FXML private TableColumn<MyItem, Integer> howManyStoresSellsItem_col;
    @FXML private TableColumn<MyItem, String> avgPrice_col;
    @FXML private TableColumn<MyItem, String> howManyTimeSold_col;

    private AppSceneController appSceneController;

    public void setAppSceneController(AppSceneController appSceneController) {
        this.appSceneController = appSceneController;
        this.initialize();
    }

    @FXML
    private void initialize() {
        if(this.appSceneController != null){
            //set up columns in the table
            this.idCulmn.setCellValueFactory(new PropertyValueFactory<>("itemId"));
            this.nameCulmn.setCellValueFactory(new PropertyValueFactory<>("name"));
            this.purchaseCategoryCulmn.setCellValueFactory(new PropertyValueFactory<>("purchaseCategory"));
            howManyStoresSellsItem_col.setCellValueFactory(new PropertyValueFactory<>("howManyStoresSellsThisItem"));
            avgPrice_col.setCellValueFactory(new PropertyValueFactory<>("averageItemPriceString"));
            howManyTimeSold_col.setCellValueFactory(new PropertyValueFactory<>("howManyTimesItemSoldString"));


            //load data from sdm
            Logic logic = appSceneController.getLogic();
            MySuperMarket superMarket = logic.getMySupermarket();
            ObservableList<MyItem> ObservableList = superMarket.getItems().getItemsObesrvableListSdm();
            customerTableView.setItems(ObservableList);
        }
    }

}
