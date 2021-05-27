package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/sample.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        InputStream iconStream = getClass().getResourceAsStream("/sample/assets/curve-icon.png");
        Image image = new Image(iconStream);
        primaryStage.getIcons().add(image);

        String stylesheet = getClass().getResource("style.css").toExternalForm();
        scene.getStylesheets().add(stylesheet);

        primaryStage.setTitle("Hello World");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
