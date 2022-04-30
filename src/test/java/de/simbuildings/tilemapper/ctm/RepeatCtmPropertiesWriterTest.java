package de.simbuildings.tilemapper.ctm;

import de.simbuildings.tilemapper.image.ImageResolution;
import de.simbuildings.tilemapper.image.SquareImageResolution;
import de.simbuildings.tilemapper.tile.TileGrid;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Set;

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

    @Nested
    @DisplayName("has Conflicts")
    class HasConflicts {
        @Test
        @DisplayName("should return false when file does not exists")
        void shouldReturnFalse_WhenFileDoesNotExists() {
            // given
            underTest = new RepeatCtmPropertiesWriter(block, grid);

            // when
            boolean result = underTest.hasConflict(tempDir);

            // then
            assertThat(result).isFalse();
        }

        @Test
        @DisplayName("should return true when file already exists")
        void shouldReturnTrue_WhenFileAlreadyExists() throws IOException {
            // given
            underTest = new RepeatCtmPropertiesWriter(block, grid);
            underTest.export(tempDir);

            // when
            boolean result = underTest.hasConflict(tempDir);

            // then
            assertThat(result).isTrue();
        }
    }

    @Test
    void getConflictFiles_ShouldReturnNothing_WhenNoConflict() {
        // given
        underTest = new RepeatCtmPropertiesWriter(block, grid);

        // when
        Set<Path> result = underTest.getConflictFiles(tempDir);

        // then
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Should get conflict files")
    void getConflictFiles_ShouldReturnExportedFile_WhenConflict() throws IOException {
        // given
        underTest = new RepeatCtmPropertiesWriter(block, grid);
        underTest.export(tempDir);

        // when
        Set<Path> result = underTest.getConflictFiles(tempDir);

        // then
        assertThat(result)
                .hasSize(1)
                .contains(tempDir.resolve(fileName));
    }
}