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
        Set<Variant> textureVariants = createVariants();

        // when
        AlternateTextureExporter.builder(objectMapper, material, textureVariants)
                .build()
                .export(tempDir);

        // then
        assertThat(tempDir).isDirectoryRecursivelyContaining(file -> TestUtils.fileNameOf(file).equals("sandstone.json"));
    }

    @Test
    @DisplayName("Should generate block state when slab")
    void shouldGenerateBlockState_WhenSlab() throws IOException {
        // given
        String material = "sandstone";
        Set<Variant> variants = createVariants();

        // when
        AlternateTextureExporter.builder(objectMapper, material, variants)
                .blockTypes(BlockType.SLAB)
                .build()
                .export(tempDir);

        // then
        assertThat(tempDir).isDirectoryRecursivelyContaining(file -> TestUtils.fileNameOf(file).equals("sandstone_slab.json"));
    }

    @Test
    @DisplayName("Should generate block state when stairs")
    void shouldGenerateBlockState_WhenStairs() throws IOException {
        // given
        String material = "sandstone";
        Set<Variant> textures = createVariants();

        // when
        AlternateTextureExporter.builder(objectMapper, material, textures)
                .blockTypes(BlockType.STAIRS)
                .build()
                .export(tempDir);

        // then
        assertThat(tempDir).isDirectoryRecursivelyContaining(file -> TestUtils.fileNameOf(file).equals("sandstone_stairs.json"));
    }

    private Set<Variant> createVariants() {
        return Set.of(
                new Variant(TestUtils.getSampleTexture("alternate_sample_1.png")),
                new Variant(TestUtils.getSampleTexture("alternate_sample_2.png")));
    }
}
