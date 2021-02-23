package ca.concordia.view;

import ca.concordia.Observable;
import ca.concordia.Observer;
import ca.concordia.model.GameModel;

/**
 * Game View class that represents the view in MVC pattern.
 * This class is supposed to be the only class to
 * take all the input and print all the outputs in the console
 */
public class GameView implements Observer {

    /**
     * Constructor of the Game View
     *
     * @param p_GameModel GameModel object
     */
    public GameView(GameModel p_GameModel) {
        p_GameModel.attach(this);
    }

    /**
     * Update method to print the updates into the console
     *
     * @param p_ObservableState: Object that is passed by the subject (observable). Very often, this
     */
    @Override
    public void update(Observable p_ObservableState) {
        String text = ((GameModel) p_ObservableState).getDisplayString();
        System.out.println(text);
    }
}
