package ca.concordia.model;

import java.util.List;

public class Player {

    private String d_PlayerName;
    private int d_PlayerID;
    private int d_NoOfArmies;
    private List<Country> d_ListOfCountries;
    private List<Order> d_ListOfOrders;

    public Player(String p_PlayerName, int p_PlayerID) {
        this.d_PlayerName = p_PlayerName;
        this.d_PlayerID = p_PlayerID;
    }

    public String getPlayerName() {
        return d_PlayerName;
    }

    public void setPlayerName(String p_PlayerName) {
        this.d_PlayerName = p_PlayerName;
    }

    public int getPlayerID() {
        return d_PlayerID;
    }

    public void setPlayerID(int p_PlayerID) {
        this.d_PlayerID = p_PlayerID;
    }

    public int getNoOfArmies() {
        return d_NoOfArmies;
    }

    public void setNoOfArmies(int p_NoOfArmies) {
        this.d_NoOfArmies = p_NoOfArmies;
    }

    public List<Country> getListOfCountries() {
        return d_ListOfCountries;
    }

    public void setListOfCountries(List<Country> p_ListOfCountries) {
        this.d_ListOfCountries = p_ListOfCountries;
    }

    public List<Order> getListOfOrders() {
        return d_ListOfOrders;
    }

    public void setListOfOrders(List<Order> p_ListOfOrders) {
        this.d_ListOfOrders = p_ListOfOrders;
    }

    public int nextOrder() {
         /*called by the GameEngine during the execute orders phase
         and returns the first order in the player’s list of orders,
         then removes it from the list.*/
        return 0;
    }

    public void issueOrder() {
        /* to add an order(in this case deploy order) to the list of orders held by the player
         when the game engine calls it during the issue orders phase*/
    }
}
