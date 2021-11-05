package de.simbuildings.tilemapper;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Objects;

/**
 * Created by SimBuildings on 09.10.21 at 23:44
 */
public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        loadFonts();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("primary.fxml")));

        Scene scene = new Scene(root, 440, 1000);

        primaryStage.setTitle("Tile Mapper");
        primaryStage.setResizable(true);
        primaryStage.show();
        primaryStage.centerOnScreen();
        primaryStage.setScene(scene);
    }

    private void loadFonts() {
        // TODO better solution (DRY)

        Font.loadFont(getClass().getClassLoader().getResourceAsStream("font/Karla/Karla-Bold.ttf"), 16);
        Font.loadFont(getClass().getClassLoader().getResourceAsStream("font/Karla/Karla-BoldItalic.ttf"), 16);
        Font.loadFont(getClass().getClassLoader().getResourceAsStream("font/Karla/Karla-Italic.ttf"), 16);
        Font.loadFont(getClass().getClassLoader().getResourceAsStream("font/Karla/Karla-Regular.ttf"), 16);

        Font.loadFont(getClass().getClassLoader().getResourceAsStream("font/Rubik/Rubik-Bold.ttf"), 16);
        Font.loadFont(getClass().getClassLoader().getResourceAsStream("font/Rubik/Rubik-Medium.ttf"), 16);
        Font.loadFont(getClass().getClassLoader().getResourceAsStream("font/Rubik/Rubik-Regular.ttf"), 16);
    }
}
