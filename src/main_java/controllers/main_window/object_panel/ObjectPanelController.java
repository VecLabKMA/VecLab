package main_java.controllers.main_window.object_panel;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.layout.GridPane;
import logic.ShapeManager;
import logic.Shape;

import java.io.IOException;
import java.io.Serializable;

public class ObjectPanelController extends TabPane {

    private static final double TAB_HEIGHT = 400;
    private static final double TAB_WIDTH = 400;

    private GridPane mainPanel;
    @FXML
    public ColorManagerPanelController colorManager;
    @FXML
    private Button openCloseButton;
    @FXML
    private LayerManagerPanelController layersController;

    private boolean closed = false;
    @FXML
    private BezierManagerPanelController bezierManager;

    public ObjectPanelController() {
        super();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "/resources/view/main_window/object_panel/ObjectPanelView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
    }

    public void init(GridPane mainPanel) {
        this.mainPanel = mainPanel;
    }

    public void initLayers(ShapeManager sm) {layersController.init(sm);}

    public void update(Shape shape) {
        colorManager.update(shape);
    }
}
