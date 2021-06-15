package main_java.controllers.main_window.tools_panel.parameters_panel;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import main_java.controllers.canvas.CanvasController;

import java.io.IOException;

public class ParametersPanelController extends HBox {
    public ColorPicker fillColorPicker;
    public Rectangle fillColorRect;
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

        init();
        }

    private void init() {
        fillColorRect.setFill(Color.LIGHTBLUE);
        fillColorPicker.setValue(Color.LIGHTBLUE);

        fillColorPicker.setOnAction(e -> {
            fillColorRect.setFill(fillColorPicker.getValue());
            CanvasController.sm.SetFillColor(fillColorPicker.getValue());
        });

        setSpacing(10);
    }
}
