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

    @Override
    public void start(Stage primaryStage) throws Exception {
        MainWindowPanelController mainPanel = new MainWindowPanelController();
        mainPanel.setPrimaryStage(primaryStage);

        GridPane root = new GridPane();
        root.getChildren().add(mainPanel);

        Scene scene = new Scene(root);

        InputStream iconStream = getClass().getResourceAsStream("/resources/assets/images/bezier-curve-icon.png");
        Image image = new Image(iconStream);
        primaryStage.getIcons().add(image);

        primaryStage.setScene(scene);
        primaryStage.setTitle("VecLab");
        primaryStage.show();

        mainPanel.init();
    }

    public static void main(String[] args) {
        launch(args);
    }


}
