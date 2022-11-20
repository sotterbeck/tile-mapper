package de.simbuildings.tilemapper.core.variations;

import com.fasterxml.jackson.databind.ObjectMapper;
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
class AlternateTextureExporterModelTest {

    @TempDir
    Path tempDir;
    ObjectMapper objectMapper;

    @BeforeEach
    void setUp(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Test
    @DisplayName("Should generate single model when block with single variant")
    void shouldGenerateSingleModel_WhenBlockWithSingleVariant() throws IOException {
        // given
        String material = "sandstone";

        // when
        Set<Variant> variants = createSingleVariant();
        AlternateTextureExporter.builder(objectMapper, material, variants)
                .build()
                .export(tempDir);

        // then
        assertThat(tempDir).isDirectoryRecursivelyContaining(file -> TestUtils.fileNameOf(file).equals("sandstone_1.json"));

    }

    @Test
    @DisplayName("Should generate multiple models when block with multiple variants")
    void shouldGenerateMultipleModels_WhenBlockWithMultipleVariants() throws IOException {
        // given
        String material = "sandstone";

        // when
        Set<Variant> variants = createMultipleVariants();
        AlternateTextureExporter.builder(objectMapper, material, variants)
                .build()
                .export(tempDir);

        // then
        assertThat(tempDir)
                .isDirectoryRecursivelyContaining(file -> TestUtils.fileNameOf(file).equals("sandstone_1.json"))
                .isDirectoryRecursivelyContaining(file -> TestUtils.fileNameOf(file).equals("sandstone_2.json"));

    }

    private Set<Variant> createMultipleVariants() {
        return Set.of(
                new Variant(TestUtils.getSampleTexture("alternate_sample_1.png")),
                new Variant(TestUtils.getSampleTexture("alternate_sample_2.png")));
    }

    private Set<Variant> createSingleVariant() {
        return Set.of(new Variant(TestUtils.getSampleTexture("alternate_sample_1.png")));
    }
}