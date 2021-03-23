package ca.concordia.patterns.state.play;

import ca.concordia.dao.Player;
import ca.concordia.dao.Territory;
import ca.concordia.gameengine.GameEngine;
import ca.concordia.patterns.command.*;
import ca.concordia.patterns.observer.LogUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * This is the OrderCreationPhase class that extends the MainPlay class
 * This class takes the orders for players and place the reinforcement armies
 * Deploy, Advance, Bomb, blockade, Airlift, Diplomacy - These commands works here,
 * for rest of the commands it throws invalid command message to the user
 */
public class OrderCreationPhase extends MainPlay {

    public static final String COMMAND_SHOW_MAP = "showmap";
    public static final String COMMAND_DEPLOY = "deploy";
    public static final String COMMAND_ADVANCE = "advance";
    public static final String COMMAND_BOMB = "bomb";
    public static final String COMMAND_BLOCKADE = "blockade";
    public static final String COMMAND_AIRLIFT = "airlift";
    public static final String COMMAND_NEGOTIATE = "negotiate";
    public static final String COMMAND_QUIT = "quit";



    /**
     * Constructor that takes GameEngine object as argument
     * @param p_ge GameEngine object
     */
    public OrderCreationPhase(GameEngine p_ge) {
        super(p_ge);
    }

    /**
     * This method moves to next phase - OrderExecutionPhase
     */
    public void next() {
        System.out.println("--> setting order execution phase ");
        d_ge.setPhase(new OrderExecutionPhase(d_ge));
        d_ge.getPhase().fortify();
    }

    /**
     * This method shows invalid command message to the user
     */
    public void reinforce() {
        printInvalidCommandMessage();
    }

    /**
     * This method starts the creation of order for players
     * takes orders for players and place all the reinforcement armies
     * and finishes the order creation
     */
    @Override
    public void attack() {
        System.out.println("--> start of order creation");
        for (Player l_Player : d_ge.getListOfPlayers()) {
            boolean l_MaintainLoop = true;
            do {
                LogUtil.log("Taking order for player: " + l_Player.getPlayerName());
                l_MaintainLoop = takeOrder(l_Player);
                if (l_Player.getNoOfArmies() < 1) {
                    LogUtil.log("All the reinforcement armies have been placed ..");
                    break;
                }
            } while (l_MaintainLoop);
        }
        System.out.println("--> finish of order creating ");
        next();
    }

    /**
     * This method shows invalid command message to the user
     */
    public void fortify() {
        printInvalidCommandMessage();
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
        System.out.println("taking order ");
        Scanner keyboard = new Scanner(System.in);
        boolean l_MaintainLoop = true;
        do {
            System.out.println("============================================================================================");
            System.out.println("| Play:MainPlay:Order  : deploy          <country-name> <num-of-armies>                    |");
            System.out.println("| Play:MainPlay:Order  : advance         <country-from> <country-to> <num-of-armies>       |");
            System.out.println("| Play:MainPlay:Order  : bomb            <country-name>                                    |");
            System.out.println("| Play:MainPlay:Order  : blockade        <country-name>                                    |");
            System.out.println("| Play:MainPlay:Order  : airlift         <source-country> <target-country> <num-of-armies> |");
            System.out.println("| Play:MainPlay:Order  : negotiate       <player-name>                                     |");
            System.out.println("| Any                  : showmap                                                           |");
            System.out.println("| Any                  : quit                                                              |");
            System.out.println("============================================================================================");

            System.out.println("deploy, advance, "+p_Player.getOrderCards().toString()+", quit" +" cards are available for player "+ p_Player.getPlayerName());


            String l_CommandInput = keyboard.nextLine();
            LogUtil.log(l_CommandInput);

            if ("quit".equalsIgnoreCase(l_CommandInput)) {
                //If end the game if quit is passed during the order creation, move to another player..
                if (p_Player.getNoOfArmies() >0){
                    LogUtil.log("Cannot quit as not all armies are deployed");
                    System.out.println("Cannot quit as not all armies are deployed");
                }else{
                    return false;
                }
            }

            if (l_CommandInput.length() > 0) {
                String[] l_CommandArray = l_CommandInput.trim().split(" ");
                if (l_CommandArray.length > 0) {
                    String l_FirstCommand = l_CommandArray[0].toLowerCase();
                    System.out.println("firstCommand : " + l_FirstCommand);

                    switch (l_FirstCommand) {
                        case COMMAND_DEPLOY:
                            processDeployCommand(p_Player, l_CommandArray);
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
                            showMap();
                            break;

                        default:
                            System.out.println("INVALID COMMAND in MainPlay:Order phase");
                    }
                }
            }
        } while (l_MaintainLoop);
        keyboard.close();
        return false;
    }

