package ca.concordia.patterns.command;

import ca.concordia.dao.Player;
import ca.concordia.dao.Territory;
import ca.concordia.patterns.observer.LogUtil;

public class Airlift implements Order {

    Territory source;
    Territory target;
    Player initiator;
    int num_to_airlift;

    //TODO : revise this code, it requires airlift card ..
    public Airlift(Player initiator, Territory source, Territory target, int num) {
        // encapsulate all necessary data to execute the command
        this.initiator = initiator;
        this.source = source;
        this.target = target;
        this.num_to_airlift = num;
    }


    public void execute() {
        // Here both the source and the target Territories are Receivers
        System.out.println("advance execute ");

        if (valid()) {
                // if the source and the target belong to the same player
                // then just move the armies to the target Territory
                target.d_ArmyCount+=num_to_airlift;
                source.d_ArmyCount-=num_to_airlift;
        }
        else {
                System.out.println("invalid order");
        }

    }

    public boolean valid() {
        //TODO what is the valid condition
        if((source.d_ArmyCount>=num_to_airlift) && (target.getOwner().equalsIgnoreCase(initiator.getPlayerName())) &&(source!=null) && (target!=null) ) {
            return true;
        }
        else{
            System.out.println("invalid order");
            return false;
        }
    }

    public void printOrder() {
        // TODO
        // print the order
        System.out.println();
        LogUtil.log("");
    }
}
