package de.simbuildings.tilemapper.variations;

import de.simbuildings.tilemapper.resourcepack.Resource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class BlockStateVariantTest {
    @Test
    @DisplayName("Should build variant with rotation when its in increments of 90 degrees")
    void builder_ShouldBuildVariantWithRotation_WhenRotationsIsInIncrementsOf90Degrees() {
        // given
        Resource resource = createAnyResource();
        int rotationX = 90;
        int rotationY = 180;

        // when
        Throwable thrown = catchThrowable(
                () -> new BlockStateVariant.Builder(resource)
                        .rotationX(rotationX)
                        .rotationY(rotationY)
                        .build());

        // then
        assertThat(thrown).isNull();
    }

    @Test
    @DisplayName("Should not build variant with rotation when its not in increments of 90 degrees")
    void builder_ShouldNotBuildVariantWithRotation_WhenRotationIsNotInIncrementsOf90Degrees() {
        // given
        Resource resource = createAnyResource();
        int rotationX = 60;
        int rotationY = 45;

        // when
        Throwable thrown = catchThrowable(
                () -> new BlockStateVariant.Builder(resource)
                        .rotationX(rotationX)
                        .rotationY(rotationY)
                        .build());

        // then
        assertThat(thrown).isNotNull();
    }

    @Test
    @DisplayName("Should compare two variants by model name")
    void compareTo_shouldCompareTwoVariantsByModelName() {
        // given
        BlockStateVariant variantOne = new BlockStateVariant.Builder(
                new Resource("sandstone1"))
                .build();

        BlockStateVariant variantTwo = new BlockStateVariant.Builder(
                new Resource("sandstone2"))
                .build();

        // then
        assertThat(variantOne).isLessThan(variantTwo);
    }

    private Resource createAnyResource() {
        return new Resource("grass");
    }

    @Test
    void builder_ShouldCreateVariantWithCustomNamespace() {
        // given
        Resource resource = createAnyResource();
        BlockStateVariant variant = new BlockStateVariant.Builder(resource.withNamespace("iuvat"))
                .build();

        // when
        String model = variant.model();

        // then
        assertThat(model).startsWith("iuvat:block/");
    }
}