package ca.concordia.model;

public class Order {

    private String d_OrderCommand;

    public Order(String p_OrderCommand) {
        d_OrderCommand = p_OrderCommand;
    }

    public String getOrder() {
        return d_OrderCommand;
    }

}
