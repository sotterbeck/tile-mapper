package de.simbuildings.tilemapper.variations;

import de.simbuildings.tilemapper.resourcepack.Resource;
import de.simbuildings.tilemapper.resourcepack.Resourcepack;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.Set;
import java.util.function.Supplier;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static net.javacrumbs.jsonunit.assertj.JsonAssertions.json;

class SlabBlockStateTest {

    @Test
    @DisplayName("should return json for generic slab with single variant")
    void shouldReturnJsonForGenericSlabWithSingleVariant() {
        // given
        Resourcepack resourcepack = createAnyResourcepack();
        Resource resource = new Resource(resourcepack, "sandstone", "sandstone");
        Set<Variant.Builder> variants = Set.of(new Variant.Builder(resource));

        Supplier<BlockState> blockstateFactory = new SlabBlockStateFactory(variants);

        // when
        BlockState blockState = blockstateFactory.get();

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

    private Resourcepack createAnyResourcepack() {
        return new Resourcepack("default", Path.of("test"));
    }
}
