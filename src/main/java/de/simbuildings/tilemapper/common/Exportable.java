package de.simbuildings.tilemapper.common;

import java.io.File;
import java.io.IOException;

public interface Exportable {
    void export(File destination) throws IOException;

    boolean outputExists(File destinationDirectory);
}
