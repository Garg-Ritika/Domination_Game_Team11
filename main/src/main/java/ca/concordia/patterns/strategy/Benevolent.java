package ca.concordia.patterns.strategy;

import ca.concordia.dao.Country;
import ca.concordia.dao.Player;
import ca.concordia.dao.Territory;
import ca.concordia.gameengine.GameEngine;
import ca.concordia.patterns.command.*;
import ca.concordia.patterns.observer.LogUtil;

import java.util.*;

/**
 * Benevolent Player: <br>
 * A benevolent computer player strategy that focuses on protecting its weak countries (deploys on its weakest country, never
 * attacks, then moves its armies in order to reinforce its weaker country).
 */

public class Benevolent extends Strategy {

    public static final String COMMAND_SHOW_MAP = "showmap";
    public static final String COMMAND_DEPLOY = "deploy";
    public static final String COMMAND_BLOCKADE = "blockade";
    public static final String COMMAND_AIRLIFT = "airlift";
    public static final String COMMAND_NEGOTIATE = "negotiate";
    List<String> d_Order_Names = new ArrayList<>();
    GameEngine d_ge;
    private LinkedList<Order> d_ListOfOrders = new LinkedList<Order>();

    /**
     * Constructor to override the method depending on the input parameters
     * @param p_Ge GameEngine object
     */
    public Benevolent(GameEngine p_Ge) {
        super(p_Ge);
        this.d_ge = p_Ge;
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
        boolean l_MaintainLoop = true;
        int l_Army = p_Player.getNoOfArmies();
        d_Order_Names.clear();

        do {

               /* no need to print this part for non-human strategy
            LogUtil.log("============================================================================================");
            LogUtil.log("| Play:MainPlay:Order  : deploy          <country-name> <num-of-armies>                    |");
            LogUtil.log("| Play:MainPlay:Order  : advance         <country-from> <country-to> <num-of-armies>       |");
            LogUtil.log("| Play:MainPlay:Order  : bomb            <country-name>                                    |");
            LogUtil.log("| Play:MainPlay:Order  : blockade        <country-name>                                    |");
            LogUtil.log("| Play:MainPlay:Order  : airlift         <source-country> <target-country> <num-of-armies> |");
            LogUtil.log("| Play:MainPlay:Order  : negotiate       <player-name>                                     |");
            LogUtil.log("| Any                  : showmap                                                           |");
            LogUtil.log("| Any                  : quit                                                              |");
            LogUtil.log("============================================================================================");

            LogUtil.log("deploy, advance, " + p_Player.getOrderCards().toString() + ", quit" + " cards are available for player " + p_Player.getPlayerName());
            */

            String l_CommandInput = null;
            Country l_MaxArmyCountry = p_Player.getListOfCountries().get(0);
            Country l_MinArmyCountry = l_MaxArmyCountry;
            for (int i = 0; i < p_Player.getListOfCountries().size(); i++) {
                if (p_Player.getListOfCountries().get(i).getArmyCount() > l_MaxArmyCountry.getArmyCount()) {
                    l_MaxArmyCountry = p_Player.getListOfCountries().get(i);
                }
                if (p_Player.getListOfCountries().get(i).getArmyCount() < l_MinArmyCountry.getArmyCount()) {
                    l_MinArmyCountry = p_Player.getListOfCountries().get(i);
                }
            }

            String l_TargetCountryName = l_MinArmyCountry.getName();

            Random l_Rand = new Random();
            System.out.println(d_Order_Names.toString());
            System.out.println(d_Order_Names.isEmpty());
            if (d_Order_Names.isEmpty()) {
                l_CommandInput = "deploy " + l_TargetCountryName + " " + p_Player.getNoOfArmies();
                d_Order_Names.add("deploy");
            } else if (d_Order_Names.contains("deploy") && !d_Order_Names.contains("airlift") && !d_Order_Names.contains("bomb") && !d_Order_Names.contains("blockade") && !d_Order_Names.contains("diplomacy")) {
                if (p_Player.getOrderCards().isEmpty()) {
                    return false;
                }
                for (String l_Card : p_Player.getOrderCards()) {
                    if (l_Card.equals("airlift")) {
                        l_CommandInput = l_Card + " " + l_MaxArmyCountry.getName() + " " + l_MinArmyCountry.getName() + " " + p_Player.getNoOfArmies();
                        d_Order_Names.add("airlift");
                    } else if (l_Card.equals("blockade")) {
                        l_CommandInput = l_Card + " " + l_MinArmyCountry.getName() + " " + p_Player.getNoOfArmies();
                        d_Order_Names.add("blockade");
                    } else if (l_Card.equals("bomb")) {
                        LogUtil.log("Does not execute bomb l_Card");
                        d_Order_Names.add("bomb");
                        return false;
                    } else if (l_Card.equals("diplomacy")) {
                        Player NegotiatePlayer = null;
                        for (int i = 0; i < d_ge.getListOfPlayers().size(); i++) {
                            int randomIndex = l_Rand.nextInt(d_ge.getListOfPlayers().size());
                            if (!p_Player.equals(d_ge.getListOfPlayers().get(randomIndex))) {
                                NegotiatePlayer = d_ge.getListOfPlayers().get(randomIndex);
                            }
                        }
                        l_CommandInput = l_Card + " " + NegotiatePlayer;
                        d_Order_Names.add("diplomacy");
                    } else {
                        LogUtil.log("Quitting here");
                        return false;
                    }
                }
            } else {
                return false;
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

                        default:
                            LogUtil.log("INVALID COMMAND in MainPlay:Order phase");
                    }
                }
            }
        } while (l_MaintainLoop);
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
}
