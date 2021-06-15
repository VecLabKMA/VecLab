package main_java.controllers.main_window.object_panel;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import logic.Shape;
import main_java.controllers.common.DoubleTextField;

import java.io.IOException;

public class ColorManagerPanelController extends GridPane {
    @FXML
    public ColorPicker borderColorPicker;
    @FXML
    private Rectangle borderColorRect;
    @FXML
    public ColorPicker fillColorPicker;
    @FXML
    private Rectangle fillColorRect;
    @FXML
    public CheckBox shapeFilled;
    @FXML
    public DoubleTextField strokeWidthField;

    public ColorManagerPanelController() {
        super();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "/resources/view/main_window/object_panel/ColorManagerPanelView.fxml"));
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
        setHgap(10);
        setVgap(10);

        borderColorPicker.setValue(Color.BLUE);

        borderColorRect.setFill(borderColorPicker.getValue());

        borderColorPicker.setOnAction(event -> borderColorRect.setFill(borderColorPicker.getValue()));

        fillColorPicker.setValue(Color.YELLOW);

        fillColorRect.setFill(fillColorPicker.getValue());

        fillColorPicker.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                fillColorRect.setFill(fillColorPicker.getValue());
            }
        });
    }

    public void update(Shape shape) {
        if (shape == null) {
            fillColorPicker.setDisable(true);

            borderColorPicker.setDisable(true);

            fillColorRect.setFill(Color.valueOf("f2f2f2"));
            borderColorRect.setFill(Color.valueOf("f2f2f2"));

            shapeFilled.setDisable(true);

            strokeWidthField.setEditable(false);
            strokeWidthField.setFocusTraversable(false);
        } else {
            fillColorPicker.setDisable(false);
            fillColorPicker.setValue(shape.GetFillColor());

            borderColorPicker.setDisable(false);
            borderColorPicker.setValue(shape.GetStrokeColor());

            fillColorRect.setFill(fillColorPicker.getValue());
            borderColorRect.setFill(borderColorPicker.getValue());

            shapeFilled.setDisable(false);

            strokeWidthField.setEditable(true);
            strokeWidthField.setFocusTraversable(true);
            strokeWidthField.setText(String.valueOf(shape.GetBorderThickness()));
        }
    }
}
