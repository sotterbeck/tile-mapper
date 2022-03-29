package de.simbuildings.tilemapper.ui;

import dagger.Component;
import de.simbuildings.tilemapper.ui.resourcepack.ResourcepackModel;
import de.simbuildings.tilemapper.ui.resourcepack.ResourcepackModule;

import javax.inject.Named;
import javax.inject.Provider;
import javax.inject.Singleton;
import java.util.Map;

@Component(modules = {
        ControllerModule.class,
        ResourcepackModule.class
})
@Singleton
public interface AppComponent {
    @Named("Controllers")
    Map<Class<?>, Provider<Object>> controllers();

    ResourcepackModel resourcepackModel();
}
