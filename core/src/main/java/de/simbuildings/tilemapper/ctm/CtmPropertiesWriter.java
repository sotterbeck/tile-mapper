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

abstract class CtmPropertiesWriter implements Exportable {

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
        buildProperties();

        File outputFile = getOutputPath(destinationDirectory).toFile();
        try (OutputStream outputStream = new FileOutputStream(outputFile)) {
            properties.store(outputStream, comment);
        }
    }

    private void buildProperties() {
        if (properties == null) {
            properties = getPropertiesBuilder().build();
        }
    }

    @Override
    public final boolean hasConflict(Path destinationDirectory) {
        return Files.exists(getOutputPath(destinationDirectory));
    }

    @Override
    public final Set<Path> conflictFiles(Path destinationDirectory) {
        if (!hasConflict(destinationDirectory)) {
            return Collections.emptySet();
        }
        return Set.of(getOutputPath(destinationDirectory));
    }

    final CtmPropertiesBuilder defaultPropertiesBuilder() {
        return new CtmPropertiesBuilder()
                .matchBlocks(block);
    }

    abstract CtmPropertiesBuilder getPropertiesBuilder();

    final Properties getProperties() {
        buildProperties();
        return (Properties) properties.clone();
    }

    private String getFileBlockName() {
        return block.replace("minecraft:", "");
    }

    private Path getOutputPath(Path destinationDirectory) {
        String fileName = String.format("%s.properties", getFileBlockName());
        return destinationDirectory.resolve(fileName);
    }
}