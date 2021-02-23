package ca.concordia.controller;

import ca.concordia.Main;
import ca.concordia.Observable;
import ca.concordia.controller.game.GameEngine;
import ca.concordia.controller.map.MapEditor;
import ca.concordia.model.GameModel;
import ca.concordia.model.Map;
import ca.concordia.view.GameView;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * GameController is the controller class in MVC pattern.
 * It is supposed to be the main class for all the controller functions
 * <p>
 * Currently, it is taking command input for the game
 */
public class GameController extends Observable {

    public static final String COMMAND_LOAD_MAP = "loadmap";
    public static final String COMMAND_GAME_PLAYER = "gameplayer";
    public static final String COMMAND_ASSIGN_COUNTRIES = "assigncountries";
    public static final String COMMAND_DEPLOY = "deploy";


    // .map editor commands ..
    public static final String COMMAND_EDIT_CONTINENT = "editcontinent";
    public static final String COMMAND_EDIT_COUNTRY = "editcountry";
    public static final String COMMAND_EDIT_NEIGHBOUR = "editneighbor";

    public static final String COMMAND_SHOW_MAP = "showmap";
    public static final String COMMAND_SAVE_MAP = "savemap";
    public static final String COMMAND_EDIT_MAP = "editmap";
    public static final String COMMAND_VALIDATE_MAP = "validatemap";

    private GameModel d_GameModel;
    private GameView d_GameView;

    /**
     * Game Controller constructor
     *
     * @param p_GameView  Game View
     * @param p_GameModel Game model
     */
    public GameController(GameView p_GameView, GameModel p_GameModel) {
        d_GameView = p_GameView;
        d_GameModel = p_GameModel;
    }

    /**
     * Helper method to take all the command input for the mapeditor ..
     */
    public void takeCommandInput() {
        Scanner l_Scanner = new Scanner(System.in);
        while (true) {
            System.out.println("use command \"exit\" to exit the program");
            String l_Input = l_Scanner.nextLine();
            System.out.println("input: " + l_Input);
            if ("exit".equalsIgnoreCase(l_Input)) {
                break;
            } else {
                if (l_Input.length() > 0) {
                    String[] l_CommandArray = l_Input.trim().split(" ");
                    processCommands(l_CommandArray);
                }
            }
        }
    }

    /**
     * process all the mapeditor commands as per commands and call the right method accordingly
     *
     * @param p_Command command array
     */
    private void processCommands(String[] p_Command) {
        Main l_Main = new Main();
        try {
            if (p_Command.length > 0) {
                String l_FirstCommand = p_Command[0].toLowerCase();
                System.out.println("firstCommand : " + l_FirstCommand);

                switch (l_FirstCommand) {
                    case COMMAND_EDIT_CONTINENT:
                        processEditContinentCommand(p_Command);
                        break;

                    case COMMAND_EDIT_COUNTRY:
                        processEditCountryCommand(p_Command);
                        break;

                    case COMMAND_EDIT_NEIGHBOUR:
                        processEditNeighbourCommand(p_Command);
                        break;

                    case COMMAND_SHOW_MAP:
                        processShowMapCommand();
                        break;

                    case COMMAND_SAVE_MAP:
                        processSaveMapCommand(p_Command);
                        break;

                    case COMMAND_EDIT_MAP:
                        processEditMapCommand(p_Command);
                        break;

                    case COMMAND_VALIDATE_MAP:
                        processValidateMapCommand();
                        break;

                    case COMMAND_LOAD_MAP:
                        processLoadGameCommand(p_Command);
                        break;

                    default:
                        System.out.println("INVALID COMMAND");
                }
            }
        } catch (Exception p_E) {
            p_E.printStackTrace();
        }
    }

    /**
     * Helper method process edit continent command
     * format: editcontinet -add asia 1 - add africa 2
     *
     * @param p_Command command array
     */
    private void processEditContinentCommand(String[] p_Command) {
        System.out.println("editcontinent command received ..... ");

        // there could be more than one "-add" and "-remove" commands
        for (int l_I = 0; l_I < p_Command.length; l_I++) {
            String l_Tag = p_Command[l_I];

            if (l_Tag.toLowerCase().startsWith("-add")) {
                // make sure the index is not increasing the size of array
                if (l_I + 2 < p_Command.length) {
                    String l_ContinentName = p_Command[++l_I];
                    String l_ContinentArmyCount = p_Command[++l_I];
                    int l_ContinentArmyCountInteger = Integer.parseInt(l_ContinentArmyCount);

                    if (MapEditor.getInstance().addContinent(l_ContinentName, l_ContinentArmyCountInteger)) {
                        System.out.println("Successfully added continent name: " + l_ContinentName + " army: " + l_ContinentArmyCount + " into the map");
                    }

                } else {
                    System.out.println("INCOMPLETE COMMAND ");
                }

            } else if (l_Tag.toLowerCase().startsWith("-remove")) {
                // make sure the index is not increasing the size of array
                if (l_I + 1 < p_Command.length) {
                    String l_ContinentName = p_Command[++l_I];

                    if (MapEditor.getInstance().removeContinent(l_ContinentName)) {
                        System.out.println("Successfully removed continent name: " + l_ContinentName + " from the map");
                    }
                } else {
                    System.out.println("INCOMPLETE COMMAND");
                }
            }
        }
    }

