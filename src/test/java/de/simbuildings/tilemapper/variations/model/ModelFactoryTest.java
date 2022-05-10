package de.simbuildings.tilemapper.variations.model;

import de.simbuildings.tilemapper.junit.StubResourcepackParameterResolver;
import de.simbuildings.tilemapper.resourcepack.Resource;
import de.simbuildings.tilemapper.resourcepack.Resourcepack;
import net.javacrumbs.jsonunit.core.Option;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Set;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static net.javacrumbs.jsonunit.assertj.JsonAssertions.json;

@ExtendWith(StubResourcepackParameterResolver.class)
class ModelFactoryTest {

    private Resourcepack stubResourcepack;

    @BeforeEach
    void setUp(Resourcepack resourcepack) {
        this.stubResourcepack = resourcepack;
    }

    @Test
    void shouldCreateModelForBlock(Resourcepack resourcepack) {
        // given
        Resource resource = createGraniteResource();

        // when
        Model model = Model.createBlock(resource);


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
    void shouldCreateModelForSlabs() {
        // given
        Resource resource = createGraniteResource();

        // when
        Set<Model> models = Model.createSlab(resource, resource, resource);

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
    void shouldCreateModelForStairs() {
        // given
        Resource resource = createGraniteResource();

        // when
        Set<Model> models = Model.createStairs(resource, resource, resource);

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
        return new Resource(stubResourcepack, "granite", "granite1");
    }
}
