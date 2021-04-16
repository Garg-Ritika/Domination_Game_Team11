package ca.concordia.patterns.strategy;

import ca.concordia.dao.Player;
import ca.concordia.dao.Territory;
import ca.concordia.gameengine.GameEngine;
import ca.concordia.gameengine.GameSaver;
import ca.concordia.patterns.command.*;
import ca.concordia.patterns.observer.LogUtil;

import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Human Player: <br>
 * A human player that requires user interaction to make decisions.
 */

public class Human extends Strategy {

    public static final String COMMAND_SHOW_MAP = "showmap";
    public static final String COMMAND_SAVE_GAME = "savegame";
    public static final String COMMAND_DEPLOY = "deploy";
    public static final String COMMAND_ADVANCE = "advance";
    public static final String COMMAND_BOMB = "bomb";
    public static final String COMMAND_BLOCKADE = "blockade";
    public static final String COMMAND_AIRLIFT = "airlift";
    public static final String COMMAND_NEGOTIATE = "negotiate";
    public static final String COMMAND_QUIT = "quit";

    private LinkedList<Order> d_ListOfOrders = new LinkedList<Order>();


    public Human(GameEngine p_Ge) {
        super(p_Ge);
    }

    /**
     * Abstract class for LinkedList
     * @param p_Player player object for whose Order is created
     * @return list of orders
     */
    @Override
    public LinkedList<Order> create(Player p_Player) {
        d_ListOfOrders.clear();
        takeOrder(p_Player);
        return d_ListOfOrders;
    }

    /**
     * This method takes order from user and process the commands
     * Player cannot quit the game unless all armies are deployed
     * when keyboard's input is no longer needed, keyboard.close() function is called
     *
     * @param p_Player Player name
     * @return false if player's number of armies is 0 or after keyboard.close() is called
     */
    public boolean takeOrder(Player p_Player) {
        LogUtil.log("taking order ");
        Scanner l_Keyboard = new Scanner(System.in);
        boolean l_MaintainLoop = true;
        int l_Army = p_Player.getNoOfArmies();
        do {
            LogUtil.log("============================================================================================");
            LogUtil.log("| Play:MainPlay:Order  : deploy          <country-name> <num-of-armies>                    |");
            LogUtil.log("| Play:MainPlay:Order  : advance         <country-from> <country-to> <num-of-armies>       |");
            LogUtil.log("| Play:MainPlay:Order  : bomb            <country-name>                                    |");
            LogUtil.log("| Play:MainPlay:Order  : blockade        <country-name>                                    |");
            LogUtil.log("| Play:MainPlay:Order  : airlift         <source-country> <target-country> <num-of-armies> |");
            LogUtil.log("| Play:MainPlay:Order  : negotiate       <player-name>                                     |");
            LogUtil.log("| Any                  : showmap                                                           |");
            LogUtil.log("| Any                  : savegame        <filename>                                        |");
            LogUtil.log("| Any                  : quit                                                              |");
            LogUtil.log("============================================================================================");

            LogUtil.log("deploy, advance, " + p_Player.getOrderCards().toString() + ", quit" + " cards are available for player " + p_Player.getPlayerName());


            String l_CommandInput = l_Keyboard.nextLine();
            LogUtil.log(l_CommandInput);

            if ("quit".equalsIgnoreCase(l_CommandInput)) {
                //If end the game if quit is passed during the order creation, move to another player..
                if (l_Army > 0) {
                    LogUtil.log("Cannot quit as not all armies are deployed");
                } else {
                    return false;
                }
            }

            if (l_CommandInput.length() > 0) {
                String[] l_CommandArray = l_CommandInput.trim().split(" ");
                if (l_CommandArray.length > 0) {
                    String l_FirstCommand = l_CommandArray[0].toLowerCase();
                    LogUtil.log("firstCommand : " + l_FirstCommand);

                    switch (l_FirstCommand) {
                        case COMMAND_DEPLOY:
                            l_Army = processDeployCommand(p_Player, l_CommandArray, l_Army);
                            break;

                        case COMMAND_ADVANCE:
                            processAdvanceCommand(p_Player, l_CommandArray);
                            break;

                        case COMMAND_BOMB:
                            processBombCommand(p_Player, l_CommandArray);
                            break;

                        case COMMAND_BLOCKADE:
                            processBlockadeCommand(p_Player, l_CommandArray);
                            break;

                        case COMMAND_AIRLIFT:
                            processAirliftCommand(p_Player, l_CommandArray);
                            break;

                        case COMMAND_NEGOTIATE:
                            processDiplomacyCommand(p_Player, l_CommandArray);
                            break;

                        case COMMAND_SHOW_MAP:
                            d_Ge.getPhase().showMap();
                            break;

                        case COMMAND_SAVE_GAME:
                            processSaveGameCommand(l_CommandArray);
                            break;

                        default:
                            LogUtil.log("INVALID COMMAND in MainPlay:Order phase");
                    }
                }
            }
        } while (l_MaintainLoop);
        l_Keyboard.close();
        return false;
    }

