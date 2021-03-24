package ca.concordia.patterns.state.play;

import ca.concordia.dao.Player;
import ca.concordia.gameengine.GameEngine;
import ca.concordia.patterns.command.Order;
import ca.concordia.patterns.observer.LogUtil;
import ca.concordia.patterns.state.end.End;

public class OrderExecutionPhase extends MainPlay {
    public OrderExecutionPhase(GameEngine p_ge) {
        super(p_ge);
    }

    public void next() {
        LogUtil.log("setting reinforcement phase");
        for (Player p : d_ge.getListOfPlayers()) {
            p.setIsNegotiatedPlayer(false);
            p.setD_RandomCardAssigned(false);
        }
        d_ge.setPhase(new ReinforcementPhase(d_ge));
        d_ge.getPhase().reinforce();
    }

    public void reinforce() {
        printInvalidCommandMessage();
    }

    public void createOrder() {
        printInvalidCommandMessage();
    }

    public void executeOrder() {
        LogUtil.log("execute order");
        executeAllOrders();
        showMap();
        checkForEnd();
    }

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
                    removePlayers(p);
                } catch (Exception e) {
                    LogUtil.log("EXCEPTION: " + e.getMessage());
                }
            }
        } while (still_more_orders);
        LogUtil.log("All orders has been executed ");
    }

    private void removePlayers(Player p) {
        if (p.getListOfCountries().size() == 0) {
            d_ge.getListOfPlayers().remove(p);
            LogUtil.log("PLayer " + p.getPlayerName() + " is out of the game");
        }
    }

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


