package de.simbuildings.tilemapper.injection;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.ElementsIntoSet;
import de.simbuildings.tilemapper.common.DocumentDao;
import de.simbuildings.tilemapper.common.Persistable;
import de.simbuildings.tilemapper.resourcepack.JsonResourcepackDao;
import de.simbuildings.tilemapper.resourcepack.Resourcepack;
import de.simbuildings.tilemapper.ui.alternate.AlternateModel;
import de.simbuildings.tilemapper.ui.imagesplitting.TileModel;
import de.simbuildings.tilemapper.ui.resourcepack.ResourcepackModel;

import javax.inject.Singleton;
import java.util.Set;

@Module
abstract class TileMapperAppModule {

    @Provides
    @Singleton
    static ObjectMapper provideObjectMapper() {
        return new ObjectMapper()
                .enable(SerializationFeature.INDENT_OUTPUT);
    }

    @Provides
    @Singleton
    static DocumentDao<Resourcepack> provideResourcepackDAO(ObjectMapper objectMapper) {
        return new JsonResourcepackDao(objectMapper);
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
    @Singleton
    static AlternateModel provideAlternateModel() {
        return new AlternateModel();
    }

    @Provides
    @ElementsIntoSet
    static Set<Persistable> providePersistables(ResourcepackModel resourcepackModel) {
        return Set.of(resourcepackModel);
    }
}