    /**
     * This method process "deploy" command
     * "deploy countryID numarmies"
     *
     * @param p_Player  playername
     * @param p_Command actions for the player e.g. deploy
     */
    private int processDeployCommand(Player p_Player, String[] p_Command, int p_Army) {
        LogUtil.log("deploy  command received ..... ");
        try {
            if (p_Command.length == 3) {
                String l_CountryName = p_Command[1];
                Territory l_Territory = d_Ge.getMap().getTerritoryByName(l_CountryName);
                String l_Num = p_Command[2];
                int l_NumInt = Integer.parseInt(l_Num);
                int l_ArmyCountOfPlayer = p_Player.getNoOfArmies();
                if (l_ArmyCountOfPlayer >= l_NumInt) {
                    Order l_O = new Deploy(p_Player, l_Territory, l_NumInt);
                    p_Army = p_Army - l_NumInt;
                    d_ListOfOrders.add(l_O);
                    LogUtil.log("ORDER CREATED: " + Arrays.toString(p_Command));
                } else {
                    LogUtil.log("ORDER FAILED: only " + l_ArmyCountOfPlayer + " is available to be deployed !");
                }
            }
        } catch (Exception l_E) {
            l_E.printStackTrace();
        }
        return p_Army;
    }

    /**
     * This method process "advance" command
     * "advance countrynamefrom countynameto numarmies"
     *
     * @param p_Player  playername
     * @param p_Command actions for the player e.g. deploy
     */
    private void processAdvanceCommand(Player p_Player, String[] p_Command) {
        LogUtil.log("advance command received ..");
        try {
            if (p_Command.length == 4) {
                String l_CountryNameSource = p_Command[1];
                Territory l_TerritorySource = d_Ge.getMap().getTerritoryByName(l_CountryNameSource);
                String l_CountryNameTarget = p_Command[2];
                Territory l_TerritoryTarget = d_Ge.getMap().getTerritoryByName(l_CountryNameTarget);
                String l_Num = p_Command[3];
                int l_NumInt = Integer.parseInt(l_Num);
                if (p_Player.getIsNegotiatedPlayer() == true
                        && l_TerritoryTarget.getOwner().getIsNegotiatedPlayer() == true) {
                    LogUtil.log(p_Player + " cannot attack the target country");
                } else {
                    Order l_O = new Advance(p_Player, l_TerritorySource, l_TerritoryTarget, l_NumInt);
                    d_ListOfOrders.add(l_O);
                    LogUtil.log(p_Player.getOrderCards().toString() + " cards are available for player " + p_Player.getPlayerName());

                }
            }
        } catch (Exception l_E) {
            l_E.printStackTrace();
        }
    }

    /**
     * This method process "bomb" command
     * "bomb countryID"
     *
     * @param p_Player  playername
     * @param p_Command actions for the player e.g. deploy
     */
    private void processBombCommand(Player p_Player, String[] p_Command) {
        LogUtil.log("bomb command received ..");
        try {
            if (p_Command.length == 2) {
                String l_CountryNameSource = p_Command[1];
                Territory l_TerritoryTarget = d_Ge.getMap().getTerritoryByName(l_CountryNameSource);
                if (p_Player.getIsNegotiatedPlayer() == true
                        && l_TerritoryTarget.getOwner().getIsNegotiatedPlayer() == true) {
                    LogUtil.log(p_Player + " cannot attack the target country");
                } else {
                    if (p_Player.getOrderCards().contains("bomb")) {
                        p_Player.removeNewOrderCard("bomb");
                        Order l_O = new Bomb(p_Player, l_TerritoryTarget);
                        d_ListOfOrders.add(l_O);
                    } else {
                        LogUtil.log("bomb order card doesnot exist");
                    }
                }
            }
        } catch (Exception l_E) {
            l_E.printStackTrace();
        }
    }

