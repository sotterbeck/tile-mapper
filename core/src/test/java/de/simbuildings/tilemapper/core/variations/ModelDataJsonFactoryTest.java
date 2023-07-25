package de.simbuildings.tilemapper.core.variations;

import de.simbuildings.tilemapper.core.resourcepack.Resource;
import net.javacrumbs.jsonunit.core.Option;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static net.javacrumbs.jsonunit.assertj.JsonAssertions.json;

class ModelDataJsonFactoryTest {

    private ResourcePackJsonFactory underTest;

    @Test
    @DisplayName("Should create block model")
    void createBlock_ShouldReturnBlockModel() {
        // given
        Resource modelResource = createGraniteResource();
        Resource graniteResource = createGraniteResource();
        underTest = new BlockJsonFactory();

        // when
        Set<ModelData> modelData = underTest.models(modelResource, new VariantTextureInfo(graniteResource));


        // then
        assertThatJson(modelData)
                .when(Option.IGNORING_ARRAY_ORDER)
                .isEqualTo(
                        json("""
                                [
                                {
                                  "parent": "minecraft:block/cube_all",
                                  "textures": {
                                    "all": "minecraft:block/granite/granite1"
                                  }
                                }
                                ]
                                """));
    }

    @Test
    @DisplayName("Should create slab models")
    void createSlab_ShouldReturnSlabModels() {
        // given
        Resource modelResource = createGraniteResource();
        Resource textureResource = createGraniteResource();
        underTest = new SlabJsonFactory();

        // when
        Set<ModelData> modelData = underTest.models(modelResource, new VariantTextureInfo(textureResource));

        // then
        assertThatJson(modelData)
                .when(Option.IGNORING_ARRAY_ORDER)
                .isEqualTo(
                        json("""
                                [
                                {
                                  "parent": "minecraft:block/slab",
                                  "textures": {
                                    "bottom": "minecraft:block/granite/granite1",
                                    "top": "minecraft:block/granite/granite1",
                                    "side": "minecraft:block/granite/granite1"
                                  }
                                },
                                {
                                  "parent": "minecraft:block/slab_top",
                                  "textures": {
                                    "bottom": "minecraft:block/granite/granite1",
                                    "top": "minecraft:block/granite/granite1",
                                    "side": "minecraft:block/granite/granite1"
                                  }
                                }
                                ]
                                """));
    }

    @Test
    @DisplayName("Should create slab models")
    void createStairs_ShouldReturnStairModel() {
        // given
        Resource modelResource = createGraniteResource();
        Resource textureResource = createGraniteResource();
        underTest = new StairsJsonFactory();

        // when
        Set<ModelData> modelData = underTest.models(modelResource, new VariantTextureInfo(textureResource));

        // then
        assertThatJson(modelData)
                .when(Option.IGNORING_ARRAY_ORDER)
                .isEqualTo(
                        json("""
                                [
                                {
                                  "parent": "minecraft:block/stairs",
                                  "textures": {
                                    "bottom": "minecraft:block/granite/granite1",
                                    "top": "minecraft:block/granite/granite1",
                                    "side": "minecraft:block/granite/granite1"
                                  }
                                },
                                {
                                  "parent": "minecraft:block/inner_stairs",
                                  "textures": {
                                    "bottom": "minecraft:block/granite/granite1",
                                    "top": "minecraft:block/granite/granite1",
                                    "side": "minecraft:block/granite/granite1"
                                  }
                                },
                                {
                                  "parent": "minecraft:block/outer_stairs",
                                  "textures": {
                                    "bottom": "minecraft:block/granite/granite1",
                                    "top": "minecraft:block/granite/granite1",
                                    "side": "minecraft:block/granite/granite1"
                                  }
                                }
                                ]
                                """));

    }

    private Resource createGraniteResource() {
        return new Resource("granite", "granite1");
    }
}
