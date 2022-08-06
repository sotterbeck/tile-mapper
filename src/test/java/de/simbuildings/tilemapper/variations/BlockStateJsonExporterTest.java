package de.simbuildings.tilemapper.variations;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.simbuildings.tilemapper.common.Exportable;
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
class BlockStateJsonExporterTest {

    @TempDir
    Path tempDir;
    ObjectMapper objectMapper;
    Resource sandstone;
    private String namespace;

    @BeforeEach
    void setUp(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        sandstone = new Resource("sandstone");
        namespace = "minecraft";
    }

    @Test
    @DisplayName("when block")
    void shouldExportSingleBlockState_WhenBlock() throws IOException {
        // given
        Exportable blockStateJsonExporter = new BlockStateJsonExporter(objectMapper, namespace, BlockType.BLOCK, new BlockStateVariant.Builder(sandstone));

        // when
        blockStateJsonExporter.export(tempDir);

        // then
        assertThat(tempDir).isDirectoryContaining(file -> Files.isRegularFile(tempDir.resolve(
                Paths.get("assets", namespace, "blockstates", "sandstone.json"))));
    }

    @Test
    @DisplayName("when stairs")
    void shouldExportSingleBlockState_WhenStairs() throws IOException {
        // given
        Exportable blockStateJsonExporter = new BlockStateJsonExporter(objectMapper, namespace, BlockType.STAIRS, new BlockStateVariant.Builder(sandstone));

        // when
        blockStateJsonExporter.export(tempDir);

        // then
        assertThat(tempDir).isDirectoryContaining(file -> Files.isRegularFile(tempDir.resolve(
                Paths.get("assets", namespace, "blockstates", "sandstone_stairs.json"))));
    }

    @Test
    @DisplayName("when slab")
    void shouldExportSingleBlockState_WhenSlab() throws IOException {
        // given
        Exportable blockStateJsonExporter = new BlockStateJsonExporter(objectMapper, namespace, BlockType.SLAB, new BlockStateVariant.Builder(sandstone));

        // when
        blockStateJsonExporter.export(tempDir);

        // then
        assertThat(tempDir).isDirectoryContaining(file -> Files.isRegularFile(tempDir.resolve(
                Paths.get("assets", namespace, "blockstates", "sandstone_slab.json"))));
    }
}
