package ca.concordia.gameengine;

import ca.concordia.dao.Player;
import ca.concordia.patterns.observer.LogUtil;

import java.io.*;

/**
 * GameSaver class saves the GameFile along MapLocation ,Phases & Players details.
 */

public class GameSaver {

    private GameEngine d_Ge;
    public GameSaver(GameEngine p_Ge){
        d_Ge = p_Ge;
    }

    /**
     * This method is used for saving GameFile
     * @param p_GameFile
     */
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

    /**
     * This method adds MapLocation to the GameFile
     * @param p_Content
     */
    private void addMapLocation(String p_Content){
        p_Content += ("\r\n[Map]\r\n");
        p_Content += (d_Ge.getMap().getName()+"\r\n");
    }

    /**
     * This method is used for getting Phase details
     * @param p_Content
     */
    private void addPhaseDetails(String p_Content){
        p_Content += ("\r\n[CurrentPhase]\r\n");
        p_Content += (d_Ge.getPhase().getClass().getName() +"\r\n");
    }

    /**
     * This method is used for getting Player Details 
     * @param p_Content
     */

    private void addPlayers(String p_Content){
        p_Content += ("\r\n[Players]\r\n");
        for (Player l_Player: d_Ge.getListOfPlayers()){
            p_Content += ( l_Player.getPlayerName() +"\r\n");
        }
    }

}
