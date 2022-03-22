package de.simbuildings.tilemapper.tile;

import de.simbuildings.tilemapper.common.Exportable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

/**
 * Created by SimBuildings on 31.10.21 at 15:59
 */
public class TilePropertiesWriter implements Exportable {
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
        properties.setProperty("tiles", "0-" + (tileGrid.getTileAmount() - 1));  // TileAmount-1 is last png in folder

        properties.setProperty("width", String.valueOf(tileGrid.getWidth()));
        properties.setProperty("height", String.valueOf(tileGrid.getHeight()));
    }

    public void export(File destinationDirectory) throws IOException {
        String blockName = getFileBlockName();

        File outputFile = getOutputFile(destinationDirectory, blockName);

        try (OutputStream outputStream = new FileOutputStream(outputFile)) {
            properties.store(outputStream, comments);
        }
    }

    public boolean outputExists(File destinationDirectory) {
        return getOutputFile(destinationDirectory, getFileBlockName()).exists();
    }

    private String getFileBlockName() {
        return block.replace("minecraft:", "");
    }

    private File getOutputFile(File destinationDirectory, String blockName) {
        return new File(destinationDirectory, String.format("%s.properties", blockName));
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}