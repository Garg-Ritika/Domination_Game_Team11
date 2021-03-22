package ca.concordia.patterns.state.edit;

import ca.concordia.gameengine.GameEngine;
import ca.concordia.mapworks.MapEditor;
import ca.concordia.patterns.state.Phase;

/**
 * This is the abstract class that extends phase class
 * in this phase only showmap command executes, rest all prints the invalid command message
 */
public abstract class Edit extends Phase {

    /**
     * This is the constructor that takes GameEngine class object as argument
     * @param p_ge GameEngine class object
     */
    public Edit(GameEngine p_ge) {
        super(p_ge);
    }

    /**
     * This method shows Map(that contains all the details of countries, continents) to the user
     */
    public void showMap() {
        System.out.println("showmap command received ...");
        MapEditor.getInstance().showMap();
    }

    /**
     * This method prints invalid command message if user wants to set Players
     * @param p_Command setPlayers command
     */
    public void setPlayers(String[] p_Command) {
        printInvalidCommandMessage();
    }

    /**
     * This method prints invalid command message if user wants to assign countries
     */
    public void assignCountries() {
        printInvalidCommandMessage();
    }

    /**
     * This method prints invalid command message if user wants to use reinforce command
     */
    public void reinforce() {
        printInvalidCommandMessage();
    }

    /**
     * This method prints invalid command message if user wants to attack
     */
    public void attack() {
        printInvalidCommandMessage();
    }

    /**
     * This method prints invalid command message if user wants to apply fortify command
     */
    public void fortify() {
        printInvalidCommandMessage();
    }

    /**
     * This method prints invalid command message if user wants to endGame
     */
    public void endGame() {
        printInvalidCommandMessage();
    }
}
