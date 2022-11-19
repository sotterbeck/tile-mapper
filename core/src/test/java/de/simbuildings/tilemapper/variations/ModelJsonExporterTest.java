package de.simbuildings.tilemapper.variations;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.simbuildings.tilemapper.junit.ObjectMapperParameterResolver;
import de.simbuildings.tilemapper.resourcepack.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(ObjectMapperParameterResolver.class)
class ModelJsonExporterTest {
    @TempDir
    private Path tempDir;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Test
    @DisplayName("Should export single model when block")
    void shouldExportSingleModel_WhenBlock() throws IOException {
        // given
        ModelJsonExporter modelJsonExporterFactory = new ModelJsonExporter(objectMapper, BlockType.BLOCK,
                new ResourceVariant(new Resource("sandstone")));

        // when
        modelJsonExporterFactory.export(tempDir);

        // then
        assertThat(tempDir).isDirectoryContaining(file -> Files.isRegularFile(tempDir.resolve(
                Paths.get("assets", "minecraft", "models", "block", "sandstone", "sandstone.json"))));
    }

    @Test
    @DisplayName("Should export multiple models when slab")
    void shouldExportMultipleModels_WhenSlab() throws IOException {
        // given
        ModelJsonExporter modelJsonExporterFactory = new ModelJsonExporter(objectMapper, BlockType.SLAB,
                new ResourceVariant(new Resource("sandstone")));
        // when
        modelJsonExporterFactory.export(tempDir);

        // then
        assertThat(tempDir)
                .isDirectoryContaining(file -> Files.isRegularFile(tempDir.resolve(
                        Paths.get("assets", "minecraft", "models", "block", "sandstone", "slab", "sandstone_slab.json"))))
                .isDirectoryContaining(file -> Files.isRegularFile(tempDir.resolve(
                        Paths.get("assets", "minecraft", "models", "block", "sandstone", "slab", "sandstone_slab_top.json"))));
    }

    @Test
    @DisplayName("Should export multiple models when stairs")
    void shouldExportMultipleModels_WhenStairs() throws IOException {
        // given
        ModelJsonExporter modelJsonExporterFactory = new ModelJsonExporter(objectMapper, BlockType.STAIRS,
                new ResourceVariant(new Resource("sandstone")));

        // when
        modelJsonExporterFactory.export(tempDir);

        // then
        assertThat(tempDir)
                .isDirectoryContaining(file -> Files.isRegularFile(tempDir.resolve(
                        Paths.get("assets", "minecraft", "models", "block", "sandstone", "stairs", "sandstone_stairs.json"))))
                .isDirectoryContaining(file -> Files.isRegularFile(tempDir.resolve(
                        Paths.get("assets", "minecraft", "models", "block", "sandstone", "stairs", "sandstone_stairs_inner.json"))))
                .isDirectoryContaining(file -> Files.isRegularFile(tempDir.resolve(
                        Paths.get("assets", "minecraft", "models", "block", "sandstone", "stairs", "sandstone_stairs_outer.json"))));
    }
}
