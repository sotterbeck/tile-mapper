package de.simbuildings.tilemapper.variations.blockstate;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.simbuildings.tilemapper.junit.ObjectMapperParameterResolver;
import de.simbuildings.tilemapper.junit.StubResourcepackParameterResolver;
import de.simbuildings.tilemapper.resourcepack.Resource;
import de.simbuildings.tilemapper.resourcepack.Resourcepack;
import de.simbuildings.tilemapper.variations.Variant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Set;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static net.javacrumbs.jsonunit.assertj.JsonAssertions.json;

@ExtendWith({ObjectMapperParameterResolver.class, StubResourcepackParameterResolver.class})
class BlockBlockStateTest {

    Resourcepack stubResourcepack;

    @BeforeEach
    void setUp(Resourcepack resourcepack) {
        stubResourcepack = resourcepack;
    }

    @Nested
    class SingleVariant {
        private Resource sandstoneResource;

        @BeforeEach
        void setUp() {
            sandstoneResource = new Resource(stubResourcepack, "sandstone");
        }

        @Test
        void shouldReturnJsonForSingleVariant(ObjectMapper objectMapper) {
            // given
            Resource resource = sandstoneResource;
            Variant variant = new Variant.Builder(resource).build();
            BlockState blockState = BlockState.ofDefaultVariantName(variant);

            // when
            String blockStateJson = new BlockStateExporter(objectMapper, blockState).toJson();

            // then
            assertThatJson(blockStateJson).isEqualTo(
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
        void shouldReturnJsonForSingleVariantWithWeight(ObjectMapper objectMapper, Resourcepack resourcepack) {
            // given
            Resource resource = sandstoneResource;
            Variant variant = new Variant.Builder(resource)
                    .weight(4)
                    .build();

            // when
            BlockState blockState = BlockState.ofDefaultVariantName(variant);

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
        void shouldReturnJsonForSingleVariantWithRotation(ObjectMapper objectMapper) {
            // given
            Resource resource = sandstoneResource;
            Variant variant = new Variant.Builder(resource)
                    .rotationX(90)
                    .rotationY(180)
                    .build();

            // when
            BlockState blockState = BlockState.ofDefaultVariantName(variant);

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
        void shouldReturnJsonForSingleVariantWithUVLock(ObjectMapper objectMapper) {
            // given
            Variant variant = new Variant.Builder(sandstoneResource)
                    .uvLock(true)
                    .build();

            // when
            BlockState blockState = BlockState.ofDefaultVariantName(variant);

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
    }

    @Test
    void shouldReturnJsonForMultipleVariants(ObjectMapper objectMapper) {
        // given
        Resource sandstoneOne = new Resource(stubResourcepack, "sandstone", "sandstone1");
        Resource sandstoneTwo = new Resource(stubResourcepack, "sandstone", "sandstone2");

        Set<Variant> models = Set.of(
                new Variant.Builder(sandstoneOne).build(),
                new Variant.Builder(sandstoneTwo).build());

        // when
        BlockState blockstate = BlockState.ofDefaultVariantName(models);

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