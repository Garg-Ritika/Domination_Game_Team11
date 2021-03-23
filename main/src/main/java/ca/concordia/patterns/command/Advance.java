package ca.concordia.patterns.command;

import ca.concordia.dao.Country;
import ca.concordia.dao.Player;
import ca.concordia.dao.Territory;
import ca.concordia.patterns.observer.LogUtil;
import ca.concordia.patterns.state.play.Play;

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

    /**
     * This is the constructor that takes player, source and target territory object and no of armies as argument
     * encapsulating all necessary data to execute the command
     * @param p_Initiator player who runs the command
     * @param p_Source Source territory form which armies are to be moved
     * @param p_Target target territory to which armies are to be attacked
     * @param p_Num no of armies attacking
     */
    public Advance(Player p_Initiator, Territory p_Source, Territory p_Target, int p_Num) {
        // encapsulate all necessary data to execute the command
        this.d_Initiator = p_Initiator;
        this.d_Source = p_Source;
        this.d_Target = p_Target;
        this.d_NumToAdvance = p_Num;
    }

    /**
     * execute method executes the Advance card after validity check
     * move some armies from one of the current player’s territories (source) to an adjacent territory
     * (target). If the target territory belongs to the current player, the armies are moved to the target
     * territory. If the target territory belongs to another player, an attack happens between the two
     * territories.
     * If attack happens successfulyy, update owner to initiator
     * Here both the source and the target Territories are Receivers
     */
    public void execute() {
        System.out.println("advance execute ");

        if (valid()) {
            if (d_Target.getOwner().getPlayerName().equalsIgnoreCase(d_Initiator.getPlayerName())) {
                // if the source and the target belong to the same player
                // then just move the armies to the target Territory

                this.d_Target.setArmyCount(this.d_Target.getArmyCount() + d_NumToAdvance);
            } else {
                // implement a battle
                int l_DefendingArmies = (int) (this.d_Target.getArmyCount()*0.7); //7
                int l_AttackingArmies = (int) (this.d_NumToAdvance *0.6); //15
                this.d_Target.setArmyCount(this.d_Target.getArmyCount() - l_AttackingArmies); //10-18=-8
                if (this.d_Target.getArmyCount() < 0) {
                    // move surviving attacking armies to the target country
                    // transfer ownership of the conquered country
                    this.d_Target.getOwner().removeNewCountry((Country) this.d_Target);

                    this.d_Target.setOwner(d_Initiator);
                    this.d_Target.setArmyCount(this.d_Source.getArmyCount()-l_DefendingArmies);
                    //removing the territory from the list and adding to the player who won it
                    this.d_Initiator.addNewCountry((Country)this.d_Target);
                    this.d_Source.setArmyCount(this.d_Source.getArmyCount() - d_NumToAdvance);
                }
                else{
                    this.d_Source.setArmyCount(d_Source.getArmyCount() -l_DefendingArmies);
                }
            }
        }
    }

    /**
     * This method will check if the given inputs are a valid input for Advance Card
     * check if countrynamefrom exists
     * check if countryname has the same owner as the initiator of this commad
     * check if countrynamefrom has  greater than or equal to numarmies being advanced..
     * check if countrynameto is there
     * check if countrynameto exists
     *
     * @return true/false
     */
    public boolean valid() {
        boolean valid_condition=false;

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
