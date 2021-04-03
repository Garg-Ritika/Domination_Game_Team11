package ca.concordia.patterns.adapter;

import ca.concordia.dao.Map;
import ca.concordia.mapworks.MapEditor;
import ca.concordia.patterns.observer.LogUtil;

import java.io.File;
import java.io.IOException;

public class MapFileAdapter extends MapEditor {

    public Map d_Map;

    public MapFileAdapter(Map p_Map){
        d_Map = p_Map;
    }

    /**
     * Helper method to read the map file from filesystem
     *
     * @param p_MapFile the file object of map file
     * @return map object
     * @throws IOException if an error is found, it will throw an error of InputOutput type
     */
    public Map readMapFile(File p_MapFile) throws IOException {
        super.resetCurrentMap();
        d_Map = new ConquestFileHandler(d_Map).readMapFile(p_MapFile);
        return d_Map;
    }

    /**
     * Private method to write the map file
     * @param p_MapFile the map file object
     *
     */
    private void writeMapFile(File p_MapFile)  {
        LogUtil.log("writing .map file to path " + p_MapFile.getAbsolutePath());
        new ConquestFileHandler(d_Map).writeMapFile(p_MapFile);
        LogUtil.log("Successfully written map to .map file at: " + p_MapFile.getAbsolutePath());
    }

}
