package main_java.controllers.main_window;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import main_java.controllers.canvas.CanvasController;
import main_java.models.canvas.CanvasModel;
import main_java.models.logiс.ShapeManager;

import java.awt.event.ActionEvent;
import java.io.IOException;

public class CustomPanelController extends GridPane {
    @FXML
    CanvasController main_canvas;

    public CustomPanelController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "/resources/view/main_window/CustomPanelView.fxml"));
         fxmlLoader.setRoot(this);
          fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        new ShapeManager(main_canvas).AddSimple();

    }
}
