package ca.concordia.patterns.command;

import ca.concordia.dao.Player;
import ca.concordia.patterns.observer.LogUtil;

public class Diplomacy implements Order {

    Player initiator;
    Player negotiatePlayerId;

    // TODO : Requires Diplomacy Card, revise this
    public Diplomacy(Player initiator, Player playerId) {
        // encapsulate all necessary data to execute the command
        this.initiator = initiator;
        this.negotiatePlayerId = playerId;
    }

    public void execute() {
        System.out.println("deploy execute ");
        // Here, the target Territory object is the Receiver

        if (valid()) {
            // behavior of the concrete command
            // how to negotiate player id?
        }
    }

    public boolean valid() {
        if (negotiatePlayerId.getPlayerName() !=null) {
            // target Player Id must exist
            return true;
        }
        System.out.println("invalid order");
        return false;
    }

    public void printOrder() {
        //TODO
        System.out.println();
        LogUtil.log("");
    }
}
