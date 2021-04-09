package ca.concordia.patterns.state.play;

import ca.concordia.dao.Player;
import ca.concordia.gameengine.GameEngine;
import ca.concordia.patterns.observer.LogUtil;

/**
 * This is the OrderCreationPhase class that extends the MainPlay class
 * This class takes the orders for players and place the reinforcement armies
 * Deploy, Advance, Bomb, blockade, Airlift, Diplomacy - These commands works here,
 * for rest of the commands it throws invalid command message to the user
 */
public class OrderCreationPhase extends MainPlay {


    /**
     * Constructor that takes GameEngine object as argument
     *
     * @param p_ge GameEngine object
     */
    public OrderCreationPhase(GameEngine p_ge) {
        super(p_ge);
    }

    /**
     * This method moves to next phase - OrderExecutionPhase
     */
    public void next() {
        LogUtil.log("setting order execution phase ");
        d_ge.setPhase(new OrderExecutionPhase(d_ge));
        d_ge.getPhase().executeOrder();
    }

    /**
     * This method shows invalid command message to the user
     */
    public void reinforce() {
        printInvalidCommandMessage();
    }

    /**
     * This method starts the creation of order for players
     * takes orders for players and place all the reinforcement armies
     * and finishes the order creation
     */
    @Override
    public void createOrder() {
        LogUtil.log("start of order creation");
        for (Player l_Player : d_ge.getListOfPlayers()) {
            LogUtil.log("Taking order for player: " + l_Player.getPlayerName());
            l_Player.createOrder();
        }
        LogUtil.log("finish of order creation ");
        next();
    }

    /**
     * This method shows invalid command message to the user
     */
    public void executeOrder() {
        printInvalidCommandMessage();
    }


}