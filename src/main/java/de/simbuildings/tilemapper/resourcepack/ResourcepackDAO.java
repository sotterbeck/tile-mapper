package de.simbuildings.tilemapper.resourcepack;

import java.io.IOException;
import java.util.List;

public interface ResourcepackDAO {

    List<Resourcepack> findAll() throws IOException;

    void saveAll(List<Resourcepack> resourcepacks) throws IOException;

}
