package main_java.controllers.main_window.tools_panel.tools_buttons;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import main_java.controllers.main_window.tools_panel.ToolsPanelController;

import java.io.IOException;

// base class
public class ToolButtonBaseController extends Button {

    private boolean enabled = false;
    private String baseStyleClass;

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

        getStyleClass().clear();
        getStyleClass().add("toolButton");
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        getStyleClass().clear();
        getStyleClass().add(baseStyleClass);
        if (enabled)
            getStyleClass().addAll( "toolButton", "toolButton--enabled");
        else {
            getStyleClass().add("toolButton");
        }
    }

    public String getBaseStyleClass() {
        return baseStyleClass;
    }

    public void setBaseStyleClass(String baseStyleClass) {
        this.baseStyleClass = baseStyleClass;
    }
}
