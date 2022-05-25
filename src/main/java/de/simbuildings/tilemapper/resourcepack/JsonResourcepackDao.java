package de.simbuildings.tilemapper.resourcepack;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.simbuildings.tilemapper.common.DocumentDao;
import de.simbuildings.tilemapper.utils.PathUtils;
import net.harawata.appdirs.AppDirs;
import net.harawata.appdirs.AppDirsFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class JsonResourcepackDao implements DocumentDao<Resourcepack> {

    private final File file;
    private final ObjectMapper objectMapper;

    public JsonResourcepackDao(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;

        AppDirs appDirs = AppDirsFactory.getInstance();

        Path directory = Paths.get(appDirs.getUserConfigDir("tilemapper", "4.0.0", "simbuildings"));
        PathUtils.createDirectories(directory);
        file = directory.resolve("resourcepacks.json").toFile();
    }

    public JsonResourcepackDao(File file) {
        this.file = file;
        objectMapper = new ObjectMapper();
    }

    @Override
    public List<Resourcepack> findAll() throws IOException {
        return objectMapper.readValue(file, new TypeReference<>() {
        });
    }

    @Override
    public void saveAll(List<Resourcepack> resourcepacks) throws IOException {
        objectMapper.writeValue(file, resourcepacks);
    }
}
