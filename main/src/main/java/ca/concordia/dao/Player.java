package ca.concordia.dao;

import ca.concordia.patterns.command.Order;

import java.util.ArrayList;
import java.util.List;

/**
 * This class contains details of the Player
 */
public class Player {

    private String d_PlayerName;
    private int d_PlayerID;
    private int d_NoOfArmies;
    private List<Country> d_ListOfCountries = new ArrayList<Country>();
    private List<Continent> d_ListOfContinents = new ArrayList<Continent>();
    private List<Order> d_ListOfOrders = new ArrayList<Order>();

    /**
     * Constructor initializes the following below member variables of Player class
     *
     * @param p_PlayerName player Name
     * @param p_PlayerID   Id of Player
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
     * Setter method to add new country in the list of countries
     *
     * @param p_Country country object
     * @return boolean
     */
    public boolean addNewCountry(Country p_Country) {
        return d_ListOfCountries.add(p_Country);
    }

    /**
     * Setter method to remove any country from the list of the countries of the player
     *
     * @param p_Country country object
     * @return boolean
     */
    public boolean removeNewCountry(Country p_Country) {
        return d_ListOfCountries.remove(p_Country);
    }

    /**
     * Getter method to get all the continents controlled by the player
     *
     * @return list of continents
     */
    public List<Continent> getListOfContinents() {
        return d_ListOfContinents;
    }

    /**
     * Setter method to set the list of continents controlled by the player
     *
     * @param p_ListOfContinents list of continents
     */
    public void setListOfContinents(List<Continent> p_ListOfContinents) {
        this.d_ListOfContinents = p_ListOfContinents;
    }

    /**
     * Setter method to add a new continent in the list of continents controlled by the player
     *
     * @param p_Continent new continent object
     * @return boolean
     */
    public boolean addNewContinent(Continent p_Continent) {
        return d_ListOfContinents.add(p_Continent);
    }

    /**
     * Setter method to remove the continet from the list of continents controleld by the player
     *
     * @param p_Continent a new continent object
     * @return boolean
     */
    public boolean removeNewContinent(Continent p_Continent) {
        return d_ListOfContinents.remove(p_Continent);
    }


    public void createOrder(Order o) {
        d_ListOfOrders.add(o);
    }

    /**
     * The player class must also have a next_order() (no parameters) method
     * that is called by the GameEngine  during the execute orders phase
     * and returns the first order in the player’s list of orders,
     * then removes it from the list
     *
     * @return
     */
    public Order nextOrder() {
        Order to_return = null;
        if (d_ListOfOrders.size() > 0) {
            to_return = d_ListOfOrders.get(0);
            d_ListOfOrders.remove(0);
            return to_return;
        }
        return null;
    }

    /*
     public boolean createOrder(List<Territory> map, List<Player> players) {
        //…
        orders_list.add(new Deploy(this, target, num));
        orders_list.add(new command.Advance(this, source, target, num));
        orders_list.add(new Pacify(this, player));
        //…
        return  false;
    }

    public Order getNextOrder() {
        if (this.orders_list.isEmpty()){
            to_return = this.orders_list.get(0);
            this.orders_list.remove(0);
            return to_return;
        }else
        return null;
    }
     */


}
