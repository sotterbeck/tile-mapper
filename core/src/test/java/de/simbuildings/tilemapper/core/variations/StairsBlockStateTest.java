package de.simbuildings.tilemapper.core.variations;

import de.simbuildings.tilemapper.core.junit.StubResourcepackParameterResolver;
import de.simbuildings.tilemapper.core.resourcepack.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static net.javacrumbs.jsonunit.assertj.JsonAssertions.json;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(StubResourcepackParameterResolver.class)
class StairsBlockStateTest {

    private ResourcePackJsonFactory underTest;

    @BeforeEach
    void setUp() {
        underTest = new StairsJsonFactory();
    }

    @Test
    @DisplayName("Should return correct file location")
    void shouldReturnCorrectFileLocation() {
        // given
        Resource resource = new Resource("sandstone", "sandstone");

        // when
        BlockState blockState = underTest.blockState(new BlockStateVariantBuilder(resource));
        Path resourcepackLocation = blockState.resourcepackLocation(resource);

        // then
        assertThat(resourcepackLocation).isEqualTo(Paths.get("assets", "minecraft", "blockstates", "sandstone_stairs.json"));
    }

    @Test
    @DisplayName("Should return json for slab with single variant")
    void shouldReturnJsonForSlabWithSingleVariant() {
        // given
        Resource resource = new Resource("sandstone", "sandstone");
        Set<BlockStateVariantBuilder> variants = Set.of(new BlockStateVariantBuilder(resource));

        // when
        BlockState blockState = underTest.blockState(variants);

        // then
        assertThatJson(blockState).isEqualTo(
                json("""
                        {
                            "variants": {
                                "facing=east,half=bottom,shape=inner_left": {
                                    "model": "minecraft:block/sandstone/stairs/sandstone_stairs_inner",
                                    "y": 270,
                                    "uvlock": true
                                },
                                "facing=east,half=bottom,shape=inner_right": {
                                    "model": "minecraft:block/sandstone/stairs/sandstone_stairs_inner"
                                },
                                "facing=east,half=bottom,shape=outer_left": {
                                    "model": "minecraft:block/sandstone/stairs/sandstone_stairs_outer",
                                    "y": 270,
                                    "uvlock": true
                                },
                                "facing=east,half=bottom,shape=outer_right": {
                                    "model": "minecraft:block/sandstone/stairs/sandstone_stairs_outer"
                                },
                                "facing=east,half=bottom,shape=straight": {
                                    "model": "minecraft:block/sandstone/stairs/sandstone_stairs"
                                },
                                "facing=east,half=top,shape=inner_left": {
                                    "model": "minecraft:block/sandstone/stairs/sandstone_stairs_inner",
                                    "x": 180,
                                    "uvlock": true
                                },
                                "facing=east,half=top,shape=inner_right": {
                                    "model": "minecraft:block/sandstone/stairs/sandstone_stairs_inner",
                                    "x": 180,
                                    "y": 90,
                                    "uvlock": true
                                },
                                "facing=east,half=top,shape=outer_left": {
                                    "model": "minecraft:block/sandstone/stairs/sandstone_stairs_outer",
                                    "x": 180,
                                    "uvlock": true
                                },
                                "facing=east,half=top,shape=outer_right": {
                                    "model": "minecraft:block/sandstone/stairs/sandstone_stairs_outer",
                                    "x": 180,
                                    "y": 90,
                                    "uvlock": true
                                },
                                "facing=east,half=top,shape=straight": {
                                    "model": "minecraft:block/sandstone/stairs/sandstone_stairs",
                                    "x": 180,
                                    "uvlock": true
                                },
                               \s
                                "facing=north,half=bottom,shape=inner_left": {
                                    "model": "minecraft:block/sandstone/stairs/sandstone_stairs_inner",
                                    "y": 180,
                                    "uvlock": true
                                },
                                "facing=north,half=bottom,shape=inner_right": {
                                    "model": "minecraft:block/sandstone/stairs/sandstone_stairs_inner",
                                    "y": 270,
                                    "uvlock": true
                                },
                                "facing=north,half=bottom,shape=outer_left": {
                                    "model": "minecraft:block/sandstone/stairs/sandstone_stairs_outer",
                                    "y": 180,
                                    "uvlock": true
                                },
                                "facing=north,half=bottom,shape=outer_right": {
                                    "model": "minecraft:block/sandstone/stairs/sandstone_stairs_outer",
                                    "y": 270,
                                    "uvlock": true
                                },
                                "facing=north,half=bottom,shape=straight": {
                                    "model": "minecraft:block/sandstone/stairs/sandstone_stairs",
                                    "y": 270,
                                    "uvlock": true
                                },
                                "facing=north,half=top,shape=inner_left": {
                                    "model": "minecraft:block/sandstone/stairs/sandstone_stairs_inner",
                                    "x": 180,
                                    "y": 270,
                                    "uvlock": true
                                },
                                "facing=north,half=top,shape=inner_right": {
                                    "model": "minecraft:block/sandstone/stairs/sandstone_stairs_inner",
                                    "x": 180,
                                    "uvlock": true
                                },
                                "facing=north,half=top,shape=outer_left": {
                                    "model": "minecraft:block/sandstone/stairs/sandstone_stairs_outer",
                                    "x": 180,
                                    "y": 270,
                                    "uvlock": true
                                },
                                "facing=north,half=top,shape=outer_right": {
                                    "model": "minecraft:block/sandstone/stairs/sandstone_stairs_outer",
                                    "x": 180,
                                    "uvlock": true
                                },
                                "facing=north,half=top,shape=straight": {
                                    "model": "minecraft:block/sandstone/stairs/sandstone_stairs",
                                    "x": 180,
                                    "y": 270,
                                    "uvlock": true
                                },
                                                
                                "facing=south,half=bottom,shape=inner_left": {
                                    "model": "minecraft:block/sandstone/stairs/sandstone_stairs_inner"
                                },
                                "facing=south,half=bottom,shape=inner_right": {
                                    "model": "minecraft:block/sandstone/stairs/sandstone_stairs_inner",
                                    "y": 90,
                                    "uvlock": true
                                },
                                "facing=south,half=bottom,shape=outer_left": {
                                    "model": "minecraft:block/sandstone/stairs/sandstone_stairs_outer"
                                },
                                "facing=south,half=bottom,shape=outer_right": {
                                    "model": "minecraft:block/sandstone/stairs/sandstone_stairs_outer",
                                    "y": 90,
                                    "uvlock": true
                                },
                                "facing=south,half=bottom,shape=straight": {
                                    "model": "minecraft:block/sandstone/stairs/sandstone_stairs",
                                    "y": 90,
                                    "uvlock": true
                                },
                                "facing=south,half=top,shape=inner_left": {
                                    "model": "minecraft:block/sandstone/stairs/sandstone_stairs_inner",
                                    "x": 180,
                                    "y": 90,
                                    "uvlock": true
                                },
                                "facing=south,half=top,shape=inner_right": {
                                    "model": "minecraft:block/sandstone/stairs/sandstone_stairs_inner",
                                    "x": 180,
                                    "y": 180,
                                    "uvlock": true
                                },
                                "facing=south,half=top,shape=outer_left": {
                                    "model": "minecraft:block/sandstone/stairs/sandstone_stairs_outer",
                                    "x": 180,
                                    "y": 90,
                                    "uvlock": true
                                },
                                "facing=south,half=top,shape=outer_right": {
                                    "model": "minecraft:block/sandstone/stairs/sandstone_stairs_outer",
                                    "x": 180,
                                    "y": 180,
                                    "uvlock": true
                                },
                                "facing=south,half=top,shape=straight": {
                                    "model": "minecraft:block/sandstone/stairs/sandstone_stairs",
                                    "x": 180,
                                    "y": 90,
                                    "uvlock": true
                                },
                                                
                                "facing=west,half=bottom,shape=inner_left": {
                                    "model": "minecraft:block/sandstone/stairs/sandstone_stairs_inner",
                                    "y": 90,
                                    "uvlock": true
                                },
                                "facing=west,half=bottom,shape=inner_right": {
                                    "model": "minecraft:block/sandstone/stairs/sandstone_stairs_inner",
                                    "y": 180,
                                    "uvlock": true
                                },
                                "facing=west,half=bottom,shape=outer_left": {
                                    "model": "minecraft:block/sandstone/stairs/sandstone_stairs_outer",
                                    "y": 90,
                                    "uvlock": true
                                },
                                "facing=west,half=bottom,shape=outer_right": {
                                    "model": "minecraft:block/sandstone/stairs/sandstone_stairs_outer",
                                    "y": 180,
                                    "uvlock": true
                                },
                                "facing=west,half=bottom,shape=straight": {
                                    "model": "minecraft:block/sandstone/stairs/sandstone_stairs",
                                    "y": 180,
                                    "uvlock": true
                                },
                                "facing=west,half=top,shape=inner_left": {
                                    "model": "minecraft:block/sandstone/stairs/sandstone_stairs_inner",
                                    "x": 180,
                                    "y": 180,
                                    "uvlock": true
                                },
                                "facing=west,half=top,shape=inner_right": {
                                    "model": "minecraft:block/sandstone/stairs/sandstone_stairs_inner",
                                    "x": 180,
                                    "y": 270,
                                    "uvlock": true
                                },
                                "facing=west,half=top,shape=outer_left": {
                                    "model": "minecraft:block/sandstone/stairs/sandstone_stairs_outer",
                                    "x": 180,
                                    "y": 180,
                                    "uvlock": true
                                },
                                "facing=west,half=top,shape=outer_right": {
                                    "model": "minecraft:block/sandstone/stairs/sandstone_stairs_outer",
                                    "x": 180,
                                    "y": 270,
                                    "uvlock": true
                                },
                                "facing=west,half=top,shape=straight": {
                                    "model": "minecraft:block/sandstone/stairs/sandstone_stairs",
                                    "x": 180,
                                    "y": 180,
                                    "uvlock": true
                                }
                            }
                        }
                        """));
    }
}