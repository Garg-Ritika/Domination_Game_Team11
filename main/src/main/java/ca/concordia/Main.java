package ca.concordia;

import ca.concordia.map.MapEditor;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main implements IConstants {
    public static void main(String[] args) {

        System.out.println("Map Editor");
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Type x to exit");
            String input = scanner.nextLine();
            System.out.println("input: " + input);
            if ("x".equalsIgnoreCase(input)) {
                break;
            } else {
                if (input.length() > 0) {
                    String[] commandArray = input.trim().split(" ");
                    process(commandArray);
                }
            }
        }

        System.out.println("Args length" + args.length);
        for (int i = 0; i < args.length; i++) {
            System.out.println(args[i]);
        }
    }

    private static void process(String[] command) {
        Main main = new Main();
        try {
            if (command.length > 0) {
                String firstCommand = command[0].toLowerCase();
                System.out.println("firstCommand : " + firstCommand);

                switch (firstCommand) {
                    case COMMAND_EDIT_CONTINENT:
                        main.processEditContinentCommand(command);
                        break;

                    case COMMAND_EDIT_COUNTRY:
                        main.processEditCountryCommand(command);
                        break;

                    case COMMAND_EDIT_NEIGHBOUR:
                        main.processEditNeighbourCommand(command);
                        break;

                    case COMMAND_SHOW_MAP:
                        main.processShowMapCommand();
                        break;

                    case COMMAND_SAVE_MAP:
                        main.processSaveMapCommand(command);
                        break;

                    case COMMAND_EDIT_MAP:
                        main.processEditMapCommand(command);
                        break;

                    case COMMAND_VALIDATE_MAP:
                        main.processValidateMapCommand();
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
                    String continentID = command[++i];
                    int continentIdInteger = Integer.parseInt(continentID);
                    String continentValue = command[++i];

                    if (MapEditor.getInstance().addContinent(continentIdInteger, continentValue)) {
                        System.out.println("Successfully added continent id: " + continentID + " name: " + continentValue + " into the map");
                    }

                } else {
                    System.out.println("INCOMPLETE COMMAND ");
                }

            } else if (tag.toLowerCase().startsWith("-remove")) {
                // make sure the index is not increasing the size of array
                if (i + 1 < command.length) {
                    String continentId = command[++i];
                    int continentIdInt = Integer.parseInt(continentId);

                    if (MapEditor.getInstance().removeContinent(continentIdInt)) {
                        System.out.println("Successfully removed continent id: " + continentId + " from the map");
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
                    String countryID = command[++i];
                    int countryIdInteger = Integer.parseInt(countryID);
                    String continentID = command[++i];
                    int continentIDInteger = Integer.parseInt(continentID);

                    if (MapEditor.getInstance().addCountry(countryIdInteger, continentIDInteger)) {
                        System.out.println("Successfully added country id: " + countryIdInteger + " to continent id:  " + continentIDInteger + " into the map");
                    }

                } else {
                    System.out.println("INCOMPLETE COMMAND ");
                }

            } else if (tag.toLowerCase().startsWith("-remove")) {
                // make sure the index is not increasing the size of array
                if (i + 1 < command.length) {
                    String countryId = command[++i];
                    int countryIdInteger = Integer.parseInt(countryId);

                    if (MapEditor.getInstance().removeCountry(countryIdInteger)) {
                        System.out.println("Successfully removed country id: " + countryIdInteger + " from the map");
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
                    String countryID = command[++i];
                    int countryIdInteger = Integer.parseInt(countryID);
                    String neighbourCountryID = command[++i];
                    int neighbourCounterIDInteger = Integer.parseInt(neighbourCountryID);

                    if (MapEditor.getInstance().addNeighbor(countryIdInteger, neighbourCounterIDInteger)) {
                        System.out.println("Successfully added neighbour id " + neighbourCounterIDInteger + " to " + countryIdInteger + " into the map");
                    }

                } else {
                    System.out.println("INCOMPLETE COMMAND ");
                }

            } else if (tag.toLowerCase().startsWith("-remove")) {
                // make sure the index is not increasing the size of array
                if (i + 2 < command.length) {
                    String countryID = command[++i];
                    int countryIdInteger = Integer.parseInt(countryID);
                    String neighbourCountryID = command[++i];
                    int neighbourCounterIDInteger = Integer.parseInt(neighbourCountryID);

                    if (MapEditor.getInstance().removeNeighbor(countryIdInteger, neighbourCounterIDInteger)) {
                        System.out.println("Successfully removed neighbour id " + neighbourCounterIDInteger + " to " + countryIdInteger + " into the map");
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
            System.out.println("savemap command received ..");
            String filename = command[1];
            File mapPath = new File(filename);
            MapEditor.getInstance().saveMap(mapPath);

        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    private void processEditMapCommand(String[] command) {
        try {
            String filename = command[1];
            if (!filename.isEmpty()) {
                System.out.println("editmap command received ..");
                File mapFile = new File(filename);
                MapEditor.getInstance().editMap(mapFile);
            }
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    private void processValidateMapCommand() {
        System.out.println("validatemap command received ...");
        MapEditor.getInstance().validateMap();
    }

}