package ca.concordia.view;

import ca.concordia.Observable;
import ca.concordia.Observer;
import ca.concordia.model.GameModel;

public class GameView implements Observer {

    public GameView(GameModel p_gameModel) {
        p_gameModel.attach(this);
    }

    @Override
    public void update(Observable p_observable_state) {
        String text = ((GameModel) p_observable_state).getDisplayString();
        System.out.println(text);
    }
}
