package de.simbuildings.tilemapper.tile;

import de.simbuildings.tilemapper.image.SquareImageResolution;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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

    private static final File WORKING_IMAGE = new File("src/test/resources/image/tile_sample_working.png");
    private static final File FAILING_IMAGE = new File("src/test/resources/image/tile_sample_failing.png");
    private ImageSplitter underTest;

    @TempDir
    private File tempDir;

    @Test
    @DisplayName("Should split image when image is valid")
    void shouldSplitImage() throws IOException {
        // given
        BufferedImage workingImage = ImageIO.read(WORKING_IMAGE);
        SquareImageResolution targetResolution = new SquareImageResolution(64);

        underTest = new ImageSplitter(workingImage, targetResolution);

        // when
        underTest.split();

        // then
        assertThat(underTest.getTiles())
                .isNotEmpty()
                .hasSize(16);
    }

    @Test
    @DisplayName("Should not create image splitter with invalid image")
    void shouldNotSplitNonValidImage() throws IOException {
        // given
        SquareImageResolution targetResolution = new SquareImageResolution(64);
        BufferedImage failingImage = ImageIO.read(FAILING_IMAGE);

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
        BufferedImage workingImage = ImageIO.read(WORKING_IMAGE);

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
        BufferedImage image = ImageIO.read(WORKING_IMAGE);
        SquareImageResolution targetResolution = new SquareImageResolution(64);
        underTest = new ImageSplitter(image, targetResolution);
        underTest.split();

        // when
        underTest.export(tempDir);

        // then
        assertThat(tempDir).isNotEmptyDirectory();
    }

}