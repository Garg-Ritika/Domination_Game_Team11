package ca.concordia.dao;

public abstract class Territory {

    public abstract String getName();

    public abstract void setName(String p_Name);

    public abstract String getOwner();

    public abstract void setOwner(String p_OwnerName);

    public abstract int getArmyCount();

    public abstract void setArmyCount(int p_ArmyCount);


}
