package de.simbuildings.tilemapper.injection.jfx;

import dagger.Module;
import dagger.Provides;
import de.simbuildings.tilemapper.ui.common.FXMLSceneLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.inject.Named;

@Module
class SceneModule {
    @Provides
    @Named("imageSplitting")
    static Scene provideImageSplittingScene(FXMLSceneLoader fxmlSceneLoader) {
        return fxmlSceneLoader.createScene("/fxml/imageSplittingView.fxml");
    }

    @Provides
    @Named("imageSplitting")
    static Stage provideImageSplittingStage(@Named("imageSplitting") Scene scene, @PrimaryStage Stage primaryStage) {
        primaryStage.setScene(scene);
        primaryStage.setTitle("Tile Mapper");
        primaryStage.setResizable(false);
        return primaryStage;
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
        stage.setTitle("Resourcepacks");
        stage.initOwner(primaryStage);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                stage.close();
            }
        });
        return stage;
    }

    @Provides
    @Named("conflict")
    static Scene provideConflictScene(FXMLSceneLoader fxmlSceneLoader) {
        return fxmlSceneLoader.createScene("/fxml/conflict.fxml");
    }

    @Provides
    @Named("conflict")
    static Stage provideConflictStage(@Named("conflict") Scene conflictScene, @PrimaryStage Stage primaryStage) {
        Stage stage = new Stage();
        stage.setScene(conflictScene);
        stage.setTitle("Error");
        stage.initOwner(primaryStage);
        stage.initModality(Modality.WINDOW_MODAL);
        return stage;
    }
}
