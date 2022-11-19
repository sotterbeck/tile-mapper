package de.simbuildings.tilemapper.variations.blockstate;

import de.simbuildings.tilemapper.junit.ObjectMapperParameterResolver;
import de.simbuildings.tilemapper.resourcepack.Resource;
import de.simbuildings.tilemapper.variations.BlockStateVariant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Set;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static net.javacrumbs.jsonunit.assertj.JsonAssertions.json;

@ExtendWith(ObjectMapperParameterResolver.class)
class BlockBlockStateTest {

    @Nested
    @DisplayName("Single variant")
    class SingleVariant {
        private Resource sandstoneResource;

        @BeforeEach
        void setUp() {
            sandstoneResource = new Resource("sandstone");
        }

        @Test
        @DisplayName("should return json for single variant")
        void shouldReturnJsonForSingleVariant() {
            // given
            Resource resource = sandstoneResource;
            BlockStateVariant.Builder variant = new BlockStateVariant.Builder(resource);

            // when
            BlockState blockState = BlockState.createBlock(variant);

            // then
            assertThatJson(blockState).isEqualTo(
                    json("""
                            {
                              "variants": {
                                "": {
                                  "model": "minecraft:block/sandstone/sandstone"
                                }
                              }
                            }
                            """
                    ));
        }

        @Test
        @DisplayName("should return json for single variant with weight")
        void shouldReturnJsonForSingleVariantWithWeight() {
            // given
            Resource resource = sandstoneResource;
            BlockStateVariant.Builder variant = new BlockStateVariant.Builder(resource)
                    .weight(4);

            // when
            BlockState blockState = BlockState.createBlock(variant);

            // then
            assertThatJson(blockState).isEqualTo(
                    json("""
                            {
                              "variants": {
                                "": {
                                  "model": "minecraft:block/sandstone/sandstone", "weight": 4
                                }
                              }
                            }
                            """
                    ));
        }

        @Test
        @DisplayName("should return json for single variant with rotation")
        void shouldReturnJsonForSingleVariantWithRotation() {
            // given
            Resource resource = sandstoneResource;
            BlockStateVariant.Builder variant = new BlockStateVariant.Builder(resource)
                    .rotationX(90)
                    .rotationY(180);

            // when
            BlockState blockState = BlockState.createBlock(variant);

            // then
            assertThatJson(blockState).isEqualTo(
                    json("""
                            {
                              "variants": {
                                "": {
                                  "model": "minecraft:block/sandstone/sandstone", "x": 90, "y": 180
                                }
                              }
                            }
                            """)
            );
        }

        @Test
        @DisplayName("should return json for single variant with uv lock")
        void shouldReturnJsonForSingleVariantWithUVLock() {
            // given
            BlockStateVariant.Builder variant = new BlockStateVariant.Builder(sandstoneResource)
                    .uvLock(true);

            // when
            BlockState blockState = BlockState.createBlock(variant);

            // then
            assertThatJson(blockState).isEqualTo(
                    json("""
                            {
                              "variants": {
                                "": {
                                  "model": "minecraft:block/sandstone/sandstone", "uvlock": true
                                }
                              }
                            }
                            """)
            );

        }

        @Test
        @DisplayName("should return json for single variant with custom namespace")
        void shouldReturnJsonForSingleVariantWithNamespace() {
            // given
            BlockStateVariant.Builder variant = new BlockStateVariant.Builder(sandstoneResource)
                    .namespace("iuvat");

            // when
            BlockState blockState = BlockState.createBlock(variant);

            // then
            assertThatJson(blockState).isEqualTo(
                    json("""
                            {
                              "variants": {
                                "": {
                                  "model": "iuvat:block/sandstone/sandstone"
                                }
                              }
                            }
                            """)
            );

        }
    }

    @Test
    @DisplayName("Should return json for multiple variants")
    void shouldReturnJsonForMultipleVariants() {
        // given
        Resource sandstoneOne = new Resource("sandstone", "sandstone1");
        Resource sandstoneTwo = new Resource("sandstone", "sandstone2");

        Set<BlockStateVariant.Builder> models = Set.of(
                new BlockStateVariant.Builder(sandstoneOne),
                new BlockStateVariant.Builder(sandstoneTwo));

        // when
        BlockState blockstate = BlockState.createBlock(models);

        // then
        assertThatJson(blockstate).isEqualTo(
                json("""
                        {
                          "variants": {
                            "": [
                              { "model": "minecraft:block/sandstone/sandstone1" },
                              { "model": "minecraft:block/sandstone/sandstone2" }
                            ]
                          }
                        }
                        """
                ));
    }
}