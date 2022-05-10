package de.simbuildings.tilemapper.ctm;

import de.simbuildings.tilemapper.image.SquareImageResolution;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class RepeatCtmBlockExporterTest {

    @TempDir
    Path tempDir;

    CtmExporter underTest;

    SquareImageResolution targetResolution;
    String blockName;
    BufferedImage originalImage;

    @BeforeEach
    void setUp() throws IOException {
        Path originalImagePath = Paths.get("src", "test", "resources", "image", "tile_sample_working.png");

        originalImage = ImageIO.read(originalImagePath.toFile());
        targetResolution = new SquareImageResolution(64);
        blockName = "minecraft:stone";
    }

    private CtmExporter createRepeatCtmBlockExporter() {
        return RepeatCtmExporter.of(originalImage, targetResolution, blockName);
    }

    @Test
    @DisplayName("Should export without exception")
    void export_ShouldExportWithoutException() {
        // given
        underTest = createRepeatCtmBlockExporter();

        // when
        Throwable thrown = catchThrowable(() -> underTest.export(tempDir));

        // then
        assertThat(thrown).isNull();
    }

    @Test
    @DisplayName("Should check if file already exists")
    void hasConflicts_ShouldReturnTrue_WhenFileAlreadyExists() throws IOException {
        // given
        underTest = createRepeatCtmBlockExporter();
        underTest.export(tempDir);

        // when
        boolean result = underTest.hasConflict(tempDir);

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("Should check if file already exists when nothing was exported")
    void getConflictFiles_ShouldReturnNothing_WhenNothingWasExported() {
        // given
        underTest = createRepeatCtmBlockExporter();

        // when
        Set<Path> result = underTest.getConflictFiles(tempDir);

        // then
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Should get conflict files when was exported")
    void getConflictFiles_ShouldReturnPaths_WhenWasExported() throws IOException {
        // given
        underTest = createRepeatCtmBlockExporter();
        underTest.export(tempDir);

        // when
        Set<Path> result = underTest.getConflictFiles(tempDir);

        // then
        assertThat(result).isNotEmpty();

    }

    @Test
    @DisplayName("Should get correct amount of conflict files when was exported")
    void getConflictFiles_ShouldReturnCorrectAmountOfPaths_WhenWasExported() throws IOException {
        // given
        underTest = createRepeatCtmBlockExporter();
        underTest.export(tempDir);

        // when
        Set<Path> result = underTest.getConflictFiles(tempDir);

        // then
        assertThat(result).hasSize(4 * 4 + 1);  // 16 png files and 1 properties file
    }

    @Test
    @DisplayName("Should return correct properties")
    void ctmProperties_ShouldReturnCorrectProperties() {
        // given
        underTest = createRepeatCtmBlockExporter();

        // when
        Map<String, String> actualProperties = underTest.ctmProperties();

        // then
        assertThat(actualProperties)
                .containsEntry("matchBlocks", blockName)
                .containsEntry("height", String.valueOf(4))
                .containsEntry("width", String.valueOf(4))
                .containsEntry("method", "repeat")
                .containsEntry("tiles", "0-%d".formatted(4 * 4 - 1));
    }
}
