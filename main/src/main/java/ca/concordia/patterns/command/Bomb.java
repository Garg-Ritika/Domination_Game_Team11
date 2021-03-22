package ca.concordia.patterns.command;

import ca.concordia.dao.Player;
import ca.concordia.dao.Territory;
import ca.concordia.patterns.observer.LogUtil;

/**
 * bomb: destroy half of the armies located on an opponent’s territory that is adjacent to one of the current
 * player’s territories.
 *
 * Command Syntax:
 * bomb countryID
 */
public class Bomb implements Order {
    Territory target_territory;
    Player initiator;

    // TODO : Requires Bomb Card, revise this
    public Bomb(Player initiator, Territory target_territory) {
        // encapsulate all necessary data to execute the command
        this.target_territory = target_territory;
        this.initiator = initiator;
    }

    /**
     * execute method executes the Bomb card after validity check
     */
    public void execute() {
        System.out.println("bomb execute ");
        // Here, the target Territory object is the Receiver

        if (valid()) {
            // behavior of the concrete command
            this.target_territory.setArmyCount(this.target_territory.getArmyCount()/2);
        }
    }

    /**
     * This method will check if the given inputs are a valid input for Airlift Card
     *
     * @return true/false
     */
    public boolean valid() {
        if ((target_territory.getOwner().equalsIgnoreCase(initiator.getPlayerName()) !=true) && (target_territory !=null) ){
            // the target territory must not belong to the player that created the order
            return true;
        }
        //also check if target territory exists?
        System.out.println("invalid order");
        return false;
    }

    /**
     * This method will print all the commands that have been executed and
     * the log for which have been saved in log file
     */
    public void printOrder() {
        //TODO
        System.out.println();
        LogUtil.log("");
    }
}
