package de.simbuildings.tilemapper.gui.common;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javax.inject.Provider;
import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class FXMLSceneLoader {
    private final Map<Class<?>, Provider<Object>> controllers;
    private final ResourceBundle resources;

    public FXMLSceneLoader(Map<Class<?>, Provider<Object>> controllers, ResourceBundle resources) {
        this.controllers = controllers;
        this.resources = resources;
    }

    private FXMLLoader load(String fxmlLocation) throws IOException {
        URL fxmlResource = getClass().getResource(fxmlLocation);
        FXMLLoader fxmlLoader = new FXMLLoader(fxmlResource, resources, null, this::getControllerFactory);
        fxmlLoader.load();
        return fxmlLoader;
    }

    public Scene createScene(String fxmlLocation) {
        return new Scene(createParent(fxmlLocation));
    }

    public Parent createParent(String fxmlLocation) {
        try {
            FXMLLoader fxmlLoader = load(fxmlLocation);
            return fxmlLoader.getRoot();
        } catch (IOException e) {
            throw new UncheckedLoadException("Loading view %s failed".formatted(fxmlLocation), e);
        }
    }

    private Object getControllerFactory(Class<?> type) {
        if (!controllers.containsKey(type)) {
            throw new IllegalArgumentException("Controller of type %s does not exist".formatted(type.toString()));
        }
        return controllers.get(type).get();
    }
}
