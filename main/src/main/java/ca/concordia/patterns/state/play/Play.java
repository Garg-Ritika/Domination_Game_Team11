package ca.concordia.patterns.state.play;

import ca.concordia.dao.Country;
import ca.concordia.dao.Graph;
import ca.concordia.dao.Map;
import ca.concordia.dao.Player;
import ca.concordia.gameengine.GameEngine;
import ca.concordia.patterns.state.Phase;
import ca.concordia.patterns.state.end.End;

public class Play extends Phase {

    final int MINIMUM_PLAYER_COUNT = 3;

    public Play(GameEngine p_ge) {
        super(p_ge);
    }

    @Override
    public void loadMap(String[] p_Command) {
        printInvalidCommandMessage();
    }

    /**
     * triggers on "showmap" command, this is a different function than MapEditors' showmap commad
     * as it shows extra details like :
     * 1. show all countries
     * 2. show all continents
     * 3. Armies on each countries
     * 4. Ownership
     * 5. Connectivity in a way that enables game-play
     */
    @Override
    public void showMap() {
        System.out.println("show game command received ");
        Map l_Map = d_ge.getMap();
        if (l_Map != null) {
            Graph l_Graph = l_Map.getAdjacencyMatrix();
            System.out.println(l_Graph.toString());
        }
        for (Player l_Player : d_ge.getListOfPlayers()) {
            System.out.println("------------------------------------------------------------------------------------------------------------------------");
            System.out.println("PLAYER: " + l_Player.getPlayerName());
            System.out.println("with total army count of : " + l_Player.getNoOfArmies());
            System.out.println("has ownership of these countries: ");
            if (l_Player.getListOfCountries() != null) {
                for (Country l_Country : l_Player.getListOfCountries()) {
                    System.out.println("id: " + l_Country.getCountryID()
                            + " name: " + l_Country.getName()
                            + " army count: " + l_Country.getArmyCount()
                            + " belongs to continent " + l_Country.getContinentID());
                }
            }
        }
    }

    @Override
    public void editContinent(String[] p_Command) {
        printInvalidCommandMessage();
    }

    @Override
    public void editCountry(String[] p_Command) {
        printInvalidCommandMessage();
    }

    @Override
    public void editNeighbour(String[] p_Command) {
        printInvalidCommandMessage();
    }

    @Override
    public void saveMap(String[] p_Command) {
        printInvalidCommandMessage();
    }

    @Override
    public void editMap(String[] p_Command) {
        printInvalidCommandMessage();
    }

    @Override
    public void validateMap(String[] p_Command) {
        printInvalidCommandMessage();
    }

    @Override
    public void setPlayers(String[] p_Command) {
        printInvalidCommandMessage();
    }

    @Override
    public void assignCountries() {
        printInvalidCommandMessage();
    }

    @Override
    public void reinforce() {
        printInvalidCommandMessage();
    }

    @Override
    public void attack() {
        printInvalidCommandMessage();
    }

    @Override
    public void fortify() {
        printInvalidCommandMessage();
    }

    @Override
    public void endGame() {
        d_ge.setPhase(new End(d_ge));
        d_ge.getPhase().endGame();
    }

    @Override
    public void next() {

    }


}
