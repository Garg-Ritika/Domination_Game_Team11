package ca.concordia.patterns.state.play;

import ca.concordia.dao.Country;
import ca.concordia.dao.Graph;
import ca.concordia.dao.Map;
import ca.concordia.dao.Player;
import ca.concordia.gameengine.GameEngine;
import ca.concordia.patterns.observer.LogUtil;
import ca.concordia.patterns.state.Phase;
import ca.concordia.patterns.state.end.End;

/**
 * This class extends phase class
 * It executes showMap command
 * and for loadMap, editContinent, editCountry, editNeighbour,saveMap, editMap, validateMap, setPlayers, assignCountries,
 * reinforce, createorder, executeorder it shows invalid command message
 */
public class Play extends Phase {

    final int MINIMUM_PLAYER_COUNT = 3;

    /**
     * Constructor that contains GameEngine object as argument
     *
     * @param p_ge GameEngine object
     */
    public Play(GameEngine p_ge) {
        super(p_ge);
    }

    /**
     * loadMap method takes command as input and shows invalid commadn message
     *
     * @param p_Command loadMap command
     */
    @Override
    public void loadMap(String[] p_Command) {
        printInvalidCommandMessage();
    }

    /**
     * triggers on "showMap" command, this is a different function than MapEditors' showMap command
     * as it shows extra details like :
     * 1. show all countries
     * 2. show all continents
     * 3. Armies on each countries
     * 4. Ownership
     * 5. Connectivity in a way that enables game-play
     */
    @Override
    public void showMap() {
        LogUtil.log("show game command received ");
        Map l_Map = d_ge.getMap();
        if (l_Map != null) {
            Graph l_Graph = l_Map.getAdjacencyMatrix();
            LogUtil.log(l_Graph.toString());
        }
        for (Player l_Player : d_ge.getListOfPlayers()) {
            LogUtil.log("------------------------------------------------------------------------------------------------------------------------");
            LogUtil.log("PLAYER: " + l_Player.getPlayerName());
            LogUtil.log("with total army count of : " + l_Player.getNoOfArmies());
            LogUtil.log("has ownership of these countries: ");
            if (l_Player.getListOfCountries() != null) {
                for (Country l_Country : l_Player.getListOfCountries()) {
                    LogUtil.log("id: " + l_Country.getCountryID()
                            + " name: " + l_Country.getName()
                            + " army count: " + l_Country.getArmyCount()
                            + " belongs to continent " + l_Country.getContinentID());
                }
            }
        }
    }

    /**
     * editContinent method takes command as input and shows invalid command message to user
     *
     * @param p_Command editContinent command
     */
    @Override
    public void editContinent(String[] p_Command) {
        printInvalidCommandMessage();
    }

    /**
     * editCoountry method takes command as input and shows invalid command message to user
     *
     * @param p_Command editCoountry command
     */
    @Override
    public void editCountry(String[] p_Command) {
        printInvalidCommandMessage();
    }

    /**
     * editNeighbour method takes command as input and shows invalid command message to user
     *
     * @param p_Command editNeighbour command
     */
    @Override
    public void editNeighbour(String[] p_Command) {
        printInvalidCommandMessage();
    }

    /**
     * This method takes saveMap command as input and shows invalid command message to user
     *
     * @param p_Command saveMap command
     */

    @Override
    public void saveMap(String[] p_Command) {
        printInvalidCommandMessage();
    }

    /**
     * This method takes command as input and shows invalid command message to user
     *
     * @param p_Command editMap command
     */
    @Override
    public void editMap(String[] p_Command) {
        printInvalidCommandMessage();
    }

    /**
     * This method takes validateMap command as input and shows invalid command message to user
     *
     * @param p_Command evalidateMap command
     */
    @Override
    public void validateMap(String[] p_Command) {
        printInvalidCommandMessage();
    }

    /**
     * setPlayers method takes command as input and shows invalid command message to user
     *
     * @param p_Command setPlayers command
     */
    @Override
    public void setPlayers(String[] p_Command) {
        printInvalidCommandMessage();
    }


    /**
     * assignCountries method prints invalid command message to the user
     */
    @Override
    public void assignCountries() {
        printInvalidCommandMessage();
    }

    /**
     * reinforce method prints invalid command message to the user
     */
    @Override
    public void reinforce() {
        printInvalidCommandMessage();
    }

    /**
     * this method shows invalid command message to the user
     */
    @Override
    public void createOrder() {
        printInvalidCommandMessage();
    }

    /**
     * fortify method shows invalid command message to the user
     */
    @Override
    public void executeOrder() {
        printInvalidCommandMessage();
    }

    /**
     * This method changes the phase to endGame
     */
    @Override
    public void endGame() {
        d_ge.setPhase(new End(d_ge));
        d_ge.getPhase().endGame();
    }

    /**
     * This method moves to next phase
     */
    @Override
    public void next() {

    }

}
