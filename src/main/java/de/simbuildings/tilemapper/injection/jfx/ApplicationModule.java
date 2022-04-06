package de.simbuildings.tilemapper.injection.jfx;

import dagger.Module;
import dagger.Provides;
import de.simbuildings.tilemapper.ui.common.FXMLSceneLoader;

import javax.inject.Provider;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

@Module(includes = {ControllerModule.class, SceneModule.class})
abstract class ApplicationModule {
    @Provides
    @ApplicationScope
    static ResourceBundle provideLocaleResourceBundle() {
        return ResourceBundle.getBundle("i18n.locale", Locale.ROOT);
    }

    @Provides
    @ApplicationScope
    static FXMLSceneLoader provideFXMLSceneLoader(Map<Class<?>, Provider<Object>> controllers, ResourceBundle resourceBundle) {
        return new FXMLSceneLoader(controllers, resourceBundle);
    }
}
