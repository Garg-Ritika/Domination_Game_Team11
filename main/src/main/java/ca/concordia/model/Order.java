package ca.concordia.model;

/**
 * This class contains details of the Order to be executed by the PLayer in GameEngine
 */
public class Order {

    private String d_OrderType;
    private String d_CountryName;
    private int d_ArmyCount;

    /**
     * Constructor initializes the order command to be executed by each player
     *
     * @param p_OrderType   Order string passed by Player
     * @param p_CountryName CountryName
     * @param p_ArmyCount   armycount
     */
    public Order(String p_OrderType, String p_CountryName, int p_ArmyCount) {
        d_OrderType = p_OrderType;
        d_CountryName = p_CountryName;
        d_ArmyCount = p_ArmyCount;
    }

    /**
     * Getter to get the order type of the order
     *
     * @return order type string
     */
    public String getOrderType() {
        return d_OrderType;
    }

    /**
     * Getter to get the country name of the order
     *
     * @return country name in the order
     */
    public String getCountryName() {
        return d_CountryName;
    }

    /**
     * Getter to get the armycount integer value in the order
     *
     * @return armycount integer
     */
    public int getArmyCount() {
        return d_ArmyCount;
    }

    /**
     * The Order object’s execute() method is called,
     * which will enact the order.
     */
    public void executeOrder() {

    }

}
