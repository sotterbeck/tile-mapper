package de.simbuildings.tilemapper.tile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by SimBuildings on 31.10.21 at 15:59
 */
public class TilePropertiesWriter {
    private static final String METHOD = "repeat";

    private final Properties properties = new Properties();
    private final TileGrid tileGrid;
    private final String block;
    private String comments = "auto-generated properties file by Tile Mapper";

    public TilePropertiesWriter(TileGrid tileGrid, String block) {
        this.tileGrid = tileGrid;
        this.block = block;

        setValues();
    }

    private void setValues() {
        properties.setProperty("matchBlocks", block);
        properties.setProperty("method", METHOD);
        properties.setProperty("tiles", "0-" + (tileGrid.getTileAmout() - 1));  // TileAmount-1 is last png in folder

        properties.setProperty("width", String.valueOf(tileGrid.getWidth()));
        properties.setProperty("height", String.valueOf(tileGrid.getHeight()));
    }

    public void write(String destDir) {
        String blockName = block.replace("minecraft:", "");

        File file = new File(destDir + blockName + ".properties");
        try {
            FileOutputStream fileOut = new FileOutputStream(file);
            properties.store(fileOut, comments);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}