package ca.concordia.patterns.strategy;

import ca.concordia.dao.Country;
import ca.concordia.dao.Player;
import ca.concordia.dao.Territory;
import ca.concordia.patterns.observer.LogUtil;

import java.util.Random;

public class AggressivePlayer implements Order {

    Territory d_Source;
    Territory d_Target;
    Player d_Initiator;
    Player d_NegotiatePlayerId;
    int d_Num;
    boolean L_Valid_condition = false;
    Territory d_SourceAirlift;
    Territory d_TargetAirlift;
    int d_NumAirlift;
    Territory d_TargetDeploy;
    int d_NumDeploy;

    public AggressivePlayer(Player p_Initiator, Territory p_Source, Territory p_Target, int p_Num) {
        this.d_Initiator = p_Initiator;
        this.d_Source = p_Source;
        this.d_Target = p_Target;
        this.d_Num = p_Num;
    }

    public AggressivePlayer(String orderName, Player p_Initiator, Territory p_SourceAirlift, Territory p_TargetAirlift, int p_NumAirlift) {
        this.d_Initiator = p_Initiator;
        this.d_SourceAirlift = p_SourceAirlift;
        this.d_TargetAirlift = p_TargetAirlift;
        this.d_NumAirlift = p_NumAirlift;
    }

    public AggressivePlayer(Player p_Initiator, Territory p_TargetTerritory) {
        this.d_Target = p_TargetTerritory;
        this.d_Initiator = p_Initiator;
    }

    public AggressivePlayer(String orderName, Player p_Initiator, Territory p_TargetTerritory) {
        this.d_Target = p_TargetTerritory;
        this.d_Initiator = p_Initiator;
    }

    public AggressivePlayer(Player p_Initiator, Territory p_Target_territoryDeploy, int p_To_deploy) {
        this.d_TargetDeploy = p_Target_territoryDeploy;
        this.d_Initiator = p_Initiator;
        this.d_NumDeploy = p_To_deploy;
    }

    public AggressivePlayer(Player p_Initiator, Player p_Negotiate_PlayerId) {
        this.d_Initiator = p_Initiator;
        this.d_NegotiatePlayerId = p_Negotiate_PlayerId;
    }

    public void advance() {
        String[] l_ListOfRandomCards = {"bomb", "blockade", "airlift", "negotiate"};
        Random l_R = new Random();
        String randomCard = l_ListOfRandomCards[l_R.nextInt(l_ListOfRandomCards.length)];
        LogUtil.log("advance execute ");

        if ((d_Initiator.getListOfCountries().contains(d_Source))
                && (d_Source.getArmyCount() >= d_Num)
                && (d_Source != null)
                && (d_Target != null)) {
            L_Valid_condition = true;
        } else {
            LogUtil.log("invalid order");
        }

        if (L_Valid_condition) {
            if (d_Target.getOwner().getPlayerName().equalsIgnoreCase(d_Initiator.getPlayerName())) {
                // if the source and the target belong to the same player
                // then just move the armies to the target Territory

                this.d_Target.setArmyCount(this.d_Target.getArmyCount() + d_Num);
            } else {
                // implement a battle
                LogUtil.log("in implementing battle astage");
                int l_DefendingArmies = (int) (this.d_Target.getArmyCount() * 0.7); //7
                int l_AttackingArmies = (int) (this.d_Num * 0.6); //15
                this.d_Target.setArmyCount(this.d_Target.getArmyCount() - l_AttackingArmies); //10-18=-8
                if (this.d_Target.getArmyCount() < 0) {
                    // move surviving attacking armies to the target country
                    // transfer ownership of the conquered country
                    this.d_Target.getOwner().removeNewCountry((Country) this.d_Target);

                    this.d_Target.setOwner(d_Initiator);
                    this.d_Target.setArmyCount(this.d_Source.getArmyCount() - l_DefendingArmies);
                    //removing the territory from the l_ListOfRandomCards and adding to the player who won it
                    this.d_Initiator.addNewCountry((Country) this.d_Target);
                    this.d_Source.setArmyCount(this.d_Source.getArmyCount() - d_Num);
                    if (!this.d_Initiator.getD_RandomCardAssigned()) {
                        this.d_Initiator.addNewOrderCard(randomCard);
                        LogUtil.log(randomCard + " assigned to player " + d_Initiator);
                        this.d_Initiator.setD_RandomCardAssigned(true);
                    }
                } else {
                    this.d_Source.setArmyCount(d_Source.getArmyCount() - l_DefendingArmies);
                }
            }
        }

    }

