package ca.concordia.patterns.strategy;

public interface Order {

    void advance();

    void airlift();

    void blockade();

    void bomb();

    void deploy();

    void diplomacy();

}
