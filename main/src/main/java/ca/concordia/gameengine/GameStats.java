package ca.concordia.gameengine;

/**
 * This class includes methods that return- Map name, GameNumber
 * and Result
 */
public class GameStats {

    /** This variable name indicates the name of Map used */
    String d_MapName;

    /** This variable string indicates the game number */
    String d_GameNumber;

    /** This is the String variable that will store result */
    String d_Result;

    /**
     * This is the GameStats constructor
     * that initializes Map name, Game number and result
     * @param p_Map Name of map
     * @param p_GameNumber Game number
     * @param p_Result result of game
     */
    public GameStats(String p_Map, String p_GameNumber, String p_Result) {
        d_MapName = p_Map;
        d_GameNumber = p_GameNumber;
        d_Result = p_Result;
    }

    /**
     * This is the getter method that will return name of map
     * @return d_MapName Map name
     */
    public String getMapName() {
        return d_MapName;
    }

    /**
     * This is the getter method that will return Game number
     * @return d_GameNumber Game number
     */
    public String getGameNumber() {
        return d_GameNumber;
    }

    /**
     * This getter method returns the result
     * @return d_Result result of the game played
     */
    public String getResult() {
        return d_Result;
    }
}
