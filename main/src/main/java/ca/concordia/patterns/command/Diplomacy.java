package ca.concordia.patterns.command;

import ca.concordia.dao.Player;
import ca.concordia.patterns.observer.LogUtil;

/**
 * diplomacy: until the end of the turn, you and the target player cannot attack each other.
 * <p>
 * Command Syntax:
 * negotiate playerID
 */
public class Diplomacy implements Order {

    Player d_Initiator;
    Player d_NegotiatePlayerId;

    /**
     * this is the constructor that takes player and target player id as arguments
     * encapsulating all necessary data to execute the command
     *
     * @param p_Initiator player running the command
     * @param p_PlayerId  plauer with which negotiation needs to be done
     */
    public Diplomacy(Player p_Initiator, Player p_PlayerId) {
        this.d_Initiator = p_Initiator;
        this.d_NegotiatePlayerId = p_PlayerId;
    }

    /**
     * execute method executes the diplomacy card after validity check
     * Here, the target Territory object is the Receiver
     */
    public void execute() {
        LogUtil.log("diplomacy execute ");
        if (valid()) {
            d_Initiator.setIsNegotiatedPlayer(true);
            d_NegotiatePlayerId.setIsNegotiatedPlayer(true);
            LogUtil.log(d_Initiator + " and " + d_NegotiatePlayerId + " are refrained from attack until next turn");
        }
    }

    /**
     * This method will check if the given inputs are a valid input for Airlift Card
     *
     * @return true/false
     */
    public boolean valid() {
        if (d_NegotiatePlayerId.getPlayerName() != null) {
            // target Player Id must exist
            return true;
        }
        LogUtil.log("invalid order");
        return false;
    }

    /**
     * This method will print all the commands that have been executed and
     * the log for which have been saved in log file
     */
    public void printOrder() {
        LogUtil.log("Airlift: d_Initiator: " + this.d_Initiator
                + " negotiater-player: " + this.d_NegotiatePlayerId);
    }
}
