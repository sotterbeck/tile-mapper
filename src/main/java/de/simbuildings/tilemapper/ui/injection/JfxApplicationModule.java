package de.simbuildings.tilemapper.ui.injection;

import dagger.Module;
import dagger.Provides;
import de.simbuildings.tilemapper.ui.common.FXMLSceneLoader;

import javax.inject.Provider;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

@Module(includes = JfxControllerModule.class)
abstract class JfxApplicationModule {

    @Provides
    @JfxApplicationScope
    static ResourceBundle provideLocaleResourceBundle() {
        return ResourceBundle.getBundle("i18n.locale", Locale.ROOT);
    }

    @Provides
    @JfxApplicationScope
    static FXMLSceneLoader provideFXMLSceneLoader(Map<Class<?>, Provider<Object>> controllers, ResourceBundle resourceBundle) {
        return new FXMLSceneLoader(controllers, resourceBundle);
    }
}
