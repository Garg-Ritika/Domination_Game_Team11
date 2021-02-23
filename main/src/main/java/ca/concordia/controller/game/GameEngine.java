package ca.concordia.controller.game;

import ca.concordia.controller.GameController;
import ca.concordia.model.*;

import java.util.Scanner;

/**
 * Game Engine class that starts with "loadmap" command and automatically ends after mainloop phases
 */
public class GameEngine {

    private static GameEngine d_Instance = null;
    private final Map d_CurrentMap;
    private Graph d_Graph;
    private final PlayerActions d_PlayerActions;
    public static boolean GAME_STARTED = false;
    public static final String ORDER_DEPLOY = "deploy";

    /**
     * This is a static method to get the instance of this singleton object
     *
     * @param p_Map map on which the game is starting..
     * @return GameEngine object type
     */
    public static GameEngine getInstance(Map p_Map) {
        if (d_Instance == null) {
            d_Instance = new GameEngine(p_Map);
        }
        return d_Instance;
    }

    /**
     * This is a private constructor so that it cannot be
     * instantiated outside this class ..
     *
     * @param p_Map map on which the game is starting
     */
    private GameEngine(Map p_Map) {
        this.d_CurrentMap = p_Map;
        this.d_PlayerActions = new PlayerActions(this.d_CurrentMap);
    }

    /**
     * game starts with loading of user-saved map file, which loads the map
     * as a "connected-directed-graph"
     */
    public void loadMapforGame() {
        this.d_Graph = this.d_CurrentMap.getAdjacencyMatrix();
        GAME_STARTED = true;
        System.out.println("Game engine has loaded the map");

        waitingForInput();

        // after this step, the game-engine should only stop
    }

    /**
     * Helper method to take commands for game engine
     */
    private void waitingForInput() {
        Scanner l_Scanner = new Scanner(System.in);
        while (true) {
            int l_PlayerListSize = this.d_PlayerActions.getListOfPlayers().size();
            System.out.println("number of players available is : " + l_PlayerListSize);
            System.out.println("use command \"gameplayer -add playername -remove playername \" " +
                    "to add between 3 to 5 players");
            System.out.println("use command \"assigncountries\" to start turn based main-loop");
            System.out.println("use command \"exit\" to stop game engine");

            String l_Input = l_Scanner.nextLine();
            System.out.println("input: " + l_Input);
            if ("exit".equalsIgnoreCase(l_Input)) {
                break;
            } else if (!GAME_STARTED) {
                System.out.println("game is finished, stopping ..");
                break;
            } else {
                if (l_Input.length() > 0) {
                    String[] l_CommandArray = l_Input.trim().split(" ");
                    if (processCommands(l_CommandArray)) {
                        // true only when "assigncountries" command is successful to start main-loop
                        mainGameLoop();
                    }
                }
            }
        }
    }

    /**
     * helper method to process commands for game engine
     *
     * @param p_Command commands applicable into game play
     */
    private boolean processCommands(String[] p_Command) {
        boolean l_BreakLoop = false;
        try {
            if (p_Command.length > 0) {
                String l_firstCommand = p_Command[0].toLowerCase();
                System.out.println("firstCommand : " + l_firstCommand);

                switch (l_firstCommand) {

                    case GameController.COMMAND_SHOW_MAP:
                        showMapforGame();
                        break;

                    case GameController.COMMAND_GAME_PLAYER:
                        processGamePlayerCommand(p_Command);
                        break;

                    case GameController.COMMAND_ASSIGN_COUNTRIES:
                        l_BreakLoop = processAssignCountriesCommand();
                        break;
                    default:
                        System.out.println("Invalid command");
                }
            }
        } catch (Exception l_E) {
            l_E.printStackTrace();
        }
        return l_BreakLoop;
    }

