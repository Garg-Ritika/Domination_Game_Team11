package ca.concordia.patterns.command;

import ca.concordia.dao.Player;
import ca.concordia.dao.Territory;
import ca.concordia.patterns.observer.LogUtil;

//   ConcreteCommand of the the Command pattern.
public class Advance implements Order {

    Territory source;
    Territory target;
    Player initiator;
    int num_to_advance;


    public Advance(Player initiator, Territory source, Territory target, int num) {
        // encapsulate all necessary data to execute the command
        this.initiator = initiator;
        this.source = source;
        this.target = target;
        this.num_to_advance = num;
    }


    public void execute() {
        // Here both the source and the target Territories are Receivers
        System.out.println("advance execute ");

        //Advance order command:
        // <advance countrynamefrom countynameto numarmies>
        // what is the algorithm here ?
        // reduce the numarimes from countrynamefrom
        // attack the existing armies in countrynameto
        // if winner: update owner to initiator
        


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
        // check if countrynamefrom exists
            // check if countryname has the same owner as the initiator of this commad
            // check if countryname from has >= numarmies being advanced..
            // check if countrynameto is there
        // check if countrynameto exists

        boolean valid_condition = true;
        if (valid_condition) {
            return true;
        }
        System.out.println("invalid order");
        return false;
    }

    public void printOrder() {
        // print the order
        System.out.println();
        LogUtil.log("");
    }
}
