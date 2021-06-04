package main_java.controllers.main_window;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import main_java.controllers.canvas.CanvasController;

public class MainWindowController extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        CustomPanelController pane = new CustomPanelController();

        // pane.getChildren().add(new CanvasController());

        Scene scene = new Scene(pane, 800, 600);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
