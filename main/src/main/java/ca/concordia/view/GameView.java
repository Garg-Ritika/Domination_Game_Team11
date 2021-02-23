package ca.concordia.view;

import ca.concordia.Observable;
import ca.concordia.Observer;
import ca.concordia.model.GameModel;

public class GameView implements Observer {

    public GameView(GameModel p_GameModel) {
        p_GameModel.attach(this);
    }

    @Override
    public void update(Observable p_ObservableState) {
        String text = ((GameModel) p_ObservableState).getDisplayString();
        System.out.println(text);
    }
}
