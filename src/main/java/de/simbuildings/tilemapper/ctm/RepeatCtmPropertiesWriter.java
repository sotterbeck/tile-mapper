package de.simbuildings.tilemapper.ctm;

import de.simbuildings.tilemapper.tile.TileGrid;

public class RepeatCtmPropertiesWriter extends CtmPropertiesWriterBase {
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
    protected CtmPropertiesBuilder getPropertiesBuilder() {
        return defaultPropertiesBuilder()
                .tiles(tileGrid)
                .repeat(tileGrid.getWidth(), tileGrid.getHeight());
    }
}
