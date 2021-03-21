package ca.concordia.patterns.command;

import ca.concordia.dao.Player;
import ca.concordia.dao.Territory;
import ca.concordia.patterns.observer.LogUtil;

public class Airlift implements Order {

    Territory source;
    Territory target;
    Player initiator;
    int num_to_advance;

    //TODO : revise this code, it requires airlift card ..
    public Airlift(Player initiator, Territory source, Territory target, int num) {
        // encapsulate all necessary data to execute the command
        this.initiator = initiator;
        this.source = source;
        this.target = target;
        this.num_to_advance = num;
    }


    public void execute() {
        // Here both the source and the target Territories are Receivers
        System.out.println("advance execute ");

        if (valid()) {
            if (target.getOwner().equalsIgnoreCase(initiator.getPlayerName())) {
                // if the source and the target belong to the same player
                // then just move the armies to the target Territory
            } else {
                // implement a battle
                if (target.d_ArmyCount < 0) {
                    // move surviving attacking armies to the target country
                    // transfer ownership of the conquered country
                }
            }
        }
    }

    public boolean valid() {
        //TODO what is the valid condition
        boolean valid_condition = true;
        if (valid_condition) {
            return true;
        }
        System.out.println("invalid order");
        return false;
    }

    public void printOrder() {
        // print the order
        LogUtil.log("");
    }
}