    /**
     * This method process "deploy" command
     *  "deploy countryID numarmies"
     * @param p_Player  playername
     * @param p_Command actions for the player e.g. deploy
     */
    private void processDeployCommand(Player p_Player, String[] p_Command) {
        System.out.println("deploy  command received ..... ");
        try {
            if (p_Command.length == 3) {
                String l_CountryName = p_Command[1];
                Territory l_Territory = d_ge.getMap().getTerritoryByName(l_CountryName);
                String l_Num = p_Command[2];
                int l_NumInt = Integer.parseInt(l_Num);
                int l_ArmyCountOfPlayer = p_Player.getNoOfArmies();
                if (l_ArmyCountOfPlayer >= l_NumInt) {
                    Order o = new Deploy(p_Player, l_Territory, l_NumInt);
                    p_Player.createOrder(o);
                    LogUtil.log("ORDER CREATED: " + Arrays.toString(p_Command));
                } else {
                    LogUtil.log("ORDER FAILED: only " + l_ArmyCountOfPlayer + " is available to be deployed !");
                    System.out.println("ORDER FAILED: only " + l_ArmyCountOfPlayer + " is available to be deployed !");
                }
            }
        } catch (Exception l_E) {
            l_E.printStackTrace();
        }
    }

    /**
     * This method process "advance" command
     *  "advance countrynamefrom countynameto numarmies"
     * @param p_Player  playername
     * @param p_Command actions for the player e.g. deploy
     */
    private void processAdvanceCommand(Player p_Player, String[] p_Command) {
        System.out.println("advance command received ..");
        try {
            if (p_Command.length == 4) {
                String l_CountryNameSource = p_Command[1];
                Territory l_TerritorySource = d_ge.getMap().getTerritoryByName(l_CountryNameSource);
                String l_CountryNameTarget = p_Command[2];
                Territory l_TerritoryTarget = d_ge.getMap().getTerritoryByName(l_CountryNameTarget);
                String l_Num = p_Command[3];
                int l_NumInt = Integer.parseInt(l_Num);
                int l_ArmyCountOfPlayer = p_Player.getNoOfArmies();
                if (p_Player.getIsNegotiatedPlayer() == false && l_TerritoryTarget.getOwner().getIsNegotiatedPlayer() == false) {
                    System.out.println(p_Player + " cannot attack the target country");
                }
                else {
                /*if (l_ArmyCountOfPlayer >= l_NumInt) {
                    p_Player.setNoOfArmies(l_ArmyCountOfPlayer - l_NumInt);*/
                Order o = new Advance(p_Player, l_TerritorySource, l_TerritoryTarget, l_NumInt);
                p_Player.createOrder(o);
                System.out.println(p_Player.getOrderCards().toString() + " cards are available for player " + p_Player.getPlayerName());
                /*} else {
                    LogUtil.log("TRY AGAIN: only " + l_ArmyCountOfPlayer + " is available to advance!");
                    System.out.println("TRY AGAIN: only " + l_ArmyCountOfPlayer + " is available to advance!");
                }*/
            }
            }
        } catch (Exception l_E) {
            l_E.printStackTrace();
        }
    }

