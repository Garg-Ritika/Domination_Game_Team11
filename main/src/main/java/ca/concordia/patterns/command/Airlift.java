package ca.concordia.patterns.command;

import ca.concordia.dao.Player;
import ca.concordia.dao.Territory;
import ca.concordia.patterns.observer.LogUtil;


/**
 * Airlift: advance some armies from one of the current player’s territories to any another territory.
 * <p>
 * Command Syntax:
 * airlift sourcecountryID targetcountryID numarmies
 */
public class Airlift implements Order {

    Territory d_Source;
    Territory d_Target;
    Player d_Initiator;
    int d_NumToAirlift;

    /**
     * This is the constructor that takes player, source and target territory object and no of armies as argument
     * encapsulating all necessary data to execute the command
     *
     * @param p_Initiator player who runs the command
     * @param p_Source    Source territory form which armies are to be moved
     * @param p_Target    target territory to which armies are to be moved
     * @param p_Num       no of armies to be moved
     */
    public Airlift(Player p_Initiator, Territory p_Source, Territory p_Target, int p_Num) {
        this.d_Initiator = p_Initiator;
        this.d_Source = p_Source;
        this.d_Target = p_Target;
        this.d_NumToAirlift = p_Num;
    }

    /**
     * execute method executes the Airlift card after validity check
     * move any number of army units from one of your territories to another territory, even if they are
     * not adjacent.
     * Here both the source and the target Territories are Receivers
     */
    public void execute() {
        LogUtil.log("advance execute ");

        if (valid()) {
            // if the source and the target belong to the same player
            // then just move the armies to the target Territory
            d_Target.setArmyCount(d_Target.getArmyCount() + d_NumToAirlift);
            d_Source.setArmyCount(d_Source.getArmyCount() - d_NumToAirlift);
            LogUtil.log(d_NumToAirlift + " has been moved");
        } else {
            LogUtil.log("invalid order");
        }
    }

    /**
     * This method will check if the given inputs are a valid input for Airlift Card
     *
     * @return true/false
     */
    public boolean valid() {
        //TODO what is the valid condition
        if ((d_Source.getArmyCount() >= d_NumToAirlift)
                && (d_Target.getOwner().getPlayerName().equalsIgnoreCase(d_Initiator.getPlayerName()))
                && (d_Source != null)
                && (d_Target != null)) {
            return true;
        } else {
            LogUtil.log("invalid order");
            return false;
        }
    }

    /**
     * This method will print all the commands that have been executed and
     * the log for which have been saved in log file
     */
    public void printOrder() {

        LogUtil.log("Airlift: d_Initiator: " + this.d_Initiator
                + " source: " + this.d_Source
                + " target: " + this.d_Target
                + " number to airlift: " + d_NumToAirlift);
    }
}
