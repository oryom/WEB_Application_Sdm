package GuiScenes.makeOrderScene.sellScene;

import Logic.My_CLASS.MyOrder;
import Logic.My_CLASS.MySuperMarket;
import Logic.SDM_CLASS.SDMDiscount;
import Logic.SDM_CLASS.SDMOffer;
import Logic.SDM_CLASS.ThenYouGet;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class SingleSellController {

    @FXML private Label saleName;
    @FXML private AnchorPane saleInfoPane;
    @FXML private VBox saleVB;
    @FXML private Label SaleTypeLabel;
    private ToggleGroup tg = new ToggleGroup();

    public void init(SDMDiscount discount, MyOrder order, AllSellsSceneControlller allSellsSceneControlller) {
        MySuperMarket superMarket = allSellsSceneControlller.getSuperMarket();
        ThenYouGet thenYouGet = discount.getThenYouGet();
        saleName.setText(discount.getName());
        if(thenYouGet.getOperator().equals("ONE-OF")){
            SaleTypeLabel.setText("Choose one:");
            for (SDMOffer offer: thenYouGet.getSDMOffer()) {
                String itemName = superMarket.getItems().getItemsMap().get(offer.getItemId())
                        .getSdmItem().getName();
                RadioButton btn = new RadioButton(String.valueOf(offer.getQuantity())+" "
                + itemName + " for " + String.valueOf(offer.getForAdditional()) + "NIS");
                btn.setPadding(new Insets(10));
                saleVB.getChildren().add(btn);
                btn.setToggleGroup(tg);
            }
            RadioButton btn = new RadioButton("None");
            btn.setSelected(true);
            btn.setPadding(new Insets(10));

            saleVB.getChildren().add(btn);
            btn.setToggleGroup(tg);
            allSellsSceneControlller.getSalesChosen().put(tg,discount);
        }
        else {
            SaleTypeLabel.setText("Take all or nothing:");
            String allItemNames = "";
            Double itemTotalPrice = 0.0;
            for (SDMOffer offer:thenYouGet.getSDMOffer()) {
                String itemName = superMarket.getItems().getItemsMap().get(offer.getItemId())
                        .getSdmItem().getName();
                allItemNames = allItemNames + " " + offer.getQuantity() + " " + itemName + " "
                        +  " " + offer.getForAdditional() + " NIS";
                itemTotalPrice = itemTotalPrice +offer.getForAdditional();
                allItemNames = allItemNames + "\n";
            }
            allItemNames = allItemNames + "Total of " + itemTotalPrice + "NIS";
            RadioButton btn = new RadioButton(allItemNames);
            btn.setToggleGroup(tg);
            btn.setPadding(new Insets(10));
            saleVB.getChildren().add(btn);
            btn = new RadioButton("None");
            btn.setSelected(true);
            btn.setPadding(new Insets(10));
            btn.setPadding(new Insets(10));
            saleVB.getChildren().add(btn);
            btn.setToggleGroup(tg);
            allSellsSceneControlller.getSalesChosen().put(tg,discount);
        }
    }
}
