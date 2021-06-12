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

        InputStream iconStream = getClass().getResourceAsStream("/sample/assets/bezier-curve-icon.png");
        Image image = new Image(iconStream);
        primaryStage.getIcons().add(image);

        primaryStage.widthProperty().addListener((obs, oldVal, newVal) -> {
            System.out.println(mainPanel.getWidth());
        });

        primaryStage.heightProperty().addListener((obs, oldVal, newVal) -> {
            // Do whatever you want
        });
        primaryStage.setScene(scene);
        primaryStage.setTitle("VecLab");
        primaryStage.show();


    }

    public static void main(String[] args) {
        launch(args);
    }


}
