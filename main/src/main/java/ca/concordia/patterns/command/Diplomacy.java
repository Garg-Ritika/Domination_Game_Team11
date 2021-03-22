package ca.concordia.patterns.command;

import ca.concordia.dao.Player;
import ca.concordia.patterns.observer.LogUtil;

/**
 * diplomacy: until the end of the turn, you and the target player cannot attack each other.
 *
 * Command Syntax:
 * negotiate playerID
 */
public class Diplomacy implements Order {

    Player initiator;
    Player negotiatePlayerId;

    // TODO : Requires Diplomacy Card, revise this
    public Diplomacy(Player initiator, Player playerId) {
        // encapsulate all necessary data to execute the command
        this.initiator = initiator;
        this.negotiatePlayerId = playerId;
    }

    /**
     * execute method executes the diplomacy card after validity check
     */
    public void execute() {
        System.out.println("diplomacy execute ");
        // Here, the target Territory object is the Receiver

        if (valid()) {
            // behavior of the concrete command
            // how to negotiate player id?
        }
    }

    /**
     * This method will check if the given inputs are a valid input for Airlift Card
     *
     * @return true/false
     */
    public boolean valid() {
        if (negotiatePlayerId.getPlayerName() !=null) {
            // target Player Id must exist
            return true;
        }
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
