package de.simbuildings.tilemapper.core.variations;

import de.simbuildings.tilemapper.core.image.TextureImage;
import de.simbuildings.tilemapper.core.resourcepack.Resource;
import de.simbuildings.tilemapper.core.variations.model.Face;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.function.BiFunction;

import static de.simbuildings.tilemapper.core.junit.TestUtils.getSampleTexture;
import static org.assertj.core.api.Assertions.assertThat;

class VariantResourceTest {

    TextureImage texture;
    BiFunction<String, Integer, String> renameFunction;

    @BeforeEach
    void setUp() {
        texture = getSampleTexture("alternate_sample_1.png");
        renameFunction = new NumberedRenameFunction();
    }

    @Test
    void shouldReturnResourceForFirstVariant() {
        // given
        String material = "sandstone";

        // when
        Variant variant = new Variant(texture);
        Resource resource = variant.textureInfoAt(material, 0, renameFunction).defaultResource();

        // then
        assertThat(resource).isEqualTo(new Resource("sandstone", "sandstone_1"));
    }

    @Test
    void shouldReturnResourceForSecondVariant() {
        // given
        String material = "sandstone";

        // when
        Variant variant = new Variant(texture);
        Resource resource = variant.textureInfoAt(material, 1, renameFunction).defaultResource();

        // then
        assertThat(resource).isEqualTo(new Resource("sandstone", "sandstone_2"));
    }

    @Test
    void shouldReturnTextureInfoForFirstVariant_WhenNoOverride() {
        // given
        String material = "sandstone";

        // when
        Variant variant = new Variant(texture);
        VariantTextureInfo variantTextureInfo = variant.textureInfoAt(material, 0, renameFunction);

        // then
        Resource defaultResource = new Resource("sandstone", "sandstone_1");
        assertThat(variantTextureInfo.slabResource(Face.TOP)).isEqualTo(defaultResource);
        assertThat(variantTextureInfo.stairResource(Face.TOP)).isEqualTo(defaultResource);
    }

    @Test
    void shouldReturnTextureInfoForSecondVariant_WhenNoOverride() {
        // given
        String material = "sandstone";

        // when
        Variant variant = new Variant(texture);
        VariantTextureInfo variantTextureInfo = variant.textureInfoAt(material, 1, renameFunction);

        // then
        Resource defaultResource = new Resource("sandstone", "sandstone_2");
        assertThat(variantTextureInfo.slabResource(Face.TOP)).isEqualTo(defaultResource);
        assertThat(variantTextureInfo.stairResource(Face.TOP)).isEqualTo(defaultResource);
    }

    @Test
    void shouldReturnTextureInfo_WhenSlabOverride() {
        // given
        String material = "sandstone";
        TextureImage overrideTexture = getSampleTexture("alternate_sample_2.png");

        // when
        Variant variant = Variant.builder(texture)
                .slabTexture(Face.TOP, overrideTexture)
                .build();
        VariantTextureInfo variantTextureInfo = variant.textureInfoAt(material, 0, renameFunction);

        // then
        assertThat(variantTextureInfo.slabResource(Face.TOP)).isEqualTo(new Resource("sandstone", "alternate_sample_2"));
        assertThat(variantTextureInfo.slabResource(Face.BOTTOM)).isEqualTo(new Resource("sandstone", "sandstone_1"));
    }

    @Test
    void shouldReturnTextureInfo_WhenStairOverride() {
        // given
        String material = "sandstone";
        TextureImage overrideTexture = getSampleTexture("alternate_sample_2.png");

        // when
        Variant variant = Variant.builder(texture)
                .stairTexture(Face.TOP, overrideTexture)
                .build();
        VariantTextureInfo variantTextureInfo = variant.textureInfoAt(material, 0, renameFunction);

        // then
        assertThat(variantTextureInfo.stairResource(Face.TOP)).isEqualTo(new Resource("sandstone", "alternate_sample_2"));
        assertThat(variantTextureInfo.stairResource(Face.BOTTOM)).isEqualTo(new Resource("sandstone", "sandstone_1"));
    }
}