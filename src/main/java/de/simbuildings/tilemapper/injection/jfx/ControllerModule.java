package de.simbuildings.tilemapper.injection.jfx;

import dagger.Lazy;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.ClassKey;
import dagger.multibindings.IntoMap;
import de.simbuildings.tilemapper.ui.alternate.AlternateController;
import de.simbuildings.tilemapper.ui.common.ConflictController;
import de.simbuildings.tilemapper.ui.common.ExportModel;
import de.simbuildings.tilemapper.ui.common.FXMLSceneLoader;
import de.simbuildings.tilemapper.ui.common.MainController;
import de.simbuildings.tilemapper.ui.imagesplitting.ImageSplittingController;
import de.simbuildings.tilemapper.ui.imagesplitting.TileModel;
import de.simbuildings.tilemapper.ui.imagesplitting.TilePreview;
import de.simbuildings.tilemapper.ui.resourcepack.ResourcepackController;
import de.simbuildings.tilemapper.ui.resourcepack.ResourcepackModel;
import javafx.stage.Stage;

import javax.inject.Named;

@Module
class ControllerModule {
    @Provides
    @IntoMap
    @ClassKey(MainController.class)
    static Object provideMainController() {
        return new MainController();
    }

    @Provides
    @IntoMap
    @ClassKey(ImageSplittingController.class)
    static Object provideImageSplittingController(TileModel tileModel,
                                                  ExportModel exportModel,
                                                  @Named("resourcepack") Lazy<Stage> resourcepackStage,
                                                  @Named("conflict") Lazy<Stage> conflictStage) {
        return new ImageSplittingController(tileModel, exportModel, resourcepackStage, conflictStage);
    }

    @Provides
    @IntoMap
    @ClassKey(AlternateController.class)
    static Object provideAlternateController() {
        return new AlternateController();
    }

    @Provides
    @IntoMap
    @ClassKey(ResourcepackController.class)
    static Object provideResourcepackController(ResourcepackModel resourcepackModel) {
        return new ResourcepackController(resourcepackModel);
    }

    @Provides
    @IntoMap
    @ClassKey(ConflictController.class)
    static Object provideConflictController(ExportModel exportModel) {
        return new ConflictController(exportModel);
    }

    @Provides
    @IntoMap
    @ClassKey(TileModel.class)
    static Object provideTilePreviewController(FXMLSceneLoader sceneLoader, TileModel tileModel) {
        return new TilePreview();
    }
}
