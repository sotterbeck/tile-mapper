package de.simbuildings.tilemapper;

import de.simbuildings.tilemapper.ui.AppComponent;
import de.simbuildings.tilemapper.ui.DaggerAppComponent;
import de.simbuildings.tilemapper.ui.common.PreferencesModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import javax.inject.Provider;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by SimBuildings on 09.10.21 at 23:44
 */
public class App extends Application {

    public static final int SCENE_WIDTH = 400;
    public static final int SCENE_HEIGHT = 893;
    private PreferencesModel resourcepackModel;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        loadFonts();

        AppComponent appComponent = DaggerAppComponent.create();
        Map<Class<?>, Provider<Object>> controllers = appComponent.controllers();

        resourcepackModel = appComponent.resourcepackModel();

        FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/primaryView.fxml")));
        fxmlLoader.setControllerFactory(type -> controllers.get(type).get());
        Parent root = fxmlLoader.load();

        Scene scene = new Scene(root);
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/app_icon.png"))));

        resourcepackModel.load();
        stage.setTitle("Tile Mapper");
        stage.setResizable(false);
        stage.show();
        stage.setScene(scene);
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        resourcepackModel.save();
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
