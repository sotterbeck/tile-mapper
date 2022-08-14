package de.simbuildings.tilemapper.variations;

import de.simbuildings.tilemapper.image.TextureImage;
import de.simbuildings.tilemapper.resourcepack.Resource;
import de.simbuildings.tilemapper.variations.model.Face;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.function.BiFunction;

import static de.simbuildings.tilemapper.junit.TestUtils.getSampleTexture;
import static org.assertj.core.api.Assertions.assertThat;

class VariantDtoResourceTest {

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
        VariantDto variantDto = new VariantDto(texture);
        Resource resource = variantDto.resourceAt(material, 0, renameFunction).defaultResource();

        // then
        assertThat(resource).isEqualTo(new Resource("sandstone", "sandstone_1"));
    }

    @Test
    void shouldReturnResourceForSecondVariant() {
        // given
        String material = "sandstone";

        // when
        VariantDto variantDto = new VariantDto(texture);
        Resource resource = variantDto.resourceAt(material, 1, renameFunction).defaultResource();

        // then
        assertThat(resource).isEqualTo(new Resource("sandstone", "sandstone_2"));
    }

    @Test
    void shouldReturnResourceVariantForFirstVariant_WhenNoOverride() {
        // given
        String material = "sandstone";

        // when
        VariantDto variantDto = new VariantDto(texture);
        ResourceVariant resourceVariant = variantDto.resourceAt(material, 0, renameFunction);

        // then
        Resource defaultResource = new Resource("sandstone", "sandstone_1");
        assertThat(resourceVariant.slabResource(Face.TOP)).isEqualTo(defaultResource);
        assertThat(resourceVariant.stairResource(Face.TOP)).isEqualTo(defaultResource);
    }

    @Test
    void shouldReturnResourceVariantForSecondVariant_WhenNoOverride() {
        // given
        String material = "sandstone";

        // when
        VariantDto variantDto = new VariantDto(texture);
        ResourceVariant resourceVariant = variantDto.resourceAt(material, 1, renameFunction);

        // then
        Resource defaultResource = new Resource("sandstone", "sandstone_2");
        assertThat(resourceVariant.slabResource(Face.TOP)).isEqualTo(defaultResource);
        assertThat(resourceVariant.stairResource(Face.TOP)).isEqualTo(defaultResource);
    }

    @Test
    void shouldReturnResourceVariant_WhenSlabOverride() {
        // given
        String material = "sandstone";
        TextureImage overrideTexture = getSampleTexture("alternate_sample_2.png");

        // when
        VariantDto variantDto = VariantDto.builder(texture)
                .slabTexture(Face.TOP, overrideTexture)
                .build();
        ResourceVariant resourceVariant = variantDto.resourceAt(material, 0, renameFunction);

        // then
        assertThat(resourceVariant.slabResource(Face.TOP)).isEqualTo(new Resource("sandstone", "alternate_sample_2"));
        assertThat(resourceVariant.slabResource(Face.BOTTOM)).isEqualTo(new Resource("sandstone", "sandstone_1"));
    }

    @Test
    void shouldReturnResourceVariant_WhenStairOverride() {
        // given
        String material = "sandstone";
        TextureImage overrideTexture = getSampleTexture("alternate_sample_2.png");

        // when
        VariantDto variantDto = VariantDto.builder(texture)
                .stairTexture(Face.TOP, overrideTexture)
                .build();
        ResourceVariant resourceVariant = variantDto.resourceAt(material, 0, renameFunction);

        // then
        assertThat(resourceVariant.stairResource(Face.TOP)).isEqualTo(new Resource("sandstone", "alternate_sample_2"));
        assertThat(resourceVariant.stairResource(Face.BOTTOM)).isEqualTo(new Resource("sandstone", "sandstone_1"));
    }
}