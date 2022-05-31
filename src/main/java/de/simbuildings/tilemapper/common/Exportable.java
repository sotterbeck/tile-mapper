package de.simbuildings.tilemapper.common;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Set;

public interface Exportable {
    void export(Path destination) throws IOException;

    boolean hasConflict(Path destination);

    Set<Path> conflictFiles(Path destination);
}
