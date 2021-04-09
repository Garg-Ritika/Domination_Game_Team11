package ca.concordia.patterns.strategy;


import ca.concordia.dao.Player;
import ca.concordia.gameengine.GameEngine;
import ca.concordia.patterns.command.Order;

import java.util.LinkedList;

/**
 * Random strategy,
 */
public class Odd extends Strategy {

    private LinkedList<Order> d_ListOfOrders = new LinkedList<Order>();

    public Odd(GameEngine p_Ge) {
        super(p_Ge);
    }

    @Override
    public LinkedList<Order> create(Player p_Player) {
        return d_ListOfOrders;
    }
}
