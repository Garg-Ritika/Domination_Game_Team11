package ca.concordia.patterns.adapter;

import ca.concordia.dao.Map;
import ca.concordia.patterns.observer.LogUtil;

import java.io.File;
import java.io.IOException;

/**
 * This class extends the DominationMapHandler class
 * This enable the application to read/write from/to a file using "conquest" game map format.
 */
public class MapHandlerAdapter extends DominationMapHandler {

    /**
     * ConquestMapHandler class's object is initialized
     */
    ConquestMapHandler d_CMH;

    public MapHandlerAdapter(ConquestMapHandler p_CMH) {
        d_CMH = p_CMH;
    }

    /**
     * Helper method to read the map file from filesystem
     *
     * @param p_MapFile the file object of map file
     * @return map object
     * @throws IOException if an error is found, it will throw an error of InputOutput type
     */
    @Override
    public Map readMapFile(File p_MapFile) throws IOException {
        return new ConquestMapHandler().readMapFile(p_MapFile);
    }

    /**
     * Private method to write the map file
     *
     * @param p_MapFile the map file object
     */
    @Override
    public void writeMapFile(File p_MapFile) {
        LogUtil.log("writing .map file to path " + p_MapFile.getAbsolutePath());
        new ConquestMapHandler().writeMapFile(p_MapFile);
        LogUtil.log("Successfully written map to .map file at: " + p_MapFile.getAbsolutePath());
    }

}
