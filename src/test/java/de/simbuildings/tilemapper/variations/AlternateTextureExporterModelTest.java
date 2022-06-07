package de.simbuildings.tilemapper.variations;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.simbuildings.tilemapper.junit.ObjectMapperParameterResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Set;

import static de.simbuildings.tilemapper.junit.TestUtils.fileNameOf;
import static de.simbuildings.tilemapper.junit.TestUtils.getSampleTexture;
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
        Set<VariantDto> variantDtos = createSingleVariant();
        AlternateTextureExporter.create(objectMapper, material, variantDtos, BlockType.BLOCK)
                .export(tempDir);

        // then
        assertThat(tempDir).isDirectoryRecursivelyContaining(file -> fileNameOf(file).equals("sandstone_1.json"));

    }

    @Test
    @DisplayName("Should generate multiple models when block with multiple variants")
    void shouldGenerateMultipleModels_WhenBlockWithMultipleVariants() throws IOException {
        // given
        String material = "sandstone";

        // when
        Set<VariantDto> variantDtos = createMultipleVariants();
        AlternateTextureExporter.create(objectMapper, material, variantDtos, BlockType.BLOCK)
                .export(tempDir);

        // then
        assertThat(tempDir)
                .isDirectoryRecursivelyContaining(file -> fileNameOf(file).equals("sandstone_1.json"))
                .isDirectoryRecursivelyContaining(file -> fileNameOf(file).equals("sandstone_2.json"));

    }

    private Set<VariantDto> createMultipleVariants() {
        return Set.of(
                new VariantDto(getSampleTexture("alternate_sample_1.png")),
                new VariantDto(getSampleTexture("alternate_sample_2.png")));
    }

    private Set<VariantDto> createSingleVariant() {
        return Set.of(new VariantDto(getSampleTexture("alternate_sample_1.png")));
    }
}