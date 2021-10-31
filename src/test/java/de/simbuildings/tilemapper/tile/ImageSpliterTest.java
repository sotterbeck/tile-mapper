package de.simbuildings.tilemapper.tile;

import de.simbuildings.tilemapper.image.SquareImageResolution;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Created by SimBuildings on 11.10.21 at 19:10
 */
class ImageSpliterTest {

    private final String path = "src/test/resources/image/";
    private ImageSpliter underTest;

    @AfterEach
    void tearDown() throws IOException {
        FileUtils.cleanDirectory(new File(path + "split/"));
    }

    @Test
    void shouldSplitImage() throws IOException {
        // given
        BufferedImage image = ImageIO.read(new File(path + "tile_sample_working.png"));
        SquareImageResolution targetResoltion = new SquareImageResolution(64);

        // when
        underTest = new ImageSpliter(image, targetResoltion);
        underTest.split();

        // then
        assertThat(underTest.getTiles()).isNotEmpty();
        assertThat(underTest.getTiles().length).isEqualTo(16);
    }

    @Test
    void shouldNotSplitNonValidImage() throws IOException {
        // given
        SquareImageResolution targetResoltion = new SquareImageResolution(64);

        // when
        BufferedImage image = ImageIO.read(new File(path + "tile_sample_failing.png"));

        // then
        assertThatThrownBy(() -> underTest = new ImageSpliter(image, targetResoltion)).isInstanceOf(RuntimeException.class);
    }

    @Test
    void shouldExportSplitImage() throws IOException {
        // given
        BufferedImage image = ImageIO.read(new File(path + "tile_sample_working.png"));
        SquareImageResolution targetResoltion = new SquareImageResolution(64);
        String destDir = path + "split/";

        // when
        underTest = new ImageSpliter(image, targetResoltion);
        underTest.split();
        underTest.save(destDir);

        // then
        assertThat(new File(destDir)).isNotEmptyDirectory();
    }

}