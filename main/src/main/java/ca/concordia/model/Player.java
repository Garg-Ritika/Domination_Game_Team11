package ca.concordia.model;

import java.util.List;

public class Player {

    private String playerName;
    private int playerID;
    private int noOfArmies;
    private List<Country> listOfCountries;
    private List<Order> listOfOrders;

    public Player(String playerName, int playerID) {
        this.playerName = playerName;
        this.playerID = playerID;
    }

    /**
     * Getters and Setters
     **/
    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getPlayerID() {
        return playerID;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    public int getNoOfArmies() {
        return noOfArmies;
    }

    public void setNoOfArmies(int noOfArmies) {
        this.noOfArmies = noOfArmies;
    }

    public List<Country> getListOfCountries() {
        return listOfCountries;
    }

    public void setListOfCountries(List<Country> listOfCountries) {
        this.listOfCountries = listOfCountries;
    }

    public List<Order> getListOfOrders() {
        return listOfOrders;
    }

    public void setListOfOrders(List<Order> listOfOrders) {
        this.listOfOrders = listOfOrders;
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
