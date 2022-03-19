package de.simbuildings.tilemapper.ui.controllers;

import de.simbuildings.tilemapper.ui.models.DragAndDropModel;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.IOException;

public class DragAndDropOverlay extends VBox {
    private static final Duration FADE_DURATION = Duration.millis(200);

    private DragAndDropModel dragAndDropModel;

    @FXML
    private VBox dropZone;

    public DragAndDropOverlay() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("components/drag_and_drop_overlay.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setDragAndDropModel(DragAndDropModel dragAndDropModel) {
        this.dragAndDropModel = dragAndDropModel;
        initializeBindings();
    }

    private void initializeBindings() {
        dragAndDropModel.isDraggingProperty().addListener((observable, isDraggingOld, isDraggingNow) -> changeVisibility(isDraggingNow));
    }

    private void changeVisibility(boolean isDragging) {
        if (isDragging) {
            fadeIn();
            playDropZoneTranslation();
        } else {
            fadeOut();
        }
    }

    private void fadeOut() {
        FadeTransition fadeTransition = new FadeTransition(FADE_DURATION, this);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.setOnFinished(event -> this.setVisible(false));

        fadeTransition.play();
    }

    private void fadeIn() {
        this.setVisible(true);
        FadeTransition fadeTransition = new FadeTransition(FADE_DURATION, this);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);

        fadeTransition.play();
    }

    private void playDropZoneTranslation() {
        TranslateTransition translateTransition = new TranslateTransition(FADE_DURATION, dropZone);
        translateTransition.setFromY(-8);
        translateTransition.setToY(0);
        translateTransition.setInterpolator(Interpolator.EASE_OUT);

        translateTransition.play();
    }
}
