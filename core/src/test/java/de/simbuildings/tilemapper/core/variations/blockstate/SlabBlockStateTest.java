package de.simbuildings.tilemapper.core.variations.blockstate;

import de.simbuildings.tilemapper.core.resourcepack.Resource;
import de.simbuildings.tilemapper.core.variations.BlockStateVariant;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static net.javacrumbs.jsonunit.assertj.JsonAssertions.json;
import static org.assertj.core.api.Assertions.assertThat;

class SlabBlockStateTest {

    @Test
    @DisplayName("Should return correct file location")
    void shouldReturnCorrectFileLocation() {
        // given
        Resource resource = new Resource("sandstone", "sandstone");

        // when
        BlockState blockState = BlockState.createSlab(Set.of(new BlockStateVariant.Builder(resource)));
        Path fileLocation = blockState.resourcepackLocation(resource);

        // then
        assertThat(fileLocation).isEqualTo(Paths.get("assets", "minecraft", "blockstates", "sandstone_slab.json"));
    }

    @Test
    @DisplayName("Should return json for slab with single variant")
    void shouldReturnJsonForSlabWithSingleVariant() {
        // given
        Resource resource = new Resource("sandstone", "sandstone");
        Set<BlockStateVariant.Builder> variants = Set.of(new BlockStateVariant.Builder(resource));

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
    @DisplayName("Should return json for slab with single variant with custom namespace")
    void shouldReturnJsonForSlabWithSingleVariantWithNamespace() {
        // given
        Resource resource = new Resource("sandstone", "sandstone");
        Set<BlockStateVariant.Builder> variants = Set.of(new BlockStateVariant.Builder(resource)
                .namespace("iuvat"));

        // when
        BlockState blockState = BlockState.createSlab(variants);

        // then
        assertThatJson(blockState).isEqualTo(
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
        Set<BlockStateVariant.Builder> variants = Set.of(
                new BlockStateVariant.Builder(sandstoneOne),
                new BlockStateVariant.Builder(sandstoneTwo));

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
