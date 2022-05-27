package de.simbuildings.tilemapper.variations;

import de.simbuildings.tilemapper.image.TextureImage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Set;

import static de.simbuildings.tilemapper.junit.TestUtils.getSampleTexture;
import static org.assertj.core.api.Assertions.assertThat;

class TextureExporterTest {

    @TempDir
    private Path tempDir;

    @Test
    void shouldExportTextures_WhenBasicTextures() throws IOException {
        // given
        TextureImage textureOne = getSampleTexture("alternate_sample_1.png");
        TextureImage textureTwo = getSampleTexture("alternate_sample_2.png");

        TextureExporter textureExporter = new TextureExporter("sandstone",
                Map.of("sandstone_1", textureOne, "sandstone_2", textureTwo));

        // when
        textureExporter.export(tempDir);

        // then
        assertThat(tempDir.resolve(Paths.get("assets", "minecraft", "textures", "block", "sandstone", "sandstone_1.png"))).exists();
        assertThat(tempDir.resolve(Paths.get("assets", "minecraft", "textures", "block", "sandstone", "sandstone_2.png"))).exists();
    }

    @Test
    void shouldExportTextures_WhenAdditionalTextures() throws IOException {
        // given
        TextureImage textureOne = getSampleTexture("alternate_sample_1.png");
        TextureImage textureTwo = getSampleTexture("alternate_sample_2.png");
        TextureImage additionalTexture = getSampleTexture("tile_sample_working.png");

        TextureExporter textureExporter = new TextureExporter("sandstone",
                Map.of("sandstone_1", textureOne, "sandstone_2", textureTwo),
                Set.of(additionalTexture));

        // when
        textureExporter.export(tempDir);

        // then
        assertThat(tempDir.resolve(Paths.get("assets", "minecraft", "textures", "block", "sandstone", "sandstone_1.png"))).exists();
        assertThat(tempDir.resolve(Paths.get("assets", "minecraft", "textures", "block", "sandstone", "sandstone_2.png"))).exists();
        assertThat(tempDir.resolve(Paths.get("assets", "minecraft", "textures", "block", "sandstone", "tile_sample_working.png"))).exists();
    }

}