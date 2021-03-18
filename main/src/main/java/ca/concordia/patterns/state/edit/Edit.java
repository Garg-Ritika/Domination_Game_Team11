package ca.concordia.patterns.state.edit;

import ca.concordia.gameengine.GameEngine;
import ca.concordia.mapworks.MapEditor;
import ca.concordia.patterns.state.Phase;

public abstract class Edit extends Phase {
    public Edit(GameEngine p_ge) {
        super(p_ge);
    }

    public void showMap() {
        System.out.println("showmap command received ...");
        MapEditor.getInstance().showMap();
    }

    public void setPlayers(String[] p_Command) {
        printInvalidCommandMessage();
    }

    public void assignCountries() {
        printInvalidCommandMessage();
    }

    public void reinforce() {
        printInvalidCommandMessage();
    }

    public void attack() {
        printInvalidCommandMessage();
    }

    public void fortify() {
        printInvalidCommandMessage();
    }

    public void endGame() {
        printInvalidCommandMessage();
    }
}
