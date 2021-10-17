package de.simbuildings.tilemapper.tile;

import de.simbuildings.tilemapper.image.SquareImageResolution;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Created by SimBuildings on 11.10.21 at 19:10
 */
@Disabled
class ImageSpliterTest {

    private final String path = "src/test/resources/image/";
    private final InputStream workingImage = getClass().getClassLoader().getResourceAsStream("image/tile_sample_working.png");
    private final InputStream failingImage = getClass().getClassLoader().getResourceAsStream("image/tile_sample_failing.png");
    private static final URL splitDir = ImageSpliterTest.class.getClassLoader().getResource("image/split");

    private ImageSpliter underTest;

    @BeforeAll
    static void beforeAll() throws IOException {
        FileUtils.forceMkdir(new File(Objects.requireNonNull(splitDir).getFile()));
    }

    @AfterEach
    void tearDown() throws IOException {
        FileUtils.cleanDirectory(new File(Objects.requireNonNull(splitDir).getFile()));
    }

    @Test
    void shouldSplitImage() throws IOException {
        // given
        BufferedImage image = ImageIO.read(Objects.requireNonNull(workingImage));
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
        BufferedImage image = ImageIO.read(Objects.requireNonNull(failingImage));

        // then
        assertThatThrownBy(() -> underTest = new ImageSpliter(image, targetResoltion)).isInstanceOf(RuntimeException.class);
    }

    @Test
    void shouldExportSplitImage() throws IOException {
        // given
        BufferedImage image = ImageIO.read(Objects.requireNonNull(workingImage));
        SquareImageResolution targetResoltion = new SquareImageResolution(64);

        // when
        underTest = new ImageSpliter(image, targetResoltion);
        underTest.split();
        underTest.exportTiles(Objects.requireNonNull(splitDir).getPath() + "/");
        System.out.println(splitDir.getPath());

        // then
        assertThat(new File(splitDir.getPath() + "/")).isNotEmptyDirectory();
    }

}