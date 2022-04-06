package de.simbuildings.tilemapper.injection.jfx;

import dagger.Lazy;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.ClassKey;
import dagger.multibindings.IntoMap;
import de.simbuildings.tilemapper.ui.imagesplitting.ImageSplittingController;
import de.simbuildings.tilemapper.ui.imagesplitting.TileModel;
import de.simbuildings.tilemapper.ui.resourcepack.ResourcepackController;
import de.simbuildings.tilemapper.ui.resourcepack.ResourcepackModel;
import javafx.stage.Stage;

import javax.inject.Named;

@Module
class ControllerModule {
    @Provides
    @IntoMap
    @ClassKey(ImageSplittingController.class)
    static Object provideImageSplittingController(TileModel tileModel, @Named("resourcepack") Lazy<Stage> resourcepackStage) {
        return new ImageSplittingController(tileModel, resourcepackStage);
    }

    @Provides
    @IntoMap
    @ClassKey(ResourcepackController.class)
    static Object provideResourcepackController(ResourcepackModel resourcepackModel) {
        return new ResourcepackController(resourcepackModel);
    }
}
