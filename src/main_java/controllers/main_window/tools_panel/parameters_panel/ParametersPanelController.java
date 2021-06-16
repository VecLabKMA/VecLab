package main_java.controllers.main_window.tools_panel.parameters_panel;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import main_java.controllers.canvas.CanvasController;
import main_java.controllers.common.DoubleTextField;

import java.io.IOException;

public class ParametersPanelController extends HBox {
    @FXML
    public ColorPicker fillColorPicker;
    @FXML
    private Rectangle fillColorRect;
    @FXML
    public ColorPicker strokeColorPicker;
    @FXML
    private Rectangle strokeColorRect;
    @FXML
    public DoubleTextField xInput;
    @FXML
    public DoubleTextField yInput;
    @FXML
    public Label widthLabel;
    @FXML
    public Label heightLabel;


    @FXML
    public Button layerUpButton;
    @FXML
    public Button layerDownButton;
    @FXML
    public Button toTopButton;
    @FXML
    public Button toBottomButton;
    @FXML
    public Button rotateRightButton;
    @FXML
    public Button rotateLeftButton;

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

        strokeColorRect.setFill(Color.BLUE);
        strokeColorPicker.setValue(Color.BLUE);

        strokeColorPicker.setOnAction(e -> {
            strokeColorRect.setFill(strokeColorPicker.getValue());
            CanvasController.sm.SetStrokeColor(strokeColorPicker.getValue());
        });

        setSpacing(10);
    }
}
