package de.simbuildings.tilemapper.ctm;

import de.simbuildings.tilemapper.common.Exportable;

import java.util.Map;

public interface CtmExporter extends Exportable {
    Map<String, String> ctmProperties();
}
