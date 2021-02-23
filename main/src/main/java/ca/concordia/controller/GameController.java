package ca.concordia.controller;

import ca.concordia.Main;
import ca.concordia.Observable;
import ca.concordia.controller.game.GameEngine;
import ca.concordia.controller.map.MapEditor;
import ca.concordia.model.GameModel;
import ca.concordia.view.GameView;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class GameController extends Observable {

    // gameplay commands
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

    private GameModel d_gameModel;
    private GameView d_gameView;

    public GameController(GameView p_gameView, GameModel p_gameModel){
        d_gameView = p_gameView;
        d_gameModel = p_gameModel;
    }

    public void takeCommandInput(){
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("use command \"exit\" to exit the program");
            String input = scanner.nextLine();
            System.out.println("input: " + input);
            if ("exit".equalsIgnoreCase(input)) {
                break;
            } else {
                if (input.length() > 0) {
                    String[] commandArray = input.trim().split(" ");
                    processCommands(commandArray);
                }
            }
        }
    }

    private  void processCommands(String[] command) {
        Main main = new Main();
        try {
            if (command.length > 0) {
                String firstCommand = command[0].toLowerCase();
                System.out.println("firstCommand : " + firstCommand);

                switch (firstCommand) {
                    case COMMAND_EDIT_CONTINENT:
                        processEditContinentCommand(command);
                        break;

                    case COMMAND_EDIT_COUNTRY:
                        processEditCountryCommand(command);
                        break;

                    case COMMAND_EDIT_NEIGHBOUR:
                        processEditNeighbourCommand(command);
                        break;

                    case COMMAND_SHOW_MAP:
                        processShowMapCommand();
                        break;

                    case COMMAND_SAVE_MAP:
                        processSaveMapCommand(command);
                        break;

                    case COMMAND_EDIT_MAP:
                        processEditMapCommand(command);
                        break;

                    case COMMAND_VALIDATE_MAP:
                        processValidateMapCommand();
                        break;

                    case COMMAND_LOAD_MAP:
                        processLoadGameCommand(command);
                        break;

                    default:
                        System.out.println("INVALID COMMAND");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void processEditContinentCommand(String[] command) {
        System.out.println("editcontinent command received ..... ");

        // there could be more than one "-add" and "-remove" commands
        for (int i = 0; i < command.length; i++) {
            String tag = command[i];

            if (tag.toLowerCase().startsWith("-add")) {
                // make sure the index is not increasing the size of array
                if (i + 2 < command.length) {
                    String l_ContinentName = command[++i];
                    String l_ContinentArmyCount = command[++i];
                    int l_ContinentArmyCountInteger = Integer.parseInt(l_ContinentArmyCount);

                    if (MapEditor.getInstance().addContinent(l_ContinentName, l_ContinentArmyCountInteger)) {
                        System.out.println("Successfully added continent name: " + l_ContinentName + " army: " + l_ContinentArmyCount + " into the map");
                    }

                } else {
                    System.out.println("INCOMPLETE COMMAND ");
                }

            } else if (tag.toLowerCase().startsWith("-remove")) {
                // make sure the index is not increasing the size of array
                if (i + 1 < command.length) {
                    String l_ContinentName = command[++i];

                    if (MapEditor.getInstance().removeContinent(l_ContinentName)) {
                        System.out.println("Successfully removed continent name: " + l_ContinentName + " from the map");
                    }
                } else {
                    System.out.println("INCOMPLETE COMMAND");
                }
            }
        }
    }

    private void processEditCountryCommand(String[] command) {
        System.out.println("editcountry command received ..... ");

        // there could be more than one "-add" and "-remove" commands
        for (int i = 0; i < command.length; i++) {
            String tag = command[i];

            if (tag.toLowerCase().startsWith("-add")) {
                // make sure the index is not increasing the size of array
                if (i + 2 < command.length) {
                    String l_CountryName = command[++i];
                    String l_ContinentName = command[++i];

                    if (MapEditor.getInstance().addCountry(l_CountryName, l_ContinentName)) {
                        System.out.println("Successfully added country name: " + l_CountryName + " to continent name:  " + l_ContinentName + " into the map");
                    }

                } else {
                    System.out.println("INCOMPLETE COMMAND ");
                }

            } else if (tag.toLowerCase().startsWith("-remove")) {
                // make sure the index is not increasing the size of array
                if (i + 1 < command.length) {
                    String l_CountryName = command[++i];

                    if (MapEditor.getInstance().removeCountryUsingName(l_CountryName)) {
                        System.out.println("Successfully removed country name: " + l_CountryName + " from the map");
                    }
                } else {
                    System.out.println("INCOMPLETE COMMAND");
                }
            }
        }
    }

    private void processEditNeighbourCommand(String[] command) {
        System.out.println("editneighbor command received ..... ");

        // there could be more than one "-add" and "-remove" commands
        for (int i = 0; i < command.length; i++) {
            String tag = command[i];

            if (tag.toLowerCase().startsWith("-add")) {
                // make sure the index is not increasing the size of array
                if (i + 2 < command.length) {
                    String l_CountryName = command[++i];
                    String l_NeighbourCountryName = command[++i];

                    if (MapEditor.getInstance().addNeighbor(l_CountryName,l_NeighbourCountryName)) {
                        System.out.println("Successfully added neighbour name " + l_CountryName + " to " + l_NeighbourCountryName + " into the map");
                    }

                } else {
                    System.out.println("INCOMPLETE COMMAND ");
                }

            } else if (tag.toLowerCase().startsWith("-remove")) {
                // make sure the index is not increasing the size of array
                if (i + 2 < command.length) {
                    String l_CountryName = command[++i];
                    String l_NeighbourCountryName = command[++i];

                    if (MapEditor.getInstance().removeNeighbor(l_CountryName,l_NeighbourCountryName)) {
                        System.out.println("Successfully removed neighbour name " + l_CountryName + " to " + l_NeighbourCountryName + " into the map");
                    }
                } else {
                    System.out.println("INCOMPLETE COMMAND");
                }
            }
        }
    }

    private void processShowMapCommand() {
        System.out.println("showmap command received ...");
        MapEditor.getInstance().showMap();
    }

    private void processSaveMapCommand(String[] command) {
        try {
            if(command.length == 2) {
                System.out.println("savemap command received ..");
                String filename = command[1];
                File mapPath = new File(filename);
                MapEditor.getInstance().saveMap(mapPath);
            }else{
                System.out.println("INCOMPLETE COMMAND");
            }
        } catch (IOException io) {
            io.printStackTrace();
        } catch (ArrayIndexOutOfBoundsException a){
            a.printStackTrace();
        }
    }

    private void processEditMapCommand(String[] command) {
        try {
            if(command.length == 2) {
                String filename = command[1];
                if (!filename.isEmpty()) {
                    System.out.println("editmap command received ..");
                    File mapFile = new File(filename);
                    MapEditor.getInstance().editMap(mapFile);
                }
            }else{
                System.out.println("INCOMPLETE COMMAND, create an in-memory map file from scratch");
                MapEditor.getInstance().editMap(null);
            }
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    private void processValidateMapCommand() {
        System.out.println("validatemap command received ...");
        MapEditor.getInstance().validateMap();
    }

    private void processLoadGameCommand(String[] command){
        System.out.println("load map to start game ..");
        try {
            String filename = command[1];
            if(!filename.isEmpty()){
                System.out.println("loadmap command received ..");
                File mapFile = new File(filename);
                GameEngine gameEngine = GameEngine.getInstance(MapEditor.getInstance().readMapFile(mapFile));
                gameEngine.loadMapforGame();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
