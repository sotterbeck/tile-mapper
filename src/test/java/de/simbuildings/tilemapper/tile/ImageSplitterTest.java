package de.simbuildings.tilemapper.tile;

import de.simbuildings.tilemapper.image.SquareImageResolution;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

/**
 * Created by SimBuildings on 11.10.21 at 19:10
 */
class ImageSplitterTest {

    private static final File WORKING_IMAGE_FILE = new File("src/test/resources/image/tile_sample_working.png");
    private static final File WORKING_IMAGE_FILE_TWO = new File("src/test/resources/image/tile_sample_working2.png");
    private static final File FAILING_IMAGE_FILE = new File("src/test/resources/image/tile_sample_failing.png");
    private ImageSplitter underTest;

    @TempDir
    private File tempDir;

    @Test
    @DisplayName("Should not create image splitter with invalid image")
    void shouldNotSplitNonValidImage() throws IOException {
        // given
        SquareImageResolution targetResolution = new SquareImageResolution(64);
        BufferedImage failingImage = ImageIO.read(FAILING_IMAGE_FILE);

        // when
        Throwable thrown = catchThrowable(() -> underTest = new ImageSplitter(failingImage, targetResolution));

        // then
        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("original image height and width must be multiple of two");
    }

    @Test
    @DisplayName("Should not set target resolution if its not power of two (invalid)")
    void shouldNotSetTargetResolutionIfItsNotPowerOfTwo() throws IOException {
        // given
        SquareImageResolution invalidTargetResolution = new SquareImageResolution(34);
        BufferedImage workingImage = ImageIO.read(WORKING_IMAGE_FILE);

        // when
        Throwable thrown = catchThrowable(() -> underTest = new ImageSplitter(workingImage, invalidTargetResolution));

        // then
        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("target image size must be multiple of two");
    }

    @Test
    void shouldExportSplitImage() throws IOException {
        // given
        BufferedImage image = ImageIO.read(WORKING_IMAGE_FILE);
        SquareImageResolution targetResolution = new SquareImageResolution(64);
        underTest = new ImageSplitter(image, targetResolution);
        underTest.split();

        // when
        underTest.export(tempDir);

        // then
        assertThat(tempDir).isNotEmptyDirectory();
    }

    @Nested
    @DisplayName("Should split image")
    class ShouldSplitImage {
        private BufferedImage workingImagePowerOfTwo;
        private BufferedImage workingImageDividable;
        private SquareImageResolution targetResolution;

        @BeforeEach
        void setUp() throws IOException {
            workingImagePowerOfTwo = ImageIO.read(WORKING_IMAGE_FILE);
            workingImageDividable = ImageIO.read(WORKING_IMAGE_FILE_TWO);
            targetResolution = new SquareImageResolution(64);
        }

        @Test
        @DisplayName("when image resolution is power of two")
        void whenImageResolutionIsPowerOfTwo() {
            // given
            underTest = new ImageSplitter(workingImagePowerOfTwo, targetResolution);

            // when
            underTest.split();

            // then
            assertThat(underTest.getTiles())
                    .isNotEmpty()
                    .hasSize(16);
        }

        @Test
        @Disabled("Need better method to get valid resolutions")
        @DisplayName("when image resolution is dividable by target resolutions")
        void whenImageResolutionIsDividableByValidTargetResolutions() {
            // given
            underTest = new ImageSplitter(workingImageDividable, targetResolution);

            // when
            underTest.split();

            // then
            assertThat(underTest.getTiles())
                    .isNotEmpty()
                    .hasSize(9);
        }
    }
}