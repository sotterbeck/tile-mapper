package de.simbuildings.tilemapper.ctm;

import de.simbuildings.tilemapper.tile.TileGrid;

import java.util.Properties;

class CtmPropertiesBuilder {
    private final Properties properties = new Properties();

    public CtmPropertiesBuilder property(String key, String value) {
        properties.setProperty(key, value);
        return this;
    }

    public CtmPropertiesBuilder matchBlocks(String blocks) {
        properties.setProperty("matchBlocks", blocks);
        return this;
    }

    public CtmPropertiesBuilder tiles(TileGrid grid) {
        properties.setProperty("tiles", "0-%d".formatted(grid.getTileAmount() - 1));    // -1 to start from index 0
        return this;
    }

    public CtmPropertiesBuilder tiles(int start, int end) {
        properties.setProperty("tiles", "%d-%d".formatted(start, end));
        return this;
    }

    public CtmPropertiesBuilder repeat(int width, int height) {
        properties.setProperty("method", "repeat");
        properties.setProperty("width", String.valueOf(width));
        properties.setProperty("height", String.valueOf(height));
        return this;
    }

    public Properties build() {
        return properties;
    }
}
