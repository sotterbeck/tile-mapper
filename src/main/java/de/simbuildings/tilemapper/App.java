package de.simbuildings.tilemapper;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Objects;

/**
 * Created by SimBuildings on 09.10.21 at 23:44
 */
public class App extends Application {

    public static final int ROOT_WIDTH = 440;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        loadFonts();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("primary.fxml")));

        Scene scene = new Scene(root, ROOT_WIDTH, 1000);
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("app_icon.png"))));

        stage.setTitle("Tile Mapper");
        stage.setResizable(false);
        stage.show();
        stage.setScene(scene);
    }

    private void loadFonts() {
        Font.loadFont(getClass().getClassLoader().getResourceAsStream("font/Inter/Inter-Bold.otf"), 16);
        Font.loadFont(getClass().getClassLoader().getResourceAsStream("font/Inter/Inter-Medium.otf"), 16);
        Font.loadFont(getClass().getClassLoader().getResourceAsStream("font/Inter/Inter-Regular.otf"), 16);
    }
}
