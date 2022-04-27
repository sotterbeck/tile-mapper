package de.simbuildings.tilemapper.tile;

import de.simbuildings.tilemapper.image.SquareImageResolution;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class ImageSplitterTest {

    private static final File FAILING_IMAGE_FILE = new File("src/test/resources/image/tile_sample_failing.png");
    private ImageSplitter underTest;

    @TempDir
    private Path tempDir;

    private BufferedImage workingImagePowerOfTwo;
    private BufferedImage workingImageDividable;
    private BufferedImage invalidImage;

    private SquareImageResolution targetResolution;

    @BeforeEach
    void setUp() throws IOException {
        Path workingImageOnePath = Paths.get("src", "test", "resources", "image", "tile_sample_working.png");
        Path workingImageTwoPath = Paths.get("src", "test", "resources", "image", "tile_sample_working2.png");
        Path invalidImagePath = Paths.get("src", "test", "resources", "image", "tile_sample_failing.png");

        workingImagePowerOfTwo = ImageIO.read(workingImageOnePath.toFile());
        workingImageDividable = ImageIO.read(workingImageTwoPath.toFile());
        invalidImage = ImageIO.read(invalidImagePath.toFile());

        targetResolution = new SquareImageResolution(64);
    }


    @Test
    @DisplayName("Should not create image splitter with invalid image")
    void factory_ShouldNotAllowInvalidImage() throws IOException {
        // given
        SquareImageResolution targetResolution = new SquareImageResolution(64);

        // when
        Throwable thrown = catchThrowable(() -> underTest = ImageSplitter.of(invalidImage, targetResolution));

        // then
        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("original image height and width must be multiple of two");
    }

    @Test
    @DisplayName("Should not create image splitter with invalid (not power of two) resolution")
    void factory_ShouldNotAllowInvalidTargetResolution() throws IOException {
        // given
        SquareImageResolution invalidTargetResolution = new SquareImageResolution(34);

        // when
        Throwable thrown = catchThrowable(() -> underTest = ImageSplitter.of(workingImagePowerOfTwo, invalidTargetResolution));

        // then
        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("target image size must be multiple of two");
    }

    @Test
    void export_shouldSaveImages() throws IOException {
        // given
        BufferedImage image = ImageIO.read(new File("src/test/resources/image/tile_sample_working.png"));
        SquareImageResolution targetResolution = new SquareImageResolution(64);
        underTest = ImageSplitter.of(image, targetResolution);

        // when
        underTest.export(tempDir);

        // then
        assertThat(tempDir).isNotEmptyDirectory();
    }

    @Nested
    @DisplayName("Should split image")
    class Export_ShouldSplitImage {

        @Test
        @DisplayName("when image resolution is power of two")
        void whenImageResolutionIsPowerOfTwo() throws IOException {
            // given
            underTest = ImageSplitter.of(workingImagePowerOfTwo, targetResolution);

            // when
            underTest.export(tempDir);

            // then
            assertThat(underTest.getTiles())
                    .isNotEmpty()
                    .hasSize(16);
        }

        @Test
        @Disabled("Need better method to get valid resolutions")
        @DisplayName("when image resolution is dividable by target resolutions")
        void whenImageResolutionIsDividableByValidTargetResolutions() throws IOException {
            // given
            underTest = ImageSplitter.of(workingImageDividable, targetResolution);

            // when
            underTest.export(tempDir);

            // then
            assertThat(underTest.getTiles())
                    .isNotEmpty()
                    .hasSize(9);
        }
    }
}