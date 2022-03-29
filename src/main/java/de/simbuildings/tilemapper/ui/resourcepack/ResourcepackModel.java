package de.simbuildings.tilemapper.ui.resourcepack;

import de.simbuildings.tilemapper.resourcepack.Resourcepack;
import de.simbuildings.tilemapper.resourcepack.ResourcepackDAO;
import de.simbuildings.tilemapper.ui.common.PreferencesModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class ResourcepackModel implements PreferencesModel {
    private final ObservableList<Resourcepack> resourcepacksProperty = FXCollections.observableArrayList();
    private final ResourcepackDAO resourcepackDAO;

    public ResourcepackModel(ResourcepackDAO resourcepackDAO) {
        this.resourcepackDAO = resourcepackDAO;
    }


    public ObservableList<Resourcepack> resourcepacksProperty() {
        return resourcepacksProperty;
    }

    public void addResourcepack(Path directory) throws IllegalArgumentException {
        Resourcepack resourcepack = new Resourcepack(directory);
        if (!resourcepack.isResourcepackDirectory()) {
            throw new IllegalArgumentException("directory is not a resourcepack");
        }
        resourcepacksProperty.add(resourcepack);
    }

    public void removeResourcepack(Resourcepack resourcepack) {
        resourcepacksProperty.remove(resourcepack);
    }

    public void save() {
        try {
            resourcepackDAO.saveAll(resourcepacksProperty);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load() {
        try {
            List<Resourcepack> resourcepacks = resourcepackDAO.findAll().stream()
                    .filter(Resourcepack::isResourcepackDirectory)
                    .toList();
            resourcepacksProperty.setAll(resourcepacks);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