    /**
     * This method process "bomb" command
     *  "bomb countryID"
     * @param p_Player  playername
     * @param p_Command actions for the player e.g. deploy
     */
    private void processBombCommand(Player p_Player, String[] p_Command) {
        System.out.println("bomb command received ..");
        try {
            if (p_Command.length == 2) {
                String l_CountryNameSource = p_Command[1];
                Territory l_TerritoryTarget = d_ge.getMap().getTerritoryByName(l_CountryNameSource);
                if (p_Player.getIsNegotiatedPlayer() == false && l_TerritoryTarget.getOwner().getIsNegotiatedPlayer() == false) {
                    System.out.println(p_Player + " cannot attack the target country");
                }
                else {
                    if (p_Player.getOrderCards().contains("bomb")) {
                        p_Player.removeNewOrderCard("bomb");
                        Order o = new Bomb(p_Player, l_TerritoryTarget);
                        p_Player.createOrder(o);
                    } else {
                        System.out.println("bomb order card doesnot exist");
                    }
                }
            }
        } catch (Exception l_E) {
            l_E.printStackTrace();
        }
    }

    /**
     * This is the helper method to process "blockade" command
     *  "blockade countryID"
     * @param p_Player  playername
     * @param p_Command actions for the player e.g. deploy
     */
    private void processBlockadeCommand(Player p_Player, String[] p_Command) {
        System.out.println("blockade command received ..");
        try {
            if (p_Command.length == 2) {
                String l_CountryNameSource = p_Command[1];
                Territory l_TerritorySource = d_ge.getMap().getTerritoryByName(l_CountryNameSource);
                if (p_Player.getOrderCards().contains("blockade")){
                    p_Player.removeNewOrderCard("blockade");
                    Order o = new Blockade(p_Player, l_TerritorySource);
                    p_Player.createOrder(o);
                }

                else{
                    System.out.println("blockade order card doesnot exist");
                }
            }


        } catch (Exception l_E) {
            l_E.printStackTrace();
        }
    }

    /**
     * This is the helper method to process "Airlift" command
     *  "airlift sourcecountryID targetcountryID numarmies"
     * @param p_Player  playername
     * @param p_Command actions for the player e.g. deploy
     */
    private void processAirliftCommand(Player p_Player, String[] p_Command) {
        System.out.println("airlift command received ..");
        try {
            if (p_Command.length == 4) {
                String l_CountryNameSource = p_Command[1];
                Territory l_TerritorySource = d_ge.getMap().getTerritoryByName(l_CountryNameSource);
                String l_CountryNameTarget = p_Command[2];
                Territory l_TerritoryTarget = d_ge.getMap().getTerritoryByName(l_CountryNameTarget);
                String l_Num = p_Command[3];
                int l_NumInt = Integer.parseInt(l_Num);
                if (p_Player.getOrderCards().contains("airlift")) {
                    p_Player.removeNewOrderCard("airlift");
                    Order o = new Airlift(p_Player, l_TerritorySource, l_TerritoryTarget, l_NumInt);
                    p_Player.createOrder(o);
                }
                else{
                    System.out.println("airlift order card doesnot exist");
                }

            }
        } catch (Exception l_E) {
            l_E.printStackTrace();
        }
    }

    /**
     * This is the helper method to process "Diplomacy" command
     *  "negotiate playerID"
     * @param p_Player  playername
     * @param p_Command actions for the player e.g. deploy
     */
    private void processDiplomacyCommand(Player p_Player, String[] p_Command) {
        System.out.println("diplomacy command received ..");
        try {
            if (p_Command.length == 2) {
                String l_PlayerName = p_Command[1];
                for (Player l_GamePlayer :d_ge.getListOfPlayers()){
                    if (l_GamePlayer.getPlayerName().equalsIgnoreCase(l_PlayerName)){
                        if (p_Player.getOrderCards().contains("negotiate")) {
                            p_Player.removeNewOrderCard("negotiate");
                            Order o = new Diplomacy(p_Player, l_GamePlayer);
                            p_Player.createOrder(o);
                        }
                        else{
                            System.out.println("negotiate order card doesnot exist");
                        }
                    }
                }
            }
        } catch (Exception l_E) {
            l_E.printStackTrace();
        }
    }

}