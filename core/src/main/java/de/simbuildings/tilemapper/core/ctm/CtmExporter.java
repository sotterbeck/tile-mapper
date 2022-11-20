package de.simbuildings.tilemapper.core.ctm;

import de.simbuildings.tilemapper.core.common.Exportable;

import java.util.Map;

public interface CtmExporter extends Exportable {
    Map<String, String> ctmProperties();
}
