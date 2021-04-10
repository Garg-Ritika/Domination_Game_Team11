package ca.concordia.patterns.strategy;

import ca.concordia.dao.Player;
import ca.concordia.gameengine.GameEngine;
import ca.concordia.patterns.command.Order;

import java.util.LinkedList;

/**
 * Cheater Player: <br>
 * A cheater computer player strategy whose issueOrder() method conquers all the immediate neighboring enemy countries, and
 * then doubles the number of armies on its countries that have enemy neighbors.
 */

public class Cheater extends Strategy {

    private LinkedList<Order> d_ListOfOrders = new LinkedList<Order>();

    public Cheater(GameEngine p_Ge) {
        super(p_Ge);
    }

    @Override
    public LinkedList<Order> create(Player p_Player) {
        return d_ListOfOrders;
    }
}
