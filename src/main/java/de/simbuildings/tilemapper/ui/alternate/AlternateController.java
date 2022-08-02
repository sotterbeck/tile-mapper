package de.simbuildings.tilemapper.ui.alternate;

import de.simbuildings.tilemapper.image.TextureImage;
import de.simbuildings.tilemapper.ui.common.DragAndDropModel;
import de.simbuildings.tilemapper.ui.common.DragAndDropOverlay;
import de.simbuildings.tilemapper.variations.BlockType;
import de.simbuildings.tilemapper.variations.VariantDto;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

public class AlternateController implements Initializable {
    private final AlternateModel alternateModel;

    @FXML
    private TextField materialTextField;
    @FXML
    private Button exportButton;
    @FXML
    private ListView<VariantDto> variantListView;
    @FXML
    private DragAndDropOverlay dragAndDropOverlay;
    @FXML
    private Parent root;

    @Inject
    public AlternateController(AlternateModel alternateModel) {
        this.alternateModel = alternateModel;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        alternateModel.materialProperty().bind(materialTextField.textProperty());

        bindExportButtonDisableProperty();

        variantListView.setItems(alternateModel.variantDtoList());
        variantListView.setCellFactory(param -> new VariantListCell());

        setUpDragAndDrop();
    }

    private void bindExportButtonDisableProperty() {
        ListProperty<VariantDto> listProperty = new SimpleListProperty<>(alternateModel.variantDtoList());

        exportButton.disableProperty().bind(
                alternateModel.materialProperty().isEmpty()
                        .or(listProperty.emptyProperty()));
    }

    private void setUpDragAndDrop() {
        DragAndDropModel dragAndDropModel = new DragAndDropModel(root);
        dragAndDropOverlay.setDragAndDropModel(dragAndDropModel);

        root.setOnDragOver(event -> event.acceptTransferModes(TransferMode.COPY_OR_MOVE));
        root.setOnDragDropped(event -> {
            Dragboard dragboard = event.getDragboard();
            if (!dragboard.hasFiles()) {
                return;
            }
            List<VariantDto> variantDtos = variantDtosFromFiles(dragboard.getFiles());
            alternateModel.addVariants(variantDtos);
        });
    }

    @FXML
    public void handleAdd(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG image file", "*.png"));

        List<File> files = fileChooser.showOpenMultipleDialog(window());
        if (files == null) {
            return;
        }
        List<VariantDto> variantDtos = variantDtosFromFiles(files);
        alternateModel.addVariants(variantDtos);
    }

    public void handleRemove(ActionEvent actionEvent) {
        List<VariantDto> selectedItems = variantListView.getSelectionModel().getSelectedItems();
        if (selectedItems.isEmpty()) {
            return;
        }
        alternateModel.removeVariant(selectedItems.get(0));
    }

    public void handleExport(ActionEvent actionEvent) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File directory = directoryChooser.showDialog(window());
        if (directory == null) {
            return;
        }
        try {
            alternateModel.export(directory.toPath(), BlockType.BLOCK);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private List<VariantDto> variantDtosFromFiles(Collection<File> files) {
        return files.stream()
                .map(file -> new VariantDto(TextureImage.of(file.toPath())))
                .toList();
    }

    private Window window() {
        return root.getScene().getWindow();
    }
}
