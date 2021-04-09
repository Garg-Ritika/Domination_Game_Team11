package ca.concordia.patterns.command;

import ca.concordia.dao.Player;
import ca.concordia.dao.Territory;
import ca.concordia.patterns.observer.LogUtil;


/**
 * deploy: place some armies on one of the current player’s territories.
 * <p>
 * Command Syntax:
 * deploy countryID numarmies
 */

public class Deploy implements Order {

    Territory d_Target_territory;
    int d_To_deploy;
    Player d_Initiator;


    /**
     * This is the constructor that takes player and target territory object and no of armies ot be deployed as argument
     * encapsulating all necessary data to execute the command
     *
     * @param p_Initiator        player who runs the command
     * @param p_Target_territory the territory on which armies are to be deployed
     * @param p_To_deploy        no of armies to be deployed
     */

    public Deploy(Player p_Initiator, Territory p_Target_territory, int p_To_deploy) {
        this.d_Target_territory = p_Target_territory;
        this.d_Initiator = p_Initiator;
        this.d_To_deploy = p_To_deploy;
    }


    /**
     * execute method executes the deploy card after validity check
     * place some armies on one of the current player’s territories
     * Here, the target Territory object is the Receiver
     */

    public void execute() {
        LogUtil.log("deploy execute ");
        if (valid()) {
            // behavior of the concrete command
            int l_ExistingArmy = this.d_Target_territory.getArmyCount();
            this.d_Initiator.setNoOfArmies(this.d_Initiator.getNoOfArmies() - d_To_deploy);
            this.d_Target_territory.setArmyCount(l_ExistingArmy + d_To_deploy);
            LogUtil.log("deploying " + d_To_deploy + " to " + d_Target_territory.getName());
        }
    }


    /**
     * This method will check if the given inputs are a valid input for Airlift Card
     *
     * @return true/false
     */

    public boolean valid() {
        LogUtil.log("--> deploy valid ");

        if (d_Target_territory.getOwner().getPlayerName().equalsIgnoreCase(d_Initiator.getPlayerName())) {
            // the target territory must belong to the player that created the order
            return true;
        }
        LogUtil.log("invalid deploy order");
        return false;
    }

    /**
     * This method will print all the commands that have been executed and
     * the log for which have been saved in log file
     */

    public void printOrder() {

        LogUtil.log("Deploy order issued by player " + this.d_Initiator.getPlayerName());
        LogUtil.log("Deploy " + this.d_To_deploy + " to " + this.d_Target_territory.getName());
    }
}

