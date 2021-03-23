package ca.concordia.gameengine;

import ca.concordia.dao.Map;
import ca.concordia.dao.Player;
import ca.concordia.patterns.observer.LogUtil;
import ca.concordia.patterns.state.Phase;
import ca.concordia.patterns.state.edit.Preload;
import ca.concordia.patterns.state.play.PlaySetup;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Game Engine class that starts with "loadmap" command and automatically ends after mainloop phases
 */
public class GameEngine {

    /**
     * Map Editor Commands:
     * These are global data members corresponding to which a command is associated
     *
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
     *showMap method is called which prints different attributes of the map like ID, Name etc
     */
    public static final String COMMAND_SHOW_MAP = "showmap";
    /**
     *When this command is executed then "break" is performed which pulls out from the execution
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

    // data members
    private Phase d_GamePhase;
    private Map d_Map;
    private List<Player> d_ListOfPlayers = new ArrayList<Player>();

    /**
     * This getter method returns the current GamePhase
     * @return d_GamePhase returns the current game phase
     */
    public Phase getPhase() {
        return d_GamePhase;
    }

    /**
     * This setter method is used to set the phase
     * returned from getPhase method
     * @param p_Phase  sets the Phase
     */
    public void setPhase(Phase p_Phase) {
        this.d_GamePhase = p_Phase;
    }

    /**
     * This getter method is used to return the map in PlaySetup class
     * @return d_Map returns the map in PlaySetup class
     */
    public Map getMap() {
        return d_Map;
    }

    /**
     * This setter method is used to return the map in PlaySetup class
     * @param p_Map object of Map class is passed as p_Map
     */
    public void setMap(Map p_Map) {
        d_Map = p_Map;
    }

    /**
     * This method is used to store the player names in a List
     * @return d_ListOfPlayers
     */
    public List<Player> getListOfPlayers() {
        return d_ListOfPlayers;
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
            System.out.println("========================================");
            System.out.println("edit");
            System.out.println("play");
            System.out.println("quit");
            System.out.println("choose one of the option from above?: ");
            System.out.println("=======================================");
            String l_Input = l_Keyboard.nextLine();
            LogUtil.log(l_Input);
            switch (l_Input) {
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
                case "quit":
                    return;
            }
        } while (true);
    }

    /**
     * This method will be called when player selected option "edit"
     * @param keyboard
     */
    void startMapEditor(Scanner keyboard) {
        boolean l_MaintainLoop = true;
        do {
            System.out.println("===================================================================================================");
            System.out.println("| PHASE          : command         command arguments                                             |");
            System.out.println("| Any            : showmap                                                                       |");
            System.out.println("| Edit:          : editmap        <filepath>                                                     |");
            System.out.println("| Edit:PostLoad  : editcontinent  -add <continent-name> <continent-id> -remove <continent-name>  |");
            System.out.println("| Edit:PostLoad  : editcountry    -add <country-name> <continent-name> -remove <country-name>    |");
            System.out.println("| Edit:PostLoad  : editneighbor  -add <country-name> <neigbor> -remove <country-name> <neighbor> |");
            System.out.println("| Edit:PostLoad  : savemap        <filepath>                                                     |");
            System.out.println("| Edit:PostLoad  : validatemap                                                                   |");
            System.out.println("| Any            : quit                                                                          |");
            System.out.println("===================================================================================================");
            String l_EditInput = keyboard.nextLine();
            LogUtil.log(l_EditInput);

            if ("quit".equalsIgnoreCase(l_EditInput)) {
                break;
            }

            if (l_EditInput.length() > 0) {
                String[] l_CommandArray = l_EditInput.trim().split(" ");
                if (l_CommandArray.length > 0) {
                    String l_FirstCommand = l_CommandArray[0].toLowerCase();
                    System.out.println("firstCommand : " + l_FirstCommand);

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
                            System.out.println("INVALID COMMAND in edit phase");
                    }
                }
            }
        } while (l_MaintainLoop);
    }

    /**
     * This method will be called when player selected option "play"
     * @param keyboard
     */
    void startMainPlay(Scanner keyboard) {
        boolean l_MaintainLoop = true;
        do {
            System.out.println("============================================================================================");
            System.out.println("| PHASE                : command         command arguments                                 |");
            System.out.println("| Any                  : showmap                                                           |");
            System.out.println("| Play:PlaySetup       : loadmap         <filepath>                                        |");
            System.out.println("| Play:PlaySetup       : gameplayer      -add <player-name>                                |");
            System.out.println("| Play:PlaySetup       : assigncountries                                                   |");
            System.out.println("| Any                  : quit                                                              |");
            System.out.println("============================================================================================");

            String l_GameInput = keyboard.nextLine();
            LogUtil.log(l_GameInput);

            if ("quit".equalsIgnoreCase(l_GameInput)) {
                break;
            }

            if (l_GameInput.length() > 0) {
                String[] l_CommandArray = l_GameInput.trim().split(" ");
                if (l_CommandArray.length > 0) {
                    String l_FirstCommand = l_CommandArray[0].toLowerCase();
                    System.out.println("firstCommand : " + l_FirstCommand);

                    switch (l_FirstCommand) {
                        case COMMAND_LOAD_MAP:
                            d_GamePhase.loadMap(l_CommandArray);
                            break;

                        case COMMAND_GAME_PLAYER:
                            d_GamePhase.setPlayers(l_CommandArray);
                            break;

                        case COMMAND_ASSIGN_COUNTRIES:
                            d_GamePhase.assignCountries();
                            break;

                        case COMMAND_SHOW_MAP:
                            d_GamePhase.showMap();
                            break;

                        default:
                            System.out.println("INVALID COMMAND in PlaySetup phase");
                    }
                }
            }
        } while (l_MaintainLoop);
    }
}