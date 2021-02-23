package ca.concordia.controller.game;

import ca.concordia.model.Country;
import ca.concordia.model.Map;
import ca.concordia.model.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *  Helper class that is used to
 *  1. add player into player list
 *  2. remove player from player list
 *  3. assign countries to the player
 *  4. assign armies to their countries of the players *
 *
 * @author Nilesh Aggarwal
 */

public class PlayerActions {

    private final int MINIMUM_PLAYER_COUNT = 3;
    private List<Player> listOfPlayers = new ArrayList<Player>();
    private final Map map;
    private Player winner;

    /**
     *
     * @param map "to be updated"
     */
    public PlayerActions(Map map) {
        this.map = map;
        this.winner = null;
    }

    /**
     *
     * @param player "to be updated"
     * @return "to be updated"
     */
    public boolean addPlayer(Player player) {
        //TODO: cannot add more player than number of countries check
        return this.listOfPlayers.add(player);
    }

    /**
     *
     * @param player "to be updated"
     * @return "to be updated"
     */
    public boolean removePlayer(Player player) {
        return this.listOfPlayers.remove(player);
    }

    /**
     *
     * @return "to be updated"
     */
    public List<Player> getListOfPlayers(){
        return this.listOfPlayers;
    }

    /**
     * This method assigns countries to players
     * @return boolean
     */
    public boolean assignCountriesToPlayers() {
        if (listOfPlayers.size() < MINIMUM_PLAYER_COUNT) {
            System.out.println("Number of players should be atleast " + MINIMUM_PLAYER_COUNT + " to start assigning countries");
            return false;
        }
        System.out.println("start assigning countries ");

        int countryCount = this.map.getListOfCountries().size();
        int playerCount = this.listOfPlayers.size();
        int playerCountryRatio = countryCount/playerCount;

        // temporary country list that will be randomly assigned in quantity playCountryRatio to each player ..
        ArrayList<Country> countriesToAssignRandomly = new ArrayList<Country>();
        for (Country country: map.getListOfCountries()){
            countriesToAssignRandomly.add(country);
        }

        for (Player player: listOfPlayers){

            ArrayList<Country> countriesForPlayer = new ArrayList<Country>();
            while((countriesForPlayer.size() < playerCountryRatio)) {

                int index = 0;
                if(countriesToAssignRandomly.size() <1){
                    System.out.println("All countries are assigned successfully ");
                    break;
                }else if(countriesToAssignRandomly.size() > 1){
                    index = new Random().nextInt(countriesToAssignRandomly.size() -1);
                }
                Country country = countriesToAssignRandomly.get(index);
                countriesToAssignRandomly.remove(country);
                countriesForPlayer.add(country);
                System.out.println(player.getPlayerName() + " has " +country.getName());
            }

            player.setListOfCountries(countriesForPlayer);
        }
        return true;
    }

    /**
     * All turns start with reinforcement. Each turn, each player receives a number of armies equal to:
     * (max(3, # of countries the player own/3)+(continent value of all continents controlled by the player)).
     * - Joey's message on discord
     * @param player "player for which the reinforcement is happening"
     */
    public void assignReinforcementPhase(Player player) {
        int l_CountryOwnedByPlayer = player.getListOfCountries().size();
        System.out.println("#countries own by player: "+ player.getPlayerName() + " is "+ l_CountryOwnedByPlayer);
        //TODO: let's assume it is 1 for now, need to come up with a method here ..
        int l_NetContinentValue = 1;
        System.out.println("net continent value of all the continents controlled by player: " + l_NetContinentValue);
        int l_NewArmy = Math.max(3,l_CountryOwnedByPlayer/3) + l_NetContinentValue;
        System.out.println("#new armies being assigned to playeR: "+ player.getPlayerName() + " is " + l_NewArmy);
        player.setNoOfArmies(l_NewArmy);
    }

    /**
     * The GameEngine class calls the issue_order() method of the Player.
     * This method will wait for the following command, then create a deploy order object on the player’s list of orders,
     * then reduce the number of armies in the player’s reinforcement pool.
     *
     * The game engine does this for all players in round-robin fashion until all the players have placed all their reinforcement armies on the map.
     *
     * Issuing order command:
     * deploy countryID num (until all reinforcements have been placed)
     *
     * @param player player is passed "to be updated"
     *
     */
    public void issueOrdersPhase(Player player){

        // round robin means, every player should interact with other player .. ?
        for (Player nextPlayer: this.listOfPlayers){

            if (nextPlayer.getPlayerID() == player.getPlayerID()){
                continue;
            }

        }
    }


    /**
     *
     * @param player "to be updated"
     *
     */
    public void executeOrderPhase(Player player){

    }

    /**
     * Game is only over when one player has captured all the countries ..
     * @return "to be updated"
     */
    public boolean isGameOver(){
        // TODO :
        return true;
    }

    /**
     *
     * @param player player parameter is passed
     */
    public void setWinner(Player player){
        this.winner= player;
    }

    /**
     *
     * @return winner
     */
    public Player getWinner(){
        return this.winner;
    }
}