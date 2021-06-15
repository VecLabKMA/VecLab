package main_java.controllers.main_window;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import main_java.controllers.canvas.CanvasController;
import main_java.controllers.main_window.menu_bar.MenuBarController;
import main_java.controllers.main_window.object_panel.ObjectPanelController;
import main_java.controllers.main_window.tools_panel.ToolsPanelController;
import main_java.controllers.main_window.status_bar.StatusBarController;

import java.io.IOException;

public class MainWindowPanelController extends GridPane {
    @FXML
    private ScrollPane canvasWrapper;
    @FXML
    public CanvasController mainCanvas;
    @FXML
    private MenuBarController menuBar;
    @FXML
    private ObjectPanelController objectPanel;
    @FXML
    public ToolsPanelController toolsPanel;
    @FXML
    private StatusBarController statusBar;
    @FXML
    private Button openCloseButton;
    @FXML
    private AnchorPane toolsPanelButtonWrapper;

    private boolean closed = false;

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

        canvasWrapper = new ScrollPane(mainCanvas);
        canvasWrapper.setPannable(true);

        menuBar.setMainCanvas(mainCanvas);
        menuBar.init(this);

        objectPanel.init(this);
        objectPanel.update(null);

        statusBar.init(this);
        statusBar.getCanvas(mainCanvas);

        mainCanvas.init(toolsPanel, objectPanel);

        AnchorPane.setTopAnchor(toolsPanel, 0.0);
        AnchorPane.setRightAnchor(toolsPanel, 0.0);
        AnchorPane.setLeftAnchor(toolsPanel, 0.0);
//        AnchorPane.setBottomAnchor(toolsPanel, 0.0);

        AnchorPane.setRightAnchor(openCloseButton, 5.0);
        AnchorPane.setBottomAnchor(openCloseButton, 5.0);

        openCloseButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            openCloseButton.getStyleClass().clear();
            if (closed) {
                openCloseButton.getStyleClass().addAll("button", "openCloseButton", "closeButton");
                setMinWidth(360);
                setPrefWidth(360);
                getChildren().add(objectPanel);
            } else {
                openCloseButton.getStyleClass().addAll("button", "openCloseButton", "openButton");
                setMinWidth(0);
                setPrefWidth(0);
                getChildren().remove(objectPanel);
            }
            requestFocus();
            closed = !closed;
        });
    }
}
