package de.simbuildings.tilemapper.injection.jfx;

import dagger.Module;
import dagger.Provides;
import de.simbuildings.tilemapper.ui.common.FXMLSceneLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.inject.Named;

@Module
public class SceneModule {
    @Provides
    @Named("imageSplitting")
    static Scene provideImageSplittingScene(FXMLSceneLoader fxmlSceneLoader) {
        return fxmlSceneLoader.createScene("/fxml/imageSplittingView.fxml");
    }

    @Provides
    @Named("imageSplitting")
    static Stage provideImageSplittingStage(@Named("imageSplitting") Scene scene) {
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setResizable(false);
        return stage;
    }

    @Provides
    @Named("resourcepack")
    static Scene provideResourcepackScene(FXMLSceneLoader fxmlSceneLoader) {
        return fxmlSceneLoader.createScene("/fxml/resourcepackView.fxml");
    }

    @Provides
    @Named("resourcepack")
    static Stage provideResourcepackStage(@Named("resourcepack") Scene scene, @PrimaryStage Stage primaryStage) {
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initOwner(primaryStage);
        stage.initModality(Modality.APPLICATION_MODAL);
        return stage;
    }
}
