package ca.concordia.patterns.state.play;

import ca.concordia.dao.Player;
import ca.concordia.dao.Territory;
import ca.concordia.gameengine.GameEngine;
import ca.concordia.patterns.command.Deploy;
import ca.concordia.patterns.command.Order;
import ca.concordia.patterns.observer.LogUtil;

import java.util.Scanner;

public class Attack extends MainPlay {

    public static final String COMMAND_SHOW_MAP = "showmap";
    public static final String COMMAND_DEPLOY = "deploy";
    public static final String COMMAND_ADVANCE = "advance";
    public static final String COMMAND_BOMB = "bomb";
    public static final String COMMAND_BLOCKADE = "blockade";
    public static final String COMMAND_AIRLIFT = "airlift";
    public static final String COMMAND_NEGOTIATE = "negotiate";


    public Attack(GameEngine p_ge) {
        super(p_ge);
    }

    public void next() {
        d_ge.setPhase(new Fortify(d_ge));
        d_ge.getPhase().next();
    }

    public void reinforce() {
        printInvalidCommandMessage();
    }

    @Override
    public void attack() {
        System.out.println("attacking");
        for (Player l_Player : d_ge.getListOfPlayers()) {
            boolean l_MaintainLoop = true;
            do {
                takeOrder(l_Player);
                if (l_Player.getNoOfArmies() < 1) {
                    LogUtil.log("All the reinforcement armies have been placed ..");
                    System.out.println("All the reinforcement armies have been placed ..");
                    break;
                }
            } while (l_MaintainLoop);
        }
        next();
    }

    public void fortify() {
        printInvalidCommandMessage();
    }


    void takeOrder(Player p_Player) {
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
            String l_CommandInput = keyboard.nextLine();
            LogUtil.log(l_CommandInput);

            if ("quit".equalsIgnoreCase(l_CommandInput)) {
                //TODO: end the game if quit is passed during the attack ?

                d_ge.getPhase().endGame();
                return;
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
    }

    /**
     * Helper method to process deploy command
     *
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
                    p_Player.setNoOfArmies(l_ArmyCountOfPlayer - l_NumInt);
                    Order o = new Deploy(p_Player, l_Territory, l_NumInt);
                    p_Player.createOrder(o);
                } else {
                    LogUtil.log("TRY AGAIN: only " + l_ArmyCountOfPlayer + " is available to be deployed !");
                    System.out.println("TRY AGAIN: only " + l_ArmyCountOfPlayer + " is available to be deployed !");
                }
            }
        } catch (Exception l_E) {
            l_E.printStackTrace();
        }
    }

    // TODO:
    private void processAdvanceCommand(Player player, String[] p_Command) {
        System.out.println("advance command received ..");
    }

    // TODO:
    private void processBombCommand(Player player, String[] p_Command) {
        System.out.println("bomb command received ..");

    }

    // TODO:
    private void processBlockadeCommand(Player player, String[] p_Command) {
        System.out.println("blockade command received ..");

    }

    // TODO:
    private void processAirliftCommand(Player player, String[] p_Command) {
        System.out.println("airlift command received ..");

    }

    // TODO:
    private void processDiplomacyCommand(Player player, String[] p_Command) {
        System.out.println("diplomacy command received ..");

    }

}