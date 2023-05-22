package de.simbuildings.tilemapper.gui.injection;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.ElementsIntoSet;
import de.simbuildings.tilemapper.core.common.DocumentDao;
import de.simbuildings.tilemapper.core.common.Persistable;
import de.simbuildings.tilemapper.core.resourcepack.JsonResourcepackDao;
import de.simbuildings.tilemapper.core.resourcepack.Resourcepack;
import de.simbuildings.tilemapper.core.storage.AppDataDirectory;
import de.simbuildings.tilemapper.core.storage.AppDirectory;
import de.simbuildings.tilemapper.gui.alternate.AlternateModel;
import de.simbuildings.tilemapper.gui.imagesplitting.TileModel;
import de.simbuildings.tilemapper.gui.resourcepack.ResourcepackModel;

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
    static AppDirectory provideAppDataDirectory() {
        return AppDataDirectory.of("TileMapper");
    }

    @Provides
    @Singleton
    static DocumentDao<Resourcepack> provideResourcepackDao(ObjectMapper objectMapper, AppDirectory appDirectory) {
        return new JsonResourcepackDao(objectMapper, appDirectory.path()
                .resolve("resourcepacks.json"));
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
    static AlternateModel provideAlternateModel(ObjectMapper objectMapper) {
        return new AlternateModel(objectMapper);
    }

    @Provides
    @ElementsIntoSet
    static Set<Persistable> providePersistables(ResourcepackModel resourcepackModel) {
        return Set.of(resourcepackModel);
    }
}
