package ca.concordia.patterns.command;

import ca.concordia.dao.Player;
import ca.concordia.dao.Territory;
import ca.concordia.patterns.observer.LogUtil;

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

    /**
     * execute method executes the deploy card after validity check
     */
    public void execute() {
        System.out.println("deploy execute ");
        // Here, the target Territory object is the Receiver

        if (valid()) {
            // behavior of the concrete command
            this.target_territory.d_ArmyCount += to_deploy;
        }
    }

    /**
     * This method will check if the given inputs are a valid input for Airlift Card
     *
     * @return true/false
     */
    public boolean valid() {
        if (target_territory.getOwner().equalsIgnoreCase(initiator.getPlayerName())) {
            // the target territory must belong to the player that created the order
            return true;
        }
        LogUtil.log("invalid deploy order");
        System.out.println("invalid  deploy order");
        return false;
    }

    /**
     * This method will print all the commands that have been executed and
     * the log for which have been saved in log file
     */
    public void printOrder() {
        System.out.println("Deploy order issued by player " + this.initiator.getPlayerName());
        System.out.println("Deploy " + this.to_deploy + " to " + this.target_territory.getOwner());

        LogUtil.log("Deploy order issued by player " + this.initiator.getPlayerName());
        LogUtil.log("Deploy " + this.to_deploy + " to " + this.target_territory.getOwner());
    }
}
