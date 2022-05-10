package de.simbuildings.tilemapper.variations.blockstate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.UncheckedIOException;

public class BlockStateExporter {
    private final ObjectMapper objectMapper;
    private final BlockState blockstate;

    public BlockStateExporter(ObjectMapper objectMapper, BlockState blockstate) {
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
