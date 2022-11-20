package de.simbuildings.tilemapper.core.resourcepack;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
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
}