    /**
     * triggers on "showmap" command, this is a different function than MapEditors' showmap commad
     * as it shows extra details like :
     * 1. show all countries
     * 2. show all continents
     * 3. Armies on each countries
     * 4. Ownership
     * 5. Connectivity in a way that enables game-play
     */
    public void showMapforGame() {
        System.out.println("show game command received ");

        Graph l_Graph = d_CurrentMap.getAdjacencyMatrix();
        System.out.println(l_Graph.toString());

        for (Player l_Player : this.d_PlayerActions.getListOfPlayers()) {
            System.out.println("------------------------------------------------------------------------------------------------------------------------");
            System.out.println("PLAYER: " + l_Player.getPlayerName());
            System.out.println("with total army count of : " + l_Player.getNoOfArmies());
            System.out.println("has ownership of these countries: ");
            if (l_Player.getListOfCountries() != null) {
                for (Country l_Country : l_Player.getListOfCountries()) {
                    System.out.println("id: " + l_Country.getCountryID()
                            + " name: " + l_Country.getName()
                            + " army count: " + l_Country.getArmyCount()
                            + " belongs to continent " + l_Country.getContinentID());
                }
            }
        }
    }

    /**
     * Main Game loop that does three things for each player:
     * 1. Assign reinforcements
     * 2. Issue Orders
     * 3. Execute Orders
     * <p>
     * (After above three steps, it self-exit the game.)
     */

    private void mainGameLoop() {

        GAME_STARTED = true;
        while (true) {

            assignReinforcementPhase();
            issueOrderPhase();
            executeOrderPhase();

            if (this.d_PlayerActions.isGameOver() || GAME_STARTED == false) {
                System.out.println("GAME OVER");
                GAME_STARTED = false;
                break;
            }
        }
        Player l_Winner = this.d_PlayerActions.getWinner();
        if (l_Winner != null) {
            System.out.println("Winner is player > id: " + d_PlayerActions.getWinner().getPlayerID() + " name: " + d_PlayerActions.getWinner().getPlayerName());
        } else {
            System.out.println("Unknown winner ..");
        }
    }

    /***
     *
     * Assign to each player the correct number of reinforcement armies, according to Warzone rules
     */
    private void assignReinforcementPhase() {
        //assign each player the correct number of reinforcement
        for (Player l_Player : this.d_PlayerActions.getListOfPlayers()) {
            System.out.println("----------------------------------------------------------------");
            System.out.println("ASSIGN REINFORCEMENT PHASE : " + l_Player.getPlayerName());
            this.d_PlayerActions.assignReinforcementPhase(l_Player);
        }

    }

