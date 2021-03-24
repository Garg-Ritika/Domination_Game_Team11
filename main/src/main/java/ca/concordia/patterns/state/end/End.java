package ca.concordia.patterns.state.end;

import ca.concordia.gameengine.GameEngine;
import ca.concordia.patterns.observer.LogUtil;
import ca.concordia.patterns.state.Phase;

public class End extends Phase {

    public End(GameEngine p_ge) {
        super(p_ge);
    }

    @Override
    public void loadMap(String[] p_Command) {
        printInvalidCommandMessage();
    }

    @Override
    public void showMap() {
        printInvalidCommandMessage();
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
    public void createOrder() {
        printInvalidCommandMessage();
    }

    @Override
    public void executeOrder() {
        printInvalidCommandMessage();
    }

    @Override
    public void endGame() {
        LogUtil.log("Game has ended ! ");
        LogUtil.log("Game has ended ! ");
        System.exit(0);
    }

    @Override
    public void next() {

    }
}
