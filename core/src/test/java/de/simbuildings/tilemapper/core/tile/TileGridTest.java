package de.simbuildings.tilemapper.core.tile;

import de.simbuildings.tilemapper.core.image.ImageResolution;
import de.simbuildings.tilemapper.core.image.SquareImageResolution;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.catchThrowable;

class TileGridTest {
    @Test
    @DisplayName("Should not create tile grid when target resolution is lager than original resolution")
    void ShouldNotCreateTileGridWithResolutions_WhenTargetResolutionIsLargerThanOriginal() {
        // given
        ImageResolution originalResolution = new ImageResolution(256, 256);
        SquareImageResolution targetResolution = new SquareImageResolution(1024);

        // when
        Throwable thrown = catchThrowable(() -> new TileGrid(originalResolution, targetResolution));

        // then
        assertThat(thrown).isNotNull();
    }
}