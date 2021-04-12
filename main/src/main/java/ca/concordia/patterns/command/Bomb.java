package ca.concordia.patterns.command;

import ca.concordia.dao.Player;
import ca.concordia.dao.Territory;
import ca.concordia.patterns.observer.LogUtil;

/**
 * bomb: destroy half of the armies located on an opponent’s territory that is adjacent to one of the current
 * player’s territories.
 * <p>
 * Command Syntax:
 * bomb countryID
 */

public class Bomb implements Order {
    Territory d_Target_territory;
    Player d_Initiator;


    /**
     * This is the constructor that takes player and target territory object as argument
     * encapsulating all necessary data to execute the command
     *
     * @param p_Initiator        player who runs the command
     * @param p_Target_territory the territory whose half armies are to be destroyed
     */

    public Bomb(Player p_Initiator, Territory p_Target_territory) {
        // encapsulate all necessary data to execute the command
        this.d_Target_territory = p_Target_territory;
        this.d_Initiator = p_Initiator;
    }


    /**
     * execute method executes the Bomb card after validity check
     * the target country loses half of their army units.
     * Here, the target Territory object is the Receiver
     */

    public void execute() {
        LogUtil.log("bomb execute ");

        if (valid()) {
            // behavior of the concrete command
            this.d_Target_territory.setArmyCount(this.d_Target_territory.getArmyCount() / 2);
            LogUtil.log(this.d_Target_territory + " half of the armies is destroyed");
        }
    }


    /**
     * This method will check if the given inputs are a valid input for Airlift Card
     *
     * @return true/false
     */

    public boolean valid() {
        if ((d_Target_territory.getOwner().getPlayerName().equalsIgnoreCase(d_Initiator.getPlayerName()) != true) && (d_Target_territory != null)) {
            // the target territory must not belong to the player that created the order
            return true;
        }
        //also check if target territory exists?
        LogUtil.log("invalid order");
        return false;
    }


    /**
     * This method will print all the commands that have been executed and
     * the log for which have been saved in log file
     */

    public void printOrder() {
        LogUtil.log("Bomb : d_Initiator : " + this.d_Initiator.getPlayerName() + " target " + this.d_Target_territory.getName());
    }
}
