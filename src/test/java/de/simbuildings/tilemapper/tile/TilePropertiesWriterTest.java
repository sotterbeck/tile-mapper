package de.simbuildings.tilemapper.tile;

import de.simbuildings.tilemapper.ctm.RepeatCtmPropertiesWriter;
import de.simbuildings.tilemapper.image.SquareImageResolution;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class TilePropertiesWriterTest {

    private RepeatCtmPropertiesWriter underTest;

    @TempDir
    private Path tempDir;

    @Test
    void shouldWritePropertiesFile() {
        // given
        String block = "minecraft:stone";
        SquareImageResolution originalResolution = new SquareImageResolution(128);
        SquareImageResolution targetResolution = new SquareImageResolution(32);
        TileGrid grid = new TileGrid(originalResolution, targetResolution);
        underTest = new RepeatCtmPropertiesWriter(block, grid);

        // when
        Throwable thrown = catchThrowable(() -> underTest.export(tempDir));

        // then
        assertThat(thrown).isNull();
        assertThat(tempDir).isDirectoryContaining(path -> path.getFileName().toString().equals("stone.properties"));
    }

    // TODO: test for reading properties file and check if values are as expected
}