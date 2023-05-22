package de.simbuildings.tilemapper.gui;

import de.simbuildings.tilemapper.core.common.Persistable;
import de.simbuildings.tilemapper.gui.common.FontLoader;
import de.simbuildings.tilemapper.gui.injection.DaggerTileMapperAppComponent;
import de.simbuildings.tilemapper.gui.injection.TileMapperAppComponent;
import de.simbuildings.tilemapper.gui.injection.jfx.ApplicationComponent;
import javafx.application.Application;
import javafx.stage.Stage;

public class TileMapperApp extends Application {

    private static final TileMapperAppComponent LAUNCHER = DaggerTileMapperAppComponent.create();
    private final FontLoader fontLoader = new FontLoader();

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        fontLoader.loadFonts();
        ApplicationComponent applicationComponent = LAUNCHER.jfxApplication()
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

}
