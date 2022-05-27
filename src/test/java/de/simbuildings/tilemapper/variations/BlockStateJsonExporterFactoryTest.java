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
class BlockStateJsonExporterFactoryTest {

    @TempDir
    Path tempDir;
    ObjectMapper objectMapper;
    Resource sandstone;

    @BeforeEach
    void setUp(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        sandstone = new Resource("sandstone");
    }

    @Nested
    @DisplayName("Should export single block state")
    class ShouldExportSingleBlockState {
        @Test
        @DisplayName("when block")
        void whenBlock() throws IOException {
            // given
            BlockStateJsonExporterFactory alternateJson = BlockStateJsonExporterFactory.create(objectMapper, new Variant.Builder(sandstone));

            // when
            alternateJson.getExporter(BlockType.BLOCK)
                    .export(tempDir);

            // then
            assertThat(tempDir.resolve(Paths.get("assets", "minecraft", "blockstates", "sandstone.json")))
                    .exists();
        }

        @Test
        @DisplayName("when stairs")
        void whenStairs() throws IOException {
            // given
            BlockStateJsonExporterFactory alternateJson = BlockStateJsonExporterFactory.create(objectMapper, new Variant.Builder(sandstone));

            // when
            alternateJson.getExporter(BlockType.STAIRS)
                    .export(tempDir);

            // then
            assertThat(tempDir.resolve(Paths.get("assets", "minecraft", "blockstates", "sandstone_stairs.json")))
                    .exists();
        }

        @Test
        @DisplayName("when slab")
        void whenSlab() throws IOException {
            // given
            BlockStateJsonExporterFactory alternateJson = BlockStateJsonExporterFactory
                    .create(objectMapper, new Variant.Builder(sandstone));

            // when
            alternateJson.getExporter(BlockType.SLAB)
                    .export(tempDir);

            // then
            assertThat(tempDir.resolve(Paths.get("assets", "minecraft", "blockstates", "sandstone_slab.json")))
                    .exists();
        }

        @Test
        @DisplayName("when multiple variants")
        void whenMultipleVariants() throws IOException {
            // given
            Resource resourceOne = new Resource("sandstone", "sandstone_1");
            Resource resourceTwo = new Resource("sandstone", "sandstone_2");

            BlockStateJsonExporterFactory alternateJson = BlockStateJsonExporterFactory.create(objectMapper,
                    new Variant.Builder(resourceOne),
                    new Variant.Builder(resourceTwo));

            // when
            alternateJson.getExporter(BlockType.BLOCK)
                    .export(tempDir);

            // then
            assertThat(tempDir.resolve(Paths.get("assets", "minecraft", "blockstates", "sandstone.json")))
                    .exists();
        }
    }

    @Test
    @DisplayName("Should export all block state when all block types")
    void shouldExportMultipleBlockStates_WhenAllBlockTypes() throws IOException {
        // given
        BlockStateJsonExporterFactory alternateJson = BlockStateJsonExporterFactory.create(objectMapper, new Variant.Builder(sandstone));

        // when
        alternateJson.getExporter(BlockType.BLOCK, BlockType.STAIRS, BlockType.SLAB)
                .export(tempDir);

        // then
        assertThat(tempDir)
                .isDirectoryRecursivelyContaining(file -> fileNameOf(file).equals("sandstone.json"))
                .isDirectoryRecursivelyContaining(file -> fileNameOf(file).equals("sandstone_stairs.json"))
                .isDirectoryRecursivelyContaining(file -> fileNameOf(file).equals("sandstone_slab.json"));

    }
}
