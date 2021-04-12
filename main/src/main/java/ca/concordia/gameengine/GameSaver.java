package ca.concordia.gameengine;

import ca.concordia.dao.Player;
import ca.concordia.patterns.observer.LogUtil;

import java.io.*;


public class GameSaver {

    private GameEngine d_Ge;
    public GameSaver(GameEngine p_Ge){
        d_Ge = p_Ge;
    }

    public void saveFile(File p_GameFile){
        try{
            FileWriter l_Fw = new FileWriter(p_GameFile, true);
            BufferedWriter l_Bw = new BufferedWriter(l_Fw);

            String l_Content ="";
            addMapLocation(l_Content);
            addPhaseDetails(l_Content);
            addPlayers(l_Content);

            l_Bw.write(l_Content);
            l_Bw.close();

        }catch (IOException io){
            LogUtil.log(io.getMessage());
        }
    }

    private void addMapLocation(String p_Content){
        p_Content += ("\r\n[Map]\r\n");
        p_Content += (d_Ge.getMap().getName()+"\r\n");
    }

    private void addPhaseDetails(String p_Content){
        p_Content += ("\r\n[CurrentPhase]\r\n");
        p_Content += (d_Ge.getPhase().getClass().getName() +"\r\n");
    }

    private void addPlayers(String p_Content){
        p_Content += ("\r\n[Players]\r\n");
        for (Player l_Player: d_Ge.getListOfPlayers()){
            p_Content += ( l_Player.getPlayerName() +"\r\n");
        }
    }

}
