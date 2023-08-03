package de.simbuildings.tilemapper.core.variations;

import de.simbuildings.tilemapper.core.resourcepack.Resource;
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
        VariantTextureInfo variantTextureInfo = VariantTextureInfo.of(sandstoneResource);

        // then
        assertThat(variantTextureInfo.defaultResource()).isEqualTo(sandstoneResource);
    }

    @Test
    @DisplayName("should get slab resource without override")
    void shouldGetSlabResource_WhenNoOverride() {
        // given
        Resource sandstoneResource = createSandstoneResource();

        // when
        VariantTextureInfo variantTextureInfo = VariantTextureInfo.of(sandstoneResource);
        Resource resourceTop = variantTextureInfo.getTexture("slab", "top");

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
                .addTexture("slab", "top", sandstoneResourceTop)
                .build();
        Resource resourceTop = variantTextureInfo.getTexture("slab", "top");

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
                .addTexture("stairs", "top", sandstoneResourceTop)
                .namespace("iuvat")
                .build();

        // then
        assertThat(variantTextureInfo.defaultResource().namespace()).isEqualTo("iuvat");
        assertThat(variantTextureInfo.getTexture("stairs", "top").namespace()).isEqualTo("iuvat");
    }
}