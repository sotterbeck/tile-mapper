package de.simbuildings.tilemapper;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.List;
import java.util.Objects;

/**
 * Created by SimBuildings on 09.10.21 at 23:44
 */
public class App extends Application {

    public static final int SCENE_WIDTH = 400;
    public static final int SCENE_HEIGHT = 893;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        loadFonts();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/primary.fxml")));

        Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/app_icon.png"))));

        stage.setTitle("Tile Mapper");
        stage.setResizable(false);
        stage.show();
        stage.setScene(scene);
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
