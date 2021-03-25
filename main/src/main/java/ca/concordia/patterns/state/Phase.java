package ca.concordia.patterns.state;

import ca.concordia.gameengine.GameEngine;
import ca.concordia.patterns.observer.LogUtil;

/**
 * This class is an abstract class for defining states in the state pattern
 */
public abstract class Phase {
    public GameEngine d_ge;

    /**
     * This method is used to store the Game Engine object to run the game commands and process
     * @param p_ge Game engine object
     */
    public Phase(GameEngine p_ge) {
        this.d_ge = p_ge;
    }

    /**
     * Abstract method def  to showmap
     */
    abstract public void showMap();

    /**
     * Abstract method def  to edit continent map state behavior
     * @param p_Command edit continent command
     */
    abstract public void editContinent(String[] p_Command);

    /**
     *  Abstract method def  to edit country map state behavior
     * @param p_Command edit country command
     */
    abstract public void editCountry(String[] p_Command);

    /**
     * Abstract method def  to edit neighbour map state behavior
     * @param p_Command edit neighbour command
     */
    abstract public void editNeighbour(String[] p_Command);

    /**
     * Save map abstract method
     * @param p_Command save map command
     */
    abstract public void saveMap(String[] p_Command);

    /**
     * Abstract method def  to edit map
     * @param p_Command editmap command
     */
    abstract public void editMap(String[] p_Command);

    /**
     * Abstract method def to validate map
     * @param p_Command validate map command
     */
    abstract public void validateMap(String[] p_Command);

    /**
     * Abstract method def to loadmap
     * @param p_Command loadmap command
     */
    abstract public void loadMap(String[] p_Command);

    /**
     * Abstract method def to set players in game setup state behaviour
     * @param p_Command setplayers command
     */
    abstract public void setPlayers(String[] p_Command);

    /**
     * Abstract method def to assign countries in game setup state behaviour
     */
    abstract public void assignCountries();

    /**
     * Abstract method def to reinforcement state behavior in game setup state behaviour
     */
    abstract public void reinforce();

    /**
     * Abstract method def to create order in attack state behaviour
     */
    abstract public void createOrder();

    /**
     * Abstract method def to create order in attack state behaviour
     */
    abstract public void executeOrder();

    /**
     * Abstract method def to end state behaviour
     */
    abstract public void endGame();

    /**
     * Abstract method def to got ot next phase after the turns
     */
    abstract public void next();

    /**
     * Common method to all the states
     */
    public void printInvalidCommandMessage() {
        LogUtil.log("Invalid command in state" + this.getClass().getSimpleName());
    }
}