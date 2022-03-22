package de.simbuildings.tilemapper.tile;

import de.simbuildings.tilemapper.image.SquareImageResolution;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

/**
 * Created by SimBuildings on 31.10.21 at 16:21
 */
class TilePropertiesWriterTest {

    private TilePropertiesWriter underTest;

    @TempDir
    private File tempDir;

    @Test
    void shouldWritePropertiesFile() {
        // given
        String block = "minecraft:stone";
        SquareImageResolution originalResolution = new SquareImageResolution(128);
        SquareImageResolution targetResolution = new SquareImageResolution(32);
        TileGrid grid = new TileGrid(originalResolution, targetResolution);
        underTest = new TilePropertiesWriter(grid, block);

        // when
        Throwable thrown = catchThrowable(() -> underTest.export(tempDir));

        // then
        assertThat(thrown).isNull();
        assertThat(tempDir).isDirectoryContaining(file -> file.getName().equals("stone.properties"));
    }

    // TODO: test for reading properties file and check if values are as expected
}