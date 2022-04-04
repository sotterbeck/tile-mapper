package de.simbuildings.tilemapper.ui.injection;

import dagger.Component;

import javax.inject.Singleton;

@Component(modules = TileMapperAppModule.class)
@Singleton
public interface TileMapperAppComponent {
    JfxApplicationComponent.Builder JfxApplication();
}
