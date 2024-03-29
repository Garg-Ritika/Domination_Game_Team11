package ca.concordia.gameengine;

import ca.concordia.dao.Map;
import ca.concordia.dao.Player;
import ca.concordia.patterns.observer.LogUtil;
import ca.concordia.patterns.state.Phase;
import ca.concordia.patterns.state.edit.Preload;
import ca.concordia.patterns.state.play.PlaySetup;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * Game Engine class that starts with "loadmap" command and automatically ends after mainloop phases
 */
public class GameEngine {

    /**
     * Map Editor Commands:
     * These are global data members corresponding to which a command is associated
     * <p>
     * editcontinent: in the switch case if this command corresponds to case then the corresponding command is called from the Phase.java file
     */
    public static final String COMMAND_EDIT_CONTINENT = "editcontinent";
    /**
     * editcountry: in the switch case if this command corresponds to case then the corresponding command is called from the Phase.java file
     */
    public static final String COMMAND_EDIT_COUNTRY = "editcountry";
    /**
     * editneighbor: in the switch case if this command corresponds to case then the corresponding command is called from the Phase.java file
     */
    public static final String COMMAND_EDIT_NEIGHBOUR = "editneighbor";
    /**
     * savemap: in the switch case if this command corresponds to case then the corresponding command is called from the Phase.java file
     */
    public static final String COMMAND_SAVE_MAP = "savemap";
    /**
     * editmap: in the switch case if this command corresponds to case then the corresponding command is called from the Phase.java file
     */
    public static final String COMMAND_EDIT_MAP = "editmap";
    /**
     * validatemap: in the switch case if this command corresponds to case then the corresponding command is called from the Phase.java file
     */
    public static final String COMMAND_VALIDATE_MAP = "validatemap";

    //Any commands
    /**
     * showMap method is called which prints different attributes of the map like ID, Name etc
     */
    public static final String COMMAND_SHOW_MAP = "showmap";
    /**
     * When this command is executed then "break" is performed which pulls out from the execution
     */
    public static final String COMMAND_QUIT = "quit";

    // play commands
    /**
     * loadMap command loads the map to start the game, it checks if the map with the input name already exists then
     * it loads that specified file otherwise it creates a new map
     * Implementation of code can be found in PlaySetup classset
     */
    public static final String COMMAND_LOAD_MAP = "loadmap";
    /**
     * This command is a gameplayer command and players are added in player list using addPlayer() method
     */
    public static final String COMMAND_GAME_PLAYER = "gameplayer";
    /**
     * this method is used to assign countries to different players in the game
     */
    public static final String COMMAND_ASSIGN_COUNTRIES = "assigncountries";

    /**
     * This public data member is used for the tournament command
     */
    public static final String COMMAND_TOURNAMENT = "tournament";
    /**
     * This public data member is used to load the game
     */
    public static final String COMMAND_LOAD_GAME = "loadgame";
    private Phase d_GamePhase;
    private Map d_Map = new Map();
    private List<Player> d_ListOfPlayers = new ArrayList<Player>();
    private int d_NumberOfTurnsAllowed = 50;
    private int d_CurrentTurnCount = 0;
    private LinkedList<GameStats> d_OverallResult = new LinkedList<GameStats>();

    /**
     * This getter method returns the current GamePhase
     *
     * @return d_GamePhase returns the current game phase
     */
    public Phase getPhase() {
        return d_GamePhase;
    }

    /**
     * This setter method is used to set the phase
     * returned from getPhase method
     *
     * @param p_Phase sets the Phase
     */
    public void setPhase(Phase p_Phase) {
        this.d_GamePhase = p_Phase;
    }

    /**
     * This getter method is used to return the map in PlaySetup class
     *
     * @return d_Map returns the map in PlaySetup class
     */
    public Map getMap() {
        return d_Map;
    }

    /**
     * This setter method is used to return the map in PlaySetup class
     *
     * @param p_Map object of Map class is passed as p_Map
     */
    public void setMap(Map p_Map) {
        d_Map = p_Map;
    }

    /**
     * This method is used to return the player names in a List
     *
     * @return d_ListOfPlayers list of players
     */
    public List<Player> getListOfPlayers() {
        return d_ListOfPlayers;
    }

    /**
     * This setter method is used to store the list of players passed as a parameter to the function
     * @param p_ListOfPlayers list of players
     */
    public void setListOfPlayers(List<Player> p_ListOfPlayers){
        d_ListOfPlayers = p_ListOfPlayers;
    }

