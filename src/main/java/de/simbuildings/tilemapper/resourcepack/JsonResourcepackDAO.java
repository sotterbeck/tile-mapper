package de.simbuildings.tilemapper.resourcepack;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.harawata.appdirs.AppDirs;
import net.harawata.appdirs.AppDirsFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class JsonResourcepackDAO implements ResourcepackDAO {

    private final File file;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public JsonResourcepackDAO() {
        // TODO: use dependency injection
        AppDirs appDirs = AppDirsFactory.getInstance();
        Path jsonFile = Paths.get(appDirs.getUserConfigDir("tilemapper", "4.0.0", "simbuildings"), "resourcepacks.json");

        file = jsonFile.toFile();
    }

    public JsonResourcepackDAO(File file) {
        this.file = file;
    }

    @Override
    public List<Resourcepack> findAll() throws IOException {
        return objectMapper.readValue(file, new TypeReference<List<Resourcepack>>() {
        });
    }

    @Override
    public void saveAll(List<Resourcepack> resourcepacks) throws IOException {
        objectMapper.writeValue(file, resourcepacks);
    }
}
