package main_java.controllers.main_window;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.InputStream;

public class MainWindowController extends Application {

    private static final int MAX_WINDOW_HEIGHT = 540;
    private static final int MAX_WINDOW_WIDTH = 540;

    @Override
    public void start(Stage primaryStage) throws Exception {
        MainWindowPanelController mainPanel = new MainWindowPanelController();
        Scene scene = new Scene(mainPanel);

        primaryStage.setMinHeight(MAX_WINDOW_HEIGHT);
        primaryStage.setMinWidth(MAX_WINDOW_WIDTH);

        InputStream iconStream = getClass().getResourceAsStream("/sample/assets/bezier-curve-icon.png");
        Image image = new Image(iconStream);
        primaryStage.getIcons().add(image);

        primaryStage.setScene(scene);
        primaryStage.setTitle("VecLab");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
