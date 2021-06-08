package main_java.controllers.main_window.tools_panel;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.FlowPane;
import main_java.controllers.main_window.tools_panel.tools_buttons.ToolButtonBaseController;

import java.io.IOException;

public class ToolsPanelController extends FlowPane {
    @FXML
    ToolButtonBaseController drawLine;

    public ToolsPanelController() {
        super();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "/resources/view/main_window/tools_panel/ToolsPanelView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
