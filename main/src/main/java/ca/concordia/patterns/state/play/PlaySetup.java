package ca.concordia.patterns.state.play;

import ca.concordia.dao.Country;
import ca.concordia.dao.Map;
import ca.concordia.dao.Player;
import ca.concordia.gameengine.GameEngine;
import ca.concordia.mapworks.MapEditor;
import ca.concordia.patterns.observer.LogUtil;
import ca.concordia.patterns.strategy.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

/**
 * This class is used to set the game play by loading the map, creating the players and assigning respective
 * countries and adding and removing players as and when required
 */
public class PlaySetup extends Play {

    /**
     * Constructor that takes GameEngine object as argument
     *
     * @param p_ge GameEngine object
     */
    public PlaySetup(GameEngine p_ge) {
        super(p_ge);
    }

    /**
     * game starts with loading of user-saved map file, which loads the map
     * as a "connected-directed-graph"
     */
    @Override
    public void loadMap(String[] p_Command) {
        LogUtil.log("load map to start game ..");
        try {
            String l_Filename = p_Command[1];
            if (!l_Filename.isEmpty()) {
                LogUtil.log("loadmap command received ..");
                File l_MapFile = new File(l_Filename);
                if (l_MapFile.exists()) {

                    // read the map file
                    Map l_Map = MapEditor.getInstance().readMapFile(l_MapFile);

                    // TODO : updating map in the parent .. (review this)
                    d_ge.setMap(l_Map);
                    LogUtil.log(" map is successfully loaded and ready for gameplay ..");

                } else {
                    LogUtil.log("The map file" + l_MapFile.getAbsolutePath() + "doesn't exists");
                }
            }
        } catch (Exception p_E) {
            p_E.printStackTrace();
        }
    }

    /**
     * This method is used to set the players that is add the players to move to game play commands
     *
     * @param p_Command setPlayers command
     */
    @Override
    public void setPlayers(String[] p_Command) {
        LogUtil.log("gameplayer command received ..... ");

        if (d_ge.getMap() == null) {
            LogUtil.log("map must be loaded before assigning any player");
            return;
        }

        for (int l_I = 0; l_I < p_Command.length; l_I++) {
            String l_Tag = p_Command[l_I];

            if (l_Tag.toLowerCase().startsWith("-add")) {
                if (l_I + 1 < p_Command.length) {
                    String l_PlayerName = p_Command[++l_I];
                    addPlayer(l_PlayerName);
                } else {
                    LogUtil.log("INCOMPLETE COMMAND ");
                    LogUtil.log("INCOMPLETE COMMAND ");
                }

            } else if (l_Tag.toLowerCase().startsWith("-remove")) {
                if (l_I + 1 < p_Command.length) {
                    String l_PlayerName = p_Command[++l_I];
                    removePlayer(l_PlayerName);
                } else {
                    LogUtil.log("INCOMPLETE COMMAND ");
                    LogUtil.log("INCOMPLETE COMMAND");
                }
            }
        }
    }


    /**
     * takes player name and if there is no player with such name, it adds a new player with this name
     * in playerlist
     *
     * @param p_PlayerName playername to add
     * @return boolean
     */
    private boolean addPlayer(String p_PlayerName) {
        int l_Count = 0;
        for (Player l_Player : d_ge.getListOfPlayers()) {
            l_Count++;
            if (l_Player.getPlayerName().equalsIgnoreCase(p_PlayerName)) {
                LogUtil.log("A player with name: " + p_PlayerName + " already exists!");
                return false;
            }
        }
        Player l_NewPlayer = new Player(p_PlayerName, l_Count);
        return d_ge.getListOfPlayers().add(l_NewPlayer);
    }

    /**
     * takes playerName and if it is found in existing playerlist, remove it .
     *
     * @param p_PlayerName playername to remove
     * @return boolean
     */
    private boolean removePlayer(String p_PlayerName) {
        for (Player l_Player : d_ge.getListOfPlayers()) {
            if (l_Player.getPlayerName().equalsIgnoreCase(p_PlayerName)) {
                return d_ge.getListOfPlayers().remove(l_Player);
            }
        }
        LogUtil.log("player not found : " + p_PlayerName);
        return false;
    }


