package de.simbuildings.tilemapper.variations;

import de.simbuildings.tilemapper.image.TextureImage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

import static de.simbuildings.tilemapper.junit.TestUtils.getSampleTexture;
import static org.assertj.core.api.Assertions.assertThat;

class TextureExporterTest {

    @TempDir
    Path tempDir;

    @Test
    @DisplayName("Should export multiple textures")
    void shouldExportTextures_WhenMultiple() throws IOException {
        // given
        TextureImage textureOne = getSampleTexture("alternate_sample_1.png").withName("sandstone_1");
        TextureImage textureTwo = getSampleTexture("alternate_sample_2.png").withName("sandstone_2");


        // when
        TextureExporter textureExporter = new TextureExporter("sandstone", Set.of(textureOne, textureTwo));
        textureExporter.export(tempDir);

        // then
        assertThat(tempDir.resolve(Paths.get("assets", "minecraft", "textures", "block", "sandstone", "sandstone_1.png"))).exists();
        assertThat(tempDir.resolve(Paths.get("assets", "minecraft", "textures", "block", "sandstone", "sandstone_2.png"))).exists();
    }

    @Test
    @DisplayName("Should get no conflict paths when nothing was exported")
    void conflictFiles_ShouldReturnNoPaths_WhenNothingExported() {
        // given
        String material = "sandstone";
        TextureImage textureImage = getSampleTexture("alternate_sample_1.png");
        TextureExporter textureExporter = new TextureExporter(material, Set.of(textureImage));

        // when
        Set<Path> files = textureExporter.conflictFiles(tempDir);

        // then
        assertThat(files).isEmpty();
    }

    @Test
    @DisplayName("Should get conflict files when was exported")
    void conflictFiles_ShouldReturnPaths_WhenWasExported() throws IOException {
        // given
        String material = "sandstone";
        TextureImage textureImage = getSampleTexture("alternate_sample_1.png");
        TextureExporter textureExporter = new TextureExporter(material, Set.of(textureImage));

        // when
        textureExporter.export(tempDir);
        Set<Path> files = textureExporter.conflictFiles(tempDir);

        // then
        assertThat(files).hasSize(1);
    }
}