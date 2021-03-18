package ca.concordia.patterns.state.edit;

import ca.concordia.gameengine.GameEngine;

public class Preload extends Edit {

    public Preload(GameEngine p_ge) {
        super(p_ge);
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
        d_ge.setPhase(new PostLoad(d_ge));
    }

    @Override
    public void validateMap(String[] p_Command) {
        printInvalidCommandMessage();
    }

    @Override
    public void loadMap(String[] p_Command) {
        printInvalidCommandMessage();

    }

    @Override
    public void setPlayers(String[] p_Command) {
        printInvalidCommandMessage();
    }

    public void next() {
        System.out.println("must load map");
    }

}
