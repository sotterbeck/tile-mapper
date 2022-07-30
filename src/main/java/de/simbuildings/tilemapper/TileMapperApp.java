package de.simbuildings.tilemapper;

import de.simbuildings.tilemapper.common.Persistable;
import de.simbuildings.tilemapper.injection.DaggerTileMapperAppComponent;
import de.simbuildings.tilemapper.injection.TileMapperAppComponent;
import de.simbuildings.tilemapper.injection.jfx.ApplicationComponent;
import javafx.application.Application;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.List;

public class TileMapperApp extends Application {

    public static final int SCENE_WIDTH = 400;
    public static final int SCENE_HEIGHT = 893;
    private static final TileMapperAppComponent LAUNCHER = DaggerTileMapperAppComponent.create();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        loadFonts();
        ApplicationComponent applicationComponent = LAUNCHER.JfxApplication()
                .primaryStage(primaryStage)
                .application(this)
                .build();

        LAUNCHER.persistables()
                .forEach(Persistable::load);

        Stage stage = applicationComponent.mainStage();
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        LAUNCHER.persistables()
                .forEach(Persistable::save);
    }

    private void loadFonts() {
        List<String> fontPaths = List.of(
                "/font/Inter/Inter-Bold.otf",
                "/font/Inter/Inter-SemiBold.otf",
                "/font/Inter/Inter-Medium.otf",
                "/font/Inter/Inter-Regular.otf"
        );
        fontPaths.forEach(path -> Font.loadFont(getClass().getResourceAsStream(path), 14));
    }
}
