package de.simbuildings.tilemapper.util;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public class PathUtils {

    private PathUtils() {
    }

    /**
     * Creates a directory by creating all nonexistent parent directories fist.
     *
     * @param path the directory to create
     * @throws NullPointerException if path is null
     * @throws UncheckedIOException if an I/O error occurs
     */
    public static void createDirectories(Path path) {
        Objects.requireNonNull(path);
        try {
            Files.createDirectories(path);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
