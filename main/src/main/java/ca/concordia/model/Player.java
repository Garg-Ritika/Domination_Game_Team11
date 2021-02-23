package ca.concordia.model;

import java.util.List;

/**
 * This class contains details of the Player
 *
 * @author to be updated
 *
 */
public class Player {

    private String d_PlayerName;
    private int d_PlayerID;
    private int d_NoOfArmies;
    private List<Country> d_ListOfCountries;
    private List<Order> d_ListOfOrders;

    /**
     * Constructor initializes the following below member variables of Player class
     *
     * @param p_PlayerName player Name
     * @param p_PlayerID Id of Player
     */
    public Player(String p_PlayerName, int p_PlayerID) {
        this.d_PlayerName = p_PlayerName;
        this.d_PlayerID = p_PlayerID;
    }

    /**
     * This method returns the PLayer name
     *
     * @return d_PlayerName
     */
    public String getPlayerName() {
        return d_PlayerName;
    }

    /**
     * This method sets the name of the Player
     *
     * @param p_PlayerName Player name
     */
    public void setPlayerName(String p_PlayerName) {
        this.d_PlayerName = p_PlayerName;
    }

    /**
     * This method returns the id of the Player
     *
     * @return d_PlayerID
     */
    public int getPlayerID() {
        return d_PlayerID;
    }

    /**
     * This method sets the id of the Player
     *
     * @param p_PlayerID Player id
     */
    public void setPlayerID(int p_PlayerID) {
        this.d_PlayerID = p_PlayerID;
    }

    /**
     * This method returns the no of armies associated with the Player
     *
     * @return d_NoOfArmies
     */
    public int getNoOfArmies() {
        return d_NoOfArmies;
    }

    /**
     * This method sets the no of armies to each Player
     *
     * @param p_NoOfArmies no of Armies
     */
    public void setNoOfArmies(int p_NoOfArmies) {
        this.d_NoOfArmies = p_NoOfArmies;
    }

    /**
     * This method returns the list of Countries owned by each Player
     *
     * @return d_ListOfCountries
     */
    public List<Country> getListOfCountries() {
        return d_ListOfCountries;
    }

    /**
     * This method sets the list of Countries owned by each Player
     *
     * @param p_ListOfCountries country List for each Player
     */
    public void setListOfCountries(List<Country> p_ListOfCountries) {
        this.d_ListOfCountries = p_ListOfCountries;
    }

    /**
     * This method returns the list of Orders issued by each Player
     *
     * @return d_ListOfOrders
     */
    public List<Order> getListOfOrders() {
        return d_ListOfOrders;
    }

    /**
     * This method sets the list of Orders issued by each Player
     *
     * @param p_ListOfOrders list of Orders
     */
    public void setListOfOrders(List<Order> p_ListOfOrders) {
        this.d_ListOfOrders = p_ListOfOrders;
    }

    /**
     * This method is called by the GameEngine during the execute orders phase
     * and returns the first order in the player’s list of orders,
     * then removes it from the list
     *
     * @return next order to be executed
     */
    public int nextOrder() {

        return 0;
    }

    /**
     * This method is called to add an order(in this case deploy order) to the list of orders held by the player
     * when the game engine calls it during the issue orders phase
     *
     */
    public void issueOrder() {

    }
}
