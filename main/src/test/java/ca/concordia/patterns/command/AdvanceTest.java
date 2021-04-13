package ca.concordia.patterns.command;

import ca.concordia.dao.Country;
import ca.concordia.dao.Player;
import ca.concordia.dao.Territory;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static junit.framework.TestCase.assertEquals;


/**
 * Test class for Advance Class
 */

public class AdvanceTest {

    Advance d_Advance;
    Player d_PlayerOne;
    Player d_PlayerTwo;
    Territory d_Source;
    Territory d_Target;


    /**
     * This is the method to be run before test method. It initializes the Advance class objects
     */

    @Before
    public void init() {
        //source
        d_Source = new Country(1, 1, "India", 0, 0);
        d_Source.setArmyCount(30);

        //player one
        d_PlayerOne = new Player("player1", 1);
        d_PlayerOne.addNewCountry((Country) d_Source);
        d_Source.setOwner(d_PlayerOne);
        d_PlayerOne.setD_RandomCardAssigned(false);

        // target
        d_Target = new Country(1, 1, "China", 0, 0);
        d_Target.setArmyCount(10);

        // player two
        d_PlayerTwo = new Player("player2", 2);
        d_PlayerTwo.addNewCountry((Country) d_Target);
        d_Target.setOwner(d_PlayerTwo);

        int l_NumberOfArmiesAttacking = 30;
        d_Advance = new Advance(d_PlayerOne, d_Source, d_Target, l_NumberOfArmiesAttacking);
    }


    /**
     * This method verify that when the attack is executed the army is assigned to correct player.
     */

    @Test
    public void executeTest() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        d_Advance.execute();

        String ex = "assigned to player ";
        assertEquals(true, outContent.toString().contains(ex));
        assertEquals(true, d_PlayerOne.getD_RandomCardAssigned());
    }


    /**
     * This method check whether the advance order is valid or not.
     */

    @Test
    public void validTest() {
        assertEquals(true, d_Advance.valid());
    }

}

