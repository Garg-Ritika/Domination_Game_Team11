package ca.concordia.patterns.command;

import ca.concordia.dao.Player;
import ca.concordia.dao.Territory;
import ca.concordia.patterns.observer.LogUtil;

public class Diplomacy implements Order {
    Territory target_territory;
    int to_bomb;
    Player initiator;

    // TODO : Requires Diplomacy Card, revise this
    public Diplomacy(Player initiator, Territory target_territory, int to_bomb) {
        // encapsulate all necessary data to execute the command
        this.target_territory = target_territory;
        this.initiator = initiator;
        this.to_bomb = to_bomb;
    }

    public void execute() {
        System.out.println("deploy execute ");
        // Here, the target Territory object is the Receiver

        if (valid()) {
            // behavior of the concrete command
            this.target_territory.d_ArmyCount -= to_bomb;
        }
    }

    public boolean valid() {
        if (target_territory.getOwner().equalsIgnoreCase(initiator.getPlayerName())) {
            // the target territory must belong to the player that created the order
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
