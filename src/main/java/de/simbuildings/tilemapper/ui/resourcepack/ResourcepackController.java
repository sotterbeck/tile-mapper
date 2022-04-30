package de.simbuildings.tilemapper.ui.resourcepack;

import de.simbuildings.tilemapper.resourcepack.Resourcepack;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import javax.inject.Inject;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class ResourcepackController implements Initializable {

    private final ResourcepackModel model;

    @FXML
    private Parent root;

    @FXML
    private ListView<Resourcepack> listView;

    @Inject
    public ResourcepackController(ResourcepackModel model) {
        this.model = model;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setUpListView();
        setUpDragAndDrop();
    }

    private void setUpListView() {
        listView.setItems(model.resourcepacksProperty());
        listView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Resourcepack item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    return;
                }
                setText(item.name());
            }
        });
    }

    private void setUpDragAndDrop() {
        root.setOnDragOver(event -> event.acceptTransferModes(TransferMode.COPY_OR_MOVE));
        root.setOnDragDropped(event -> {
            Dragboard dragboard = event.getDragboard();
            if (dragboard.hasFiles()) {
                File droppedFile = dragboard.getFiles().get(0);
                if (!droppedFile.isDirectory()) {
                    return;
                }
                addResourcepack(droppedFile);
            }
        });
    }

    @FXML
    private void handleAdd() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File directory = directoryChooser.showDialog(root.getScene().getWindow());
        addResourcepack(directory);
    }

    private void addResourcepack(File directory) {
        if (directory != null) {
            try {
                model.addResourcepack(directory.toPath());
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void handleDelete() {
        Resourcepack selectedResourcepack = listView.getSelectionModel().getSelectedItem();
        model.removeResourcepack(selectedResourcepack);
    }

    @FXML
    private void handleConfirm() {
        closeWindow();
        model.save();
    }

    @FXML
    private void handleCancel() {
        closeWindow();
        model.load();
    }


    private void closeWindow() {
        Stage stage = (Stage) root.getScene().getWindow();
        stage.close();
    }
}
