package ca.concordia.gameengine;

import ca.concordia.dao.Continent;
import ca.concordia.dao.Country;
import ca.concordia.dao.Player;
import ca.concordia.patterns.command.Order;
import ca.concordia.patterns.observer.LogUtil;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;

/**
 *
 */
public class GameSaver {

    private final GameEngine d_Ge;

    public GameSaver(GameEngine p_Ge) {
        d_Ge = p_Ge;
    }

    public void saveFile(File p_GameFile){
        try {
            JSONObject l_Obj = new JSONObject();

            l_Obj.put("map", d_Ge.getMap().getName());
            l_Obj.put("phase-class", d_Ge.getPhase().getClass().getSimpleName());

            //players
            JSONArray l_Arr = new JSONArray();
            for (Player l_Player : d_Ge.getListOfPlayers()) {
                JSONObject l_PlayerObj = new JSONObject();
                l_PlayerObj.put("name", l_Player.getPlayerName());
                l_PlayerObj.put("id", l_Player.getPlayerID());
                l_PlayerObj.put("armies", l_Player.getNoOfArmies());
                l_PlayerObj.put("random-card-assigned",l_Player.getD_RandomCardAssigned());
                l_PlayerObj.put("negotiated",l_Player.getIsNegotiatedPlayer());
                if(l_Player.getStrategy() != null) {
                    l_PlayerObj.put("strategy-class", l_Player.getStrategy().getClass().getSimpleName());
                }

                // add countries
                JSONArray l_CountryArr = new JSONArray();
                for (Country l_Country: l_Player.getListOfCountries()){
                    JSONObject l_CountryObj = new JSONObject();
                    l_CountryObj.put("country-name",l_Country.getName());
                    l_CountryObj.put("country-id",l_Country.getCountryID());
                    l_CountryObj.put("continent-id",l_Country.getContinentID());
                    l_CountryObj.put("country-army",l_Country.getArmyCount());
                    l_CountryObj.put("country-owner-class",l_Country.getOwner().getClass().getSimpleName());
                    l_CountryObj.put("country-x",l_Country.getX());
                    l_CountryObj.put("country-y",l_Country.getY());
                    l_CountryArr.put(l_CountryObj);
                }
                l_PlayerObj.put("countries",l_CountryArr);

                //add continents
                JSONArray l_ContinentArr = new JSONArray();
                for (Continent l_Continent : l_Player.getListOfContinents()){
                    JSONObject l_ContinentObj = new JSONObject();
                    l_ContinentObj.put("continent-name",l_Continent.getName());
                    l_ContinentObj.put("continent-id",l_Continent.getID());
                    l_ContinentObj.put("continent-color",l_Continent.getColor());
                    l_ContinentObj.put("continent-army",l_Continent.getArmyCount());
                    l_ContinentObj.put("continent-owner-class",l_Continent.getOwner().getClass().getSimpleName());
                    l_ContinentArr.put(l_ContinentObj);
                }
                l_PlayerObj.put("continents",l_ContinentArr);

                //add Orders
                JSONArray l_OrdersArr = new JSONArray();
                for (Order l_Order: l_Player.getListOfOrder()){
                    JSONObject l_OrderObj = new JSONObject();
                    // no doing much here ..
                    l_OrderObj.put("order-class",l_Order.getClass().getSimpleName());
                    l_OrdersArr.put(l_OrderObj);
                }
                l_PlayerObj.put("orders",l_OrdersArr);

                //add Cards
                JSONArray l_CardsArr = new JSONArray();
                for (String l_Card : l_Player.getOrderCards()){
                    JSONObject l_CardObj = new JSONObject();
                    l_CardObj.put("card-name",l_Card);
                    l_CardsArr.put(l_CardObj);
                }
                l_PlayerObj.put("cards",l_CardsArr);



                l_Arr.put(l_PlayerObj);
            }
            l_Obj.put("players", l_Arr);

            FileWriter fw = new FileWriter(p_GameFile);
            fw.write(l_Obj.toString(4));
            fw.close();
        }catch (Exception e){
            LogUtil.log(e.getMessage());
        }
    }

}