    /**
     * This method checks if the map is loaded successfully and if the no of players added is between 3 to 5
     * then move to assigning countries to players
     */
    @Override
    public void assignCountries() {
        LogUtil.log("assigncountries command received..");

        if (d_ge.getMap() == null) {
            LogUtil.log("map must be loaded before assigning any player");
            return;
        }

        try {
            int l_NumberOfPlayers = d_ge.getListOfPlayers().size();
            if (l_NumberOfPlayers >= 3 && l_NumberOfPlayers <= 5) {

                LogUtil.log("assigning strategy to players as per name");
                assignStrategyToPlayers();

                LogUtil.log("number of countries between [3 to 5] so assign countries to player now ..");
                if (assignCountriesToPlayers()) {
                    // start main-loop after this..
                    d_ge.setPhase(new ReinforcementPhase(d_ge));
                    d_ge.getPhase().reinforce();
                }
            } else {
                LogUtil.log("number of player must be between [3 to 5] to state main-game-loop");
            }

        } catch (Exception l_E) {
            l_E.printStackTrace();
        }
    }


    /**
     * This method assigns countries to players After the map is loaded and the players are created based on the no of players and
     * the countries in the map, countries are assigned to each player
     *
     * @return boolean if the countries are assigned successfully
     */
    private boolean assignCountriesToPlayers() {
        LogUtil.log("assign countries to players ..");
        if (d_ge.getListOfPlayers().size() < MINIMUM_PLAYER_COUNT) {
            LogUtil.log("Number of players should be atleast " + MINIMUM_PLAYER_COUNT + " to start assigning countries");
            return false;
        }
        if (d_ge.getMap().getListOfCountries().size() == 0) {
            LogUtil.log("Zero countries in the map, so unable to assign anything to players");
            return false;
        }
        LogUtil.log("Number of countries available is : " + d_ge.getMap().getListOfCountries().size());

        LogUtil.log("start assigning countries ");

        int l_CountryCount = d_ge.getMap().getListOfCountries().size();
        int l_PlayerCount = d_ge.getListOfPlayers().size();
        int l_PlayerCountryRatio = l_CountryCount / l_PlayerCount;
        LogUtil.log("player-country ratio: " + l_PlayerCountryRatio);
        ArrayList<Country> l_CountriesToAssignRandomly = new ArrayList<Country>();
        for (Country l_Country : d_ge.getMap().getListOfCountries()) {
            l_CountriesToAssignRandomly.add(l_Country);
        }

        while (l_CountriesToAssignRandomly.size() > 0) {
            for (Player l_Player : d_ge.getListOfPlayers()) {
                if (l_CountriesToAssignRandomly.size() == 0) {
                    LogUtil.log("All countries has been assigned ");
                    break;
                } else if (l_CountriesToAssignRandomly.size() == 1) {
                    Country l_Country = l_CountriesToAssignRandomly.get(0);
                    l_Country.setOwner(l_Player);
                    l_Player.addNewCountry(l_Country);
                    LogUtil.log(l_Player.getPlayerName() + " has " + l_Country.getName());
                    l_CountriesToAssignRandomly.remove(l_Country);
                } else {
                    int l_Index = new Random().nextInt(l_CountriesToAssignRandomly.size() - 1);
                    Country l_Country = l_CountriesToAssignRandomly.get(l_Index);
                    l_Country.setOwner(l_Player);
                    l_Player.addNewCountry(l_Country);
                    LogUtil.log(l_Player.getPlayerName() + " has " + l_Country.getName());
                    l_CountriesToAssignRandomly.remove(l_Country);
                }

            }
        }
        return true;
    }

    /**
     * Type of strategy is taken as input in string type "l_Name" variable and then if this
     * variable name matches to the type of strategy behavior i.e Aggressive, Benevolent etc
     * then Player is set to that behavior
     */
    private void assignStrategyToPlayers() {
        LogUtil.log("Assigning strategies to player according to name");
        for (Player l_Player : d_ge.getListOfPlayers()) {
            String l_Name = l_Player.getPlayerName();
            if (l_Name.equalsIgnoreCase("aggressive")) {
                l_Player.setStrategy(new Aggressive(d_ge));
            } else if (l_Name.equalsIgnoreCase("benevolent")) {
                l_Player.setStrategy(new Benevolent(d_ge));
            } else if (l_Name.equalsIgnoreCase("cheater")) {
                l_Player.setStrategy(new Cheater(d_ge));
            } else if (l_Name.equalsIgnoreCase("random")) {
                l_Player.setStrategy(new Odd(d_ge));
            } else {
                l_Player.setStrategy(new Human(d_ge));
            }
        }

    }
}
