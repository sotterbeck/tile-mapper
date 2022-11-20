package de.simbuildings.tilemapper.core.variations;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.simbuildings.tilemapper.core.common.Exportable;
import de.simbuildings.tilemapper.core.junit.ObjectMapperParameterResolver;
import de.simbuildings.tilemapper.core.junit.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(ObjectMapperParameterResolver.class)
class AlternateTextureExporterTest {

    @TempDir
    Path tempDir;
    ObjectMapper objectMapper;
    String material;
    Set<Variant> variants;

    @BeforeEach
    void setUp(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.material = "sandstone";
        this.variants = Set.of(new Variant(TestUtils.getSampleTexture("alternate_sample_1.png")));
    }

    @Test
    @DisplayName("Should check if output exists when not exported yet")
    void hasConflict_ShouldReturnFalse_WhenNotExported() {
        // given
        Exportable alternateTextureExporter = AlternateTextureExporter.builder(objectMapper, material, variants)
                .build();

        // when
        boolean result = alternateTextureExporter.hasConflict(tempDir);

        // then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("Should check if output exists when is exported")
    void hasConflict_ShouldReturnTrue_WhenExported() throws IOException {
        // given
        Exportable alternateTextureExporter = AlternateTextureExporter.builder(objectMapper, material, variants)
                .build();

        // when
        alternateTextureExporter.export(tempDir);
        boolean result = alternateTextureExporter.hasConflict(tempDir);

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("Should get no conflict paths when nothing was exported")
    void conflictFiles_ShouldReturnNoPaths_WhenNothingExported() {
        // given
        Exportable alternateTextureExporter = AlternateTextureExporter.builder(objectMapper, material, variants)
                .build();

        // when
        Set<Path> files = alternateTextureExporter.conflictFiles(tempDir);

        // then
        assertThat(files).isEmpty();
    }

    @Test
    @DisplayName("Should get conflict files when was exported")
    void conflictFiles_ShouldReturnPaths_WhenWasExported() throws IOException {
        // given
        Exportable alternateTextureExporter = AlternateTextureExporter.builder(objectMapper, material, variants)
                .build();

        // when
        alternateTextureExporter.export(tempDir);
        Set<Path> files = alternateTextureExporter.conflictFiles(tempDir);

        // then
        assertThat(files).isNotEmpty();
    }
}