    /**
     * This getter method is used to return the number of turns
     * @return d_NumberOfTurnsAllowed
     */
    public int getNumberOfTurnsAllowed() {
        return d_NumberOfTurnsAllowed;
    }

    /**
     * This setter method is used to store the number of turns in the private data member
     * @param p_NumberOfTurnsAllowed no of turns allowed
     */
    public void setNumberOfTurnsAllowed(int p_NumberOfTurnsAllowed) {
        d_NumberOfTurnsAllowed = p_NumberOfTurnsAllowed;
    }

    /**
     * This method is used to reset the number of turns
     */
    public void resetNumberOfTurnsAllowed() {
        d_NumberOfTurnsAllowed = 50;
    }

    /**
     * This getter method is used to return the current count of turns played
     * @return turn count
     */
    public int getCurrentTurnCount() {
        return d_CurrentTurnCount;
    }

    /**
     * This setter method is used to set a passed argument turn in the private data member
     * @param p_CurrentTurnCount private data member
     */
    public void setCurrentTurnCount(int p_CurrentTurnCount) {
        d_CurrentTurnCount = p_CurrentTurnCount;
    }

    /**
     * This method is used to reset the current turn count to zero
     */
    public void resetCurrentTurnCount() {
        d_CurrentTurnCount = 0;
    }

    /**
     * Game stats method is used to return the overall result and display the winner name
     * @param p_Winner winner of the game
     */
    public void addGameStats(String p_Winner) {
        int l_size = d_OverallResult.size();
        String l_GameNumber = "Game" + l_size + 1;
        String l_MapName = getMap().getName();
        String l_Result = p_Winner;
        GameStats l_GameStats = new GameStats(l_MapName, l_GameNumber, l_Result);
        d_OverallResult.add(l_GameStats);
    }

    /**
     * This method when called implements the clear method using the private data member d_OverallResult
     */
    public void resetOverallResults() {
        d_OverallResult.clear();
    }

    /**
     * This method returns the overall result of the game.
     * @return d_OverallResult
     */
    public LinkedList<GameStats> getOverallResults() {
        return d_OverallResult;
    }

    /**
     * Main game engine used a method to GameEngine constructor Main class
     * It takes input from the user to enter the phase
     * Following three phases are presented:
     * 1. Edit
     * 2. Play
     * 3. Quit
     * And from the input option phase is loaded and functions are implemented
     */
    public void start() {
        LogUtil.clearOldLogFiles();
        LogUtil.log("Game Engine started");
        Scanner l_Keyboard = new Scanner(System.in);
        do {
            LogUtil.log("==================================================================================");
            LogUtil.log("| PURPOSE             COMMAND STRUCTURE                                          |");
            LogUtil.log("| Map Editing     :   edit                                                       |");
            LogUtil.log("| Single Mode     :   play                                                       |");
            LogUtil.log("| Tournament Mode :   tournament -M <maps> -P <players> -G <games> -D <turns>    |");
            LogUtil.log("| Load saved-game :   loadgame <filename>                                        |");
            LogUtil.log("| Close All       :   quit                                                       |");
            LogUtil.log("> choose one of the option from above?:                                          |");
            LogUtil.log("==================================================================================");
            String l_Input = l_Keyboard.nextLine();
            LogUtil.log(l_Input);
            String arr[] = l_Input.split(" ", 2);
            switch (arr[0]) {
                case "edit":
                    // setting phase as preload
                    setPhase(new Preload(this));
                    startMapEditor(l_Keyboard);
                    break;
                case "play":
                    // setting phase as playsetup
                    setPhase(new PlaySetup(this));
                    startMainPlay(l_Keyboard);
                    break;
                case COMMAND_TOURNAMENT:
                    // start tournament
                    TournamentCreator l_TC = new TournamentCreator(this, l_Input);
                    l_TC.startTournament();
                    break;
                case COMMAND_LOAD_GAME:
                    // load game
                    LogUtil.log("loadgame command received ..");
                    try{
                        if (arr.length == 2) {
                            String l_FileName =  arr[1];
                            new GameLoader(this).loadGameFile(new File(l_FileName));
                        }
                    }catch (Exception l_E){
                        l_E.printStackTrace();
                    }
                    break;
                case "quit":
                    return;
            }
        } while (true);
    }

