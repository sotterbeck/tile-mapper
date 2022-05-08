package de.simbuildings.tilemapper.variations;

import de.simbuildings.tilemapper.resourcepack.Resource;
import de.simbuildings.tilemapper.resourcepack.Resourcepack;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static net.javacrumbs.jsonunit.assertj.JsonAssertions.json;

class BlockModelTest {
    @TempDir
    Path tempDir;

    @Test
    void shouldCreateModelForBlock() {
        // given
        Resourcepack resourcepack = new Resourcepack("default", tempDir);
        Resource resource = new Resource(resourcepack, "granite", "granite1");

        // when
        Model model = new Model.Builder(ModelType.BLOCK)
                .texture("all", resource)
                .build();


        // then
        assertThatJson(model).isEqualTo(
                json("""
                        {
                          "parent": "minecraft:block/cube_all",
                          "textures": {
                            "all": "minecraft:block/granite/granite1"
                          }
                        }
                        """
                ));
    }
}
