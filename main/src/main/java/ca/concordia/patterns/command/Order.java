package ca.concordia.patterns.command;

public interface Order {

    void execute();

    boolean valid();

    void printOrder();
}
