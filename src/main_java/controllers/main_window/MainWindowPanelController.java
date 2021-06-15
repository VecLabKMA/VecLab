package main_java.controllers.main_window;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import main_java.controllers.canvas.CanvasController;
import main_java.controllers.main_window.menu_bar.MenuBarController;
import main_java.controllers.main_window.object_panel.ObjectPanelController;
import main_java.controllers.main_window.tools_panel.ToolsPanelController;

import java.io.IOException;

public class MainWindowPanelController extends GridPane {
    @FXML
    private Pane canvasWrapper;
    @FXML
    private CanvasController mainCanvas;
    @FXML
    private MenuBarController menuBar;
    @FXML
    private ObjectPanelController objectPanel;
    @FXML
    private ToolsPanelController toolsPanel;

    public MainWindowPanelController() {
        super();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "/resources/view/main_window/MainWindowPanelView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void init() {
        maxWidth(Double.POSITIVE_INFINITY);
        maxHeight(Double.POSITIVE_INFINITY);

        mainCanvas.widthProperty().bind(canvasWrapper.widthProperty());
        mainCanvas.heightProperty().bind(canvasWrapper.heightProperty());

        menuBar.setMainCanvas(mainCanvas);
        objectPanel.init(this);

        mainCanvas.init(toolsPanel);
    }
}
