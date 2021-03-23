package ca.concordia.dao;

public abstract class Territory {

    public abstract String getName();

    public abstract void setName(String p_Name);

    public abstract Player getOwner();

    public abstract void setOwner(Player p_OwnerName);

    public abstract int getArmyCount();

    public abstract void setArmyCount(int p_ArmyCount);


}
