package de.simbuildings.tilemapper.common;

import java.io.IOException;
import java.util.List;

public interface DocumentDao<T> {

    List<T> findAll() throws IOException;

    void saveAll(List<T> resourcepacks) throws IOException;

}
