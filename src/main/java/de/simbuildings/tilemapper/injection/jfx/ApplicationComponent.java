package de.simbuildings.tilemapper.injection.jfx;

import dagger.BindsInstance;
import dagger.Subcomponent;
import de.simbuildings.tilemapper.ui.common.FXMLSceneLoader;
import javafx.application.Application;
import javafx.stage.Stage;

import javax.inject.Named;

@Subcomponent(modules = ApplicationModule.class)
@ApplicationScope
public interface ApplicationComponent {

    FXMLSceneLoader fxmlSceneLoader();

    @Named("main")
    Stage mainStage();

    @Subcomponent.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        @BindsInstance
        Builder primaryStage(@PrimaryStage Stage primaryStage);

        ApplicationComponent build();
    }
}
