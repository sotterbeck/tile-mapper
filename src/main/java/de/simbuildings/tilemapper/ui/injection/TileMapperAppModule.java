package de.simbuildings.tilemapper.ui.injection;

import dagger.Module;
import dagger.Provides;
import de.simbuildings.tilemapper.resourcepack.JsonResourcepackDAO;
import de.simbuildings.tilemapper.resourcepack.ResourcepackDAO;
import de.simbuildings.tilemapper.ui.imagesplitting.TileModel;
import de.simbuildings.tilemapper.ui.resourcepack.ResourcepackModel;

import javax.inject.Singleton;

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
}
