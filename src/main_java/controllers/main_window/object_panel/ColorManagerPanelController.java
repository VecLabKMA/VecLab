package main_java.controllers.main_window.object_panel;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.IOException;

public class ColorManagerPanelController extends GridPane {
    @FXML
    private ColorPicker borderColorPicker;
    @FXML
    private Rectangle borderColorRect;
    @FXML
    private ColorPicker fillColorPicker;
    @FXML
    private Rectangle fillColorRect;

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
        setHgap(20);
        setVgap(10);

        borderColorPicker.setValue(Color.BLUE);

        borderColorRect.setFill(borderColorPicker.getValue());

        borderColorPicker.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                borderColorRect.setFill(borderColorPicker.getValue());
            }
        });

        fillColorPicker.setValue(Color.YELLOW);

        fillColorRect.setFill(fillColorPicker.getValue());

        fillColorPicker.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                fillColorRect.setFill(fillColorPicker.getValue());
            }
        });
    }
}
