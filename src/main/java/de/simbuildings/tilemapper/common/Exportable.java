package de.simbuildings.tilemapper.common;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Set;

public interface Exportable {
    void export(Path destination) throws IOException;

    default boolean hasConflict(Path destination) {
        return !conflictFiles(destination).isEmpty();
    }

    Set<Path> conflictFiles(Path destination);
}
