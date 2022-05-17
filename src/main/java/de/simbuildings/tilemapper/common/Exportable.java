package de.simbuildings.tilemapper.common;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Set;

public interface Exportable<T> {
    void export(T destination) throws IOException;

    boolean hasConflict(T destination);

    Set<Path> conflictFiles(T destination);
}
