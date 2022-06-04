package de.simbuildings.tilemapper.variations;

import de.simbuildings.tilemapper.image.TextureImage;
import de.simbuildings.tilemapper.variations.model.Face;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static de.simbuildings.tilemapper.junit.TestUtils.getSampleTexture;
import static org.assertj.core.api.Assertions.assertThat;

class VariantDtoTextureTest {

    @Test
    @DisplayName("Should return single additional texture when single override")
    void shouldReturnSingleAdditionalTexture_WhenSingleOverride() {
        // given
        TextureImage textureDefault = getSampleTexture("alternate_sample_1.png");
        TextureImage textureTop = getSampleTexture("alternate_sample_2.png");

        // when
        VariantDto variantDto = VariantDto.builder(textureDefault)
                .weight(2)
                .overrideSlabTexture(Face.TOP, textureTop)
                .build();
        Set<TextureImage> additionalTextures = variantDto.additionalTextures();

        // then
        assertThat(additionalTextures)
                .contains(textureTop)
                .doesNotContain(textureDefault);
    }

    @Test
    @DisplayName("Should return single additional texture when multiple overrides with same file")
    void shouldReturnSingleAdditionalTexture_WhenMultipleOverridesWithSameFile() {
        // given
        TextureImage textureDefault = getSampleTexture("alternate_sample_1.png");
        TextureImage textureTop = getSampleTexture("alternate_sample_2.png");

        // when
        VariantDto variantDto = VariantDto.builder(textureDefault)
                .weight(2)
                .overrideSlabTexture(Face.TOP, textureTop)
                .overrideSlabTexture(Face.BOTTOM, textureTop)
                .build();
        Set<TextureImage> additionalTextures = variantDto.additionalTextures();

        // then
        assertThat(additionalTextures)
                .hasSize(1);
    }

    @Test
    @DisplayName("Should get slab textures without override")
    void shouldGetSlabTextures_WhenNoOverride() {
        // given
        TextureImage textureDefault = getSampleTexture("alternate_sample_1.png");

        // when
        VariantDto variantDto = new VariantDto(textureDefault);
        TextureImage slabOverride = variantDto.slabTexture(Face.TOP);

        // then
        assertThat(slabOverride).isEqualTo(textureDefault);
    }

    @Test
    @DisplayName("Should get stair textures without override")
    void shouldGetStairTextures_WhenNoOverride() {
        // given
        TextureImage textureDefault = getSampleTexture("alternate_sample_1.png");

        // when
        VariantDto variantDto = new VariantDto(textureDefault);

        // then
        assertThat(variantDto.stairTexture(Face.BOTTOM)).isEqualTo(textureDefault);
        assertThat(variantDto.stairTexture(Face.SIDE)).isEqualTo(textureDefault);
        assertThat(variantDto.stairTexture(Face.TOP)).isEqualTo(textureDefault);
    }

    @Test
    @DisplayName("Should get slab textures with override")
    void shouldGetSlabTexture_WhenOverride() {
        // given
        TextureImage textureDefault = getSampleTexture("alternate_sample_1.png");
        TextureImage textureTop = getSampleTexture("alternate_sample_2.png");

        // when
        VariantDto variantDto = VariantDto.builder(textureDefault)
                .weight(2)
                .overrideSlabTexture(Face.TOP, textureTop)
                .build();
        TextureImage slabTexture = variantDto.slabTexture(Face.TOP);

        // then
        assertThat(slabTexture).isEqualTo(textureTop);
    }

    @Test
    @DisplayName("Should get stair textures with override")
    void shouldGetStairTextures_WhenOverride() {
        // given
        TextureImage textureDefault = getSampleTexture("alternate_sample_1.png");
        TextureImage textureTop = getSampleTexture("alternate_sample_2.png");

        // when
        VariantDto variantDto = VariantDto.builder(textureDefault)
                .overrideStairTexture(Face.TOP, textureTop)
                .build();
        TextureImage stairTexture = variantDto.stairTexture(Face.TOP);

        // then
        assertThat(stairTexture).isEqualTo(textureTop);
    }
}