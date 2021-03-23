package ca.concordia.patterns.command;

import ca.concordia.dao.Country;
import ca.concordia.dao.Player;
import ca.concordia.dao.Territory;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;


import static junit.framework.TestCase.assertEquals;


public class AdvanceTest {

    Advance d_Advance;
    Player d_PlayerOne;
    Player d_PlayerTwo;
    Territory d_Source;
    Territory d_Target;

    @Before
    public void init() {
        //source
        d_Source = new Country(1, 1, "India", 0, 0);
        d_Source.setArmyCount(30);

        //player one
        d_PlayerOne = new Player("player1", 1);
        d_PlayerOne.addNewCountry((Country) d_Source);
        d_Source.setOwner(d_PlayerOne);

        // target
        d_Target = new Country(1, 1, "China", 0, 0);
        d_Target.setArmyCount(10);

        // player two
        d_PlayerTwo = new Player("player2", 2);
        d_PlayerTwo.addNewCountry((Country) d_Target);
        d_Target.setOwner(d_PlayerTwo);

        int l_NumberOfArmiesAttacking = 20;
        d_Advance = new Advance(d_PlayerOne, d_Source, d_Target, l_NumberOfArmiesAttacking);
    }

    @Test
    public void executeTest() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        d_Advance.execute();

        String ex = "assigned to player ";
        assertEquals(true, outContent.toString().contains(ex));
    }

    @Test
    public void validTest() {
        assertEquals(true,d_Advance.valid());
    }

}
