package ca.concordia.patterns.state.end;

import ca.concordia.gameengine.GameEngine;
import ca.concordia.patterns.state.Phase;

public class End extends Phase {

    public End(GameEngine p_ge) {
        super(p_ge);
    }

    @Override
    public void loadMap(String[] p_Command) {

    }

    @Override
    public void showMap() {

    }

    @Override
    public void editContinent(String[] p_Command) {

    }

    @Override
    public void editCountry(String[] p_Command) {

    }

    @Override
    public void editNeighbour(String[] p_Command) {

    }

    @Override
    public void saveMap(String[] p_Command) {

    }

    @Override
    public void editMap(String[] p_Command) {

    }

    @Override
    public void validateMap(String[] p_Command) {

    }

    @Override
    public void setPlayers(String[] p_Command) {

    }

    @Override
    public void assignCountries() {

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
        System.out.println("Game has ended ! ");
        System.exit(0);
    }

    @Override
    public void next() {

    }
}
