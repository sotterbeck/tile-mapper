package de.simbuildings.tilemapper.image;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by SimBuildings on 16.10.21 at 16:44
 */
class ImageResolutionTest {
    private ImageResolution underTest;

    @Test
    void shouldGenerateSqrResolutionOfTwoUntilMaxResolution() {
        // given
        int width = 512;
        int height = 256;
        SquareImageResolution target = new SquareImageResolution(64);

        // when
        underTest = new ImageResolution(width, height);

        // then
        assertThat(underTest.getValuesPowerOfTwoUntilRes().size()).isEqualTo(8);
        for (SquareImageResolution res :
                underTest.getValuesPowerOfTwoUntilRes()) {
            System.out.println(res.getHeight());
        }
    }

    @Test
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
}