package ca.concordia.patterns.strategy;

import ca.concordia.dao.Player;
import ca.concordia.gameengine.GameEngine;
import ca.concordia.patterns.command.Order;

import java.util.LinkedList;

/**
 * Aggressive Player: <br>
 * An aggressive computer player strategy that focuses on centralization of forces and then attack, i.e. it deploys on its strongest
 * country, then always attack with its strongest country, then moves its armies in order to maximize aggregation of forces in one
 * country.
 * This class extends abstract strategy class
 */

public class Aggressive extends Strategy {

    /**
     * Initialized linked list of orders
     */
    private LinkedList<Order> d_ListOfOrders = new LinkedList<Order>();

    /**
     * Constructor that includes GameEngine object as its parameters
     * @param p_Ge GameEngine object
     */
    public Aggressive(GameEngine p_Ge) {
        super(p_Ge);
    }

    /**
     * Create method that takes player object as its parameters
     * and return list of order
     * @param p_Player
     * @return
     */
    @Override
    public LinkedList<Order> create(Player p_Player) {
        return d_ListOfOrders;
    }
}
