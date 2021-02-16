package ca.concordia.game;

import ca.concordia.model.Map;
import ca.concordia.model.Player;

public class GameEngine {

    private static GameEngine instance = null;
    private final Map currentMap;
    private final PlayerActions playerActions;

    public static GameEngine getInstance(Map map) {
        if (instance == null) {
            instance = new GameEngine(map);
        }
        return instance;
    }

    private GameEngine(Map map) {
        this.currentMap = map;
        this.playerActions = new PlayerActions(this.currentMap);
        this.loadMapforGame(); // automatically load game in the constructor
    }

    private void loadMapforGame() {
        mainGameLoop();
    }

    public boolean addPlayer(Player player){
        return this.playerActions.addPlayer(player);
    }

    public boolean removePlayer(Player player){
        return this.playerActions.removePlayer(player);
    }

    /**
     *  Main Game loop that does three things for each player:
     *  1. Assign reinforcements
     *  2. Issue Orders
     *  3. Execute Orders
     *
     *  (After above three steps, it self-exit the game.)
     * @author
     * @return
     */

    private void mainGameLoop(){
        this.playerActions.assignCountriesToPlayers();

        boolean gameOver = false;
        while (true){
            for(Player player: this.playerActions.getListOfPlayers()){
                System.out.println("----------------------------------------------------------------");
                System.out.println("ASSIGN REINFORCEMENT PHASE : " + player.getPlayerName());
                this.playerActions.assignReinforcementPhase(player);

                System.out.println("----------------------------------------------------------------");
                System.out.println("ISSUE ORDER PHASE : " + player.getPlayerName());
                this.playerActions.issueOrdersPhase(player);

                System.out.println("----------------------------------------------------------------");
                System.out.println("EXECUTE ORDER  PHASE : " + player.getPlayerName());
                this.playerActions.executeOrderPhase(player);

            }
            if(this.playerActions.isGameOver()){
                System.out.println("GAME OVER");
                break;
            }
        }
        System.out.println("Winner is player > id: " + playerActions.getWinner().getPlayerID() + " name: " + playerActions.getWinner().getPlayerName());
    }
}