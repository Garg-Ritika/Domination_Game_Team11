package ca.concordia.gameengine;

public class GameStats {

    String d_MapName;
    String d_GameNumber;
    String d_Result;

    public GameStats(String p_Map, String p_GameNumber, String p_Result) {
        d_MapName = p_Map;
        d_GameNumber = p_GameNumber;
        d_Result = p_Result;
    }

    public String getMapName() {
        return d_MapName;
    }

    public String getGameNumber() {
        return d_GameNumber;
    }

    public String getResult() {
        return d_Result;
    }
}
