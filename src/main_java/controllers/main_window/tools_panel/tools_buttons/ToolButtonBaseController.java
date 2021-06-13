package main_java.controllers.main_window.tools_panel.tools_buttons;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

// base class
public class ToolButtonBaseController extends Button {

    private boolean enabled;

    public ToolButtonBaseController() {
        super();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "/resources/view/main_window/tools_panel/ToolButtonBaseView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        getStyleClass().add("toolButton");

        addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (enabled) {
                getStyleClass().clear();
                getStyleClass().add("toolButton");
            } else {
                getStyleClass().add("toolButton--enabled");
            }
            enabled = !enabled;
        });
    }
}
