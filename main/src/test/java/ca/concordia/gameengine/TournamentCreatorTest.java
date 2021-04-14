package ca.concordia.gameengine;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static junit.framework.TestCase.assertEquals;

/**
 * TournamentCreator test class to test conditions
 * if map is between 1 to 5
 * if no of players is between 2 to 4
 * if no of games is between 1 to 5
 */
public class TournamentCreatorTest {

    /**
     * Declaration of TournamentCreator objects for different assert methods
     */
    TournamentCreator d_tc, d_tc1, d_tc2;

    /**
     * GameEngine object declared
     */
    GameEngine d_ge;

    /**
     * This is the method that runs before every test method
     * It initializes TournamentCreator objects
     */
    @Before
    public void before() {
        d_ge = new GameEngine();
        d_tc = new TournamentCreator(d_ge, "tournament -M -P aggressive cheater -G 3 -D 20");
        d_tc1 = new TournamentCreator(d_ge, "tournament -M abc.map -P -G 3 -D 20");
        d_tc2 = new TournamentCreator(d_ge, "tournament -M abc.map -P aggressive cheater -G  -D 20");

    }

    /**
     * This is the test method to test TournamentCreator's startTournament method
     * It checks if number of maps are between 1 to 5 or not
     * it uses assert Statement
     */

    @Test
    public void startTournament() {

        ByteArrayOutputStream l_OutContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(l_OutContent));

        d_tc.startTournament();

        String str= "Number of maps is not between 1 to 5" + System.lineSeparator() + "Unable to start tournament because of wrong command" + System.lineSeparator();
        assertEquals(true, l_OutContent.toString().startsWith(str));


    }

    /**
     * This test method checks if number of players are between 2 to 4
     * using assert methods
     */
    @Test
    public void startTournament1() {

        ByteArrayOutputStream l_OutContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(l_OutContent));

        d_tc1.startTournament();

        assertEquals(true,
                l_OutContent.toString().startsWith("Number of players is not between 2 to 4"));

    }

    /**
     * This test method shows that no of games should be between 1 to 5
     * using aassertEquals
     */
    @Test
    public void startTournament2() {

        ByteArrayOutputStream l_OutContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(l_OutContent));

        d_tc2.startTournament();


        assertEquals(true,l_OutContent.toString().startsWith("Number of games is not between 1 to 5"));


    }
}
