package de.simbuildings.tilemapper.resourcepack;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.harawata.appdirs.AppDirs;
import net.harawata.appdirs.AppDirsFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class JsonResourcepackDAO implements ResourcepackDAO {

    private final File file;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public JsonResourcepackDAO() {
        AppDirs appDirs = AppDirsFactory.getInstance();
        Path directory = Paths.get(appDirs.getUserConfigDir("tilemapper", "4.0.0", "simbuildings"));

        createConfigPath(directory);
        file = directory.resolve("resourcepacks.json").toFile();
    }

    private void createConfigPath(Path directory) {
        try {
            Files.createDirectories(directory);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
