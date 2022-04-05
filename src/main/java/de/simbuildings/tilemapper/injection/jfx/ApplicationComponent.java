package de.simbuildings.tilemapper.injection.jfx;

import dagger.BindsInstance;
import dagger.Subcomponent;
import de.simbuildings.tilemapper.ui.common.FXMLSceneLoader;
import javafx.application.Application;
import javafx.stage.Stage;

@Subcomponent(modules = ApplicationModule.class)
@ApplicationScope
public interface ApplicationComponent {

    FXMLSceneLoader fxmlSceneLoader();

    @Subcomponent.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        @BindsInstance
        Builder primaryStage(Stage primaryStage);

        ApplicationComponent build();
    }
}
