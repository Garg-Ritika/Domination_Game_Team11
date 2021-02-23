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
    private List<Player> d_ListOfPlayers = new ArrayList<Player>();
    private final Map d_Map;
    private Player d_Winner;

    /**
     *
     * @param p_Map "to be updated"
     */
    public PlayerActions(Map p_Map) {
        this.d_Map = p_Map;
        this.d_Winner = null;
    }

    /**
     *
     * @param p_Player "to be updated"
     * @return "to be updated"
     */
    public boolean addPlayer(Player p_Player) {
        //TODO: cannot add more player than number of countries check
        return this.d_ListOfPlayers.add(p_Player);
    }

    /**
     *
     * @param p_Player "to be updated"
     * @return "to be updated"
     */
    public boolean removePlayer(Player p_Player) {
        return this.d_ListOfPlayers.remove(p_Player);
    }

    /**
     *
     * @return "to be updated"
     */
    public List<Player> getListOfPlayers(){
        return this.d_ListOfPlayers;
    }

    /**
     * This method assigns countries to players
     * @return boolean
     */
    public boolean assignCountriesToPlayers() {
        if (d_ListOfPlayers.size() < MINIMUM_PLAYER_COUNT) {
            System.out.println("Number of players should be atleast " + MINIMUM_PLAYER_COUNT + " to start assigning countries");
            return false;
        }
        System.out.println("start assigning countries ");

        int l_CountryCount = this.d_Map.getListOfCountries().size();
        int l_PlayerCount = this.d_ListOfPlayers.size();
        int l_PlayerCountryRatio = l_CountryCount /l_PlayerCount;

        // temporary country list that will be randomly assigned in quantity playCountryRatio to each player ..
        ArrayList<Country> l_CountriesToAssignRandomly = new ArrayList<Country>();
        for (Country l_Country : d_Map.getListOfCountries()){
            l_CountriesToAssignRandomly.add(l_Country);
        }

        for (Player l_Player : d_ListOfPlayers){

            ArrayList<Country> l_CountriesForPlayer = new ArrayList<Country>();
            while((l_CountriesForPlayer.size() < l_PlayerCountryRatio)) {

                int l_Index = 0;
                if(l_CountriesToAssignRandomly.size() <1){
                    System.out.println("All countries are assigned successfully ");
                    break;
                }else if(l_CountriesToAssignRandomly.size() > 1){
                    l_Index = new Random().nextInt(l_CountriesToAssignRandomly.size() -1);
                }
                Country l_Country = l_CountriesToAssignRandomly.get(l_Index);
                l_CountriesToAssignRandomly.remove(l_Country);
                l_CountriesForPlayer.add(l_Country);
                System.out.println(l_Player.getPlayerName() + " has " + l_Country.getName());
            }

            l_Player.setListOfCountries(l_CountriesForPlayer);
        }
        return true;
    }

    /**
     * All turns start with reinforcement. Each turn, each player receives a number of armies equal to:
     * (max(3, # of countries the player own/3)+(continent value of all continents controlled by the player)).
     * - Joey's message on discord
     * @param p_Player "player for which the reinforcement is happening"
     */
    public void assignReinforcementPhase(Player p_Player) {
        int l_CountryOwnedByPlayer = p_Player.getListOfCountries().size();
        System.out.println("#countries own by player: "+ p_Player.getPlayerName() + " is "+ l_CountryOwnedByPlayer);
        //TODO: let's assume it is 1 for now, need to come up with a method here ..
        int l_NetContinentValue = 1;
        System.out.println("net continent value of all the continents controlled by player: " + l_NetContinentValue);
        int l_NewArmy = Math.max(3,l_CountryOwnedByPlayer/3) + l_NetContinentValue;
        System.out.println("#new armies being assigned to playeR: "+ p_Player.getPlayerName() + " is " + l_NewArmy);
        p_Player.setNoOfArmies(l_NewArmy);
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
     * @param p_Player player is passed "to be updated"
     *
     */
    public void issueOrdersPhase(Player p_Player){

        // round robin means, every player should interact with other player .. ?
        for (Player l_NextPlayer : this.d_ListOfPlayers){

            if (l_NextPlayer.getPlayerID() == p_Player.getPlayerID()){
                continue;
            }

        }
    }


    /**
     *
     * @param p_Player "to be updated"
     *
     */
    public void executeOrderPhase(Player p_Player){

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
        this.d_Winner= player;
    }

    /**
     *
     * @return winner
     */
    public Player getWinner(){
        return this.d_Winner;
    }
}