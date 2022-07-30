package de.simbuildings.tilemapper.ui.alternate;

import de.simbuildings.tilemapper.image.TextureImage;
import de.simbuildings.tilemapper.ui.common.DragAndDropModel;
import de.simbuildings.tilemapper.ui.common.DragAndDropOverlay;
import de.simbuildings.tilemapper.variations.VariantDto;
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
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class AlternateController implements Initializable {
    private final AlternateModel alternateModel;


    @FXML
    private TextField materialTextField;
    @FXML
    private Button exportButton;
    @FXML
    public ListView variantListView;
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
        exportButton.disableProperty().bind(alternateModel.materialProperty().isEmpty());
        variantListView.setItems(alternateModel.variantDtos());

        setUpDragAndDrop();
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

        File image = fileChooser.showOpenDialog(root.getScene().getWindow());
        TextureImage textureImage = TextureImage.of(image.toPath());
        alternateModel.addVariant(new VariantDto(textureImage));
    }
}
