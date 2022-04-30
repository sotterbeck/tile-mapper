package de.simbuildings.tilemapper.ctm;

import de.simbuildings.tilemapper.image.ImageResolution;
import de.simbuildings.tilemapper.image.SquareImageResolution;
import de.simbuildings.tilemapper.tile.ImageSplitter;
import de.simbuildings.tilemapper.tile.TileGrid;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RepeatCtmExporter implements CtmExporter {

    private final ImageSplitter imageSplitter;
    private final RepeatCtmPropertiesWriter propertiesWriter;

    private RepeatCtmExporter(BufferedImage originalImage, SquareImageResolution targetResolution, String blockName) {
        imageSplitter = ImageSplitter.of(originalImage, targetResolution);
        propertiesWriter = new RepeatCtmPropertiesWriter(blockName, new TileGrid(new ImageResolution(originalImage), targetResolution));
    }

    public static CtmExporter of(BufferedImage originalImage, SquareImageResolution targetResolution, String blockName) {
        return new RepeatCtmExporter(originalImage, targetResolution, blockName);
    }

    @Override
    public void export(Path destination) throws IOException {
        imageSplitter.export(destination);
        propertiesWriter.export(destination);
    }

    @Override
    public boolean hasConflict(Path destinationDirectory) {
        return imageSplitter.hasConflict(destinationDirectory)
                || propertiesWriter.hasConflict(destinationDirectory);
    }

    @Override
    public Set<Path> getConflictFiles(Path destinationDirectory) {
        return Stream.concat(
                        imageSplitter.getConflictFiles(destinationDirectory).stream(),
                        propertiesWriter.getConflictFiles(destinationDirectory).stream())
                .collect(Collectors.toUnmodifiableSet());
    }

    @Override
    public Map<String, String> ctmProperties() {
        return propertiesWriter.getProperties().entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> String.valueOf(entry.getKey()),
                        entry -> String.valueOf(entry.getValue()),
                        (prev, next) -> next,
                        HashMap::new
                ));
    }
}
