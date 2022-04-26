package de.simbuildings.tilemapper.tile;

import de.simbuildings.tilemapper.ctm.RepeatCtmPropertiesWriter;
import de.simbuildings.tilemapper.image.ImageResolution;
import de.simbuildings.tilemapper.image.SquareImageResolution;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class RepeatCtmPropertiesWriterTest {

    private RepeatCtmPropertiesWriter underTest;

    @TempDir
    private Path tempDir;

    private TileGrid grid;
    private String block;
    private String fileName;

    @BeforeEach
    void setUp() {
        block = "minecraft:stone";
        fileName = "stone.properties";

        ImageResolution originalResolution = new SquareImageResolution(128);
        SquareImageResolution targetResolution = new SquareImageResolution(32);
        grid = new TileGrid(originalResolution, targetResolution);
    }

    @Test
    @DisplayName("Should write properties file without exception")
    void export_ShouldWritePropertiesFileWithoutException() {
        // given
        underTest = new RepeatCtmPropertiesWriter(block, grid);

        // when
        Throwable thrown = catchThrowable(() -> underTest.export(tempDir));

        // then
        assertThat(thrown).isNull();
        assertThat(tempDir).isDirectoryContaining(path -> path.getFileName().toString().equals("stone.properties"));
    }

    @Test
    @DisplayName("Should write properties file with correct entries")
    void export_ShouldWritePropertiesFileWithCorrectEntries() throws IOException {
        // given
        underTest = new RepeatCtmPropertiesWriter(block, grid);
        underTest.export(tempDir);

        Path actualPropertiesPath = tempDir.resolve(fileName);
        Properties actualProperties = new Properties();

        // when
        actualProperties.load(new FileInputStream(actualPropertiesPath.toFile()));

        // then
        assertThat(actualProperties)
                .containsEntry("matchBlocks", block)
                .containsEntry("height", String.valueOf(grid.getHeight()))
                .containsEntry("width", String.valueOf(grid.getWidth()))
                .containsEntry("method", "repeat")
                .containsEntry("tiles", "0-%d".formatted(grid.getTileAmount() - 1));
    }
}