package ca.concordia;

import ca.concordia.controller.GameController;
import ca.concordia.model.GameModel;
import ca.concordia.view.GameView;

public class Main {

    GameModel d_gameModel;
    GameController d_gameController;
    GameView d_gameView;

    public Main(){
        d_gameModel = new GameModel();
        d_gameView = new GameView(d_gameModel);
        d_gameController = new GameController(d_gameView,d_gameModel);
    }

    public static void main(String[] args) {
        System.out.println("Welcome to TEAM-11 Game");
        new Main().startGame();
    }

    public void startGame(){
        d_gameController.takeCommandInput();
        d_gameModel.detach(d_gameView);
    }
}