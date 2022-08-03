package de.simbuildings.tilemapper.ui.resourcepack;

import de.simbuildings.tilemapper.resourcepack.Resourcepack;
import javafx.scene.control.ListCell;

public final class ResourcepackListCell extends ListCell<Resourcepack> {
    @Override
    protected void updateItem(Resourcepack resourcepack, boolean isEmpty) {
        super.updateItem(resourcepack, isEmpty);
        if (isEmpty || resourcepack == null) {
            setText(null);
            return;
        }
        setText(resourcepack.name());
    }
}
