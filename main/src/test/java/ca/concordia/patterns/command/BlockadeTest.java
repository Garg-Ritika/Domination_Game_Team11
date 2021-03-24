package ca.concordia.patterns.command;

import ca.concordia.dao.Country;
import ca.concordia.dao.Player;
import ca.concordia.dao.Territory;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static junit.framework.Assert.assertNotSame;
import static junit.framework.TestCase.assertEquals;

public class BlockadeTest {
    Blockade d_Blockade;
    Player d_PlayerOne;
    Territory d_Target;

    @Before
    public void init() {


        // target
        d_Target = new Country(1, 1, "China", 0, 0);
        d_Target.setArmyCount(10);

        //player one
        d_PlayerOne = new Player("player1", 1);
        d_PlayerOne.addNewCountry((Country) d_Target);
        d_Target.setOwner(d_PlayerOne);

        d_Blockade = new Blockade(d_PlayerOne, d_Target);
    }
    @Test
    public void executeTest() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        d_Blockade.execute();

        String ex = "has been neutralized";
        assertEquals(true, outContent.toString().contains(ex));
        assertEquals(30, d_Target.getArmyCount());
        assertNotSame("player1", d_Target.getOwner().getPlayerName());
    }

    @Test
    public void validTest() {
        assertEquals(true,d_Blockade.valid());
    }


}
