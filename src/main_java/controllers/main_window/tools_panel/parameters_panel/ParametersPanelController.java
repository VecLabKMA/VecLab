package main_java.controllers.main_window.tools_panel.parameters_panel;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class ParametersPanelController extends HBox {
    @FXML
    private Button layerUpButton;
    @FXML
    private Button layerDownButton;
    @FXML
    private Button toTopButton;
    @FXML
    private Button toBottomButton;
    @FXML
    private Button rotateRightButton;
    @FXML
    private Button rotateLeftButton;

    public ParametersPanelController() {
        super();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "/resources/view/main_window/tools_panel/parameters_panel/ParametersPanelView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        setSpacing(10);
    }
}
