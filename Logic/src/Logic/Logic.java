package Logic;


import Logic.My_CLASS.*;
import javafx.beans.property.SimpleBooleanProperty;

import java.util.*;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;

public class

Logic {
     // need to have instance of superMarket!!!!!
   private MySuperMarket mySupermarket;
   private SimpleBooleanProperty isSuperMarketIsValid ;


    public boolean isIsSuperMarketIsValid() {
        return isSuperMarketIsValid.get();
    }

    public SimpleBooleanProperty isSuperMarketIsValidProperty() {
        return isSuperMarketIsValid;
    }



    public MySuperMarket getMySupermarket() {
        return mySupermarket;
    }

    public void setMySupermarket(MySuperMarket mySupermarket) {
        this.mySupermarket = mySupermarket;
    }

    public boolean isSuperMarketIsValid() {
        return isSuperMarketIsValid.get();
    }

    public void setSuperMarketIsValid(boolean superMarketIsValid) {
        isSuperMarketIsValid.set(superMarketIsValid);
    }

    public Logic() {
        this.mySupermarket = null;
        isSuperMarketIsValid = new SimpleBooleanProperty(false);
    }

//    public void newLoadXml(String path , xmlController controler) throws ExecutionException, InterruptedException {
//        Consumer<MySuperMarket> superMarketConsumer = v-> {this.mySupermarket = v ;};
//        Consumer<Boolean> validationConsumer = v -> {this.isSuperMarketIsValid.set(v);} ;
//        XmlLoaderTask task = new XmlLoaderTask (path,superMarketConsumer,validationConsumer);
//        controler.bindToXmlLoaderGui(task); // wire up
//        new Thread(task).start();
//    }

    public List<String> askForStoreNamesList() {
        return getMySupermarket().getStores().getStoresName();
    }

//    public List<String> askforCustomerNames() {
//        return getMySupermarket().getCustomers().getCustomerNames();
//    }

//    public void createStoreSingleOrderInstance(MyOrder order,
//                                               Map<Integer, MyStoreSingleOrderItems> storeSingleOrderItemsMap){
//        Map<MyStoreItem, Double> ordersQuantityMap = order.getQuantityMap();
//
//        for (MyStoreItem storeItem: ordersQuantityMap.keySet()) {
//            //initialize
//            int storeId= storeItem.getStoreId();
//            MyStore store = this.mySupermarket.getStores().getStoreMap().get(storeId);
//            double deliveryDistance = this.mySupermarket.caculateDeliveryDistance(store,order.getCustomer().getLocation(),
//                    store.getMyLocation());
//            double deliveryCost = order.getDeliveryCostMap().get(storeId);
//
//            //creat instance
//            if(!storeSingleOrderItemsMap.containsKey(storeId)){
//                MyStoreSingleOrderItems storeSingleOrderItems = new MyStoreSingleOrderItems(order.getOrderId(),
//                        order.getDate(),storeId,order.getCustomer(),order.getOrderKind() ,deliveryDistance , deliveryCost );
//                //saving the instance in map.
//                storeSingleOrderItemsMap.put(storeId,storeSingleOrderItems);
//            }
//
//            // adding item to sub quantity map
//            storeSingleOrderItemsMap.get(storeId).addToQuantityMap(storeItem,ordersQuantityMap.get(storeItem));
//
//    }
//
//  }
}

