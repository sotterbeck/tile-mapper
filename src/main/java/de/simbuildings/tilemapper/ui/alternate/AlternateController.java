package de.simbuildings.tilemapper.ui.alternate;

import dagger.Lazy;
import de.simbuildings.tilemapper.image.TextureImage;
import de.simbuildings.tilemapper.ui.common.DragAndDropModel;
import de.simbuildings.tilemapper.ui.common.DragAndDropOverlay;
import de.simbuildings.tilemapper.variations.VariantDto;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.File;
import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

public class AlternateController implements Initializable {
    private final AlternateModel alternateModel;
    private final SelectedVariantModel selectedVariantModel;

    private final Lazy<Stage> alternateExportStage;
    private final Lazy<Stage> variantPropertiesStage;

    @FXML
    private TextField materialTextField;
    @FXML
    private Button exportButton;
    @FXML
    private ListView<VariantDto> variantListView;
    @FXML
    private DragAndDropOverlay dragAndDropOverlay;
    @FXML
    private MenuItem faceMenuItem;
    @FXML
    private MenuItem weightMenuItem;
    @FXML
    private Parent root;

    @Inject
    public AlternateController(AlternateModel alternateModel,
                               SelectedVariantModel selectedVariantModel, @Named("alternate_export") Lazy<Stage> alternateExportStage,
                               @Named("variant_properties") Lazy<Stage> variantPropertiesStage) {
        this.alternateModel = alternateModel;
        this.selectedVariantModel = selectedVariantModel;
        this.alternateExportStage = alternateExportStage;
        this.variantPropertiesStage = variantPropertiesStage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        alternateModel.materialProperty().bind(materialTextField.textProperty());

        bindExportButton();
        bindListView();
        bindMenuItems();

        setUpDragAndDrop();
    }

    private void bindMenuItems() {
        BooleanBinding itemNotSelected = selectedVariantModel.variantProperty().isNull();
        faceMenuItem.disableProperty().bind(itemNotSelected);
        weightMenuItem.disableProperty().bind(itemNotSelected);
    }

    private void bindListView() {
        variantListView.setItems(alternateModel.variantDtos());
        variantListView.setCellFactory(param -> new VariantListCell());
        MultipleSelectionModel<VariantDto> selectionModel = variantListView.getSelectionModel();
        selectionModel.selectedIndexProperty().addListener(this::updateSelectedIndex);
        selectionModel.selectedItemProperty().addListener(this::updateSelectedVariant);
        variantListView.setEditable(true);
    }

    private void bindExportButton() {
        ListProperty<VariantDto> listProperty = new SimpleListProperty<>(alternateModel.variantDtos());

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
             alternateModel.add(variantDtos);
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
        alternateModel.add(variantDtos);
    }

    public void handleRemove(ActionEvent actionEvent) {
        List<VariantDto> selectedItems = variantListView.getSelectionModel().getSelectedItems();
        if (selectedItems.isEmpty()) {
            return;
        }
        alternateModel.remove(selectedItems.get(0));
    }

    public void handleExport(ActionEvent actionEvent) {
        alternateExportStage.get().show();
    }

    @FXML
    public void handleShowVariantProperties(ActionEvent actionEvent) {
        variantPropertiesStage.get().show();
        System.out.println("Selected: " + selectedVariantModel.nameProperty().getValue() + " " + selectedVariantModel.weightProperty());
    }

    private List<VariantDto> variantDtosFromFiles(Collection<File> files) {
        return files.stream()
                .map(file -> new VariantDto(TextureImage.of(file.toPath())))
                .toList();
    }

    private Window window() {
        return root.getScene().getWindow();
    }

    private void updateSelectedVariant(ObservableValue<? extends VariantDto> observable, VariantDto oldVariant, VariantDto newVariant) {
        if (newVariant == null) {
            return;
        }
        selectedVariantModel.variantProperty().set(newVariant);
    }

    private void updateSelectedIndex(ObservableValue<? extends Number> observable, Number oldIndex, Number newIndex) {
        if (newIndex == null) {
            return;
        }
        selectedVariantModel.indexProperty().set(newIndex.intValue());
    }
}
