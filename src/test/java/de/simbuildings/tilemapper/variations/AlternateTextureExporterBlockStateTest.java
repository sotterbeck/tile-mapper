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
class AlternateTextureExporterBlockStateTest {
    @TempDir
    Path tempDir;
    ObjectMapper objectMapper;


    @BeforeEach
    void setUp(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Test
    @DisplayName("Should generate block state when block")
    void shouldGenerateBlockState_WhenBlock() throws IOException {
        // given
        String material = "sandstone";
        Set<VariantDto> textureVariants = createVariants();

        // when
        AlternateTextureExporter.create(objectMapper, material, textureVariants, BlockType.BLOCK)
                .export(tempDir);

        // then
        assertThat(tempDir).isDirectoryRecursivelyContaining(file -> fileNameOf(file).equals("sandstone.json"));
    }

    @Test
    @DisplayName("Should generate block state when slab")
    void shouldGenerateBlockState_WhenSlab() throws IOException {
        // given
        String material = "sandstone";
        Set<VariantDto> textureVariants = createVariants();

        // when
        AlternateTextureExporter.create(objectMapper, material, textureVariants, BlockType.SLAB)
                .export(tempDir);

        // then
        assertThat(tempDir).isDirectoryRecursivelyContaining(file -> fileNameOf(file).equals("sandstone_slab.json"));
    }

    @Test
    @DisplayName("Should generate block state when stairs")
    void shouldGenerateBlockState_WhenStairs() throws IOException {
        // given
        String material = "sandstone";
        Set<VariantDto> textureVariants = createVariants();

        // when
        AlternateTextureExporter.create(objectMapper, material, textureVariants, BlockType.STAIRS)
                .export(tempDir);

        // then
        assertThat(tempDir).isDirectoryRecursivelyContaining(file -> fileNameOf(file).equals("sandstone_stairs.json"));
    }

    private Set<VariantDto> createVariants() {
        return Set.of(
                new VariantDto(getSampleTexture("alternate_sample_1.png")),
                new VariantDto(getSampleTexture("alternate_sample_2.png")));
    }
}
