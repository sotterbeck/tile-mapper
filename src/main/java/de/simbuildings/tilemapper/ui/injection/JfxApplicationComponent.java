package de.simbuildings.tilemapper.ui.injection;

import dagger.BindsInstance;
import dagger.Subcomponent;
import de.simbuildings.tilemapper.ui.common.FXMLSceneLoader;
import javafx.application.Application;
import javafx.stage.Stage;

@Subcomponent(modules = JfxApplicationModule.class)
@JfxApplicationScope
public interface JfxApplicationComponent {

    FXMLSceneLoader fxmlSceneLoader();

    @Subcomponent.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        @BindsInstance
        Builder primaryStage(Stage primaryStage);

        JfxApplicationComponent build();
    }
}
