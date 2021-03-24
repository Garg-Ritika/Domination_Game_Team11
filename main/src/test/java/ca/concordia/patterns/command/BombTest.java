package ca.concordia.patterns.command;

import ca.concordia.dao.Country;
import ca.concordia.dao.Player;
import ca.concordia.dao.Territory;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static junit.framework.TestCase.assertEquals;

public class BombTest {
    Bomb d_Bomb;
    Player d_PlayerOne;
    Player d_PlayerTwo;
    Territory d_Target;

    @Before
    public void init() {

        //player one
        d_PlayerOne = new Player("player1", 1);

        // target
        d_Target = new Country(1, 1, "China", 0, 0);
        d_Target.setArmyCount(10);

        // player two
        d_PlayerTwo = new Player("player2", 2);
        d_PlayerTwo.addNewCountry((Country) d_Target);
        d_Target.setOwner(d_PlayerTwo);

        d_Bomb = new Bomb(d_PlayerOne, d_Target);
    }

    @Test
    public void executeTest() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        d_Bomb.execute();

        String ex = "half of the armies is destroyed";
        assertEquals(true, outContent.toString().contains(ex));
        assertEquals(5, d_Target.getArmyCount());
    }

    @Test
    public void validTest() {
        assertEquals(true, d_Bomb.valid());
    }
}