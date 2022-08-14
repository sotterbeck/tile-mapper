package de.simbuildings.tilemapper.variations;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.simbuildings.tilemapper.common.Exportable;
import de.simbuildings.tilemapper.image.TextureImage;
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
        Exportable alternateTextureExporter = AlternateTextureExporter.builder(objectMapper, material, singleVariant)
                .build();
        alternateTextureExporter.export(tempDir);

        // then
        assertThat(tempDir).isDirectoryRecursivelyContaining(file -> fileNameOf(file).equals("sandstone_1.png"));
    }

    @Test
    @DisplayName("Should export single texture when single variant with override")
    void shouldExportSingleTexture_WhenSingleVariantWithVariant() throws IOException {
        // given
        TextureImage texture = getSampleTexture("alternate_sample_1.png");
        Set<VariantDto> singleVariant = Set.of(new VariantDto(texture));

        // when
        Exportable alternateTextureExporter = AlternateTextureExporter.builder(objectMapper, material, singleVariant)
                .defaultTexture(texture)
                .build();
        alternateTextureExporter.export(tempDir);

        // then
        assertThat(tempDir)
                .isDirectoryRecursivelyContaining(file -> fileNameOf(file).equals("sandstone_1.png"))
                .isDirectoryRecursivelyContaining(file -> fileNameOf(file).equals("sandstone.png"));
    }

    @Test
    @DisplayName("Should export two textures when single variant with override")
    void shouldExportTwoTextures_WhenSingleVariantWithOverride() throws IOException {
        // given
        Set<VariantDto> singleVariantWithOverride = Set.of(new VariantDto.Builder(getSampleTexture("alternate_sample_1.png"))
                .slabTexture(Face.TOP, getSampleTexture("alternate_sample_2.png"))
                .build());

        // when
        Exportable alternateTextureExporter = AlternateTextureExporter.builder(objectMapper, material, singleVariantWithOverride)
                .build();
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
                .slabTexture(Face.TOP, getSampleTexture("alternate_sample_2.png"))
                .stairTexture(Face.BOTTOM, getSampleTexture("alternate_sample_2.png"))
                .build());

        // when
        Exportable alternateTextureExporter = AlternateTextureExporter.builder(objectMapper, material, singleVariantWithOverrides)
                .build();
        alternateTextureExporter.export(tempDir);

        // then
        assertThat(tempDir)
                .isDirectoryRecursivelyContaining(file -> fileNameOf(file).equals("sandstone_1.png"))
                .isDirectoryRecursivelyContaining(file -> fileNameOf(file).equals("alternate_sample_2.png"));
    }
}