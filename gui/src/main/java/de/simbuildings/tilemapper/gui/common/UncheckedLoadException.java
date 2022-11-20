package de.simbuildings.tilemapper.gui.common;

/**
 * Wraps an {@link javafx.fxml.LoadException} with an unchecked exception.
 */
public class UncheckedLoadException extends RuntimeException {
    public UncheckedLoadException(Throwable cause) {
        super(cause);
    }

    public UncheckedLoadException(String message, Throwable cause) {
        super(message, cause);
    }
}
