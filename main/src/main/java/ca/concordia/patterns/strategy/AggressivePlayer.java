package ca.concordia.patterns.strategy;

import ca.concordia.dao.Player;
import ca.concordia.dao.Territory;

public class AggressivePlayer implements OrderInterface {

    Territory d_Source;
    Territory d_Target;
    Player d_Initiator;
    Player d_NegotiatePlayerId;
    int d_Num;
    boolean L_Valid_condition = false;

    public AggressivePlayer(Player p_Initiator, Territory p_Source, Territory p_Target, int p_Num) {
        this.d_Initiator = p_Initiator;
        this.d_Source = p_Source;
        this.d_Target = p_Target;
        this.d_Num = p_Num;
    }

    public AggressivePlayer(Player p_Initiator, Territory p_TargetTerritory) {
        this.d_Target = p_TargetTerritory;
        this.d_Initiator = p_Initiator;
    }

    public AggressivePlayer(Player p_Initiator, Territory p_Target_territory, int p_To_deploy) {
        this.d_Target = p_Target_territory;
        this.d_Initiator = p_Initiator;
        this.d_Num = p_To_deploy;
    }

    public AggressivePlayer(Player p_Initiator, Player p_Negotiate_PlayerId) {
        this.d_Initiator = p_Initiator;
        this.d_NegotiatePlayerId = p_Negotiate_PlayerId;
    }

    public void advance() {

    }

    public void airlift() {

    }

    public void blockade() {

    }

    public void bomb() {

    }

    public void deploy() {

    }

    public void diplomacy() {

    }
}
