package com.projectsolution.hierarchicalstruct;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * main class
 */
public class HelloApplication extends Application {
    /**
     * @param stage on start
     * @throws IOException for files
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("scene-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Разработчик иерархической структуры работ по проекту");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args from console
     */
    public static void main(String[] args) {
        launch();
    }
}