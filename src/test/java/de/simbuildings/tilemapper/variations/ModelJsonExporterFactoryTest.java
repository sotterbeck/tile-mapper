package de.simbuildings.tilemapper.variations;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.simbuildings.tilemapper.junit.ObjectMapperParameterResolver;
import de.simbuildings.tilemapper.resourcepack.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static de.simbuildings.tilemapper.junit.TestUtils.fileNameOf;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(ObjectMapperParameterResolver.class)
class ModelJsonExporterFactoryTest {
    @TempDir
    private Path tempDir;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @DisplayName("Should export single model")
    @Nested
    class ShouldExportSingleModel {
        @Test
        @DisplayName("when block")
        void whenBlock() throws IOException {
            // given
            ModelJsonExporterFactory modelJsonExporterFactory = ModelJsonExporterFactory.create(objectMapper,
                    new Variant.Builder(new Resource("sandstone")));

            // when
            modelJsonExporterFactory.getExporter(BlockType.BLOCK).export(tempDir);

            // then
            assertThat(tempDir.resolve(Paths.get("assets", "minecraft", "models", "block", "sandstone", "sandstone.json")))
                    .exists();
        }

        @Test
        @DisplayName("when slab")
        void whenSlab() throws IOException {
            // given
            ModelJsonExporterFactory modelJsonExporterFactory = ModelJsonExporterFactory.create(objectMapper,
                    new Variant.Builder(new Resource("sandstone")));

            // when
            modelJsonExporterFactory.getExporter(BlockType.SLAB).export(tempDir);

            // then
            assertThat(tempDir.resolve(Paths.get("assets", "minecraft", "models", "block", "sandstone", "slab", "sandstone_slab.json")))
                    .exists();
            assertThat(tempDir.resolve(Paths.get("assets", "minecraft", "models", "block", "sandstone", "slab", "sandstone_slab_top.json")))
                    .exists();
        }

        @Test
        @DisplayName("when stairs")
        void whenStairs() throws IOException {
            // given
            ModelJsonExporterFactory modelJsonExporterFactory = ModelJsonExporterFactory.create(objectMapper,
                    new Variant.Builder(new Resource("sandstone")));

            // when
            modelJsonExporterFactory.getExporter(BlockType.STAIRS).export(tempDir);

            // then
            assertThat(tempDir.resolve(Paths.get("assets", "minecraft", "models", "block", "sandstone", "stairs", "sandstone_stairs.json")))
                    .exists();
            assertThat(tempDir.resolve(Paths.get("assets", "minecraft", "models", "block", "sandstone", "stairs", "sandstone_stairs_inner.json")))
                    .exists();
            assertThat(tempDir.resolve(Paths.get("assets", "minecraft", "models", "block", "sandstone", "stairs", "sandstone_stairs_outer.json")))
                    .exists();
        }
    }

    @Test
    @DisplayName("Should export multiple models when all block types")
    void shouldExportMultipleModels_WhenAllBlockTypes() throws IOException {
        // given
        ModelJsonExporterFactory modelJsonExporterFactory = ModelJsonExporterFactory.create(objectMapper,
                new Variant.Builder(new Resource("sandstone")));

        // when
        modelJsonExporterFactory.getExporter(BlockType.BLOCK, BlockType.SLAB, BlockType.STAIRS).export(tempDir);

        // then
        assertThat(tempDir)
                .isDirectoryRecursivelyContaining(file -> fileNameOf(file).equals("sandstone.json"))
                .isDirectoryRecursivelyContaining(file -> fileNameOf(file).equals("sandstone_slab.json"))
                .isDirectoryRecursivelyContaining(file -> fileNameOf(file).equals("sandstone_slab_top.json"))
                .isDirectoryRecursivelyContaining(file -> fileNameOf(file).equals("sandstone_stairs.json"))
                .isDirectoryRecursivelyContaining(file -> fileNameOf(file).equals("sandstone_stairs_inner.json"))
                .isDirectoryRecursivelyContaining(file -> fileNameOf(file).equals("sandstone_stairs_outer.json"));
    }

    @Test
    @DisplayName("Should export multiple models when multiple variants")
    void shouldExportMultipleModels_WhenMultipleVariants() throws IOException {
        // given
        ModelJsonExporterFactory modelJsonExporterFactory = ModelJsonExporterFactory.create(objectMapper,
                new Variant.Builder(new Resource("sandstone", "sandstone1")),
                new Variant.Builder(new Resource("sandstone", "sandstone2")));

        // when
        modelJsonExporterFactory.getExporter(BlockType.BLOCK).export(tempDir);

        // then
        assertThat(tempDir)
                .isDirectoryRecursivelyContaining(file -> fileNameOf(file).equals("sandstone1.json"))
                .isDirectoryRecursivelyContaining(file -> fileNameOf(file).equals("sandstone2.json"));
    }
}
