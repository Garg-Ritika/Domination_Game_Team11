/*
package ca.concordia.patterns.command;

import ca.concordia.dao.Country;
import ca.concordia.dao.Player;
import ca.concordia.dao.Territory;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static junit.framework.TestCase.assertEquals;

*/
/**
 * This class checks the methods of Deploy class
 * using assert method
 *//*

public class DeployTest {
    Deploy d_Deploy;
    Player d_PlayerOne;
    Territory d_Target;

    */
/**
     * This is the method that runs before every test method in this class
     *//*

    @Before
    public void init() {

        // target
        d_Target = new Country(1, 1, "China", 0, 0);
        d_Target.setArmyCount(10);

        //player one
        d_PlayerOne = new Player("player1", 1);
        d_PlayerOne.addNewCountry((Country) d_Target);
        d_Target.setOwner(d_PlayerOne);

        int l_NumberOfArmiesDeploying = 20;
        d_Deploy = new Deploy(d_PlayerOne, d_Target, l_NumberOfArmiesDeploying);
    }

    */
/**
     * This method checks that whether execute method of Deploy class works properly or not
     * by verifying actual and expected value
     *//*

    @Test
    public void executeTest() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        d_Deploy.execute();

        String ex = "deploying";
        assertEquals(true, outContent.toString().contains(ex));
        assertEquals(30, d_Target.getArmyCount());
    }

    */
/**
     * This method checks whether expected and actual value of valid method in deploy class matches or not
     *//*

    @Test
    public void validTest() {
        assertEquals(true, d_Deploy.valid());
    }
}*/
