package ca.concordia.gameengine;

import ca.concordia.dao.Continent;
import ca.concordia.dao.Country;
import ca.concordia.dao.Player;
import ca.concordia.patterns.command.Advance;
import ca.concordia.patterns.command.Order;
import ca.concordia.patterns.observer.LogUtil;
import ca.concordia.patterns.state.play.OrderCreationPhase;
import ca.concordia.patterns.state.play.PlaySetup;
import ca.concordia.patterns.strategy.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 */
public class GameLoader {
    private GameEngine d_Ge;

    private String d_MapPath;
    private String d_Phase;
    private List<Player> d_ListOfPlayer = new ArrayList<Player>();

    public GameLoader(GameEngine p_Ge) {
        d_Ge = p_Ge;
    }

    public void loadGameFile(File p_SavedGameFile) {
        readGameFile(p_SavedGameFile);

        try {
            if (!d_MapPath.isEmpty() && !d_Phase.isEmpty() && d_ListOfPlayer.size() > 0) {
                // set playsetup phase
                d_Ge.setPhase(new PlaySetup(d_Ge));

                // load map
                String[] l_LoadCommand = {"loadmap ", d_MapPath};
                d_Ge.getPhase().loadMap(l_LoadCommand);

                // gameplayer command - to add players
                int l_PlayersCount = d_ListOfPlayer.size();
                String l_GamePlayerCommand = "gameplayer";
                for (Player l_Player : d_ListOfPlayer) {
                    l_GamePlayerCommand += " -add " + l_Player.getPlayerName();
                }
                LogUtil.log(l_GamePlayerCommand);
                String[] l_GamePlayerCommandArray = l_GamePlayerCommand.trim().split(" ");
                d_Ge.getPhase().setPlayers(l_GamePlayerCommandArray);

                //Instead of "assigncountries" in playsetup,
                // move it to the phase taken for saved gamefile i.e. always be OrderCreationPhase of human player
                d_Ge.setPhase(new OrderCreationPhase(d_Ge));
                d_Ge.getPhase().createOrder();

            }else{
                LogUtil.log("Invalid saved game file to load the game");
            }
        }catch (Exception e){
            LogUtil.log(e.getMessage());
        }
    }

