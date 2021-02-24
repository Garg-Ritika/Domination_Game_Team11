package ca.concordia.controller.game;

import ca.concordia.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Helper class that is used to
 * 1. add player into player list
 * 2. remove player from player list
 * 3. assign countries to the player
 * 4. assign armies to their countries of the players *
 */

public class PlayerActions {

    private final int MINIMUM_PLAYER_COUNT = 3;
    private final Map d_Map;
    private List<Player> d_ListOfPlayers = new ArrayList<Player>();
    private Player d_Winner;

    /**
     * @param p_Map "to be updated"
     */
    public PlayerActions(Map p_Map) {
        this.d_Map = p_Map;
        this.d_Winner = null;
    }

    /**
     * Getter to get the map on which game is running ..
     *
     * @return Map object
     */
    private Map getMap() {
        return d_Map;
    }

    /**
     * @param p_Player "to be updated"
     * @return "to be updated"
     */
    public boolean addPlayer(Player p_Player) {
        //TODO: cannot add more player than number of countries check
        return this.d_ListOfPlayers.add(p_Player);
    }

    /**
     * @param p_Player "to be updated"
     * @return "to be updated"
     */
    public boolean removePlayer(Player p_Player) {
        return this.d_ListOfPlayers.remove(p_Player);
    }

    /**
     * @return "to be updated"
     */
    public List<Player> getListOfPlayers() {
        return this.d_ListOfPlayers;
    }

    /**
     * This method assigns countries to players
     *
     * @return boolean
     */
    public boolean assignCountriesToPlayers() {
        if (d_ListOfPlayers.size() < MINIMUM_PLAYER_COUNT) {
            System.out.println("Number of players should be atleast " + MINIMUM_PLAYER_COUNT + " to start assigning countries");
            return false;
        }
        if (getMap().getListOfCountries().size() == 0) {
            System.out.println("Zero countries in the map, so unable to assign anything to players");
            return false;
        }
        System.out.println("Number of countries available is : " + getMap().getListOfCountries().size());

        System.out.println("start assigning countries ");

        int l_CountryCount = this.d_Map.getListOfCountries().size();
        int l_PlayerCount = this.d_ListOfPlayers.size();
        int l_PlayerCountryRatio = l_CountryCount / l_PlayerCount;
        System.out.println("player-country ratio: " + l_PlayerCountryRatio);

        // temporary country list that will be randomly assigned in quantity playCountryRatio to each player ..
        ArrayList<Country> l_CountriesToAssignRandomly = new ArrayList<Country>();
        for (Country l_Country : d_Map.getListOfCountries()) {
            l_CountriesToAssignRandomly.add(l_Country);
        }

        while (l_CountriesToAssignRandomly.size() > 0) {
            for (Player l_Player : d_ListOfPlayers) {
                if (l_CountriesToAssignRandomly.size() == 0) {
                    System.out.println("All countries has been assigned ");
                    break;
                } else if (l_CountriesToAssignRandomly.size() == 1) {
                    Country l_Country = l_CountriesToAssignRandomly.get(0);
                    l_CountriesToAssignRandomly.remove(l_Country);
                    l_Player.addNewCountry(l_Country);
                    System.out.println(l_Player.getPlayerName() + " has " + l_Country.getName());
                } else {
                    // Assign countries one-by-one, pick it randomly
                    int l_Index = new Random().nextInt(l_CountriesToAssignRandomly.size() - 1);
                    Country l_Country = l_CountriesToAssignRandomly.get(l_Index);
                    l_CountriesToAssignRandomly.remove(l_Country);
                    l_Player.addNewCountry(l_Country);
                    System.out.println(l_Player.getPlayerName() + " has " + l_Country.getName());
                }

            }
        }
        return true;
    }

    /**
     * All turns start with reinforcement. Each turn, each player receives a number of armies equal to:
     * (max(3, # of countries the player own/3)+(continent value of all continents controlled by the player)).
     * - Joey's message on discord
     *
     * @param p_Player "player for which the reinforcement is happening"
     */
    public int assignReinforcementPhase(Player p_Player) {
        int l_CountryOwnedByPlayer = p_Player.getListOfCountries().size();
        System.out.println("#countries own by player: " + p_Player.getPlayerName() + " is " + l_CountryOwnedByPlayer);
        int l_NetContinentValue = 0;
        if (p_Player.getListOfContinents().size() > 1) {
            for (Continent p_Continent : p_Player.getListOfContinents()) {
                l_NetContinentValue += p_Continent.getArmyCount();
            }
        }
        System.out.println("#net continent value of all the continents controlled by player: " + l_NetContinentValue);
        int l_NewArmy = Math.max(3, l_CountryOwnedByPlayer / 3) + l_NetContinentValue;
        System.out.println("#new armies being assigned to playeR: " + p_Player.getPlayerName() + " is " + l_NewArmy);
        p_Player.setNoOfArmies(l_NewArmy);
        return l_NewArmy;
    }

    /**
     * The GameEngine class calls the issue_order() method of the Player.
     * This method will wait for the following command, then create a deploy order object on the player’s list of orders,
     * then reduce the number of armies in the player’s reinforcement pool.
     * <p>
     * The game engine does this for all players in round-robin fashion until all the players have placed all their reinforcement armies on the map.
     * <p>
     * Issuing order command:
     * deploy countryID num (until all reinforcements have been placed)
     *
     * @param p_Player player is passed "to be updated"
     */
    public void issueOrdersPhase(Player p_Player) {
        p_Player.issueOrder();
    }


    /**
     * @param p_Player "to be updated"
     */
    public void executeOrderPhase(Player p_Player) {
        Order l_Order = p_Player.nextOrder();
        if (l_Order != null) {
            String l_OrderType = l_Order.getOrderType();
            switch (l_OrderType) {
                case "deploy":
                    String l_CountryName = l_Order.getCountryName();
                    int l_ArmyCount = l_Order.getArmyCount();
                    int l_PlayerArmyAvailable = p_Player.getNoOfArmies();
                    if (l_PlayerArmyAvailable < l_ArmyCount) {
                        System.out.println("Invalid: player " + p_Player.getPlayerName() + "cannot assign more armies than it has..");
                        l_ArmyCount = p_Player.getNoOfArmies();
                    }

                    for (Country l_country : getMap().getListOfCountries()) {
                        if (l_country.getName().equalsIgnoreCase(l_CountryName)) {
                            l_country.setArmyCount(l_ArmyCount);
                            p_Player.setNoOfArmies(l_PlayerArmyAvailable - l_ArmyCount);
                            System.out.println(l_ArmyCount + "  armies are deploy to the country: " + l_CountryName);
                            break;
                        }
                    }

                    break;
                default:
                    System.out.println(l_OrderType + " is not a valid order type to execute");
            }
        }

    }

    /**
     * Game is only over when one player has captured all the countries ..
     *
     * @return "to be updated"
     */
    public boolean isGameOver() {
        int l_TotalCountries = getMap().getListOfCountries().size();
        for (Player l_player : getListOfPlayers()) {
            if (l_player.getListOfContinents().size() == l_TotalCountries) {
                System.out.println(l_player.getPlayerName() + " has controlled all the countries.");
                System.out.println("!!! GAME OVER !!!");
                return true;
            }
        }
        // TODO : since build-1 doesn't include attack so game will never be over as per this logic
        return false;
    }

    /**
     * @return winner
     */
    public Player getWinner() {
        return this.d_Winner;
    }

    /**
     * @param player player parameter is passed
     */
    public void setWinner(Player player) {
        this.d_Winner = player;
    }
}