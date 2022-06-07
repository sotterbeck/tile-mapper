package de.simbuildings.tilemapper.variations;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.simbuildings.tilemapper.junit.ObjectMapperParameterResolver;
import de.simbuildings.tilemapper.variations.model.Face;
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
class AlternateTextureExporterTextureTest {

    @TempDir
    Path tempDir;
    ObjectMapper objectMapper;
    String material;

    @BeforeEach
    void setUp(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.material = "sandstone";
    }

    @Test
    @DisplayName("Should export single texture when single variant")
    void shouldExportSingleTexture_WhenSingleVariant() throws IOException {
        // given
        Set<VariantDto> singleVariant = Set.of(new VariantDto(getSampleTexture("alternate_sample_1.png")));

        // when
        AlternateTextureExporter alternateTextureExporter = AlternateTextureExporter.create(objectMapper,
                material,
                singleVariant,
                BlockType.BLOCK);
        alternateTextureExporter.export(tempDir);

        // then
        assertThat(tempDir).isDirectoryRecursivelyContaining(file -> fileNameOf(file).equals("sandstone_1.png"));
    }

    @Test
    @DisplayName("Should export two textures when single variant with override")
    void shouldExportTwoTextures_WhenSingleVariantWithOverride() throws IOException {
        // given
        Set<VariantDto> singleVariantWithOverride = Set.of(new VariantDto.Builder(getSampleTexture("alternate_sample_1.png"))
                .overrideSlabTexture(Face.TOP, getSampleTexture("alternate_sample_2.png"))
                .build());

        // when
        AlternateTextureExporter alternateTextureExporter = AlternateTextureExporter.create(objectMapper,
                material,
                singleVariantWithOverride,
                BlockType.BLOCK);
        alternateTextureExporter.export(tempDir);

        // then
        assertThat(tempDir)
                .isDirectoryRecursivelyContaining(file -> fileNameOf(file).equals("sandstone_1.png"))
                .isDirectoryRecursivelyContaining(file -> fileNameOf(file).equals("alternate_sample_2.png"));
    }

    @Test
    @DisplayName("Should export two textures when single variant with multiple overrides of same file")
    void shouldExportSingleTexture_WhenSingleVariantWithOverrideOfSameFiles() throws IOException {
        // given
        Set<VariantDto> singleVariantWithOverrides = Set.of(new VariantDto.Builder(getSampleTexture("alternate_sample_1.png"))
                .overrideSlabTexture(Face.TOP, getSampleTexture("alternate_sample_2.png"))
                .overrideStairTexture(Face.BOTTOM, getSampleTexture("alternate_sample_2.png"))
                .build());

        // when
        AlternateTextureExporter alternateTextureExporter = AlternateTextureExporter.create(objectMapper,
                material,
                singleVariantWithOverrides,
                BlockType.BLOCK);
        alternateTextureExporter.export(tempDir);

        // then
        assertThat(tempDir)
                .isDirectoryRecursivelyContaining(file -> fileNameOf(file).equals("sandstone_1.png"))
                .isDirectoryRecursivelyContaining(file -> fileNameOf(file).equals("alternate_sample_2.png"));
    }
}