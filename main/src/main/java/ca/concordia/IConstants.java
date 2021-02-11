package ca.concordia;

public interface IConstants {


    // gameplay commands
    public static String COMMAND_LOAD_MAP = "loadmap";
    public static String COMMAND_GAME_PLAYER = "gameplayer";
    public static String COMMAND_ASSIGN_COUNTRIES = "assigncountries";
    public static String COMMAND_DEPLOY = "deploy";


    // com.riskgame.org.riskgame.model.map editor commands ..
    public static String COMMAND_EDIT_CONTINENT = "editcontinent";
    public static String COMMAND_EDIT_COUNTRY = "editcountry";
    public static String COMMAND_EDIT_NEIGHBOUR = "editneighbor";

    public static String COMMAND_SHOW_MAP = "showmap";
    public static String COMMAND_SAVE_MAP = "savemap";
    public static String COMMAND_EDIT_MAP = "editmap";
    public static String COMMAND_VALIDATE_MAP = "validatemap";

    //
    /* details from domination com.riskgame.org.riskgame.model.map file */
    public static final String HEADER_CONTINENT = "[continents]";
    public static final String HEADER_COUNTRIES = "[countries]";
    public static final String HEADER_BORDERS = "[borders]";


}