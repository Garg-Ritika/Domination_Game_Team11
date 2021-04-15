package ca.concordia.patterns.strategy;

import ca.concordia.dao.Border;
import ca.concordia.dao.Country;
import ca.concordia.dao.Player;
import ca.concordia.gameengine.GameEngine;
import ca.concordia.patterns.command.Order;
import ca.concordia.patterns.observer.LogUtil;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Cheater Player: <br>
 * A cheater computer player strategy whose issueOrder() method conquers all the immediate neighboring enemy countries, and
 * then doubles the number of armies on its countries that have enemy neighbors.
 */

public class Cheater extends Strategy {

    GameEngine d_ge;
    private LinkedList<Order> d_ListOfOrders = new LinkedList<Order>();

    /**
     * Constructor to override the method depending on the input parameters
     * @param p_Ge GameEngine object
     */
    public Cheater(GameEngine p_Ge) {
        super(p_Ge);
        this.d_ge = p_Ge;
    }

    /**
     * Abstract class for LinkedList
     * @param p_Player player object for whose Order is created
     * @return list of orders
     */
    @Override
    public LinkedList<Order> create(Player p_Player) {
        takeOrder(p_Player);
        return null;
    }

    /**
     * This method takes order from user and process the commands
     * Player cannot quit the game unless all armies are deployed
     * when keyboard's input is no longer needed, keyboard.close() function is called
     *
     * @param p_Player Player name
     * @return false if player's number of armies is 0 or after keyboard.close() is called
     */
    public boolean takeOrder(Player p_Player) {
        LogUtil.log("taking order ");

        LogUtil.log(" Turn for player " + p_Player.getPlayerName());

        Random l_Rand = new Random();
        Country l_RandomCountry = null;
        for (int i = 0; i < p_Player.getListOfCountries().size(); i++) {
            int l_RandomIndex = l_Rand.nextInt(p_Player.getListOfCountries().size());
            {
                l_RandomCountry = p_Player.getListOfCountries().get(l_RandomIndex);
            }
        }
        List<Integer> l_NeighbourCountry = new ArrayList<>();
        for (Border l_Border : d_ge.getMap().getListOfBorders()) {
            if (l_RandomCountry.getCountryID() == l_Border.getCountryId()) {
                l_NeighbourCountry = l_Border.getNeighbours();
            }
        }
        System.out.println("--------------for conquering  neighbour countries------");
        for (Country l_Country : d_ge.getMap().getListOfCountries()) {
            for (int i = 0; i < l_NeighbourCountry.size(); i++) {
                if (l_Country.getCountryID() == l_NeighbourCountry.get(i)) {
                    if (!l_Country.getName().equals(l_RandomCountry.getName())) {
                        if(!p_Player.getListOfCountries().contains(l_Country)){
                            p_Player.addNewCountry(l_Country);
                        }
                        Player l_Opponent = l_Country.getOwner();
                        l_Opponent.removeNewCountry(l_Country);

                    }
                }
            }
        }

        ArrayList<Country> l_CountriesDoubled = new ArrayList<>();
        System.out.println("----- doubling the armies of own countries-------");
        for (Country l_Country : p_Player.getListOfCountries()) {
            for (Border l_Border : d_ge.getMap().getListOfBorders()) {
                if (l_Country.getCountryID() == l_Border.getCountryId()) {
                    for (int i : l_Border.getNeighbours()) {
                        if (l_Country.getCountryID() != i) {
                            if (l_Country.getArmyCount() > 0 ) {
                                if(!l_CountriesDoubled.contains(l_Country)) {
                                    l_Country.setArmyCount(l_Country.getArmyCount() * 2);
                                    l_CountriesDoubled.add(l_Country);
                                }
                                break;
                            } else {
                                l_Country.setArmyCount(2);
                                break;
                            }
                        }
                    }
                }
            }
        }
        LogUtil.log("Executed commands for " + p_Player.getPlayerName());
        l_CountriesDoubled.clear();

        return false;
    }
}
