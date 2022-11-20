package de.simbuildings.tilemapper.gui.resourcepack;

import de.simbuildings.tilemapper.core.common.DocumentDao;
import de.simbuildings.tilemapper.core.common.Persistable;
import de.simbuildings.tilemapper.core.resourcepack.Resourcepack;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

@Singleton
public class ResourcepackModel implements Persistable {
    private final ObservableList<Resourcepack> resourcepacksProperty = FXCollections.observableArrayList();
    private final DocumentDao<Resourcepack> resourcepackDAO;

    @Inject
    public ResourcepackModel(DocumentDao<Resourcepack> resourcepackDAO) {
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
