package ca.concordia.patterns.command;

import ca.concordia.dao.Country;
import ca.concordia.dao.Player;
import ca.concordia.dao.Territory;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static junit.framework.TestCase.assertEquals;

public class AirliftTest {
    Airlift d_Airlift;
    Player d_PlayerOne;
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

        d_PlayerOne.addNewCountry((Country) d_Target);
        d_Target.setOwner(d_PlayerOne);

        int l_NumberOfArmiesAttacking = 20;
        d_Airlift = new Airlift(d_PlayerOne, d_Source, d_Target, l_NumberOfArmiesAttacking);
    }

    @Test
    public void executeTest() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        d_Airlift.execute();

        String ex = "has been moved";
        assertEquals(true, outContent.toString().contains(ex));
        assertEquals(30, d_Target.getArmyCount());
        assertEquals(10, d_Source.getArmyCount());
    }

    @Test
    public void validTest() {
        assertEquals(true,d_Airlift.valid());
    }
}
