package ca.concordia.gameengine;

import ca.concordia.dao.Player;
import ca.concordia.patterns.state.Phase;
import ca.concordia.patterns.state.play.PlaySetup;
import ca.concordia.patterns.strategy.Aggressive;
import ca.concordia.patterns.strategy.Cheater;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GameSaverTest {

    GameEngine d_GameEngine;
    GameSaver d_GameSaver;
    Player d_P1;
    Player d_P2;
    List<Player> d_ListOfPlayer;
    Phase d_Phase;

    @Before
    public void before() {
        d_GameEngine = new GameEngine();
        d_GameSaver = new GameSaver(d_GameEngine);

        d_Phase = new PlaySetup(d_GameEngine);
        d_GameEngine.setPhase(d_Phase);

        String currentPath = System.getProperty("user.dir");
        File l_MapFile = new File(currentPath
                + File.separator + "maps"
                + File.separator + "domination"
                + File.separator + "canada"
                + File.separator + "canada.map");
        String[] l_LoadCommand = {"loadmap ", l_MapFile.getAbsolutePath()};
        d_GameEngine.getPhase().loadMap(l_LoadCommand);


        d_P1 = new Player("Aggressive",1);
        d_P1.setStrategy(new Aggressive(d_GameEngine));
        d_P2 = new Player("Cheater",2);
        d_P1.setStrategy(new Cheater(d_GameEngine));

        d_ListOfPlayer = new ArrayList<>();
        d_ListOfPlayer.add(d_P1);
        d_ListOfPlayer.add(d_P2);
        d_GameEngine.setListOfPlayers(d_ListOfPlayer);


    }

    @Test
    public void saveFile() {
        String currentPath = System.getProperty("user.dir");
        File l_SavedGameFile = new File(currentPath + File.separator + "saved-game1.txt");
        d_GameSaver.saveFile(l_SavedGameFile);
        assertTrue(l_SavedGameFile.exists());
    }
}
