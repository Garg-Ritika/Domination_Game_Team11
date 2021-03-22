package ca.concordia.patterns.command;

import ca.concordia.dao.Country;
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
                this.target.d_ArmyCount+=num_to_advance;
            } else {
                // implement a battle
//                num_to_advance 25
//                target.d_ArmyCount
                this.source.d_ArmyCount-=num_to_advance;
                int defending_armies= (int) (target.d_ArmyCount*0.7); //7
                int attacking_armies= (int)(num_to_advance*0.6); //15
                num_to_advance-=defending_armies;  //30-7=23
                target.d_ArmyCount-=attacking_armies; //10-18=-8

                if (target.d_ArmyCount < 0) {
                    // move surviving attacking armies to the target country
                    // transfer ownership of the conquered country
                    target.setOwner(initiator.getPlayerName());
                    target.d_ArmyCount=num_to_advance;

                }
                else{
                    this.source.d_ArmyCount+=num_to_advance;
                }
            }

        }
    }

    public boolean valid() {
        boolean valid_condition=false;
        //TODO what is the valid condition
        // check if countrynamefrom exists
            // check if countryname has the same owner as the initiator of this commad
            // check if countryname from has >= numarmies being advanced..
            // check if countrynameto is there
        // check if countrynameto exists
        if((initiator.getListOfCountries().contains(source))&&(source.d_ArmyCount>=num_to_advance) && (source!=null) && (target!=null))        {
            valid_condition = true;
        }

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
