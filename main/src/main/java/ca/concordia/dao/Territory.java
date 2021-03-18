package ca.concordia.dao;

public abstract class Territory {

    public int d_ArmyCount;

    public abstract String getOwner();

    public abstract void setOwner(String p_OwnerName);
}
