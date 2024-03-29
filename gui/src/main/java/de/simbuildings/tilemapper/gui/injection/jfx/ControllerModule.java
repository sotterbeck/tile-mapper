package de.simbuildings.tilemapper.gui.injection.jfx;

import dagger.Lazy;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.ClassKey;
import dagger.multibindings.IntoMap;
import de.simbuildings.tilemapper.gui.alternate.AlternateController;
import de.simbuildings.tilemapper.gui.alternate.AlternateExportController;
import de.simbuildings.tilemapper.gui.alternate.AlternateModel;
import de.simbuildings.tilemapper.gui.alternate.VariantPropertiesController;
import de.simbuildings.tilemapper.gui.common.ConflictController;
import de.simbuildings.tilemapper.gui.common.ExportModel;
import de.simbuildings.tilemapper.gui.common.FXMLSceneLoader;
import de.simbuildings.tilemapper.gui.common.MainController;
import de.simbuildings.tilemapper.gui.imagesplitting.ImageSplittingController;
import de.simbuildings.tilemapper.gui.imagesplitting.TileModel;
import de.simbuildings.tilemapper.gui.imagesplitting.TilePreview;
import de.simbuildings.tilemapper.gui.resourcepack.ResourcepackController;
import de.simbuildings.tilemapper.gui.resourcepack.ResourcepackModel;
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
    static Object provideAlternateController(AlternateModel alternateModel,
                                             @Named("variant_properties") Lazy<Stage> variantPropertiesStage,
                                             @Named("alternate_export") Lazy<Stage> alternateExportStage) {
        return new AlternateController(alternateModel, variantPropertiesStage, alternateExportStage);
    }

    @Provides
    @IntoMap
    @ClassKey(VariantPropertiesController.class)
    static Object provideVariantPropertiesController(AlternateModel alternateModel) {
        return new VariantPropertiesController(alternateModel.selectedVariantProperty());
    }

    @Provides
    @IntoMap
    @ClassKey(AlternateExportController.class)
    static Object provideAlternateExportController(AlternateModel alternateModel,
                                                   ResourcepackModel resourcepackModel,
                                                   @Named("resourcepack") Lazy<Stage> resourcepackStage) {
        return new AlternateExportController(alternateModel, resourcepackModel, resourcepackStage);
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
