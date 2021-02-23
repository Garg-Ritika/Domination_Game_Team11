package ca.concordia.model;

/**
 * This class contains details of the Order to be executed by the PLayer in GameEngine
 *
 * @author to be updated
 *
 */
public class Order {

    private String d_OrderCommand;

    /**
     * Constructor initializes the order command to be executed by each player
     *
     * @param p_OrderCommand Order string passed by Player
     */
    public Order(String p_OrderCommand) {
        d_OrderCommand = p_OrderCommand;
    }

    /**
     * This method returns the order to be executed by Player in game Engine
     *
     * @return order command to be executed
     */
    public String getOrder() {
        return d_OrderCommand;
    }

}
