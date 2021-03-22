package ca.concordia.patterns.command;

import ca.concordia.dao.Player;
import ca.concordia.dao.Territory;
import ca.concordia.patterns.observer.LogUtil;
import ca.concordia.patterns.state.play.Play;

//   ConcreteCommand of the the Command pattern.

/**
 * Move some armies from one of the current player’s territories (source) to an adjacent territory
 * (target).
 * If the target territory belongs to the current player, the armies are moved to the target
 * territory.
 * If the target territory belongs to another player, an attack happens between the two
 * territories.
 *
 * Command Syntax:
 * Advance order command: advance countrynamefrom countynameto numarmies
 *
 * This class implements interface "Order", so as to use
 * execute(), valid(), printOrder() methods
 */
public class Advance implements Order {

    Territory d_Source;
    Territory d_Target;
    Player d_Initiator;
    int d_NumToAdvance;

    public Advance(Player p_Initiator, Territory p_Source, Territory p_Target, int p_Num) {
        // encapsulate all necessary data to execute the command
        this.d_Initiator = p_Initiator;
        this.d_Source = p_Source;
        this.d_Target = p_Target;
        this.d_NumToAdvance = p_Num;
    }

    /**
     * execute method executes the Advance card after validity check
     */
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
            if (d_Target.getOwner().equalsIgnoreCase(d_Initiator.getPlayerName())) {
                // if the source and the target belong to the same player
                // then just move the armies to the target Territory

                this.d_Target.setArmyCount(this.d_Target.getArmyCount() + d_NumToAdvance);
            } else {
                // implement a battle
//                num_to_advance 25
//                target.d_ArmyCount
                this.d_Source.setArmyCount(this.d_Source.getArmyCount() - d_NumToAdvance);
                int l_DefendingArmies = (int) (d_Target.getArmyCount()*0.7); //7
                int l_AttackingArmies = (int) (d_NumToAdvance *0.6); //15
                d_NumToAdvance -= l_DefendingArmies;  //30-7=23
                d_Target.setArmyCount(d_Target.getArmyCount() - l_AttackingArmies); //10-18=-8

                if (d_Target.getArmyCount() < 0) {
                    // move surviving attacking armies to the target country
                    // transfer ownership of the conquered country
                    d_Target.setOwner(d_Initiator.getPlayerName());
                    this.d_Initiator.getListOfCountries().remove(this.d_Target);
                    d_Target.setArmyCount(d_NumToAdvance);
                    //removing the territory from the list and adding to the player who won it
                }
                else{
                    this.d_Source.setArmyCount(d_Source.getArmyCount() + d_NumToAdvance);
                }
            }
        }
    }

    /**
     * This method will check if the given inputs are a valid input for Advance Card
     *
     * @return true/false
     */
    public boolean valid() {
        boolean valid_condition=false;

        //TODO what is the valid condition
        // check if countrynamefrom exists
        // check if countryname has the same owner as the initiator of this commad
        // check if countrynamefrom has >= numarmies being advanced..
        // check if countrynameto is there
        // check if countrynameto exists

        if((d_Initiator.getListOfCountries().contains(d_Source))
                &&(d_Source.getArmyCount()>= d_NumToAdvance)
                && (d_Source !=null)
                && (d_Target !=null))        {
            valid_condition = true;
        }

        if (valid_condition) {
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
        // print the order
        System.out.println();
        LogUtil.log("");
    }
}
