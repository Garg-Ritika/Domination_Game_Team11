package ca.concordia.patterns.state.play;

import ca.concordia.gameengine.GameEngine;

/**
 * this class extends play class
 * State of the state pattern
 *
 */
public abstract class MainPlay extends Play {

    /**
     * constructor that contains GameEngine object
     * @param p_ge GameEngine object
     */
    public MainPlay(GameEngine p_ge) {
        super(p_ge);
    }

    /**
     * This loadMap method shows invalid command message to user
     */
    public void loadMap() {
        this.printInvalidCommandMessage();
    }

    /**
     * setPlayers method shows invalid command messsage to user
     */
    public void setPlayers() {
        this.printInvalidCommandMessage();
    }

    /**
     * This method shows, assignCountries command cannot be executed in MainPlay
     * It shows invalid command message to user
     */
    public void assignCountries() {
        this.printInvalidCommandMessage();
    }
}
