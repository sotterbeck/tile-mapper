package de.simbuildings.tilemapper.ctm;

import de.simbuildings.tilemapper.common.Exportable;

import java.nio.file.Path;
import java.util.Map;

public interface CtmExporter extends Exportable<Path> {
    Map<String, String> ctmProperties();
}
