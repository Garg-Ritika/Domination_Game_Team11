package ca.concordia;

import ca.concordia.controller.GameController;
import ca.concordia.model.GameModel;
import ca.concordia.view.GameView;

public class Main {

    GameModel d_GameModel;
    GameController d_GameController;
    GameView d_GameView;

    public Main(){
        d_GameModel = new GameModel();
        d_GameView = new GameView(d_GameModel);
        d_GameController = new GameController(d_GameView, d_GameModel);
    }

    public static void main(String[] args) {
        System.out.println("Welcome to TEAM-11 Game");
        new Main().startGame();
    }

    public void startGame(){
        d_GameController.takeCommandInput();
        d_GameModel.detach(d_GameView);
    }
}