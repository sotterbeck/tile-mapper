package de.simbuildings.tilemapper.injection;

import dagger.Component;
import de.simbuildings.tilemapper.injection.jfx.ApplicationComponent;

import javax.inject.Singleton;

@Component(modules = TileMapperAppModule.class)
@Singleton
public interface TileMapperAppComponent {
    ApplicationComponent.Builder JfxApplication();
}
