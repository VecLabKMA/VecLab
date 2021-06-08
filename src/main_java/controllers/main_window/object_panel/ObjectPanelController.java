package main_java.controllers.main_window.object_panel;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import java.io.IOException;

public class ObjectPanelController extends TabPane {

    private static final double TAB_HEIGHT = 400;
    private static final double TAB_WIDTH = 400;

    public ObjectPanelController() {
        super();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "/resources/view/main_window/object_panel/ObjectPanelView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
    }
}
