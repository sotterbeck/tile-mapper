package de.simbuildings.tilemapper.ui.resourcepack;

import dagger.Module;
import dagger.Provides;
import de.simbuildings.tilemapper.resourcepack.JsonResourcepackDAO;
import de.simbuildings.tilemapper.resourcepack.ResourcepackDAO;

@Module
public class ResourcepackModule {

    @Provides
    public static ResourcepackDAO provideResourcepackDAO() {
        return new JsonResourcepackDAO();
    }

    @Provides
    public static ResourcepackModel provideResourcepackModel(ResourcepackDAO resourcepackDAO) {
        return new ResourcepackModel(resourcepackDAO);
    }
}
