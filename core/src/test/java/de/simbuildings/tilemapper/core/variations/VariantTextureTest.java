package de.simbuildings.tilemapper.core.variations;

import de.simbuildings.tilemapper.core.image.TextureImage;
import de.simbuildings.tilemapper.core.variations.model.Face;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static de.simbuildings.tilemapper.core.junit.TestUtils.getSampleTexture;
import static org.assertj.core.api.Assertions.assertThat;

class VariantTextureTest {

    @Test
    @DisplayName("Should return single additional texture when single override")
    void shouldReturnSingleAdditionalTexture_WhenSingleOverride() {
        // given
        TextureImage textureDefault = getSampleTexture("alternate_sample_1.png");
        TextureImage textureTop = getSampleTexture("alternate_sample_2.png");

        // when
        Variant variant = Variant.builder(textureDefault)
                .weight(2)
                .slabTexture(Face.TOP, textureTop)
                .build();
        Set<TextureImage> additionalTextures = variant.additionalTextures();

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
        Variant variant = Variant.builder(textureDefault)
                .weight(2)
                .slabTexture(Face.TOP, textureTop)
                .slabTexture(Face.BOTTOM, textureTop)
                .build();
        Set<TextureImage> additionalTextures = variant.additionalTextures();

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
        Variant variant = new Variant(textureDefault);
        TextureImage slabOverride = variant.slabTexture(Face.TOP);

        // then
        assertThat(slabOverride).isEqualTo(textureDefault);
    }

    @Test
    @DisplayName("Should get stair textures without override")
    void shouldGetStairTextures_WhenNoOverride() {
        // given
        TextureImage textureDefault = getSampleTexture("alternate_sample_1.png");

        // when
        Variant variant = new Variant(textureDefault);

        // then
        Assertions.assertThat(variant.stairTexture(Face.BOTTOM)).isEqualTo(textureDefault);
        Assertions.assertThat(variant.stairTexture(Face.SIDE)).isEqualTo(textureDefault);
        Assertions.assertThat(variant.stairTexture(Face.TOP)).isEqualTo(textureDefault);
    }

    @Test
    @DisplayName("Should get slab textures with override")
    void shouldGetSlabTexture_WhenOverride() {
        // given
        TextureImage textureDefault = getSampleTexture("alternate_sample_1.png");
        TextureImage textureTop = getSampleTexture("alternate_sample_2.png");

        // when
        Variant variant = Variant.builder(textureDefault)
                .weight(2)
                .slabTexture(Face.TOP, textureTop)
                .build();
        TextureImage slabTexture = variant.slabTexture(Face.TOP);

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
        Variant variant = Variant.builder(textureDefault)
                .stairTexture(Face.TOP, textureTop)
                .build();
        TextureImage stairTexture = variant.stairTexture(Face.TOP);

        // then
        assertThat(stairTexture).isEqualTo(textureTop);
    }
}