package main_java.controllers.main_window.object_panel;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class GeneralManagerPanelController extends GridPane {
    public GeneralManagerPanelController() {
        super();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "/resources/view/main_window/object_panel/GeneralManagerPanelView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public GeneralManagerPanelController(double width, double height) {
        this();
        setWidth(width);
        setHeight(height);
    }
}