    /**
     * This is the helper method to process "blockade" command
     * "blockade countryID"
     *
     * @param p_Player  playername
     * @param p_Command actions for the player e.g. deploy
     */
    private void processBlockadeCommand(Player p_Player, String[] p_Command) {
        LogUtil.log("blockade command received ..");
        try {
            if (p_Command.length == 2) {
                String l_CountryNameSource = p_Command[1];
                Territory l_TerritorySource = d_Ge.getMap().getTerritoryByName(l_CountryNameSource);
                if (p_Player.getOrderCards().contains("blockade")) {
                    p_Player.removeNewOrderCard("blockade");
                    Order l_O = new Blockade(p_Player, l_TerritorySource);
                    d_ListOfOrders.add(l_O);
                } else {
                    LogUtil.log("blockade order card doesnot exist");
                }
            }


        } catch (Exception l_E) {
            l_E.printStackTrace();
        }
    }

    /**
     * This is the helper method to process "Airlift" command
     * "airlift sourcecountryID targetcountryID numarmies"
     *
     * @param p_Player  playername
     * @param p_Command actions for the player e.g. deploy
     */
    private void processAirliftCommand(Player p_Player, String[] p_Command) {
        LogUtil.log("airlift command received ..");
        try {
            if (p_Command.length == 4) {
                String l_CountryNameSource = p_Command[1];
                Territory l_TerritorySource = d_Ge.getMap().getTerritoryByName(l_CountryNameSource);
                String l_CountryNameTarget = p_Command[2];
                Territory l_TerritoryTarget = d_Ge.getMap().getTerritoryByName(l_CountryNameTarget);
                String l_Num = p_Command[3];
                int l_NumInt = Integer.parseInt(l_Num);
                if (p_Player.getOrderCards().contains("airlift")) {
                    p_Player.removeNewOrderCard("airlift");
                    Order l_O = new Airlift(p_Player, l_TerritorySource, l_TerritoryTarget, l_NumInt);
                    d_ListOfOrders.add(l_O);
                } else {
                    LogUtil.log("airlift order card doesnot exist");
                }

            }
        } catch (Exception l_E) {
            l_E.printStackTrace();
        }
    }

    /**
     * This is the helper method to process "Diplomacy" command
     * "negotiate playerID"
     *
     * @param p_Player  playername
     * @param p_Command actions for the player e.g. deploy
     */
    private void processDiplomacyCommand(Player p_Player, String[] p_Command) {
        LogUtil.log("diplomacy command received ..");
        try {
            if (p_Command.length == 2) {
                String l_PlayerName = p_Command[1];
                for (Player l_GamePlayer : d_Ge.getListOfPlayers()) {
                    if (l_GamePlayer.getPlayerName().equalsIgnoreCase(l_PlayerName)) {
                        if (p_Player.getOrderCards().contains("negotiate")) {
                            p_Player.removeNewOrderCard("negotiate");
                            Order l_O = new Diplomacy(p_Player, l_GamePlayer);
                            d_ListOfOrders.add(l_O);
                        } else {
                            LogUtil.log("negotiate order card doesnot exist");
                        }
                    }
                }
            }
        } catch (Exception l_E) {
            l_E.printStackTrace();
        }
    }

    /**
     * This is the helper method to process "savegame" command
     * "savegame filename"
     *
     *
     * @param p_Command actions for the player e.g. deploy
     */
    private void processSaveGameCommand(String[] p_Command){
        LogUtil.log("savegame command received ..");
        try{
            if (p_Command.length == 2) {
                String l_FileName =  p_Command[1];
                new GameSaver(d_Ge).saveFile(new File(l_FileName));
            }
        }catch (Exception l_E){
            l_E.printStackTrace();
        }
    }
}