    private void readGameFile(File p_SavedGameFile) {
        try (FileReader l_Fr = new FileReader(p_SavedGameFile))
        {
            char[] l_Chars = new char[(int) p_SavedGameFile.length()];
            l_Fr.read(l_Chars);

            String l_FileContent = new String(l_Chars);
            LogUtil.log(l_FileContent);

            JSONObject l_Obj = new JSONObject(l_FileContent);

            String l_Map = (String)l_Obj.get("map");
            d_MapPath = l_Map;
            String l_Phase = (String)l_Obj.get("phase-class");
            d_Phase = l_Phase;


            JSONArray l_PlayersArr = (JSONArray)l_Obj.get("players");
            for (int i = 0; i < l_PlayersArr.length(); i++) {
                JSONObject l_PlayerObj = l_PlayersArr.getJSONObject(i);
                String l_Name = l_PlayerObj.getString("name");
                int l_Id = l_PlayerObj.getInt("id");
                int l_Armies = l_PlayerObj.getInt("armies");
                boolean l_RandomCardAssigned = l_PlayerObj.getBoolean("random-card-assigned");
                boolean l_Negotiated = l_PlayerObj.getBoolean("negotiated");
                String l_StrategyClass = l_PlayerObj.getString("strategy-class");

                // re-creating player object
                Player l_CurrentPlayer = new Player(l_Name,l_Id);
                l_CurrentPlayer.setNoOfArmies(l_Armies);
                l_CurrentPlayer.setD_RandomCardAssigned(l_RandomCardAssigned);
                l_CurrentPlayer.setIsNegotiatedPlayer(l_Negotiated);
                if (l_StrategyClass.contains("Aggressive")){
                    l_CurrentPlayer.setStrategy(new Aggressive(d_Ge));
                }else if (l_StrategyClass.contains("Benevolent")){
                    l_CurrentPlayer.setStrategy(new Benevolent(d_Ge));
                }else if (l_StrategyClass.contains("Cheater")){
                    l_CurrentPlayer.setStrategy(new Cheater(d_Ge));
                }else if (l_StrategyClass.contains("Odd")){
                    l_CurrentPlayer.setStrategy(new Odd(d_Ge));
                }else {
                    l_CurrentPlayer.setStrategy(new Human(d_Ge));
                }

                //countries belongs to the player
                List<Country> l_ListOfCountry = new ArrayList<Country>();
                //parsing countries
                JSONArray l_CountriesArr = (JSONArray) l_PlayerObj.get("countries");
                for (int j = 0; j < l_CountriesArr.length(); j++){
                    JSONObject l_CountryObj = l_CountriesArr.getJSONObject(j);
                    String l_CountryName = l_CountryObj.getString("country-name");
                    int l_CountryId = l_CountryObj.getInt("country-id");
                    int l_ContinentId = l_CountryObj.getInt("continent-id");
                    int l_CountryArmy = l_CountryObj.getInt("country-army");
                    String l_CountryOwnerClass = l_CountryObj.getString("country-owner-class");
                    int l_CountryX = l_CountryObj.getInt("country-x");
                    int l_CountryY = l_CountryObj.getInt("country-y");

                    //create country object
                    Country l_CurrentCountry = new Country(l_CountryId,l_ContinentId,l_CountryName,l_CountryX,l_CountryY);
                    l_CurrentCountry.setArmyCount(l_CountryArmy);
                    l_CurrentCountry.setOwner(l_CurrentPlayer);
                    l_ListOfCountry.add(l_CurrentCountry);
                }
                l_CurrentPlayer.setListOfCountries(l_ListOfCountry);

                // Continents belongs to the player
                List<Continent> l_ListOfContinents = new ArrayList<Continent>();
                //parsing  continents
                JSONArray l_ContinentsArr = (JSONArray) l_PlayerObj.get("continents");
                for (int j = 0; j < l_ContinentsArr.length(); j++){
                    JSONObject l_ContinentObj = l_ContinentsArr.getJSONObject(j);
                    String l_ContinentName = l_ContinentObj.getString("continent-name");
                    int l_ContinentId = l_ContinentObj.getInt("continent-id");
                    String l_ContinentColor = l_ContinentObj.getString("continent-color");
                    int l_ContinentArmy = l_ContinentObj.getInt("continent-army");
                    String l_ContinentOwnerClass = l_ContinentObj.getString("continent-owner-class");

                    //create continent object
                    Continent l_CurrentContinent = new Continent(l_ContinentId,l_ContinentName,l_ContinentArmy,l_ContinentColor);
                    l_CurrentContinent.setOwner(l_CurrentPlayer);
                    l_ListOfContinents.add(l_CurrentContinent);
                }
                l_CurrentPlayer.setListOfContinents(l_ListOfContinents);

                //Orders by the player
                List<Order> l_ListOfOrder = new ArrayList<Order>();
                //parsing orders
                JSONArray l_OrdersArr = (JSONArray) l_PlayerObj.get("orders");
                for (int j = 0; j < l_OrdersArr.length(); j++){
                    JSONObject l_OrderObj = l_OrdersArr.getJSONObject(j);
                    String l_OrderClass = l_OrderObj.getString("order-class");

                    //TODO: we don't have the details of order yet, so order will be empty
                }
                l_CurrentPlayer.setListOfOrder(l_ListOfOrder);

                //Cards obtained by the player
                List<String> l_ListOfCards = new ArrayList<>();
                //parsing cards
                JSONArray l_CardsArr = (JSONArray) l_PlayerObj.get("cards");
                for (int j = 0; j < l_CardsArr.length(); j++){
                    JSONObject l_CardObj = l_CardsArr.getJSONObject(j);
                    String l_CardName = l_CardObj.getString("card-name");
                    l_ListOfCards.add(l_CardName);
                }
                l_CurrentPlayer.setOrderCards(l_ListOfCards);

                //finally add the player to the list of players
                d_ListOfPlayer.add(l_CurrentPlayer);
            }

        }
        catch (IOException e) {
            LogUtil.log(e.getMessage());
        }
    }
}
