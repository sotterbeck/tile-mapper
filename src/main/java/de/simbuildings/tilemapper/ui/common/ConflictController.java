package de.simbuildings.tilemapper.ui.common;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.stage.Stage;

import javax.inject.Inject;
import java.io.IOException;
import java.io.UncheckedIOException;

public class ConflictController {
    @FXML
    private Parent root;

    private final ExportModel exportModel;

    @Inject
    public ConflictController(ExportModel exportModel) {
        this.exportModel = exportModel;
    }

    private static void exportUnchecked(ExportModel.ExportJob exportJob) {
        try {
            exportJob.exportable().export(exportJob.output());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @FXML
    public void handleShowConflict(ActionEvent actionEvent) {
        actionEvent.consume();
    }

    @FXML
    public void handleCancel(ActionEvent actionEvent) {
        closeWindow();
    }

    @FXML
    public void handleContinue(ActionEvent actionEvent) {
        exportModel.getCurrentExportJob()
                .ifPresent(ConflictController::exportUnchecked);
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) root.getScene().getWindow();
        stage.close();
    }
}
