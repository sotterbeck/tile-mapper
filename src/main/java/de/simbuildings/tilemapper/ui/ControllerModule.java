package de.simbuildings.tilemapper.ui;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.ClassKey;
import dagger.multibindings.IntoMap;
import de.simbuildings.tilemapper.ui.imagesplitting.ImageSplittingController;
import de.simbuildings.tilemapper.ui.imagesplitting.TileModel;
import de.simbuildings.tilemapper.ui.resourcepack.ResourcepackController;
import de.simbuildings.tilemapper.ui.resourcepack.ResourcepackModel;

import javax.inject.Named;

@Module
class ControllerModule {
    @Provides
    @IntoMap
    @Named("Controllers")
    @ClassKey(ImageSplittingController.class)
    static Object providePrimaryController(TileModel tileModel, ResourcepackModel resourcepackModel) {
        return new ImageSplittingController(tileModel);
    }

    @Provides
    @IntoMap
    @Named("Controllers")
    @ClassKey(ResourcepackController.class)
    static Object provideResourcepackController(ResourcepackModel resourcepackModel) {
        return new ResourcepackController(resourcepackModel);
    }
}
