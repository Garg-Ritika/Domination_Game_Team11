package ca.concordia.patterns.command;

import ca.concordia.dao.Country;
import ca.concordia.dao.Player;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static junit.framework.TestCase.assertEquals;

public class DiplomacyTest {
    Diplomacy d_Diplomacy;
    Player d_PlayerOne;
    Player d_PlayerTwo;

    @Before
    public void init() {

        //player one
        d_PlayerOne = new Player("player1", 1);
        d_PlayerOne.setIsNegotiatedPlayer(false);

        // player two
        d_PlayerTwo = new Player("player2", 2);
        d_PlayerOne.setIsNegotiatedPlayer(false);

        d_Diplomacy = new Diplomacy(d_PlayerOne,d_PlayerTwo);
    }

    /**
     * This method verify that when the attack is executed the army is assigned to correct player.
     */
    @Test
    public void executeTest() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        d_Diplomacy.execute();

        String ex = "are refrained from attack until next turn";
        assertEquals(true, outContent.toString().contains(ex));
        assertEquals(true, d_PlayerOne.getIsNegotiatedPlayer());
        assertEquals(true, d_PlayerTwo.getIsNegotiatedPlayer());
    }

    /**
     * This method check whether the advance order is valid or not.
     */
    @Test
    public void validTest() {
        assertEquals(true,d_Diplomacy.valid());
    }


}