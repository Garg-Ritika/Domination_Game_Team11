package ca.concordia.dao;

/**
 * This class contains the implementation of the territory.
 */
public abstract class Territory {

    /**
     * This getter function is used to get the name of the player
     *
     * @return d_Name returns the name of the player of territory
     */
    public abstract String getName();

    /**
     * This setter function is used to set the name of the player
     *
     * @param p_Name Player name is a string type and is passed as an argument to setName function
     */
    public abstract void setName(String p_Name);

    /**
     * This getter function is used to get the player name of the current owner name of the territory
     *
     * @return d_OwnerName returns the owner name
     */
    public abstract Player getOwner();

    /**
     * This setter function is used to set the player name as the current owner name of the territory
     *
     * @param p_OwnerName Owner Name i.e. Player name is passed as an argument
     */
    public abstract void setOwner(Player p_OwnerName);

    /**
     * This function is used to get the army count of the player
     *
     * @return d_ArmyCount
     */
    public abstract int getArmyCount();

    /**
     * This function is used to set the army count of the player
     *
     * @param p_ArmyCount ArmyCount is an Integer type and is passed as an argument
     */
    public abstract void setArmyCount(int p_ArmyCount);
}
