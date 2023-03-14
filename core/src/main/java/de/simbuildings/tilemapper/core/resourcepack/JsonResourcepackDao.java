package de.simbuildings.tilemapper.core.resourcepack;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.simbuildings.tilemapper.core.common.DocumentDao;
import de.simbuildings.tilemapper.core.common.PathUtils;
import de.simbuildings.tilemapper.core.storage.AppDirectory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

public class JsonResourcepackDao implements DocumentDao<Resourcepack> {

    private final File file;
    private final ObjectMapper objectMapper;

    public JsonResourcepackDao(ObjectMapper objectMapper, AppDirectory appDirectory) {
        this.objectMapper = objectMapper;
        Path directory = appDirectory.path();

        PathUtils.createDirectories(directory);
        file = directory.resolve("resourcepacks.json").toFile();
    }

    JsonResourcepackDao(ObjectMapper objectMapper, Path path) {
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
