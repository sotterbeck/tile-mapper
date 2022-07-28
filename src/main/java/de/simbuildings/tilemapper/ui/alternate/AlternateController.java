package de.simbuildings.tilemapper.ui.alternate;

import de.simbuildings.tilemapper.ui.common.DragAndDropModel;
import de.simbuildings.tilemapper.ui.common.DragAndDropOverlay;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.TransferMode;

import java.net.URL;
import java.util.ResourceBundle;

public class AlternateController implements Initializable {
    @FXML
    private TextField materialTextField;

    @FXML
    private Button addButton;
    @FXML
    private Button faceButton;
    @FXML
    private Button deleteButton;

    @FXML
    private Button settingsButton;
    @FXML
    private Button exportButton;
    @FXML
    private DragAndDropOverlay dragAndDropOverlay;
    @FXML
    private Parent root;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        DragAndDropModel dragAndDropModel = new DragAndDropModel(root);
        dragAndDropOverlay.setDragAndDropModel(dragAndDropModel);

        root.setOnDragOver(event -> event.acceptTransferModes(TransferMode.COPY_OR_MOVE));
    }
}
