package de.simbuildings.tilemapper.variations;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static net.javacrumbs.jsonunit.assertj.JsonAssertions.json;

public class SlabBlockStateTest {

    @Test
    @DisplayName("should return json for generic slab with single variant")
    void shouldReturnJsonForGenericSlabWithSingleVariant() {
        // given
        Variant.Builder variant = new Variant.Builder("sandstone");
        BlockStateFactory blockstateFactory = new SlabBlockStateFactory();

        // when
        BlockState blockState = blockstateFactory.createBlockState("sandstone", variant);

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
                        """
                ));
    }
}
