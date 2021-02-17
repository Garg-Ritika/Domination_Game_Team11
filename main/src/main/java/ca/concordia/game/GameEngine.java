package ca.concordia.game;

import ca.concordia.IConstants;
import ca.concordia.model.*;

import java.util.Scanner;

/**
 * Game Engine class that starts with "loadmap" command and automatically ends after mainloop phases
 *
 * @author Nilesh, Binit
 */
public class GameEngine implements IConstants {

    private static GameEngine instance = null;
    private final Map currentMap;
    private Graph graph;
    private final PlayerActions playerActions;
    public static boolean GAME_STARTED = false;

    /**
     * @param map to be updated
     * @return to be updated
     */
    public static GameEngine getInstance(Map map) {
        if (instance == null) {
            instance = new GameEngine(map);
        }
        return instance;
    }

    /**
     * @param map to be updated
     * @return null
     */
    private GameEngine(Map map) {
        this.currentMap = map;
        this.playerActions = new PlayerActions(this.currentMap);
    }

    /**
     * game starts with loading of user-saved map file, which loads the map
     * as a "connected-directed-graph"
     *
     * @return null
     */
    public void loadMapforGame() {
        this.graph = this.currentMap.getAdjacencyMatrix();
        GAME_STARTED = true;
        System.out.println("Game engine has loaded the map");

        waitingForInput();

        // after this step, the game-engine should only stop
    }

    /**
     * helper method to take commands for game engine
     * @return null
     */
    private void waitingForInput(){
        Scanner scanner = new Scanner(System.in);
        while (true) {
            int playerListSize = this.playerActions.getListOfPlayers().size();
            System.out.println("number of players available is : " + playerListSize);
            System.out.println("use command \"gameplayer -add playername -remove playername \" " +
                    "to add between 3 to 5 players");
            System.out.println("use command \"assigncountries\" to start turn based main-loop");
            System.out.println("use command \"exit\" to stop game engine");

            String input = scanner.nextLine();
            System.out.println("input: " + input);
            if ("exit".equalsIgnoreCase(input)) {
                break;
            }else if(!GAME_STARTED ){
                System.out.println("game is finished, stopping ..");
                break;
            }else {
                if (input.length() > 0) {
                    String[] commandArray = input.trim().split(" ");
                    if(processCommands(commandArray)){
                        // true only when "assigncountries" command is successful to start main-loop
                        mainGameLoop();
                    }
                }
            }
        }
    }

