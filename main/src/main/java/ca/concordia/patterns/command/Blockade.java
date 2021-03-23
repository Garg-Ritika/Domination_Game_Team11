package ca.concordia.patterns.command;

import ca.concordia.dao.Player;
import ca.concordia.dao.Territory;
import ca.concordia.patterns.observer.LogUtil;

/**
 * blockade: triple the number of armies on one of the current player’s territories and make it a neutral territory.
 * <p>
 * Command Syntax:
 * blockade countryID
 */
public class Blockade implements Order {

    Territory d_TargetTerritory;
    Player d_Initiator;

    /**
     * This is the constructor that takes player and target territory object as argument
     * encapsulating all necessary data to execute the command
     *
     * @param p_Initiator       player who runs the command
     * @param p_TargetTerritory the territory which is to be neutralised
     */
    public Blockade(Player p_Initiator, Territory p_TargetTerritory) {
        this.d_TargetTerritory = p_TargetTerritory;
        this.d_Initiator = p_Initiator;
    }

    /**
     * execute method executes the Blockade card after validity check
     * where the target territory’s army units count is tripled, and the territory becomes neutral
     * Here, the target Territory object is the Receiver
     */
    public void execute() {
        System.out.println("blockade execute ");
        // Here, the target Territory object is the Receiver

        if (valid()) {
            // behavior of the concrete command
            Player neutralPlayer = new Player("Neutral", Integer.MAX_VALUE);
            this.d_TargetTerritory.setArmyCount(this.d_TargetTerritory.getArmyCount() * 3);
            //how to make this territory neutral?
            this.d_TargetTerritory.setOwner(neutralPlayer);
            this.d_Initiator.getListOfCountries().remove(this.d_TargetTerritory);
        }
    }

    /**
     * This method will check if the given inputs are a valid input for Advance Card
     *
     * @return true/false
     */
    public boolean valid() {
        if ((d_TargetTerritory.getOwner().getPlayerName().equalsIgnoreCase(d_Initiator.getPlayerName())) && (d_TargetTerritory != null)) {
            // the target territory must belong to the player that created the order
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
