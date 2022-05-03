package de.simbuildings.tilemapper.variations;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.simbuildings.tilemapper.junit.ObjectMapperParameterResolver;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Set;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static net.javacrumbs.jsonunit.assertj.JsonAssertions.json;

@ExtendWith(ObjectMapperParameterResolver.class)
class BlockstateVariationTest {

    @Nested
    class SingleVariant {

        @Test
        void shouldReturnJsonForSingleVariant(ObjectMapper objectMapper) {
            // given
            Variant variant = new Variant.Builder("sandstone").build();
            Blockstate blockstate = Blockstate.of(variant);

            // when
            String blockstateJson = new BlockstateExporter(objectMapper, blockstate).toJson();

            // then
            System.out.println(blockstateJson);
            assertThatJson(blockstateJson).isEqualTo(
                    json("""
                            {
                              "variants": {
                                "": {
                                  "model": "minecraft:block/sandstone"
                                }
                              }
                            }
                            """
                    ));
        }

        @Test
        void shouldReturnJsonForSingleVariantWithWeight(ObjectMapper objectMapper) {
            // given
            Variant variant = new Variant.Builder("sandstone")
                    .weight(4)
                    .build();
            Blockstate blockstate = Blockstate.of(variant);

            // when
            String blockstateJson = new BlockstateExporter(objectMapper, blockstate).toJson();

            // then
            assertThatJson(blockstate).isEqualTo(
                    json("""
                            {
                              "variants": {
                                "": {
                                  "model": "minecraft:block/sandstone", "weight": 4
                                }
                              }
                            }
                            """
                    ));
        }

        @Test
        void shouldReturnJsonForSingleVariantWithRotation(ObjectMapper objectMapper) {
            // given
            Variant variant = new Variant.Builder("sandstone")
                    .rotationX(90)
                    .rotationY(180)
                    .build();
            Blockstate blockstate = Blockstate.of(variant);

            // when
            String blockstateJson = new BlockstateExporter(objectMapper, blockstate).toJson();

            // then
            assertThatJson(blockstateJson).isEqualTo(
                    json("""
                            {
                              "variants": {
                                "": {
                                  "model": "minecraft:block/sandstone", "x": 90, "y": 180
                                }
                              }
                            }
                            """)
            );
        }

        @Test
        void shouldReturnJsonForSingleVariantWithUVLock(ObjectMapper objectMapper) {
            // given
            Variant variant = new Variant.Builder("sandstone")
                    .uvLock()
                    .build();
            Blockstate blockstate = Blockstate.of(variant);

            // when
            String blockstateJson = new BlockstateExporter(objectMapper, blockstate).toJson();

            // then
            assertThatJson(blockstateJson).isEqualTo(
                    json("""
                            {
                              "variants": {
                                "": {
                                  "model": "minecraft:block/sandstone", "uvlock": true
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
        Set<Variant> models = Set.of(
                new Variant.Builder("sandstone1").build(),
                new Variant.Builder("sandstone2").build());

        Blockstate blockstate = Blockstate.of(models);

        // when
        String blockstateJson = new BlockstateExporter(objectMapper, blockstate).toJson();

        // then
        System.out.println(blockstateJson);
        assertThatJson(blockstateJson).isEqualTo(
                json("""
                        {
                          "variants": {
                            "": [
                              { "model": "minecraft:block/sandstone1" },
                              { "model": "minecraft:block/sandstone2" }
                            ]
                          }
                        }
                        """
                ));
    }
}