    /**
     * helper method to process commands for game enginer
     * @param command
     * @return null
     */
    private boolean processCommands(String[] command){
        boolean breakLoop = false;
        try {
            if (command.length > 0) {
                String firstCommand = command[0].toLowerCase();
                System.out.println("firstCommand : " + firstCommand);

                switch (firstCommand) {

                    case COMMAND_SHOW_MAP:
                        showMapforGame();
                        break;

                    case COMMAND_GAME_PLAYER:
                        processGamePlayerCommand(command);
                        break;

                    case COMMAND_ASSIGN_COUNTRIES:
                        breakLoop = processAssignCountriesCommand();
                        break;
                    default:
                        System.out.println("Invalid command");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return breakLoop;
    }

    /**
     * triggers on "showmap" command, this is a different function than MapEditors' showmap commad
     * as it shows extra details like :
     * 1. show all countries
     * 2. show all continents
     * 3. Armies on each countries
     * 4. Ownership
     * 5. Connectivity in a way that enables game-play
     *
     * @return null
     */
    public void showMapforGame() {
        System.out.println("show game command received ");

        Graph graph = currentMap.getAdjacencyMatrix();
        System.out.println(graph.toString());

        for (Player player: this.playerActions.getListOfPlayers()){
            System.out.println("------------------------------------------------------------------------------------------------------------------------");
            System.out.println("PLAYER: " + player.getPlayerName());
            System.out.println("with total army count of : "+ player.getNoOfArmies());
            System.out.println("has ownership of these countries: ");
            for(Country country: player.getListOfCountries()){
                System.out.println("id: " + country.getCountryID()
                        + " name: "+ country.getName()
                        + " army count: " + country.getArmyCount()
                        + " belongs to continent " + country.getContinentID());
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
     *
     * @return null
     */

    private void mainGameLoop() {
        this.playerActions.assignCountriesToPlayers();

        GAME_STARTED = true;
        while (true) {

            assignReinforcementPhase();
            issueOrderPhase();
            executeOrderPhase();

            if (this.playerActions.isGameOver() || GAME_STARTED == false) {
                System.out.println("GAME OVER");
                GAME_STARTED = false;
                break;
            }
        }
        Player winner = this.playerActions.getWinner();
        if(winner != null){
            System.out.println("Winner is player > id: " + playerActions.getWinner().getPlayerID() + " name: " + playerActions.getWinner().getPlayerName());
        }else{
            System.out.println("Unknown winner ..");
        }
    }

    /***
     * Assign to each player the correct number of reinforcement armies, according to Warzone rules
     * @return null
     */
    private void assignReinforcementPhase(){
        //assign each player the correct number of reinforcement
        for (Player player : this.playerActions.getListOfPlayers()) {
            System.out.println("----------------------------------------------------------------");
            System.out.println("ASSIGN REINFORCEMENT PHASE : " + player.getPlayerName());
            this.playerActions.assignReinforcementPhase(player);
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
     *
     * @return null
     */
    private void issueOrderPhase(){
        //the method will wait for commands
        for (Player player : this.playerActions.getListOfPlayers()) {
            System.out.println("----------------------------------------------------------------");
            System.out.println("ISSUE ORDER PHASE : " + player.getPlayerName());
            this.playerActions.issueOrdersPhase(player);

            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("use command like [\"showmap\" ,  \"deploy <countryid> <num>\" , \"exit\" ");
                String input = scanner.nextLine();
                System.out.println("input: " + input);

                if (input.length() > 0) {
                    String[] commandArray = input.trim().split(" ");
                    try{
                        String command = commandArray[0];
                        if ((IConstants.COMMAND_DEPLOY).equalsIgnoreCase(command)){
                            processDeployCommand(player,commandArray);
                            // TODO : do we want to break after first try only ..
                            break;
                        }else if((IConstants.COMMAND_SHOW_MAP).equalsIgnoreCase(command)){
                            showMapforGame();
                        }else if("exit".equalsIgnoreCase(command)){
                            GAME_STARTED = false;
                            break;
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * The GameEngine calls the next_order() method of the Player. Then the Order object’s execute() method
     * is called, which will enact the order. The effect of a deploy order is to place num armies on
     * the country countryID.
     * @return null
     */
    private void executeOrderPhase(){
        for (Player player : this.playerActions.getListOfPlayers()) {
            System.out.println("----------------------------------------------------------------");
            System.out.println("EXECUTE ORDER  PHASE : " + player.getPlayerName());
            this.playerActions.executeOrderPhase(player);
        }


    }

    /**
     * helper method to take "gameplayer command to add or remove palyers"
     * @param command
     * @return null
     */
    private void processGamePlayerCommand(String[] command) {
        System.out.println("gameplayer command received ..... ");

        // there could be more than one "-add" and "-remove" commands
        for (int i = 0; i < command.length; i++) {
            String tag = command[i];

            if (tag.toLowerCase().startsWith("-add")) {
                // make sure the index is not increasing the size of array
                if (i + 1 < command.length) {
                    String playerName = command[++i];
                    addPlayer(playerName);
                } else {
                    System.out.println("INCOMPLETE COMMAND ");
                }

            } else if (tag.toLowerCase().startsWith("-remove")) {
                // make sure the index is not increasing the size of array
                if (i + 1 < command.length) {
                    String playerName = command[++i];
                    removePlayer(playerName);
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
     * @param playerName
     * @return boolean
     */
    public boolean addPlayer(String playerName) {
        int count = 0;
        for (Player player : instance.playerActions.getListOfPlayers()) {
            count++;
            if (player.getPlayerName().equalsIgnoreCase(playerName)) {
                System.out.println("A player with name: " + playerName + " already exists!");
                return false;
            }
        }
        // at end of loop
        Player newPlayer = new Player(playerName, count);
        return instance.playerActions.addPlayer(newPlayer);
    }

    /**
     * takes playerName and if it is found in existing playerlist, remove it .
     *
     * @param playerName
     * @return boolean
     */
    public boolean removePlayer(String playerName) {

        for (Player player : instance.playerActions.getListOfPlayers()) {
            if (player.getPlayerName().equalsIgnoreCase(playerName)) {
                return instance.playerActions.removePlayer(player);
            }
        }
        return false;
    }



    /**
     * helper method to process assigncountries command
     * @return null
     */
    private boolean processAssignCountriesCommand(){
        System.out.println("assigncountries command received..");
        try {
            int numberOfPlayers = this.playerActions.getListOfPlayers().size();
            if (numberOfPlayers >=3 || numberOfPlayers <=5 ) {
                return assignCountries();
            }else{
                System.out.println("number of player must be between [3 to 5] to state main-game-loop");
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * All countries are assigned randomly to the player
     *
     * @return void
     */
    public boolean assignCountries() {
        return this.playerActions.assignCountriesToPlayers();
    }



    /**
     * helper method to process deploy command
     * @param player
     * @param command
     * @return null
     */
    private void processDeployCommand(Player player,String[] command) {
        System.out.println("deploy  command received ..... ");
        //TODO : could be multiple countryid under one command ..
        try{
            String countryID = command[1];
            int countryIDInt = Integer.parseInt(countryID);
            String num = command[2];
            int numInt = Integer.parseInt(num);

            deploy(player,countryIDInt,numInt);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Issuing order command (untill all reinforcements have been placed
     *
     * @param countryId
     * @param num
     * @return  null
     */
    public void deploy(Player player, int countryId, int num) {

    }
}