package de.simbuildings.tilemapper.variations.blockstate;

import de.simbuildings.tilemapper.resourcepack.Resource;
import de.simbuildings.tilemapper.variations.Variant;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static net.javacrumbs.jsonunit.assertj.JsonAssertions.json;

class SlabBlockStateTest {

    @Test
    @DisplayName("Should return json for slab with single variant")
    void shouldReturnJsonForSlabWithSingleVariant() {
        // given
        Resource resource = new Resource("sandstone", "sandstone");
        Set<Variant.Builder> variants = Set.of(new Variant.Builder(resource));

        // when
        BlockState blockState = BlockState.createSlab(variants);

        // then
        assertThatJson(blockState).isEqualTo(
                json("""
                        {
                          "variants": {
                            "type=bottom": {
                              "model": "minecraft:block/sandstone/slab/sandstone_slab"
                            },
                            "type=double": {
                              "model": "minecraft:block/sandstone/sandstone"
                            },
                            "type=top": {
                              "model": "minecraft:block/sandstone/slab/sandstone_slab_top"
                            }
                          }
                        }
                        """)
        );
    }

    @Test
    @DisplayName("Should return json for slab with multiple variants")
    void shouldReturnJsonForSlabWithMultipleVariants() {
        // given
        Resource sandstoneOne = new Resource("sandstone", "sandstone1");
        Resource sandstoneTwo = new Resource("sandstone", "sandstone2");
        Set<Variant.Builder> variants = Set.of(
                new Variant.Builder(sandstoneOne),
                new Variant.Builder(sandstoneTwo));

        // when
        BlockState blockState = BlockState.createSlab(variants);

        // then
        assertThatJson(blockState).isEqualTo(
                json("""
                        {
                          "variants": {
                            "type=bottom": [
                                {"model": "minecraft:block/sandstone/slab/sandstone1_slab"},
                                {"model": "minecraft:block/sandstone/slab/sandstone2_slab"}
                            ],
                            "type=double": [
                                {"model": "minecraft:block/sandstone/sandstone1"},
                                {"model": "minecraft:block/sandstone/sandstone2"}
                            ],
                            "type=top": [
                                {"model": "minecraft:block/sandstone/slab/sandstone1_slab_top"},
                                {"model": "minecraft:block/sandstone/slab/sandstone2_slab_top"}
                            ]
                          }
                        }
                        """)
        );
    }
}
