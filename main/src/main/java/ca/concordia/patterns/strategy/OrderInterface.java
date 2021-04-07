package ca.concordia.patterns.strategy;

import ca.concordia.dao.Player;
import ca.concordia.dao.Territory;

public interface OrderInterface {

    void advance();

    void airlift();

    void blockade();

    void bomb();

    void deploy();

    void diplomacy();

}
