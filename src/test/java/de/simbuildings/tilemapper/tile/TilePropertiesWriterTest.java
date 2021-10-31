package de.simbuildings.tilemapper.tile;

import de.simbuildings.tilemapper.image.SquareImageResolution;
import org.junit.jupiter.api.Test;

/**
 * Created by SimBuildings on 31.10.21 at 16:21
 */
class TilePropertiesWriterTest {
    private static final String PATH = "src/test/resources/image/";
    private TilePropertiesWriter underTest;

    @Test
    void shouldWritePropertiesFile() {
        // given
        String block = "minecraft:stone";
        TileGrid grid = new TileGrid(new SquareImageResolution(128), new SquareImageResolution(32));

        // when
        underTest = new TilePropertiesWriter(grid, block);

        // then
        underTest.write(PATH);
    }

    // TODO: test for reading properties file and check if values are as expected
}