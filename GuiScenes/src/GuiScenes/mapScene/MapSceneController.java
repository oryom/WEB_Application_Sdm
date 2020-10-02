package GuiScenes.mapScene;

import GuiScenes.appScene.AppSceneController;
import Logic.Logic;
import Logic.My_CLASS.MyCustomer;
import Logic.My_CLASS.MyLocation;
import Logic.My_CLASS.MyStore;
import Logic.My_CLASS.MySuperMarket;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class MapSceneController {
    private AppSceneController appSceneController;
    private Logic logic;
    private MySuperMarket superMarket;
    private Alert massageAlert;



    public Stage getMap(AppSceneController appSceneController) throws IOException {
        //initialize
        init(appSceneController);
        //building stage
        Stage stg = new Stage();
        FXMLLoader loader =  appSceneController.createLoader("/GuiScenes/mapScene/MapScene.fxml");
        GridPane root = loader.load(loader.getLocation().openStream());
        root.setPadding(new Insets(17,17,17,5));

        int rows = superMarket.getMaxRows() + 2;
        int cols = superMarket.getMaxCols() + 2;

        Map<Integer, MyCustomer> costumers = superMarket.getCustomers().getCustomerMap();
        Map<Integer, MyStore> stores = superMarket.getStores().getStoreMap();

        for (int i = 1; i <= rows; i++) {
            RowConstraints r = new RowConstraints();
            r.setMinHeight(10);
            r.setPrefHeight(10);
            r.setVgrow(Priority.SOMETIMES);
            root.getRowConstraints().add(r);
        }
        for (int i = 1; i <= cols; i++) {
            ColumnConstraints r = new ColumnConstraints();
            r.setMinWidth(10);
            r.setPrefWidth(10);
            r.setHgrow(Priority.SOMETIMES);
            root.getColumnConstraints().add(r);
        }

        stores.values().stream().forEach(store -> {
            Button btn = new Button();

            MyLocation p = store.getMyLocation();

            btn.setOnAction((e)->{
                massageAlert.setHeaderText("Store");
                massageAlert.getDialogPane().setContentText(
                    String.valueOf("Id:"+store.getId()) + ",Name:" + store.getName() +
                            ",PPK:"+ String.valueOf(store.getSdmStore().getDeliveryPpk()+
                          ",Number of Orders:" + store.getStoreSingleOrderItemsList().size())
            );
                massageAlert.showAndWait();});
            root.add(btn, (int) p.getSdmLocation().getY(), (int) p.getSdmLocation().getX());
        });

        costumers.values().stream().forEach(customer -> {
            Button btn = new Button();

            MyLocation p = customer.getLocation();

            //btn.setOnAction((e)-> finalStage.show());
            btn.setOnAction((e)-> {
                massageAlert.setHeaderText("Customer");
                massageAlert.getDialogPane().setContentText("Id: " +String.valueOf(customer.getId())
                                + " ,Name: " + customer.getName() +
                        ",Number of orders: " + customer.getHowManyOrdersHasMade());
                massageAlert.showAndWait();
            });
          root.add(btn, (int) p.getSdmLocation().getY(), (int) p.getSdmLocation().getX());

        });

            Scene scene = new Scene(root);
            stg.initModality(Modality.APPLICATION_MODAL);
            stg.setTitle("Map");
            stg.setScene(scene);


        return stg;
    }

    private void init(AppSceneController appSceneController) {
        this.appSceneController = appSceneController;
        this.logic = appSceneController.getLogic();
        this.superMarket = logic.getMySupermarket();
        this.massageAlert = createAlert();
    }

    private Alert createAlert() {
        Stage massageStage = (Stage) appSceneController.getDisplayScenes().getScene().getWindow();
        Alert.AlertType type = Alert.AlertType.INFORMATION;
        Alert alert = new Alert(type, "");
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(massageStage);


        return alert;
    }
}
