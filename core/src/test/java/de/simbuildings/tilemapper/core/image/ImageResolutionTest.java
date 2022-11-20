package de.simbuildings.tilemapper.core.image;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ImageResolutionTest {
    private ImageResolution underTest;

    @Test
    @DisplayName("Should get correct valid texture resolutions")
    void getValidTextureResolution_ShouldReturnCorrectTextureResolutions() {
        // given
        int width = 512;
        int height = 256;
        SquareImageResolution target = new SquareImageResolution(64);
        underTest = new ImageResolution(width, height);

        // when
        List<SquareImageResolution> actualResolutions = underTest.getValidTextureResolutions();

        // then
        assertThat(actualResolutions).hasSize(8)
                .map(ImageResolution::getHeight)
                .hasSizeBetween(2, 256);
    }

    @Test
    @DisplayName("Should compare two resolutions")
    void isLargerThat_ShouldCompareTwoResolutionSizes() {
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
        assertThat(resultLarger).isFalse();
        assertThat(resultSmaller).isTrue();
    }

    @Test
    @DisplayName("Should check if image is square")
    void isSquare_ShouldCheckIfImageIsSquare() {
        // given
        ImageResolution resolution = new ImageResolution(64, 64);

        // when
        boolean result = resolution.isSquare();

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("Should check if resolution is power of two when correct")
    void isPowerOfTwo_ShouldReturnTrue_WhenResolutionIsPowerOfTwo() {
        // given
        ImageResolution resolution = new ImageResolution(128, 128);

        // when
        boolean actual = resolution.isPowerOfTwo();

        // then
        assertThat(actual).isTrue();
    }

    @Test
    @DisplayName("Should check if resolution is power of two when false")
    void isPowerOfTwo_ShouldReturnFalse_WhenResolutionIsNotPowerOfTwo() {
        // given
        ImageResolution resolution = new ImageResolution(123, 342);

        // when
        boolean actual = resolution.isPowerOfTwo();

        // then
        assertThat(actual).isFalse();
    }
}