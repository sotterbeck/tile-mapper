package de.simbuildings.tilemapper.gui.common;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.IOException;

public class DragAndDropOverlay extends VBox {
    private static final Duration FADE_DURATION = Duration.millis(200);

    private DragAndDropModel dragAndDropModel;

    private final StringProperty titleProperty = new SimpleStringProperty();
    private final StringProperty descriptionProperty = new SimpleStringProperty();

    @FXML
    private VBox dropZone;

    @FXML
    private Label titleLabel;
    @FXML
    private Label descriptionLabel;

    public DragAndDropOverlay() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/components/drag_and_drop_overlay.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new UncheckedLoadException(e);
        }
    }

    public void setDragAndDropModel(DragAndDropModel dragAndDropModel) {
        this.dragAndDropModel = dragAndDropModel;
        initializeBindings();
    }

    private void initializeBindings() {
        dragAndDropModel.isDraggingProperty().addListener((observable, isDraggingOld, isDraggingNow) -> changeVisibility(isDraggingNow));

        titleLabel.textProperty().bind(titleProperty);
        descriptionLabel.textProperty().bind(descriptionProperty);
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

    public String getTitle() {
        return titleProperty.get();
    }

    public StringProperty titleProperty() {
        return titleProperty;
    }

    public void setTitle(String title) {
        this.titleProperty.set(title);
    }

    public String getDescription() {
        return descriptionProperty.get();
    }

    public StringProperty descriptionProperty() {
        return descriptionProperty;
    }

    public void setDescription(String descriptionProperty) {
        this.descriptionProperty.set(descriptionProperty);
    }
}
