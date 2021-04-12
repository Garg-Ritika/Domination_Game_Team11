package ca.concordia.gameengine;

import ca.concordia.dao.Player;
import ca.concordia.patterns.observer.LogUtil;
import ca.concordia.patterns.state.play.PlaySetup;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class GameLoader {
    private GameEngine d_Ge;

    private String d_MapPath ;
    private String d_Phase ;
    private List<Player> d_ListOfPlayer = new ArrayList<Player>();

    public GameLoader(GameEngine p_Ge){
        d_Ge = p_Ge;
    }

    public void loadGameFile(){
        if (d_MapPath.isEmpty() && !d_Phase.isEmpty() && d_ListOfPlayer.size()>0){
            // set playsetup phase
            d_Ge.setPhase(new PlaySetup(d_Ge));

            // load map
            String[] l_LoadCommand = {"loadmap ", d_MapPath};
            d_Ge.getPhase().loadMap(l_LoadCommand);

            // gameplayer command - to add players
            int l_PlayersCount = d_ListOfPlayer.size();
            String l_GamePlayerCommand = "gameplayer";
            for (Player l_Player: d_ListOfPlayer) {
                l_GamePlayerCommand += " -add " + l_Player.getPlayerName();
            }
            String[] l_GamePlayerCommandArray = l_GamePlayerCommand.trim().split(" ");
            d_Ge.getPhase().setPlayers(l_GamePlayerCommandArray);

            //assign countries
            d_Ge.getPhase().assignCountries();

        }
    }

    private void readGameFile(File p_SavedGameFile){
        try{
            FileReader l_Fr = new FileReader(p_SavedGameFile);
            BufferedReader l_Br = new BufferedReader(l_Fr);

            String l_CurrentLine;
            while ((l_CurrentLine = l_Br.readLine()) != null) {
                if (l_CurrentLine.contains("[Map]")) {
                    l_CurrentLine = l_Br.readLine();
                    d_MapPath = l_CurrentLine;
                }
                if (l_CurrentLine.contains("[CurrentPhase]")){
                    l_CurrentLine = l_Br.readLine();
                    d_Phase = l_CurrentLine;
                }

                if (l_CurrentLine.contains("[Players]")){
                    int count =0;
                    while (l_CurrentLine != null) {
                        l_CurrentLine = l_Br.readLine();
                        Player l_P = new Player(l_CurrentLine,count++);
                        d_ListOfPlayer.add(l_P);
                    }
                }
            }
        }catch(IOException io){
            LogUtil.log(io.getMessage());
        }
    }
}
