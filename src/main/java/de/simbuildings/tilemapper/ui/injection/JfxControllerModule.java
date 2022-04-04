package de.simbuildings.tilemapper.ui.injection;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.ClassKey;
import dagger.multibindings.IntoMap;
import de.simbuildings.tilemapper.ui.imagesplitting.ImageSplittingController;
import de.simbuildings.tilemapper.ui.imagesplitting.TileModel;
import de.simbuildings.tilemapper.ui.resourcepack.ResourcepackController;
import de.simbuildings.tilemapper.ui.resourcepack.ResourcepackModel;

@Module
class JfxControllerModule {
    @Provides
    @IntoMap
    @ClassKey(ImageSplittingController.class)
    static Object provideImageSplittingController(TileModel tileModel) {
        return new ImageSplittingController(tileModel);
    }

    @Provides
    @IntoMap
    @ClassKey(ResourcepackController.class)
    static Object provideResourcepackController(ResourcepackModel resourcepackModel) {
        return new ResourcepackController(resourcepackModel);
    }
}
