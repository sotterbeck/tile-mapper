package de.simbuildings.tilemapper.variations.model;

import de.simbuildings.tilemapper.resourcepack.Resource;
import net.javacrumbs.jsonunit.core.Option;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static net.javacrumbs.jsonunit.assertj.JsonAssertions.json;

class ModelFactoryTest {

    @Test
    @DisplayName("Should create block model")
    void createBlock_ShouldReturnBlockModel() {
        // given
        Resource modelResource = createGraniteResource();
        Resource graniteResource = createGraniteResource();

        // when
        Model model = Model.createBlock(modelResource, graniteResource);


        // then
        assertThatJson(model).isEqualTo(
                json("""
                        {
                          "parent": "minecraft:block/cube_all",
                          "textures": {
                            "all": "minecraft:block/granite/granite1"
                          }
                        }
                        """));
    }

    @Test
    @DisplayName("Should create slab models")
    void createSlab_ShouldReturnSlabModels() {
        // given
        Resource modelResource = createGraniteResource();
        Resource textureResource = createGraniteResource();

        // when
        Set<Model> models = Model.createSlab(modelResource, textureResource, textureResource, textureResource);

        // then
        assertThatJson(models)
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

        // when
        Set<Model> models = Model.createStairs(modelResource, textureResource, textureResource, textureResource);

        // then
        assertThatJson(models)
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
