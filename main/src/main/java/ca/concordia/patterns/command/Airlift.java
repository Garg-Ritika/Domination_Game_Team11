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
     * @param initiator player who runs the command
     * @param source    Source territory form which armies are to be moved
     * @param target    target territory to which armies are to be moved
     * @param num       no of armies to be moved
     */
    public Airlift(Player initiator, Territory source, Territory target, int num) {
        this.d_Initiator = initiator;
        this.d_Source = source;
        this.d_Target = target;
        this.d_NumToAirlift = num;
    }

    /**
     * execute method executes the Airlift card after validity check
     * move any number of army units from one of your territories to another territory, even if they are
     * not adjacent.
     * Here both the source and the target Territories are Receivers
     */
    public void execute() {
        System.out.println("advance execute ");

        if (valid()) {
            // if the source and the target belong to the same player
            // then just move the armies to the target Territory
            d_Target.setArmyCount(d_Target.getArmyCount() + d_NumToAirlift);
            d_Source.setArmyCount(d_Source.getArmyCount() - d_NumToAirlift);
            System.out.println(d_NumToAirlift + " has been moved");
        } else {
            System.out.println("invalid order");
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
            System.out.println("invalid order");
            return false;
        }
    }

    /**
     * This method will print all the commands that have been executed and
     * the log for which have been saved in log file
     */
    public void printOrder() {
        // TODO
        // print the order
        System.out.println();
        LogUtil.log("");
    }
}
