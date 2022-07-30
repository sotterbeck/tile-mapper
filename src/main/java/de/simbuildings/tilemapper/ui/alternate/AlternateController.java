package de.simbuildings.tilemapper.ui.alternate;

import de.simbuildings.tilemapper.image.TextureImage;
import de.simbuildings.tilemapper.ui.common.DragAndDropModel;
import de.simbuildings.tilemapper.ui.common.DragAndDropOverlay;
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
import javafx.scene.input.TransferMode;
import javafx.stage.FileChooser;

import javax.inject.Inject;
import java.net.URL;
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

        variantListView.setItems(alternateModel.variantDtos());
        variantListView.setCellFactory(param -> new VariantListCell());

        setUpDragAndDrop();
    }

    private void bindExportButtonDisableProperty() {
        ListProperty<VariantDto> listProperty = new SimpleListProperty<>(alternateModel.variantDtos());

        exportButton.disableProperty().bind(
                alternateModel.materialProperty().isEmpty()
                        .or(listProperty.emptyProperty()));
    }

    private void setUpDragAndDrop() {
        DragAndDropModel dragAndDropModel = new DragAndDropModel(root);
        dragAndDropOverlay.setDragAndDropModel(dragAndDropModel);

        root.setOnDragOver(event -> event.acceptTransferModes(TransferMode.COPY_OR_MOVE));
    }

    @FXML
    public void handleAdd(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG image file", "*.png"));

        List<VariantDto> variantDtos = fileChooser.showOpenMultipleDialog(root.getScene().getWindow()).stream()
                .map(file -> new VariantDto(TextureImage.of(file.toPath())))
                .toList();
        alternateModel.addVariants(variantDtos);
    }
}
