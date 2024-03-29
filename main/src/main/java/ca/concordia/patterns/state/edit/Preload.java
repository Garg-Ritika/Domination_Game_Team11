package ca.concordia.patterns.state.edit;

import ca.concordia.gameengine.GameEngine;
import ca.concordia.patterns.observer.LogUtil;

/**
 * This is the preload class that extends Edit Class
 * In this, only editMap command works, rest all shows invalid command message to the user
 */
public class Preload extends Edit {

    /**
     * This is the constructor that takes GameEngine class object as argument
     *
     * @param p_ge GameEngine class object
     */

    public Preload(GameEngine p_ge) {
        super(p_ge);
    }

    /**
     * This method takes edit continent command and shows invalid command message to user
     *
     * @param p_Command editContinent command
     */
    @Override
    public void editContinent(String[] p_Command) {
        printInvalidCommandMessage();
    }

    /**
     * This method takes edit Country command and shows invalid command message to user
     *
     * @param p_Command editCountry command
     */
    @Override
    public void editCountry(String[] p_Command) {
        printInvalidCommandMessage();
    }

    /**
     * This method takes edit neighbour command and shows invalid command message to user
     *
     * @param p_Command editNeighbour command
     */
    @Override
    public void editNeighbour(String[] p_Command) {
        printInvalidCommandMessage();
    }

    /**
     * This method takes save map command and shows invalid command message to user
     *
     * @param p_Command saveMap command
     */
    @Override
    public void saveMap(String[] p_Command) {
        printInvalidCommandMessage();
    }

    /**
     * This method takes edit map command from user and allows user to edit the map
     * and changes the phase to PostLoad
     *
     * @param p_Command editMap command
     */
    @Override
    public void editMap(String[] p_Command) {
        PostLoad postLoad = new PostLoad(d_ge);
        d_ge.setPhase(postLoad);
        postLoad.editMap(p_Command);
    }

    /**
     * This method takes validate map command and shows invalid command message to the user
     * This command is executed in the PreLoad phase, because map can not be validated before loading
     *
     * @param p_Command validateMap command
     */
    @Override
    public void validateMap(String[] p_Command) {
        printInvalidCommandMessage();
    }

    /**
     * This method takes load map command and shows invalid command message to user
     *
     * @param p_Command loadMap command
     */
    @Override
    public void loadMap(String[] p_Command) {
        printInvalidCommandMessage();

    }

    /**
     * This method takes set Players command and shows invalid command message to user
     *
     * @param p_Command setPlayers command
     */
    @Override
    public void setPlayers(String[] p_Command) {
        printInvalidCommandMessage();
    }

    /**
     * This method prints out message to user
     */
    public void next() {
        LogUtil.log("must load map");
    }

}
