package org.example.ppcjeuxgraphique;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setResizable(false);
        stage.getIcons().add(new Image(getClass().getResource("/org/example/ppcjeuxgraphique/images/dice.png").toExternalForm()));
        stage.setTitle("The game");
        stage.getIcons().add(new Image("file:src/main/resources/org/example/ppcjeuxgraphique/images/OIP.jpg"));
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}