    /**
     * The GameEngine class calls the issue_order() method of the Player.
     * This method will wait for the following command, then create a deploy order object
     * on the player’s list of orders, then reduce the number of armies in the player’s reinforcement pool.
     * The game engine does this for all players in round-robin fashion until all the players have placed
     * all their reinforcement armies on the map.
     * Issuing order command:
     * deploy countryID num (until all reinforcements have been placed)
     */
    private void issueOrderPhase() {
        for (Player l_Player : this.d_PlayerActions.getListOfPlayers()) {
            System.out.println("----------------------------------------------------------------");
            System.out.println("ISSUE ORDER PHASE : " + l_Player.getPlayerName());
            this.d_PlayerActions.issueOrdersPhase(l_Player);

            //the method will wait for commands
            // TODO: the following command input is supposed to happend inside Player's issueOrder command.
            Scanner l_Scanner = new Scanner(System.in);
            while (true) {
                System.out.println("use command like [\"showmap\" ,  \"deploy <countryid> <num>\" , \"exit\" ");
                String l_Input = l_Scanner.nextLine();
                System.out.println("input: " + l_Input);

                if (l_Input.length() > 0) {
                    String[] l_CommandArray = l_Input.trim().split(" ");
                    try {
                        String l_Command = l_CommandArray[0];
                        // Issue deployment orders here to update the order list of the player .
                        if ((GameController.COMMAND_DEPLOY).equalsIgnoreCase(l_Command)) {
                            processDeployCommand(l_Player, l_CommandArray);
                            if (l_Player.getNoOfArmies() < 1) {
                                System.out.println("All the reinforcement armies have been placed ..");
                                break;
                            }
                        } else if ((GameController.COMMAND_SHOW_MAP).equalsIgnoreCase(l_Command)) {
                            showMapforGame();
                        } else if ("exit".equalsIgnoreCase(l_Command)) {
                            GAME_STARTED = false;
                            break;
                        }
                    } catch (Exception l_E) {
                        l_E.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * The GameEngine calls the next_order() method of the Player. Then the Order object’s execute() method
     * is called, which will enact the order. The effect of a deploy order is to place num armies on
     * the country countryID.
     */
    private void executeOrderPhase() {
        for (Player l_Player : this.d_PlayerActions.getListOfPlayers()) {
            System.out.println("----------------------------------------------------------------");
            System.out.println("EXECUTE ORDER  PHASE : " + l_Player.getPlayerName());
            this.d_PlayerActions.executeOrderPhase(l_Player);
        }
    }

    /**
     * helper method to take "gameplayer command to add or remove palyers"
     *
     * @param p_Command command array to process
     */
    private void processGamePlayerCommand(String[] p_Command) {
        System.out.println("gameplayer command received ..... ");

        // there could be more than one "-add" and "-remove" commands
        for (int l_I = 0; l_I < p_Command.length; l_I++) {
            String l_Tag = p_Command[l_I];

            if (l_Tag.toLowerCase().startsWith("-add")) {
                // make sure the index is not increasing the size of array
                if (l_I + 1 < p_Command.length) {
                    String l_PlayerName = p_Command[++l_I];
                    addPlayer(l_PlayerName);
                } else {
                    System.out.println("INCOMPLETE COMMAND ");
                }

            } else if (l_Tag.toLowerCase().startsWith("-remove")) {
                // make sure the index is not increasing the size of array
                if (l_I + 1 < p_Command.length) {
                    String l_PlayerName = p_Command[++l_I];
                    removePlayer(l_PlayerName);
                } else {
                    System.out.println("INCOMPLETE COMMAND");
                }
            }
        }
    }

    /**
     * takes player name and if there is no player with such name, it adds a new player with this name
     * in playerlist
     *
     * @param p_PlayerName playername to add
     * @return boolean
     */
    public boolean addPlayer(String p_PlayerName) {
        int l_Count = 0;
        for (Player l_Player : d_Instance.d_PlayerActions.getListOfPlayers()) {
            l_Count++;
            if (l_Player.getPlayerName().equalsIgnoreCase(p_PlayerName)) {
                System.out.println("A player with name: " + p_PlayerName + " already exists!");
                return false;
            }
        }
        // at end of loop
        Player l_NewPlayer = new Player(p_PlayerName, l_Count);
        return d_Instance.d_PlayerActions.addPlayer(l_NewPlayer);
    }

    /**
     * takes playerName and if it is found in existing playerlist, remove it .
     *
     * @param p_PlayerName playername to remove
     * @return boolean
     */
    public boolean removePlayer(String p_PlayerName) {

        for (Player l_Player : d_Instance.d_PlayerActions.getListOfPlayers()) {
            if (l_Player.getPlayerName().equalsIgnoreCase(p_PlayerName)) {
                return d_Instance.d_PlayerActions.removePlayer(l_Player);
            }
        }
        System.out.println("player not found : " + p_PlayerName);
        return false;
    }

    /**
     * helper method to process assigncountries command
     */
    private boolean processAssignCountriesCommand() {
        System.out.println("assigncountries command received..");
        try {
            int l_NumberOfPlayers = this.d_PlayerActions.getListOfPlayers().size();
            if (l_NumberOfPlayers >= 3 && l_NumberOfPlayers <= 5) {
                System.out.println("number of countries between [3 to 5] so assign countries to player now ..");
                return assignCountries();
            } else {
                System.out.println("number of player must be between [3 to 5] to state main-game-loop");
            }

        } catch (Exception l_E) {
            l_E.printStackTrace();
        }
        return false;
    }

    /**
     * All countries are assigned randomly to the player
     *
     * @return boolean
     */
    public boolean assignCountries() {
        return this.d_PlayerActions.assignCountriesToPlayers();
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
            String l_CountryName = p_Command[1];
            String l_Num = p_Command[2];
            int l_NumInt = Integer.parseInt(l_Num);

            int l_ArmyCountOfPlayer = p_Player.getNoOfArmies();
            if (l_ArmyCountOfPlayer >= l_NumInt) {
                p_Player.setNoOfArmies(l_ArmyCountOfPlayer - l_NumInt);
                Order l_order = new Order(ORDER_DEPLOY, l_CountryName, l_NumInt);
                p_Player.addNewOrder(l_order);
            } else {
                System.out.println("TRY AGAIN: only " + l_ArmyCountOfPlayer + " is available to be deployed !");
            }

        } catch (Exception l_E) {
            l_E.printStackTrace();
        }
    }

}