package de.simbuildings.tilemapper.ctm;

import de.simbuildings.tilemapper.tile.TileGrid;

class RepeatCtmPropertiesWriter extends CtmPropertiesWriter {
    private final TileGrid tileGrid;

    public RepeatCtmPropertiesWriter(String block, TileGrid tileGrid, String comment) {
        super(block, comment);
        this.tileGrid = tileGrid;
    }

    public RepeatCtmPropertiesWriter(String block, TileGrid tileGrid) {
        super(block);
        this.tileGrid = tileGrid;
    }

    @Override
    CtmPropertiesBuilder getPropertiesBuilder() {
        return defaultPropertiesBuilder()
                .tiles(tileGrid)
                .repeat(tileGrid.getWidth(), tileGrid.getHeight());
    }
}
