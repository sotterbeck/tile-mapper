package de.simbuildings.tilemapper.ctm;

import de.simbuildings.tilemapper.common.Exportable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Properties;
import java.util.Set;

public abstract class CtmPropertiesWriter implements Exportable {

    private Properties properties;
    private final String block;
    private final String comment;

    CtmPropertiesWriter(String block, String comment) {
        this.block = block;
        this.comment = comment;
    }

    CtmPropertiesWriter(String block) {
        this.block = block;
        this.comment = "auto-generated properties file by Tile Mapper";
    }

    @Override
    public final void export(Path destinationDirectory) throws IOException {
        if (properties == null) {
            properties = getPropertiesBuilder().build();
        }

        String blockName = getFileBlockName();

        File outputFile = getOutputPath(destinationDirectory, blockName).toFile();
        try (OutputStream outputStream = new FileOutputStream(outputFile)) {
            properties.store(outputStream, comment);
        }
    }

    @Override
    public final boolean hasConflict(Path destinationDirectory) {
        return Files.exists(getOutputPath(destinationDirectory, block));
    }

    @Override
    public final Set<Path> getConflictFiles(Path destinationDirectory) {
        return Collections.emptySet();
    }

    final CtmPropertiesBuilder defaultPropertiesBuilder() {
        return new CtmPropertiesBuilder()
                .matchBlocks(block);
    }

    protected abstract CtmPropertiesBuilder getPropertiesBuilder();

    private String getFileBlockName() {
        return block.replace("minecraft:", "");
    }

    private Path getOutputPath(Path destinationDirectory, String blockName) {
        String fileName = String.format("%s.properties", blockName);
        return destinationDirectory.resolve(fileName);
    }
}