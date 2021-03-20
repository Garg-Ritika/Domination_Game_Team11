package ca.concordia.gameengine;
import ca.concordia.dao.Map;
import ca.concordia.dao.Player;
import ca.concordia.patterns.observer.LogEntryBuffer;
//import ca.concordia.patterns.observer.LogUtil;
import ca.concordia.patterns.state.Phase;
import ca.concordia.patterns.state.edit.Preload;
import ca.concordia.patterns.state.play.PlaySetup;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Scanner;

/**
 * Game Engine class that starts with "loadmap" command and automatically ends after mainloop phases
 */
public class GameEngine  {


    // Map editor commands
    public static final String COMMAND_EDIT_CONTINENT = "editcontinent";
    public static final String COMMAND_EDIT_COUNTRY = "editcountry";
    public static final String COMMAND_EDIT_NEIGHBOUR = "editneighbor";
    public static final String COMMAND_SAVE_MAP = "savemap";
    public static final String COMMAND_EDIT_MAP = "editmap";
    public static final String COMMAND_VALIDATE_MAP = "validatemap";

    //Any commands
    public static final String COMMAND_SHOW_MAP = "showmap";
    public static final String COMMAND_QUIT = "quit";

    // play commands
    public static final String COMMAND_LOAD_MAP = "loadmap";
    public static final String COMMAND_GAME_PLAYER = "gameplayer";
    public static final String COMMAND_ASSIGN_COUNTRIES = "assigncountries";

    // data members
    private Phase d_GamePhase;
    private Map d_Map;
    private List<Player> d_ListOfPlayers = new ArrayList<Player>();

    public Phase getPhase() {
        return d_GamePhase;
    }

    public void setPhase(Phase p_Phase) {
        this.d_GamePhase = p_Phase;
    }

    public Map getMap() {
        return d_Map;
    }

    public void setMap(Map p_Map) {
        d_Map = p_Map;
    }

    public List<Player> getListOfPlayers() {
        return d_ListOfPlayers;
    }

    public void start() {
//        LogUtil.clearOldLogFiles();
//        LogUtil.log("Game Engine started");
        Scanner keyboard = new Scanner(System.in);
        do {
            System.out.println("========================================");
            System.out.println("edit");
            System.out.println("play");
            System.out.println("quit");
            System.out.println("choose one of the option from above?: ");
            System.out.println("=======================================");
            String l_Input = keyboard.nextLine();
//            LogUtil.log(l_Input);
            switch (l_Input) {
                case "edit":
                    // setting phase as preload
                    setPhase(new Preload(this));
                    startMapEditor(keyboard);
                    break;
                case "play":
                    // setting phase as playsetup
                    setPhase(new PlaySetup(this));
                    startMainPlay(keyboard);
                    break;
                case "quit":
                    return;
            }

        } while (true);
    }

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
//            LogUtil.log(l_EditInput);

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
//            LogUtil.log(l_GameInput);

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
