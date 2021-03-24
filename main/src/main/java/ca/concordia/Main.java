package ca.concordia;

import ca.concordia.gameengine.GameEngine;
import ca.concordia.patterns.observer.LogUtil;

/**
 * GameDriver
 */
public class Main {

    /**
     * main method to start the execution
     *
     * @param args arguments to the program, the game is not expecting any argument on startup
     */
    public static void main(String[] args) {

        LogUtil.log("Welcome to TEAM-11 Game");
        new GameEngine().start();
    }
}