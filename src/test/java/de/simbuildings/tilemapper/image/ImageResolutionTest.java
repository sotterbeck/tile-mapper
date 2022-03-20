package de.simbuildings.tilemapper.image;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by SimBuildings on 16.10.21 at 16:44
 */
class ImageResolutionTest {
    private ImageResolution underTest;

    @Test
    @DisplayName("Should get valid texture resolutions")
    void shouldGetValidTextureResolutions() {
        // given
        int width = 512;
        int height = 256;
        SquareImageResolution target = new SquareImageResolution(64);

        // when
        underTest = new ImageResolution(width, height);

        // then
        assertThat(underTest.getValidTextureResolutions()).hasSize(8)
                .map(ImageResolution::getHeight)
                .hasSizeBetween(2, 256);
    }

    @Test
    @DisplayName("Should compare two resolutions")
    void shouldCompareTwoResolutionSizes() {
        // given
        SquareImageResolution baseRes = new SquareImageResolution(64);
        SquareImageResolution largeRes = new SquareImageResolution(128);
        SquareImageResolution smallRes = new SquareImageResolution(32);
        boolean resultLarger;
        boolean resultSmaller;

        // when
        resultLarger = baseRes.isLargerThan(largeRes);
        resultSmaller = baseRes.isLargerThan(smallRes);

        // then
        assertThat(resultLarger).isTrue();
        assertThat(resultSmaller).isFalse();
    }

    @Test
    @DisplayName("Should check if image is square")
    void shouldCheckIfImageIsSquare() {
        // given
        ImageResolution resolution = new ImageResolution(64, 64);

        // when
        boolean result = resolution.isSquare();

        // then
        assertThat(result).isTrue();
    }
}