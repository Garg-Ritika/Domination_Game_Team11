package ca.concordia.patterns.command;

import ca.concordia.dao.Player;
import ca.concordia.dao.Territory;
import ca.concordia.patterns.observer.LogUtil;

public class Bomb implements Order {
    Territory target_territory;
    Player initiator;

    // TODO : Requires Bomb Card, revise this
    public Bomb(Player initiator, Territory target_territory) {
        // encapsulate all necessary data to execute the command
        this.target_territory = target_territory;
        this.initiator = initiator;
    }

    public void execute() {
        System.out.println("deploy execute ");
        // Here, the target Territory object is the Receiver

        if (valid()) {
            // behavior of the concrete command
            this.target_territory.d_ArmyCount = this.target_territory.d_ArmyCount/2;
        }
    }

    public boolean valid() {
        if ((target_territory.getOwner().equalsIgnoreCase(initiator.getPlayerName()) !=true) && (target_territory !=null) ){
            // the target territory must not belong to the player that created the order
            return true;
        }
        //also check if target territory exists?
        System.out.println("invalid order");
        return false;
    }

    public void printOrder() {
        //TODO
        System.out.println();
        LogUtil.log("");
    }
}
