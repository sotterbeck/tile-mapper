package de.simbuildings.tilemapper.core.variations;

import de.simbuildings.tilemapper.core.resourcepack.Resource;
import de.simbuildings.tilemapper.core.variations.model.Face;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class VariantTextureInfoTest {
    @Test
    @DisplayName("Should create variant with single resource")
    void shouldCreateVariantWithSingleResource() {
        // given
        Resource sandstoneResource = createSandstoneResource();

        // when
        VariantTextureInfo variantTextureInfo = new VariantTextureInfo(sandstoneResource);

        // then
        assertThat(variantTextureInfo.defaultResource()).isEqualTo(sandstoneResource);
    }

    @Test
    @DisplayName("should get slab resource without override")
    void shouldGetSlabResource_WhenNoOverride() {
        // given
        Resource sandstoneResource = createSandstoneResource();

        // when
        VariantTextureInfo variantTextureInfo = new VariantTextureInfo(sandstoneResource);
        Resource resourceTop = variantTextureInfo.slabResource(Face.TOP);

        // then
        assertThat(resourceTop).isEqualTo(sandstoneResource);
    }

    @Test
    @DisplayName("should get stair resource without override")
    void shouldGetStairResource_WhenNoOverride() {
        // given
        Resource sandstoneResource = createSandstoneResource();

        // when
        VariantTextureInfo variantTextureInfo = new VariantTextureInfo(sandstoneResource);
        Resource resourceTop = variantTextureInfo.stairResource(Face.TOP);

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
        VariantTextureInfo variantTextureInfo = new VariantTextureInfo.Builder(sandstoneResource)
                .slabResource(Face.TOP, sandstoneResourceTop)
                .build();
        Resource resourceTop = variantTextureInfo.slabResource(Face.TOP);

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
        VariantTextureInfo variantTextureInfo = new VariantTextureInfo.Builder(sandstoneResource)
                .stairResource(Face.TOP, sandstoneResourceTop)
                .build();
        Resource resourceTop = variantTextureInfo.stairResource(Face.TOP);

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
        VariantTextureInfo variantTextureInfo = new VariantTextureInfo.Builder(sandstoneResource)
                .stairResource(Face.TOP, sandstoneResourceTop)
                .namespace("iuvat")
                .build();

        // then
        assertThat(variantTextureInfo.defaultResource().namespace()).isEqualTo("iuvat");
        assertThat(variantTextureInfo.stairResource(Face.TOP).namespace()).isEqualTo("iuvat");
    }
}