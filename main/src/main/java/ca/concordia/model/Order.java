package ca.concordia.model;

public class Order {

    private int orderID;

    public Order(int orderID) {
        this.orderID = orderID;
    }

    /**
     * Getters and Setters
     **/
    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public void execute() {
        // to enact the order- more clarity on this required?
    }

}
