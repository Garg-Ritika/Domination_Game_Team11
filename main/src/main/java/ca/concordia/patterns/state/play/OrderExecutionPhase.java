package ca.concordia.patterns.state.play;

import ca.concordia.dao.Player;
import ca.concordia.gameengine.GameEngine;
import ca.concordia.patterns.observer.LogUtil;
import ca.concordia.patterns.state.end.End;
import ca.concordia.patterns.strategy.Order;

/**
 * This classs is used to exexute all the order once the orders have been created by the players in round robin fashion
 * It also removes the player from the existing list of it has no country owned to it and
 * eventually checks if the game has ended
 */
public class OrderExecutionPhase extends MainPlay {

    /**
     * Constructor that contains GameEngine object as argument
     *
     * @param p_ge Game Engine Object
     */
    public OrderExecutionPhase(GameEngine p_ge) {
        super(p_ge);
    }

    /**
     * This method is used to go to the reinforcement phase afer one turn has been complated and all the order
     * have been executed for the same
     * it also resets the random card assigned and is negotiated player boolean values to False
     */
    public void next() {
        LogUtil.log("setting reinforcement phase");
        for (Player p : d_ge.getListOfPlayers()) {
            p.setIsNegotiatedPlayer(false);
            p.setD_RandomCardAssigned(false);
        }
        d_ge.setPhase(new ReinforcementPhase(d_ge));
        d_ge.getPhase().reinforce();
    }

    /**
     * Abstract method def to reinforcement state behavior in game setup state behaviour
     */
    public void reinforce() {
        printInvalidCommandMessage();
    }

    /**
     * Abstract method def to create order in attack state behaviour
     */
    public void createOrder() {
        printInvalidCommandMessage();
    }

    /**
     * This method is used to run the commands to call methods execute all orders then showmap
     * followed by check if the game has ended
     */
    public void executeOrder() {
        LogUtil.log("execute order");
        executeAllOrders();
        showMap();
        checkForEnd();
    }

    /**
     * This method is used to execute all orders in round robin fashion once the players have
     * created all the orders and move to next phase that is how map once all are executed
     */
    private void executeAllOrders() {
        LogUtil.log(" execute all orders ");
        String order;
        boolean still_more_orders = false;
        do {
            still_more_orders = false;
            for (Player p : d_ge.getListOfPlayers()) {
                try {
                    order = p.nextOrder();

                    if (order != null) {
                        still_more_orders = true;
//                        order.printOrder();
                        p.executeOrder(order);
                        /*if(p_orderName=="deploy"){
                            order.deploy();
                        }
                        else if(p_orderName=="advance"){
                            order.advance();
                        }
                        else if (p_orderName=="airlift"){
                            order.airlift();
                        }
                        else if (p_orderName=="blockade"){
                            order.blockade();
                        }
                        else if(p_orderName=="diplomacy"){
                            order.diplomacy();
                        }
                        else if(p_orderName=="bomb"){
                            order.bomb();
                        }*/
                    }
                    removePlayers(p);
                } catch (Exception e) {
                    LogUtil.log("EXCEPTION: " + e.getMessage());
                }
            }
        } while (still_more_orders);
        LogUtil.log("All orders has been executed ");
    }

    /**
     * This method checks for each player if countries owned by it becomes 0, the player is removed from the
     * list and is out of the game
     *
     * @param p Player p object
     */
    private void removePlayers(Player p) {
        if (p.getListOfCountries().size() == 0) {
            d_ge.getListOfPlayers().remove(p);
            LogUtil.log("PLayer " + p.getPlayerName() + " is out of the game");
        }
    }

    /**
     * This method is used to check if the game has ended by checking if there is only one available
     * player in the list of players
     */
    private void checkForEnd() {
        if (d_ge.getListOfPlayers().size() < 2) {
            d_ge.setPhase(new End(d_ge));
            if (d_ge.getListOfPlayers().size() > 0) {
                LogUtil.log("Game ends and the winner is " + d_ge.getListOfPlayers().get(0));
            } else {
                LogUtil.log("Game ends");
            }
        } else {
            next();
        }
    }

}