    /**
     * This method will be called when player selected option "edit"
     *
     * @param p_Keyboard
     */
    void startMapEditor(Scanner p_Keyboard) {
        boolean l_MaintainLoop = true;
        do {
            LogUtil.log("===================================================================================================");
            LogUtil.log("| PHASE          : command         command arguments                                             |");
            LogUtil.log("| Any            : showmap                                                                       |");
            LogUtil.log("| Edit:          : editmap        <filepath>                                                     |");
            LogUtil.log("| Edit:PostLoad  : editcontinent  -add <continent-name> <continent-id> -remove <continent-name>  |");
            LogUtil.log("| Edit:PostLoad  : editcountry    -add <country-name> <continent-name> -remove <country-name>    |");
            LogUtil.log("| Edit:PostLoad  : editneighbor  -add <country-name> <neigbor> -remove <country-name> <neighbor> |");
            LogUtil.log("| Edit:PostLoad  : savemap        <filepath>                                                     |");
            LogUtil.log("| Edit:PostLoad  : validatemap                                                                   |");
            LogUtil.log("| Any            : quit                                                                          |");
            LogUtil.log("===================================================================================================");
            String l_EditInput = p_Keyboard.nextLine();
            LogUtil.log(l_EditInput);

            if ("quit".equalsIgnoreCase(l_EditInput)) {
                break;
            }

            if (l_EditInput.length() > 0) {
                String[] l_CommandArray = l_EditInput.trim().split(" ");
                if (l_CommandArray.length > 0) {
                    String l_FirstCommand = l_CommandArray[0].toLowerCase();
                    LogUtil.log("firstCommand : " + l_FirstCommand);

                    switch (l_FirstCommand) {
                        case COMMAND_EDIT_CONTINENT:
                            d_GamePhase.editContinent(l_CommandArray);
                            break;

                        case COMMAND_EDIT_COUNTRY:
                            d_GamePhase.editCountry(l_CommandArray);
                            break;

                        case COMMAND_EDIT_NEIGHBOUR:
                            d_GamePhase.editNeighbour(l_CommandArray);
                            break;

                        case COMMAND_SHOW_MAP:
                            d_GamePhase.showMap();
                            break;

                        case COMMAND_SAVE_MAP:
                            d_GamePhase.saveMap(l_CommandArray);
                            break;

                        case COMMAND_EDIT_MAP:
                            d_GamePhase.editMap(l_CommandArray);
                            break;

                        case COMMAND_VALIDATE_MAP:
                            d_GamePhase.validateMap(l_CommandArray);
                            break;

                        default:
                            LogUtil.log("INVALID COMMAND in edit phase");
                    }
                }
            }
        } while (l_MaintainLoop);
    }

    /**
     * This method will be called when player selected option "play" to play the game further
     *
     * @param p_Keyboard Scanner keyword
     */
    void startMainPlay(Scanner p_Keyboard) {
        boolean l_MaintainLoop = true;
        do
            {
            LogUtil.log("============================================================================================");
            LogUtil.log("| PHASE                : command         command arguments                                 |");
            LogUtil.log("| Any                  : showmap                                                           |");
            LogUtil.log("| Play:PlaySetup       : loadmap         <filepath>                                        |");
            LogUtil.log("| Play:PlaySetup       : gameplayer      -add <player-name>                                |");
            LogUtil.log("| Play:PlaySetup       : assigncountries                                                   |");
            LogUtil.log("| Any                  : quit                                                              |");
            LogUtil.log("============================================================================================");

            String l_GameInput = p_Keyboard.nextLine();
            LogUtil.log(l_GameInput);

            if ("quit".equalsIgnoreCase(l_GameInput)) {
                break;
            }

            if (l_GameInput.length() > 0) {
                String[] l_CommandArray = l_GameInput.trim().split(" ");
                if (l_CommandArray.length > 0) {
                    String l_FirstCommand = l_CommandArray[0].toLowerCase();
                    LogUtil.log("firstCommand : " + l_FirstCommand);

                    switch (l_FirstCommand) {
                        case COMMAND_LOAD_MAP:
                            d_GamePhase.loadMap(l_CommandArray);
                            break;

                        case COMMAND_GAME_PLAYER:
                            d_GamePhase.setPlayers(l_CommandArray);
                            break;

                        case COMMAND_ASSIGN_COUNTRIES:
                            resetNumberOfTurnsAllowed();
                            resetCurrentTurnCount();
                            resetOverallResults();
                            d_GamePhase.assignCountries();
                            break;

                        case COMMAND_SHOW_MAP:
                            d_GamePhase.showMap();
                            break;

                        default:
                            LogUtil.log("INVALID COMMAND in PlaySetup phase");
                    }
                }
            }
        } while (l_MaintainLoop);
    }
}