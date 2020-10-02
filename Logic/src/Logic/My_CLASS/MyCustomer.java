package Logic.My_CLASS;

import Logic.SDM_CLASS.SDMCustomer;

public class MyCustomer {
    private SDMCustomer sdmCustomer;
    private MyLocation location;
    private int howManyOrdersHasMade;
    private double avgOrderPrice;
    private double avgDeliveryPrice;
    private String avgOrderPriceString;
    private String avgDeliveryPriceString;
    private MyOrders customerOrders;

    public MyCustomer(SDMCustomer sdmCustomer) {
        this.sdmCustomer = sdmCustomer;
        this.location = new MyLocation(sdmCustomer.getLocation());
        this.howManyOrdersHasMade = 0;
        this.avgDeliveryPrice = 0;
        this.avgDeliveryPrice = 0 ;
        this.avgDeliveryPriceString = "0.0";
        this.avgOrderPriceString = "0.0";

        this.customerOrders = new MyOrders();
    }

    public SDMCustomer getSdmCustomer() {
        return sdmCustomer;
    }

    public void setSdmCustomer(SDMCustomer sdmCustomer) {
        this.sdmCustomer = sdmCustomer;
    }

    public Integer getId() {
        return this.sdmCustomer.getId();
    }

    public void setId(int id){
        this.sdmCustomer.setId(id);
    }

    public MyLocation getLocation() {
        return location;
    }

    public void setLocation(MyLocation location) {
        this.location = location;
    }

    public String getName(){
        return this.sdmCustomer.getName();
    }

    public int getHowManyOrdersHasMade() {
        return howManyOrdersHasMade;
    }

    public void setHowManyOrdersHasMade(int howManyOrdersHasMade) {
        this.howManyOrdersHasMade = howManyOrdersHasMade;
    }

    public double getAvgOrderPrice() {
        return avgOrderPrice;
    }

    public void setAvgOrderPrice(double avgOrderPrice) {
        this.avgOrderPrice = avgOrderPrice;
        this.avgOrderPriceString = String.format("%.2f",avgOrderPrice);
    }

    public double getAvgDeliveryPrice() {
        return avgDeliveryPrice;
    }

    public void setAvgDeliveryPrice(double avgDeliveryPrice) {
        this.avgDeliveryPrice = avgDeliveryPrice;
        this.avgDeliveryPriceString = String.format("%.2f",avgDeliveryPrice);
    }

    public String getAvgOrderPriceString() {
        return avgOrderPriceString;
    }

    public String getAvgDeliveryPriceString() {
        return avgDeliveryPriceString;
    }

    public MyOrders getCustomerOrders() {
        return customerOrders;
    }

    public void setCustomerOrders(MyOrders customerOrders) {
        this.customerOrders = customerOrders;
    }

    @Override
    public String toString() {
        return "Customer id: " + this.getId() + " ,Customer name: " + getName();
    }

    public void setAvgDeatails() {
     this.setHowManyOrdersHasMade(this.getCustomerOrders().getOrderList().size());
     this.setAvgDeliveryPrice(calculateAvdDeliveryPrice());
     this.setAvgOrderPrice(calculateAvgOrderPrice());
    }

    private double calculateAvgOrderPrice() {
        double ordersTotalPrice = 0 ;
        int size = customerOrders.getOrderList().size();
        for (MyOrder order: customerOrders.getOrderList()) {
            ordersTotalPrice += order.getOrderCost();
        }
        return ordersTotalPrice/size;
    }

    private double calculateAvdDeliveryPrice() {
        int howManyDeliveries = 0 ;
        double deliveryPrice = 0.0;
        for (MyOrder order: customerOrders.getOrderList()) {
            for (int id : order.getDeliveryCostMap().keySet()) {
                howManyDeliveries++ ;
                deliveryPrice += order.getDeliveryCostMap().get(id);
            }
        }
        return deliveryPrice/howManyDeliveries;
    }

    public void addOrder(MyOrder order) {
        this.getCustomerOrders().addOrder(order); // add order to customer
        this.setAvgDeatails(); // calculate customer cost and delivery ave
    }
}
