package ca.concordia.patterns.state.play;

import ca.concordia.dao.Continent;
import ca.concordia.dao.Player;
import ca.concordia.gameengine.GameEngine;
import ca.concordia.patterns.observer.LogUtil;

/**
 * This is the ReinforcementPhase class that extends MainPlay class
 * Each player receives the number of armies
 * Attack and fortify- shows invalid command message in Reinforcement phase
 */

public class ReinforcementPhase extends MainPlay {

    /**
     * Constructor that takes GameEngine object as argument
     *
     * @param p_ge GameEngine class object
     */
    public ReinforcementPhase(GameEngine p_ge) {
        super(p_ge);
    }

    /**
     * This method assigns reinforcement phase to all players
     */
    public void reinforce() {
        System.out.println("reinforcing ");
        System.out.println("size" + d_ge.getListOfPlayers().size());
        for (Player l_Player : d_ge.getListOfPlayers()) {

            System.out.println("----------------------------------------------------------------");
            LogUtil.log("ASSIGN REINFORCEMENT PHASE : " + l_Player.getPlayerName());
            System.out.println("ASSIGN REINFORCEMENT PHASE : " + l_Player.getPlayerName());
            assignReinforcementPhase(l_Player);
        }
        next();
    }

    /**
     * All turns start with reinforcement. Each turn, each player receives a number of armies equal to:
     * (max(3, # of countries the player own/3)+(continent value of all continents controlled by the player)).
     *
     * @param p_Player "player for which the reinforcement is happening"
     */
    private int assignReinforcementPhase(Player p_Player) {
        int l_CountryOwnedByPlayer = p_Player.getListOfCountries().size();
        System.out.println("#countries own by player: " + p_Player.getPlayerName() + " is " + l_CountryOwnedByPlayer);
        int l_NetContinentValue = 0;
        if (p_Player.getListOfContinents().size() > 1) {
            for (Continent p_Continent : p_Player.getListOfContinents()) {
                l_NetContinentValue += p_Continent.getArmyCount();
            }
        }
        System.out.println("#net continent value of all the continents controlled by player: " + l_NetContinentValue);
        int l_NewArmy = Math.max(3, l_CountryOwnedByPlayer / 3) + l_NetContinentValue;
        LogUtil.log("#new armies being assigned to player: " + p_Player.getPlayerName() + " is " + l_NewArmy);
        System.out.println("#new armies being assigned to player: " + p_Player.getPlayerName() + " is " + l_NewArmy);
        p_Player.setNoOfArmies(l_NewArmy);
        return l_NewArmy;
    }

    /**
     * This method shows invalid command message to the user
     */
    public void createOrder() {
        printInvalidCommandMessage();
    }

    /**
     * This method shows invalid command message to the user
     */
    public void executeOrder() {
        printInvalidCommandMessage();
    }

    /**
     * This method moves to next phase- OrderCreationPhase
     */
    public void next() {
        System.out.println("--> setting order creation phase ");
        d_ge.setPhase(new OrderCreationPhase(d_ge));
        d_ge.getPhase().createOrder();
    }

}
