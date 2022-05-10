package de.simbuildings.tilemapper.resourcepack;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ResourcepackTest {

    Resourcepack underTest;

    @TempDir
    Path tempDir;

    @Test
    @DisplayName("Should serialize resourcepack")
    void shouldSerializeResourcepack() throws JsonProcessingException {
        // given
        ObjectMapper objectMapper = new ObjectMapper();
        underTest = new Resourcepack("Default Textures", Paths.get("/"));

        // when
        String resourcepackJson = objectMapper.writeValueAsString(underTest);

        // then
        assertThat(resourcepackJson)
                .contains("name")
                .contains("directory");
    }

    @Test
    @DisplayName("Should use directory name when no name is specified")
    void shouldUseDirectoryName_WhenNoNameIsSpecified() {
        // given
        Path directory = Paths.get(".minecraft", "resourcepacks", "Default Textures");

        // when
        underTest = new Resourcepack(directory);

        // then
        assertThat(underTest.name())
                .isEqualTo("Default Textures");
    }

    @Test
    @DisplayName("Should check if directory is a resourcepack")
    void isResourcepackDirectory_ShouldReturnTrue_WhenDirectoryContainsMcmetaFile() throws IOException {
        // given
        Files.createFile(tempDir.resolve("pack.mcmeta"));
        underTest = new Resourcepack(tempDir);

        // when
        boolean result = underTest.isResourcepackDirectory();

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("Should check if directory is a resource pack when directory does not contain .mcmeta file")
    void isResourcepackDirectory_ShouldReturnFalse_WhenDirectoryDoesNotContainMcmetaFile() {
        // given
        underTest = new Resourcepack(tempDir);

        // when
        boolean result = underTest.isResourcepackDirectory();

        // then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("Should compare resourcepacks by name and directory name")
    void compareTo_ShouldOrderResourcepacksByNameAndDirectoryName() {
        // given
        List<Resourcepack> resourcepacks = List.of(
                new Resourcepack("A Texture Pack", tempDir.resolve("a_textures")),
                new Resourcepack("Default", tempDir.resolve("default_v1")),
                new Resourcepack("Default", tempDir.resolve("default_v2"))
        );

        // then
        assertThat(resourcepacks).isSorted();
    }

    @Nested
    @DisplayName("Resourcepack Paths")
    class ResourcepackPaths {

        private String blockDirectoryName;
        private String blockName;

        @BeforeEach
        void setUp() {
            blockDirectoryName = "stone";
            blockName = "minecraft:stone";
        }

        private Resourcepack createGenericResourcepack() {
            return new Resourcepack("default", tempDir);
        }

        @Test
        @DisplayName("should get model directory for block")
        void shouldReturnModelDirectoryForBlock() {
            // given
            underTest = createGenericResourcepack();

            // when
            Path modelPath = underTest.modelDirectory(blockName);

            // then
            assertThat(modelPath)
                    .isDirectory()
                    .endsWith(
                            Paths.get("assets", "minecraft", "models", "block", blockDirectoryName)
                    );
        }

        @Test
        @DisplayName("should get texture directory for block")
        void shouldReturnTextureDirectoryForBlock() {
            // given
            underTest = createGenericResourcepack();

            // when
            Path texturePath = underTest.textureDirectory(blockName);

            // then
            assertThat(texturePath)
                    .isDirectory()
                    .endsWith(
                            Paths.get("assets", "minecraft", "textures", "block", blockDirectoryName)
                    );
        }

        @Test
        @DisplayName("should get blockstates directory")
        void shouldReturnBlockstatesDirectory() {
            // given
            underTest = createGenericResourcepack();

            // when
            Path blockstatesPath = underTest.blockstatesDirectory();

            // then
            assertThat(blockstatesPath)
                    .isDirectory()
                    .endsWith(
                            Paths.get("assets", "minecraft", "blockstates")
                    );
        }
    }
}