package ca.concordia.patterns.strategy;

import ca.concordia.dao.Player;
import ca.concordia.gameengine.GameEngine;
import ca.concordia.patterns.command.Order;

import java.util.LinkedList;

/**
 * Benevolent Player: <br>
 * A benevolent computer player strategy that focuses on protecting its weak countries (deploys on its weakest country, never
 * attacks, then moves its armies in order to reinforce its weaker country).
 */

public class Benevolent extends Strategy {

    private LinkedList<Order> d_ListOfOrders = new LinkedList<Order>();

    public Benevolent(GameEngine p_Ge) {
        super(p_Ge);
    }

    @Override
    public LinkedList<Order> create(Player p_Player) {
        return d_ListOfOrders;
    }
}
