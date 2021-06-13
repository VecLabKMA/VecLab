package main_java.controllers.main_window.object_panel;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class StrokeStyleManagerPanelController extends GridPane {
    public StrokeStyleManagerPanelController() {
        super();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "/resources/view/main_window/object_panel/StrokeStyleManagerPanelView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public StrokeStyleManagerPanelController(double width, double height) {
        this();
        prefWidth(width);
        prefHeight(height);
    }
}
