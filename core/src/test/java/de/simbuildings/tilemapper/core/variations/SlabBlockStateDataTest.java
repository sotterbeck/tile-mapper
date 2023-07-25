package de.simbuildings.tilemapper.core.variations;

import de.simbuildings.tilemapper.core.resourcepack.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static net.javacrumbs.jsonunit.assertj.JsonAssertions.json;

class SlabBlockStateDataTest {

    private ResourcePackJsonFactory underTest;

    @BeforeEach
    void setUp() {
        underTest = new SlabJsonFactory();
    }

    @Test
    @DisplayName("Should return json for slab with single variant")
    void shouldReturnJsonForSlabWithSingleVariant() {
        // given
        Resource resource = new Resource("sandstone", "sandstone");
        Set<BlockStateVariantBuilder> variants = Set.of(new BlockStateVariantBuilder(resource));

        // when
        BlockStateData blockStateData = underTest.blockState(variants);

        // then
        assertThatJson(blockStateData).isEqualTo(
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
    @DisplayName("Should return json for slab with single variant with custom namespace")
    void shouldReturnJsonForSlabWithSingleVariantWithNamespace() {
        // given
        Resource resource = new Resource("sandstone", "sandstone");
        Set<BlockStateVariantBuilder> variants = Set.of(new BlockStateVariantBuilder(resource)
                .namespace("iuvat"));

        // when
        BlockStateData blockStateData = underTest.blockState(variants);

        // then
        assertThatJson(blockStateData).isEqualTo(
                json("""
                        {
                          "variants": {
                            "type=bottom": {
                              "model": "iuvat:block/sandstone/slab/sandstone_slab"
                            },
                            "type=double": {
                              "model": "iuvat:block/sandstone/sandstone"
                            },
                            "type=top": {
                              "model": "iuvat:block/sandstone/slab/sandstone_slab_top"
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
        Set<BlockStateVariantBuilder> variants = Set.of(
                new BlockStateVariantBuilder(sandstoneOne),
                new BlockStateVariantBuilder(sandstoneTwo));

        // when
        BlockStateData blockStateData = underTest.blockState(variants);

        // then
        assertThatJson(blockStateData).isEqualTo(
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
