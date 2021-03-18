package ca.concordia.patterns.command;

import ca.concordia.dao.Player;
import ca.concordia.dao.Territory;

//Concrete Command for Command pattern
public class Deploy implements Order {

    Territory target_territory;
    int to_deploy;
    Player initiator;


    public Deploy(Player initiator, Territory target_territory, int to_deploy) {
        // encapsulate all necessary data to execute the command
        this.target_territory = target_territory;
        this.initiator = initiator;
        this.to_deploy = to_deploy;
    }

    public void execute() {
        System.out.println("deploy execute ");
        // Here, the target Territory object is the Receiver

        if (valid()) {
            // behavior of the concrete command
            this.target_territory.d_ArmyCount += to_deploy;
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
        System.out.println("Deploy order issued by player " + this.initiator.getPlayerName());
        System.out.println("Deploy " + this.to_deploy + " to " + this.target_territory.getOwner());
    }
}
