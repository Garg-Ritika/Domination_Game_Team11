package ca.concordia.gameengine;

import ca.concordia.patterns.observer.LogUtil;

import java.util.ArrayList;

/**
 *  This class will:
 *  1. check number of maps is between 1 to 5
 *  2. check number of players is between 2 to 4
 *  3. check number of games is between 1 to 5
 *  4. check number of turns between 10 t0 50
 *  Now, for each map, start game, find winner and save the results.
 *  (command format: tournament -M listofmapfiles -P listofplayerstrategies -G numberofgames -D maxnumberofturns)
 *
 **/

public class TournamentCreator {

    private String[] d_CommandArray;
    private ArrayList<String> d_mapFiles = new ArrayList<String>();
    private ArrayList<String> d_playerStrategies = new ArrayList<String>();
    private int d_NumberOfGames = 1; // 1 to 5
    private int d_NumberOfTurns = 10;     // 10 to 15

    /**
     * These are global data members corresponding to which, Player's behaviour strategies are associated
     */

    public static final String HUMAN_PLAYER = "human";
    public static final String AGGRESSIVE_PLAYER = "aggressive";
    public static final String BENEVOLENT_PLAYER = "benevolent";
    public static final String RANDOM_PLAYER = "random";
    public static final String CHEATER_PLAYER = "cheater";

    public static ArrayList<Result> finalResult = new ArrayList<Result>();

    /**
     * This is the TournamentCreator constructor with command as its parameter
     * and it includes various methods associated with this class
     * @param p_CommandArray array of commands
     */
    public TournamentCreator(String[] p_CommandArray){
        d_CommandArray = p_CommandArray;
        findMapFiles();
        findPlayerStrategies();
        findNumberOfGames();
        findNumberOfTurns();
    }

    /**
     * This method starts the tournament
     * Tournament starts with the user choosing different maps, computer player strategies,
     * games to be played on each map and maximum number of turns for each game
     */
    public void startTournament(){
        boolean l_GoodToStart = false;
        if (d_mapFiles.size() >=1 && d_mapFiles.size() <=5){

            if(d_playerStrategies.size()>=2 && d_playerStrategies.size() <=4){

                if (d_NumberOfGames >=1 && d_NumberOfGames <=5 ){

                    if (d_NumberOfTurns >= 10 && d_NumberOfTurns <=50){
                        l_GoodToStart = true;

                    }else{
                        LogUtil.log("Number of turns is not between 10 to 50");
                    }
                }else{
                    LogUtil.log("Number of games is not between 1 to 5");
                }
            }else{
                LogUtil.log("Number of players is not between 2 to 4");
            }
        }else{
            LogUtil.log("Number of maps is not between 1 to 5");
        }

        if(l_GoodToStart) {
            for (String l_MapFile : d_mapFiles) {
                // TODO:
                // PlaySetup: loadmap: do we need to automatically identify which type of map is it ? I think YES
                // PlaySetup: game-player, integrate it with strategy ..
                for (int i =0; i<= d_NumberOfGames; i++){
                    // PlaySetup: assigncountries --> pass number of turn as arugment, so that it can decide before that..
                    // Save result.
                }
            }
        }
    }

    /**
     * This method finds the different map files
     */
    private void findMapFiles(){
        boolean foundM = false;
        for (String val : d_CommandArray){
            if ("-m".equalsIgnoreCase(val)){
                foundM = true;
            }else if ("-p".equalsIgnoreCase(val)){
                break;
            }

            if (foundM){
                d_mapFiles.add(val);
            }
        }
    }

    /**
     * This method finds the computer player' behaviour strategies
     */
    private void findPlayerStrategies(){
        boolean foundP = false;
        for (String val: d_CommandArray){
            if("-p".equalsIgnoreCase(val)){
                foundP = true;
            }else if ("-g".equalsIgnoreCase(val)){
                break;
            }
            if(foundP){
                if(val.equalsIgnoreCase(AGGRESSIVE_PLAYER)){
                    d_playerStrategies.add(AGGRESSIVE_PLAYER);
                }else if (val.equalsIgnoreCase(BENEVOLENT_PLAYER)){
                    d_playerStrategies.add(BENEVOLENT_PLAYER);
                }else if(val.equalsIgnoreCase(RANDOM_PLAYER)) {
                    d_playerStrategies.add(RANDOM_PLAYER);
                }else if (val.equalsIgnoreCase(CHEATER_PLAYER)){
                    d_playerStrategies.add(CHEATER_PLAYER);
                }
            }
        }
    }

    /**
     * This method finds the number of games to be played on each map
     * using try-catch block
     */
    private void findNumberOfGames(){
        try{
            boolean foundG = false;
            for(String val: d_CommandArray){
                if ("-g".equalsIgnoreCase(val)){
                    foundG = true;
                }

                if(foundG){
                    d_NumberOfGames = Integer.parseInt(val);
                    break;
                }
            }
        }catch (Exception e){
            LogUtil.log(e.getMessage());
        }
    }

    /**
     * This method finds the maximum number of turns
     * for each game
     */
    private void findNumberOfTurns() {
        try {
            boolean foundD = false;
            for (String val : d_CommandArray) {
                if ("-d".equalsIgnoreCase(val)) {
                    foundD = true;
                }

                if (foundD) {
                    d_NumberOfTurns = Integer.parseInt(val);
                    break;
                }
            }
        } catch (Exception e) {
            LogUtil.log(e.getMessage());
        }
    }

    /**
     * At the end of tournament report of result should be displayed
     * So this class stores the result of the game
     * It declares map, Game number and winner player's name
     */
    class Result{
        String d_Map;
        String d_GameNo;
        String d_WinnerPlayer;

        /**
         * This is the constructor that initializes Map, Game number and winner player's name
         * @param p_Map map used
         * @param p_GameNo games to be played
         * @param p_WinnerPlayer name of winner player
         */
        public Result(String p_Map,String p_GameNo, String p_WinnerPlayer){
            d_Map = p_Map;
            d_GameNo = p_GameNo;
            d_WinnerPlayer = p_WinnerPlayer;
        }

        /**
         * This is the getter method which returns the map file
         * @return d_Map Map object
         */
        public String getMap(){
            return d_Map;
        }

        /**
         * This is the String getter method that returns Game number
         * @return d_GameNo Game played on each map
         */
        public String getGameNo(){
            return d_GameNo;
        }

        /**
         * This method returns the name of winner player
         * @return d_WinnerPlayer name of the player who wins the game
         */
        public String getWinnerPlayer() {
            return d_WinnerPlayer;
        }
    }
}