    public void airlift() {
        LogUtil.log("advance execute ");

        if ((d_SourceAirlift.getArmyCount() >= d_NumAirlift)
                && (d_TargetAirlift.getOwner().getPlayerName().equalsIgnoreCase(d_Initiator.getPlayerName()))
                && (d_SourceAirlift != null)
                && (d_TargetAirlift != null)) {
            L_Valid_condition = true;
        } else {
            LogUtil.log("invalid order");
        }

        if (L_Valid_condition) {
            // if the source and the target belong to the same player
            // then just move the armies to the target Territory
            d_TargetAirlift.setArmyCount(d_TargetAirlift.getArmyCount() + d_NumAirlift);
            d_SourceAirlift.setArmyCount(d_SourceAirlift.getArmyCount() - d_NumAirlift);
            LogUtil.log(d_NumAirlift + " has been moved");
        } else {
            LogUtil.log("invalid order");
        }

    }

    public void blockade() {
        LogUtil.log("blockade execute ");
        // Here, the target Territory object is the Receiver

        if ((d_Target.getOwner().getPlayerName().equalsIgnoreCase(d_Initiator.getPlayerName())) && (d_Target != null)) {
            // the target territory must belong to the player that created the order
            L_Valid_condition = true;
        } else {
            LogUtil.log("invalid order");

        }

        if (L_Valid_condition) {
            // behavior of the concrete command
            Player l_NeutralPlayer = new Player("Neutral", Integer.MAX_VALUE);
            this.d_Target.setArmyCount(this.d_Target.getArmyCount() * 3);
            //how to make this territory neutral?
            this.d_Target.setOwner(l_NeutralPlayer);
            this.d_Initiator.getListOfCountries().remove(this.d_Target);
            LogUtil.log(this.d_Target + " has been neutralized");
        }

    }

    public void bomb() {
        LogUtil.log("bomb execute ");

        if ((d_Target.getOwner().getPlayerName().equalsIgnoreCase(d_Initiator.getPlayerName()) != true) && (d_Target != null)) {
            // the target territory must not belong to the player that created the order
            L_Valid_condition = true;
        } else {
            LogUtil.log("invalid order");

        }

        if (L_Valid_condition) {
            // behavior of the concrete command
            this.d_Target.setArmyCount(this.d_Target.getArmyCount() / 2);
            LogUtil.log(this.d_Target + " half of the armies is destroyed");
        }

    }

    public void deploy() {
        LogUtil.log("deploy execute ");

        LogUtil.log("--> deploy valid ");
        System.out.println(this.d_TargetDeploy.getName());
        System.out.println(this.d_TargetDeploy.getOwner().getPlayerName());
        System.out.println(this.d_Initiator.getPlayerName());
        if (this.d_TargetDeploy.getOwner().getPlayerName().equalsIgnoreCase(this.d_Initiator.getPlayerName())) {
            // the target territory must belong to the player that created the order
            L_Valid_condition = true;
        } else {
            LogUtil.log("invalid deploy order");
        }

        if (L_Valid_condition) {
            // behavior of the concrete command
            int l_ExistingArmy = this.d_TargetDeploy.getArmyCount();
            this.d_Initiator.setNoOfArmies(this.d_Initiator.getNoOfArmies() - d_NumDeploy);
            this.d_TargetDeploy.setArmyCount(l_ExistingArmy + d_NumDeploy);
            LogUtil.log("deploying " + d_NumDeploy + " to " + d_TargetDeploy.getName());
        }

    }

    public void diplomacy() {

        LogUtil.log("diplomacy execute ");

        if (d_NegotiatePlayerId.getPlayerName() != null) {
            // target Player Id must exist
            L_Valid_condition = true;
        } else {
            LogUtil.log("invalid order");
        }

        if (L_Valid_condition) {
            d_Initiator.setIsNegotiatedPlayer(true);
            d_NegotiatePlayerId.setIsNegotiatedPlayer(true);
            LogUtil.log(d_Initiator + " and " + d_NegotiatePlayerId + " are refrained from attack until next turn");
        }

    }
}
