package de.simbuildings.tilemapper.injection;

import dagger.Component;
import de.simbuildings.tilemapper.common.Persistable;
import de.simbuildings.tilemapper.injection.jfx.ApplicationComponent;

import javax.inject.Singleton;
import java.util.Set;

@Component(modules = TileMapperAppModule.class)
@Singleton
public interface TileMapperAppComponent {
    ApplicationComponent.Builder jfxApplication();

    Set<Persistable> persistables();
}
