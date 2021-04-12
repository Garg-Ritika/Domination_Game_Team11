package ca.concordia.patterns.strategy;

import ca.concordia.dao.Border;
import ca.concordia.dao.Country;
import ca.concordia.dao.Player;
import ca.concordia.dao.Territory;
import ca.concordia.gameengine.GameEngine;
import ca.concordia.patterns.command.*;
import ca.concordia.patterns.observer.LogUtil;

import java.util.*;

/**
 * Cheater Player: <br>
 * A cheater computer player strategy whose issueOrder() method conquers all the immediate neighboring enemy countries, and
 * then doubles the number of armies on its countries that have enemy neighbors.
 */

public class Cheater extends Strategy {

    private LinkedList<Order> d_ListOfOrders = new LinkedList<Order>();
//    List<String> l_Order_Names = new ArrayList<>();
    GameEngine d_ge;

    public Cheater(GameEngine p_Ge) {
        super(p_Ge);
        this.d_ge= p_Ge;
    }
    @Override
    public LinkedList<Order> create(Player p_Player) {
//        d_ListOfOrders.clear();
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
//        int l_Army = p_Player.getNoOfArmies();
//        l_Order_Names.clear();


        LogUtil.log( " Turn for player " + p_Player.getPlayerName());

        Random rand = new Random();
        Country randomCountry = null;
        for (int i = 0; i < p_Player.getListOfCountries().size(); i++) {
            int randomIndex = rand.nextInt(p_Player.getListOfCountries().size()); {
                randomCountry= p_Player.getListOfCountries().get(randomIndex);                }
        }
        List<Integer> neighbourCountry = new ArrayList<>();
        for (Border l_Border : d_ge.getMap().getListOfBorders()) {
            if(randomCountry.getCountryID()== l_Border.getCountryId()){
                 neighbourCountry = l_Border.getNeighbours();
            }
        }
        System.out.println("--------------for conquering  neighbpr countries------");
        for (Country country: d_ge.getMap().getListOfCountries()) {
            for(int i=0; i<neighbourCountry.size(); i++){
                if(country.getCountryID()== neighbourCountry.get(i)) {
                    if(!country.getName().equals(randomCountry.getName())){
                        p_Player.addNewCountry(country);
                        Player opponent =country.getOwner();
                        opponent.removeNewCountry(country);
                    }
                }
            }
        }

        System.out.println("----- doubling the armies of own countries-------");
        for (Country country: p_Player.getListOfCountries()) {
            for (Border l_Border : d_ge.getMap().getListOfBorders()) {
                if(country.getCountryID()== l_Border.getCountryId()){
                    for (int i: l_Border.getNeighbours()) {
                        if(country.getCountryID()!=i){
                            if(country.getArmyCount()>0){
                                country.setArmyCount(country.getArmyCount()*2 );
                                break;
                            }
                            else {
                                country.setArmyCount(2);
                                 break;
                            }
                        }
                    }
                }
            }
        }

        return false;
    }
}
