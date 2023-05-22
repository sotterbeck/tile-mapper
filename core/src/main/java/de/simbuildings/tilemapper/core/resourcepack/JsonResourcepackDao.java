package de.simbuildings.tilemapper.core.resourcepack;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.simbuildings.tilemapper.core.common.DocumentDao;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

public class JsonResourcepackDao implements DocumentDao<Resourcepack> {

    private final File file;
    private final ObjectMapper objectMapper;

    /**
     * Class constructor.
     *
     * @param objectMapper The object mapper used to serialize and deserialize JSON data.
     * @param path         The path to the file where the resource packs will be saved or retrieved from.
     */
    public JsonResourcepackDao(ObjectMapper objectMapper, Path path) {
        this.file = path.toFile();
        this.objectMapper = objectMapper;
    }

    @Override
    public List<Resourcepack> findAll() throws IOException {
        if (!file.exists()) {
            return Collections.emptyList();
        }
        return objectMapper.readValue(file, new TypeReference<>() {
        });
    }

    @Override
    public void saveAll(List<Resourcepack> resourcepacks) throws IOException {
        objectMapper.writeValue(file, resourcepacks);
    }
}
