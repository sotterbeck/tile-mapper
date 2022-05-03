package de.simbuildings.tilemapper.variations;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.UncheckedIOException;

public class BlockstateExporter {
    private final ObjectMapper objectMapper;
    private final Blockstate blockstate;

    public BlockstateExporter(ObjectMapper objectMapper, Blockstate blockstate) {
        this.objectMapper = objectMapper;
        this.blockstate = blockstate;
    }

    public String toJson() {
        try {
            return objectMapper
                    .writerWithDefaultPrettyPrinter()
                    .writeValueAsString(blockstate);
        } catch (JsonProcessingException e) {
            throw new UncheckedIOException(e);
        }
    }
}
