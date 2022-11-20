package de.simbuildings.tilemapper.core.variations;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.simbuildings.tilemapper.core.junit.ObjectMapperParameterResolver;
import de.simbuildings.tilemapper.core.variations.blockstate.BlockState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(ObjectMapperParameterResolver.class)
class BatchJsonExporterTest {

    @TempDir
    Path tempDir;
    ObjectMapper objectMapper;

    @BeforeEach
    void setUp(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Test
    @DisplayName("Should check if output exists when not exported yet")
    void hasConflict_ShouldReturnFalse_WhenNotExported() throws IOException {
        // given
        BlockState stubBlockState = createStubBlockState();
        BatchJsonExporter batchJsonExporter = createBatchExporter(stubBlockState);

        // when
        boolean result = batchJsonExporter.hasConflict(tempDir);

        // then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("Should check if output exists when is exported")
    void hasConflict_ShouldReturnTrue_WhenExported() throws IOException {
        // given
        BlockState stubBlockState = createStubBlockState();
        BatchJsonExporter batchJsonExporter = createBatchExporter(stubBlockState);

        // when
        batchJsonExporter.export(tempDir);
        boolean result = batchJsonExporter.hasConflict(tempDir);

        // then
        assertThat(result).isTrue();

    }

    @Test
    @DisplayName("Should get no conflict paths when nothing was exported")
    void conflictFiles_ShouldReturnNoPaths_WhenNothingExported() {
        // given
        BlockState stubBlockState = createStubBlockState();
        BatchJsonExporter batchJsonExporter = createBatchExporter(stubBlockState);

        // when
        Set<Path> files = batchJsonExporter.conflictFiles(tempDir);

        // then
        assertThat(files).isEmpty();
    }

    @Test
    @DisplayName("Should get conflict files when was exported")
    void conflictFiles_ShouldReturnPaths_WhenWasExported() throws IOException {
        // given
        BlockState stubBlockState = createStubBlockState();
        BatchJsonExporter batchJsonExporter = createBatchExporter(stubBlockState);

        // when
        batchJsonExporter.export(tempDir);
        Set<Path> files = batchJsonExporter.conflictFiles(tempDir);

        // then
        assertThat(files).isNotEmpty();
    }

    private BatchJsonExporter createBatchExporter(BlockState stubBlockState) {
        return new BatchJsonExporter(objectMapper, Map.of(stubBlockState, tempDir.resolve("test.json")));
    }

    private BlockState createStubBlockState() {
        return new BlockState.Builder()
                .build();
    }
}