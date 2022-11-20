package de.simbuildings.tilemapper.gui.injection;

import dagger.Component;
import de.simbuildings.tilemapper.core.common.Persistable;
import de.simbuildings.tilemapper.gui.injection.jfx.ApplicationComponent;

import javax.inject.Singleton;
import java.util.Set;

@Component(modules = TileMapperAppModule.class)
@Singleton
public interface TileMapperAppComponent {
    ApplicationComponent.Builder jfxApplication();

    Set<Persistable> persistables();
}
