package de.simbuildings.tilemapper.resourcepack;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

class ResourcepackSerializationTest {

    Resourcepack underTest;

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
}