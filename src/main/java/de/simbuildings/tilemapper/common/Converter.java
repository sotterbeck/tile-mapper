package de.simbuildings.tilemapper.common;

import java.util.Collection;

public interface Converter<T, U> {
    U fromDto(T dto);

    default Collection<U> fromDtos(Collection<T> dtos) {
        return dtos.stream()
                .map(this::fromDto)
                .toList();
    }
}
