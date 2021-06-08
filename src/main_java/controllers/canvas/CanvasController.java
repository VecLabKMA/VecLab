package main_java.controllers.canvas;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.canvas.Canvas;
import main_java.models.canvas.CanvasModel;

import java.io.IOException;

public class CanvasController extends Canvas {
//    @FXML
//    Label label;
//    @FXML
//    Button button;

    public CanvasController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "/resources/view/canvas/CanvasView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        new CanvasModel(this).AddSimple();
    }
}
