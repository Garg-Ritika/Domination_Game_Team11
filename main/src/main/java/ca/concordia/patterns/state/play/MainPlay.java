package ca.concordia.patterns.state.play;

import ca.concordia.gameengine.GameEngine;

// State of the state pattern
public abstract class MainPlay extends Play {

    public MainPlay(GameEngine p_ge) {
        super(p_ge);
    }

    public void loadMap() {
        this.printInvalidCommandMessage();
    }

    public void setPlayers() {
        this.printInvalidCommandMessage();
    }

    public void assignCountries() {
        this.printInvalidCommandMessage();
    }
}
