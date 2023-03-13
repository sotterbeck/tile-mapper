package de.simbuildings.tilemapper.core.storage;

import java.nio.file.Path;

interface AppDirectoryProvider {
    Path dataDirectory(String programName);
}
