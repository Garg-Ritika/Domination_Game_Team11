package ca.concordia.controller.game;

import ca.concordia.model.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Player Actions Test class
 */
public class PlayerActionsTest {
    Map d_Map;
    PlayerActions d_PlayerActions;
    Player d_Player1, d_Player2, d_Player3;
    List<Player> d_PlayerList = new ArrayList<>();

    /**
     * This is the method to be run before test method. It initializes the PlayerActions lass object
     */
    @Before
    public void before() {
        // Initialize map
        d_Map = new Map();
        d_Map.getListOfContinents().add(new Continent(1, "NorthAmerica", 20, "RED"));
        d_Map.getListOfCountries().add(new Country(1, 1, "Canada", 2, 2));
        d_Map.getListOfCountries().add(new Country(2, 1, "America", 1, 1));
        d_Map.getListOfCountries().add(new Country(3, 1, "Mexico", 0, 0));
        d_Map.getListOfCountries().add(new Country(3, 1, "Quebec", 0, 0));


        d_PlayerActions = new PlayerActions(d_Map);
        d_Player1 = new Player("player1", 1);
        d_Player2 = new Player("player1", 1);
        d_Player3 = new Player("player1", 1);
        d_PlayerList.add(d_Player1);
        d_PlayerList.add(d_Player2);
        d_PlayerList.add(d_Player3);
    }

    /**
     * cumulative test to one player actions flow ..
     * 1. add players
     * 2. assign countries to players
     * 3. test reinforcement phase
     * 4. test issue order phase
     * 5. test execute order phase
     * 6. test set winner
     * 7. test get winner
     * 8. test is game over
     */
    @Test
    public void testAll() {
        testAddRemovePlayer();
        testAssignCountries();
        testMainLoop();
        testGameEndPhase();
    }

    /**
     * test add or remove player in Player actions
     */
    public void testAddRemovePlayer() {
        // test adding and removing players
        assertEquals(false, d_PlayerActions.removePlayer(d_Player1));
        assertEquals(true, d_PlayerActions.addPlayer(d_Player1));
        assertEquals(true, d_PlayerActions.addPlayer(d_Player2));
        assertEquals(true, d_PlayerActions.addPlayer(d_Player3));
        assertEquals(3, d_PlayerActions.getListOfPlayers().size());

    }


    /**
     * Test assign countries algorithm triggered by "assigcountries" command
     */
    public void testAssignCountries() {
        // test assigning countries
        assertEquals(true, d_PlayerActions.assignCountriesToPlayers());
    }


    /**
     * test main game play loop
     *  + calculation of number of reinforcement armies;
     *  + player cannot deploy more armies that there is in their reinforcement pool.
     */
    public void testMainLoop() {
        // test-main loop
        for (Player l_Player : d_PlayerList) {
            int l_CountryOwnedByPlayer = l_Player.getListOfCountries().size();
            int l_NetContinentValue = 0;
            if (l_Player.getListOfContinents().size() > 1) {
                for (Continent p_Continent : l_Player.getListOfContinents()) {
                    l_NetContinentValue += p_Continent.getArmyCount();
                }
            }
            int l_NewArmy = Math.max(3, l_CountryOwnedByPlayer / 3) + l_NetContinentValue;

            //3.verify the number of reinforcement armies
            assertEquals(l_NewArmy,d_PlayerActions.assignReinforcementPhase(l_Player));

            l_Player.addNewOrder(new Order("deploy", "Canada", 5));
            d_PlayerActions.issueOrdersPhase(l_Player);
            d_PlayerActions.executeOrderPhase(l_Player);

            //4. player cannot deploy more armies that there is in their reinforcement pool.
            assertEquals(0,l_Player.getNoOfArmies());
            int l_ArmiesAssignedToCanada = 0;
            for(Country l_country: d_Map.getListOfCountries()){
                if(l_country.getName().equalsIgnoreCase("Canada")){
                    l_ArmiesAssignedToCanada = l_country.getArmyCount();
                }
            }
            assertEquals(l_NewArmy,l_ArmiesAssignedToCanada);
        }
    }


    /**
     * test game endphase winner ..
     */
    public void testGameEndPhase() {
        // test winner selection and game closing phase
        d_PlayerActions.setWinner(d_Player1);
        assertEquals(d_Player1, d_PlayerActions.getWinner());
        assertEquals(false, d_PlayerActions.isGameOver());
    }
}
