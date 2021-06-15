package main_java.controllers.main_window;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import main_java.controllers.canvas.CanvasController;
import main_java.controllers.main_window.menu_bar.MenuBarController;
import main_java.controllers.main_window.object_panel.ObjectPanelController;
import main_java.controllers.main_window.status_bar.StatusBarController;

import java.io.IOException;

public class MainWindowPanelController extends GridPane {
    @FXML
    private ScrollPane canvasWrapper;
    @FXML
    private CanvasController mainCanvas;
    @FXML
    private MenuBarController menuBar;
    @FXML
    private ObjectPanelController objectPanel;
    @FXML
    private StatusBarController statusBar;

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

        maxWidth(Double.POSITIVE_INFINITY);
        maxHeight(Double.POSITIVE_INFINITY);


        canvasWrapper = new ScrollPane(mainCanvas);
        canvasWrapper.setPannable(true);


        menuBar.setMainCanvas(mainCanvas);
        objectPanel.init(this);
        statusBar.init(this);
        statusBar.getCanvas(mainCanvas);
    }

    public void init() {
        mainCanvas.init();
    }

}
