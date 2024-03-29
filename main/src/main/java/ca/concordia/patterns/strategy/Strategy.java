package ca.concordia.patterns.strategy;

import ca.concordia.dao.Player;
import ca.concordia.gameengine.GameEngine;
import ca.concordia.patterns.command.Order;

import java.util.LinkedList;

/**
 * Strategy class to process the type of player.
 */
public abstract class Strategy {

    public GameEngine d_Ge;

    public Strategy(GameEngine p_Ge) {
        d_Ge = p_Ge;
    }

    /**
     * Abstract class for LinkedList
     * @param p_Player player object for whose Order is created
     * @return list of orders
     */
    abstract public LinkedList<Order> create(Player p_Player);

}
