package de.simbuildings.tilemapper.variations;

import de.simbuildings.tilemapper.resourcepack.Resource;
import de.simbuildings.tilemapper.variations.model.Face;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ResourceVariantTest {
    @Test
    @DisplayName("Should create variant with single resource")
    void shouldCreateVariantWithSingleResource() {
        // given
        Resource sandstoneResource = createSandstoneResource();

        // when
        ResourceVariant resourceVariant = new ResourceVariant(sandstoneResource);

        // then
        assertThat(resourceVariant.defaultResource()).isEqualTo(sandstoneResource);
    }

    @Test
    @DisplayName("should get slab resource without override")
    void shouldGetSlabResource_WhenNoOverride() {
        // given
        Resource sandstoneResource = createSandstoneResource();

        // when
        ResourceVariant resourceVariant = new ResourceVariant(sandstoneResource);
        Resource resourceTop = resourceVariant.slabResource(Face.TOP);

        // then
        assertThat(resourceTop).isEqualTo(sandstoneResource);
    }

    @Test
    @DisplayName("should get stair resource without override")
    void shouldGetStairResource_WhenNoOverride() {
        // given
        Resource sandstoneResource = createSandstoneResource();

        // when
        ResourceVariant resourceVariant = new ResourceVariant(sandstoneResource);
        Resource resourceTop = resourceVariant.stairResource(Face.TOP);

        // then
        assertThat(resourceTop).isEqualTo(sandstoneResource);
    }

    @Test
    @DisplayName("should get slab resource with override")
    void shouldGetSlabResource_WhenOverride() {
        // given
        Resource sandstoneResource = createSandstoneResource();
        Resource sandstoneResourceTop = new Resource("sandstone", "sandstone_top");

        // when
        ResourceVariant resourceVariant = new ResourceVariant.Builder(sandstoneResource)
                .slabResource(Face.TOP, sandstoneResourceTop)
                .build();
        Resource resourceTop = resourceVariant.slabResource(Face.TOP);

        // then
        assertThat(resourceTop).isEqualTo(sandstoneResourceTop);
    }

    @Test
    @DisplayName("should get stair resource with override")
    void shouldGetStairResource_WhenOverride() {
        // given
        Resource sandstoneResource = createSandstoneResource();
        Resource sandstoneResourceTop = new Resource("sandstone", "sandstone_top");

        // when
        ResourceVariant resourceVariant = new ResourceVariant.Builder(sandstoneResource)
                .stairResource(Face.TOP, sandstoneResourceTop)
                .build();
        Resource resourceTop = resourceVariant.stairResource(Face.TOP);

        // then
        assertThat(resourceTop).isEqualTo(sandstoneResourceTop);
    }

    private Resource createSandstoneResource() {
        return new Resource("sandstone");
    }

    @Test
    @DisplayName("should set namespace for all resources")
    void shouldSetNamespaceForAllResources() {
        // given
        Resource sandstoneResource = createSandstoneResource();
        Resource sandstoneResourceTop = new Resource("sandstone", "sandstone_top");

        // when
        ResourceVariant resourceVariant = new ResourceVariant.Builder(sandstoneResource)
                .stairResource(Face.TOP, sandstoneResourceTop)
                .namespace("iuvat")
                .build();

        // then
        assertThat(resourceVariant.defaultResource().namespace()).isEqualTo("iuvat");
        assertThat(resourceVariant.stairResource(Face.TOP).namespace()).isEqualTo("iuvat");
    }
}