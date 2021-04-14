package ca.concordia.patterns.state.play;

import ca.concordia.dao.Player;
import ca.concordia.gameengine.GameEngine;
import ca.concordia.patterns.command.Order;
import ca.concordia.patterns.observer.LogUtil;
import ca.concordia.patterns.state.end.End;

/**
 * This class is used to execute all the order once the orders have been created by the players in round robin fashion
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
     * This method is used to go to the reinforcement phase after one turn has been completed and all the order
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
        for(int i=0; i<d_ge.getListOfPlayers().size(); i++){
            removePlayers(d_ge.getListOfPlayers().get(i));
        }
        checkForEnd();
    }

    /**
     * This method is used to execute all orders in round robin fashion once the players have
     * created all the orders and move to next phase that is how map once all are executed
     */
    private void executeAllOrders() {
        LogUtil.log(" execute all orders ");
        Order order;
        boolean still_more_orders = false;
        do {
            still_more_orders = false;
            for (Player p : d_ge.getListOfPlayers()) {
                try {
                    order = p.nextOrder();

                    if (order != null) {
                        still_more_orders = true;
                        order.printOrder();
                        order.execute();
                    }
                    p.setNoOfArmies(0);
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
     * player in the list of players.
     * <p>
     * Also it is checking for number of turns allowed, if it increases, the result would be DRAW
     */
    private void checkForEnd() {
        d_ge.setCurrentTurnCount(d_ge.getCurrentTurnCount() + 1);
        if (d_ge.getCurrentTurnCount() < d_ge.getNumberOfTurnsAllowed()) {
            if (d_ge.getListOfPlayers().size() < 2) {
                System.out.print("Players left in game is ");
                for(Player player: d_ge.getListOfPlayers()){
                    System.out.print(player.getPlayerName()+" ");
                }
                System.out.println(" ");
                d_ge.setPhase(new End(d_ge));
                if (d_ge.getListOfPlayers().size() > 0) {
                    String l_Winner = d_ge.getListOfPlayers().get(0).getPlayerName();
                    LogUtil.log("Game ends and the winner is " + l_Winner);
                    d_ge.addGameStats(l_Winner);
                    clearListOOfCountries(d_ge);
                } else {
                    LogUtil.log("Game ends and the result is DRAW");
                    d_ge.addGameStats("DRAW");
                    clearListOOfCountries(d_ge);
                }
            } else {
                next();
            }
        } else {
            System.out.print("Players left in game is ");
            for(Player player: d_ge.getListOfPlayers()){
                System.out.print(player.getPlayerName()+" ");
            }
            System.out.println(" ");
            d_ge.setPhase(new End(d_ge));
            LogUtil.log("Game ends and result is DRAW");
            d_ge.addGameStats("DRAW");
            clearListOOfCountries(d_ge);
        }
    }

    /**
     * Clear the list of countries for players
     * @param d_ge Game engine object
     */
    private void clearListOOfCountries(GameEngine d_ge) {
        for(int i=0; i<d_ge.getListOfPlayers().size(); i++){
            d_ge.getListOfPlayers().get(i).getListOfCountries().clear();
        }
    }

}