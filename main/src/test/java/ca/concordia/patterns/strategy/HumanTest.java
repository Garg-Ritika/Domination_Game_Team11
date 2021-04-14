package ca.concordia.patterns.strategy;

import ca.concordia.dao.Continent;
import ca.concordia.dao.Country;
import ca.concordia.dao.Map;
import ca.concordia.dao.Player;
import ca.concordia.gameengine.GameEngine;
import ca.concordia.patterns.state.play.OrderCreationPhase;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import static org.junit.Assert.assertTrue;

/**
 * This test class is for Human CLass
 */
public class HumanTest {

    Player d_PlayerOne;
    Player d_PlayerTwo;
    Country d_Source;
    Strategy d_Human;
    GameEngine d_ge = new GameEngine();
    Map d_Map;

    /**
     * This method runs before every test method
     * and it initializes the objects
     */
    @Before
    public void init() {
        d_PlayerOne = new Player("player1", 1);
        d_PlayerTwo = new Player("player2", 2);
        d_PlayerOne.setNoOfArmies(20);
        d_PlayerTwo.setNoOfArmies(30);

        d_Map = new Map();
        d_Map.getListOfContinents().add(new Continent(1, "NorthAmerica", 20, "RED"));
        d_Map.getListOfCountries().add(new Country(1, 1, "India", 0, 0));
        d_Source = d_Map.getListOfCountries().get(0);

        d_ge.setMap(d_Map);
        d_ge.setPhase(new OrderCreationPhase(d_ge));

        d_Source.setArmyCount(30);
        d_Human= new Human(d_ge);

    }

    /**
     * This method is used to test if the issueOrder method is working and if the commands are being executed successfully
     * using assertTrue
     */
    @Test
    public void takeOrderCommandTest(){
        String l_CommandInput = "deploy "+d_Source+" 20";
        String l_CommandQuit = "quit";
        String l_CommandShowmap = "showmap";

        d_PlayerOne.setStrategy(new Human(d_ge));

        ByteArrayOutputStream l_OutContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(l_OutContent));

        InputStream stdin = System.in;
        try {

            System.setIn(new ByteArrayInputStream(l_CommandInput.getBytes()));
            //System.setIn(new ByteArrayInputStream(l_CommandQuit.getBytes()));
            //System.setIn(new ByteArrayInputStream(l_CommandShowmap.getBytes()));
            d_PlayerOne.issueOrder();
        }catch (Exception e){
            System.out.println(e.getMessage());
        } finally {
            System.setIn(stdin);
        }
        assertTrue(l_OutContent.toString().contains("firstCommand :"));
    }

}
