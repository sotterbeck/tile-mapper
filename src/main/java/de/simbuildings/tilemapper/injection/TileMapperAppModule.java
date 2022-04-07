package de.simbuildings.tilemapper.injection;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.ElementsIntoSet;
import de.simbuildings.tilemapper.common.Persistable;
import de.simbuildings.tilemapper.resourcepack.JsonResourcepackDAO;
import de.simbuildings.tilemapper.resourcepack.ResourcepackDAO;
import de.simbuildings.tilemapper.ui.imagesplitting.TileModel;
import de.simbuildings.tilemapper.ui.resourcepack.ResourcepackModel;

import javax.inject.Singleton;
import java.util.Set;

@Module
abstract class TileMapperAppModule {
    @Provides
    @Singleton
    static ResourcepackDAO provideResourcepackDAO() {
        return new JsonResourcepackDAO();
    }

    @Provides
    @Singleton
    static TileModel provideTileModel() {
        return new TileModel();
    }

    @Provides
    @Singleton
    static ResourcepackModel provideResourcepackModel(ResourcepackDAO resourcepackDAO) {
        return new ResourcepackModel(resourcepackDAO);
    }

    @Provides
    @ElementsIntoSet
    static Set<Persistable> providePersistables(ResourcepackModel resourcepackModel) {
        return Set.of(resourcepackModel);
    }
}
