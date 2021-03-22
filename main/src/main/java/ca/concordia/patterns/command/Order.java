package ca.concordia.patterns.command;

/**
 * This interface is used to execute these methods which are common to different types of cards
 * Ex: execute() is used in Advance, Airlift, Blockade etc.
 */
public interface Order {

    void execute();

    boolean valid();

    void printOrder();
}
