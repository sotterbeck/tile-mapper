package de.simbuildings.tilemapper.core.variations;

import de.simbuildings.tilemapper.core.junit.ObjectMapperParameterResolver;
import de.simbuildings.tilemapper.core.resourcepack.Resource;
import de.simbuildings.tilemapper.core.variations.blocks.BlockJsonFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Set;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static net.javacrumbs.jsonunit.assertj.JsonAssertions.json;

@ExtendWith(ObjectMapperParameterResolver.class)
class BlockBlockStateDataTest {

    private ResourcePackJsonFactory underTest;

    @BeforeEach
    void setUp() {
        underTest = new BlockJsonFactory();
    }

    @Test
    @DisplayName("Should return json for multiple variants")
    void shouldReturnJsonForMultipleVariants() {
        // given
        Resource sandstoneOne = new Resource("sandstone", "sandstone1");
        Resource sandstoneTwo = new Resource("sandstone", "sandstone2");

        Set<BlockStateVariantBuilder> variants = Set.of(
                new BlockStateVariantBuilder(sandstoneOne),
                new BlockStateVariantBuilder(sandstoneTwo));

        // when
        BlockStateData blockstate = underTest.blockState(variants);

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
            BlockStateVariantBuilder variant = new BlockStateVariantBuilder(resource);

            // when
            BlockStateData blockStateData = underTest.blockState(variant);

            // then
            assertThatJson(blockStateData).isEqualTo(
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
            BlockStateVariantBuilder variant = new BlockStateVariantBuilder(resource)
                    .weight(4);

            // when
            BlockStateData blockStateData = underTest.blockState(variant);

            // then
            assertThatJson(blockStateData).isEqualTo(
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
            BlockStateVariantBuilder variant = new BlockStateVariantBuilder(resource)
                    .rotationX(90)
                    .rotationY(180);

            // when
            BlockStateData blockStateData = underTest.blockState(variant);

            // then
            assertThatJson(blockStateData).isEqualTo(
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
            BlockStateVariantBuilder variant = new BlockStateVariantBuilder(sandstoneResource)
                    .uvLock(true);

            // when
            BlockStateData blockStateData = underTest.blockState(variant);

            // then
            assertThatJson(blockStateData).isEqualTo(
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
            BlockStateVariantBuilder variant = new BlockStateVariantBuilder(sandstoneResource)
                    .namespace("iuvat");

            // when
            BlockStateData blockStateData = underTest.blockState(variant);

            // then
            assertThatJson(blockStateData).isEqualTo(
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
}