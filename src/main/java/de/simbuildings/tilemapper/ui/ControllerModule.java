package de.simbuildings.tilemapper.ui;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.ClassKey;
import dagger.multibindings.IntoMap;
import de.simbuildings.tilemapper.ui.resourcepack.ResourcepackController;
import de.simbuildings.tilemapper.ui.resourcepack.ResourcepackModel;
import de.simbuildings.tilemapper.ui.tiling.PrimaryController;
import de.simbuildings.tilemapper.ui.tiling.TileModel;

import javax.inject.Named;

@Module
class ControllerModule {
    @Provides
    @IntoMap
    @Named("Controllers")
    @ClassKey(PrimaryController.class)
    static Object providePrimaryController(TileModel tileModel, ResourcepackModel resourcepackModel) {
        return new PrimaryController(tileModel);
    }

    @Provides
    @IntoMap
    @Named("Controllers")
    @ClassKey(ResourcepackController.class)
    static Object provideResourcepackController(ResourcepackModel resourcepackModel) {
        return new ResourcepackController(resourcepackModel);
    }
}
