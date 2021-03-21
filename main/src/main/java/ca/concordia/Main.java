package ca.concordia;

import ca.concordia.gameengine.GameEngine;

/**
 * GameDriver
 */
public class Main {

    /**
     * main method to start the exeuction
     *
     * @param args arguments to the program, the game is not expecting any argument on startup
     */
    public static void main(String[] args) {

        System.out.println("Welcome to TEAM-11 Game");
        new GameEngine().start();
    }


}