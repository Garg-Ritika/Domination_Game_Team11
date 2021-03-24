package ca.concordia.patterns.state;

import ca.concordia.gameengine.GameEngine;
import ca.concordia.patterns.observer.LogUtil;

//State in the State pattern
public abstract class Phase {
    public GameEngine d_ge;

    public Phase(GameEngine p_ge) {
        this.d_ge = p_ge;
    }

    abstract public void showMap();

    abstract public void editContinent(String[] p_Command);

    // edit map state behavior
    abstract public void editCountry(String[] p_Command);

    // edit map state behavior
    abstract public void editNeighbour(String[] p_Command);

    abstract public void saveMap(String[] p_Command);

    abstract public void editMap(String[] p_Command);

    abstract public void validateMap(String[] p_Command);

    // general behavior
    abstract public void loadMap(String[] p_Command);

    // play state behavior
    // game setup state behavior
    abstract public void setPlayers(String[] p_Command);

    abstract public void assignCountries();

    // reinforcement state behavior
    abstract public void reinforce();

    // attack state behavior
    abstract public void createOrder();

    // fortify state behavior
    abstract public void executeOrder();

    // end state behavior
    abstract public void endGame();

    // go to next phase
    abstract public void next();

    // methods common to all states
    public void printInvalidCommandMessage() {
        LogUtil.log("Invalid command in state" + this.getClass().getSimpleName());
    }
}