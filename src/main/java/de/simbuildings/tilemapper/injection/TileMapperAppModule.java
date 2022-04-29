package de.simbuildings.tilemapper.injection;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.ElementsIntoSet;
import de.simbuildings.tilemapper.common.DocumentDao;
import de.simbuildings.tilemapper.common.Persistable;
import de.simbuildings.tilemapper.resourcepack.JsonResourcepackDao;
import de.simbuildings.tilemapper.resourcepack.Resourcepack;
import de.simbuildings.tilemapper.ui.imagesplitting.TileModel;
import de.simbuildings.tilemapper.ui.resourcepack.ResourcepackModel;

import javax.inject.Singleton;
import java.util.Set;

@Module
abstract class TileMapperAppModule {
    @Provides
    @Singleton
    static DocumentDao<Resourcepack> provideResourcepackDAO() {
        return new JsonResourcepackDao();
    }

    @Provides
    @Singleton
    static TileModel provideTileModel() {
        return new TileModel();
    }

    @Provides
    @Singleton
    static ResourcepackModel provideResourcepackModel(DocumentDao<Resourcepack> resourcepackDao) {
        return new ResourcepackModel(resourcepackDao);
    }

    @Provides
    @ElementsIntoSet
    static Set<Persistable> providePersistables(ResourcepackModel resourcepackModel) {
        return Set.of(resourcepackModel);
    }
}
