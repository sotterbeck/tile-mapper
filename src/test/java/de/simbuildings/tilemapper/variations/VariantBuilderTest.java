package de.simbuildings.tilemapper.variations;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class VariantBuilderTest {

    @Test
    @DisplayName("should build variant with rotation when its in increments of 90 degrees")
    void shouldBuildVariantWithRotation_WhenRotationsIsInIncrementsOf90Degrees() {
        // given
        int rotationX = 90;
        int rotationY = 180;

        // when
        Throwable thrown = catchThrowable(
                () -> new Variant.Builder("grass")
                        .rotationX(rotationX)
                        .rotationY(rotationY)
                        .build());

        // then
        assertThat(thrown).isNull();
    }

    @Test
    @DisplayName("should not build variant with rotation when its not in increments of 90 degrees")
    void shouldNotBuildVariantWithRotation_WhenRotationIsNotInIncrementsOf90Degrees() {
        // given
        int rotationX = 60;
        int rotationY = 45;

        // when
        Throwable thrown = catchThrowable(
                () -> new Variant.Builder("grass")
                        .rotationX(rotationX)
                        .rotationY(rotationY)
                        .build());

        // then
        assertThat(thrown).isNotNull();
    }
}