    /**
     * Helper method to process all the country command
     * format : editcountry india asia
     *
     * @param p_Command command array
     */
    private void processEditCountryCommand(String[] p_Command) {
        System.out.println("editcountry command received ..... ");

        // there could be more than one "-add" and "-remove" commands
        for (int l_I = 0; l_I < p_Command.length; l_I++) {
            String l_Tag = p_Command[l_I];

            if (l_Tag.toLowerCase().startsWith("-add")) {
                // make sure the index is not increasing the size of array
                if (l_I + 2 < p_Command.length) {
                    String l_CountryName = p_Command[++l_I];
                    String l_ContinentName = p_Command[++l_I];

                    if (MapEditor.getInstance().addCountry(l_CountryName, l_ContinentName)) {
                        System.out.println("Successfully added country name: " + l_CountryName + " to continent name:  " + l_ContinentName + " into the map");
                    }

                } else {
                    System.out.println("INCOMPLETE COMMAND ");
                }

            } else if (l_Tag.toLowerCase().startsWith("-remove")) {
                // make sure the index is not increasing the size of array
                if (l_I + 1 < p_Command.length) {
                    String l_CountryName = p_Command[++l_I];

                    if (MapEditor.getInstance().removeCountryUsingName(l_CountryName)) {
                        System.out.println("Successfully removed country name: " + l_CountryName + " from the map");
                    }
                } else {
                    System.out.println("INCOMPLETE COMMAND");
                }
            }
        }
    }

    /**
     * Helper method to process all the neighbors
     * format : editneighbour india china
     *
     * @param p_Command command array
     */
    private void processEditNeighbourCommand(String[] p_Command) {
        System.out.println("editneighbor command received ..... ");

        // there could be more than one "-add" and "-remove" commands
        for (int l_I = 0; l_I < p_Command.length; l_I++) {
            String p_Tag = p_Command[l_I];

            if (p_Tag.toLowerCase().startsWith("-add")) {
                // make sure the index is not increasing the size of array
                if (l_I + 2 < p_Command.length) {
                    String l_CountryName = p_Command[++l_I];
                    String l_NeighbourCountryName = p_Command[++l_I];

                    if (MapEditor.getInstance().addNeighbor(l_CountryName, l_NeighbourCountryName)) {
                        System.out.println("Successfully added neighbour name " + l_CountryName + " to " + l_NeighbourCountryName + " into the map");
                    }

                } else {
                    System.out.println("INCOMPLETE COMMAND ");
                }

            } else if (p_Tag.toLowerCase().startsWith("-remove")) {
                // make sure the index is not increasing the size of array
                if (l_I + 2 < p_Command.length) {
                    String l_CountryName = p_Command[++l_I];
                    String l_NeighbourCountryName = p_Command[++l_I];

                    if (MapEditor.getInstance().removeNeighbor(l_CountryName, l_NeighbourCountryName)) {
                        System.out.println("Successfully removed neighbour name " + l_CountryName + " to " + l_NeighbourCountryName + " into the map");
                    }
                } else {
                    System.out.println("INCOMPLETE COMMAND");
                }
            }
        }
    }

    /**
     * Helper method to process "showmap" command in mapeditor pary
     */
    private void processShowMapCommand() {
        System.out.println("showmap command received ...");
        MapEditor.getInstance().showMap();
    }

    /**
     * Helper method to process "savemap" command
     *
     * @param p_Command command array
     */
    private void processSaveMapCommand(String[] p_Command) {
        try {
            if (p_Command.length == 2) {
                System.out.println("savemap command received ..");
                String l_Filename = p_Command[1];
                File l_MapPath = new File(l_Filename);
                MapEditor.getInstance().saveMap(l_MapPath);
            } else {
                System.out.println("INCOMPLETE COMMAND");
            }
        } catch (IOException p_Io) {
            p_Io.printStackTrace();
        } catch (ArrayIndexOutOfBoundsException p_A) {
            p_A.printStackTrace();
        }
    }

    /**
     * Helper method to process editmap command
     *
     * @param p_Command command array
     */
    private void processEditMapCommand(String[] p_Command) {
        try {
            if (p_Command.length == 2) {
                String l_Filename = p_Command[1];
                if (!l_Filename.isEmpty()) {
                    System.out.println("editmap command received ..");
                    File l_MapFile = new File(l_Filename);
                    MapEditor.getInstance().editMap(l_MapFile);
                }
            } else {
                System.out.println("INCOMPLETE COMMAND, create an in-memory map file from scratch");
                MapEditor.getInstance().editMap(null);
            }
        } catch (IOException p_Io) {
            p_Io.printStackTrace();
        }
    }

    /**
     * Helper method to process validate map command
     */
    private void processValidateMapCommand() {
        System.out.println("validatemap command received ...");
        MapEditor.getInstance().validateMap();
    }

    /**
     * Helper method to process loadmap command to start the game play on a map
     *
     * @param p_Command command array
     */
    private void processLoadGameCommand(String[] p_Command) {
        System.out.println("load map to start game ..");
        try {
            String l_Filename = p_Command[1];
            if (!l_Filename.isEmpty()) {
                System.out.println("loadmap command received ..");
                File l_MapFile = new File(l_Filename);
                if (l_MapFile.exists()) {
                    Map l_Map = MapEditor.getInstance().readMapFile(l_MapFile);
                    GameEngine l_GameEngine = GameEngine.getInstance(l_Map);
                    l_GameEngine.loadMapforGame();
                } else {
                    System.out.println("The map file" + l_MapFile.getAbsolutePath() + "doesn't exists");
                }
            }
        } catch (Exception p_E) {
            p_E.printStackTrace();
        }
    }
}
