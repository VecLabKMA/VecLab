package main_java.controllers.main_window.object_panel;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import logic.ShapeManager;

import java.io.IOException;

public class ObjectPanelController extends AnchorPane {

    private static final double TAB_HEIGHT = 400;
    private static final double TAB_WIDTH = 400;

    @FXML
    private TabPane tabPane;
    @FXML
    private Button openCloseButton;
    @FXML
    private LayerManagerPanelController layersController;
    private GridPane mainPanel;

    private boolean closed = false;

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

        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        setTopAnchor(tabPane, 0.0);
        setRightAnchor(tabPane, 0.0);
        setLeftAnchor(tabPane, 0.0);
        setBottomAnchor(tabPane, 0.0);

        setLeftAnchor(openCloseButton, 0.0);
        setTopAnchor(openCloseButton, 5.0);

        openCloseButton.setTranslateX(-20);

        openCloseButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            openCloseButton.getStyleClass().clear();
            if (closed) {
                openCloseButton.getStyleClass().addAll("button", "openCloseButton", "closeButton");
                getChildren().add(tabPane);
                setWidth(360);
                setMinWidth(360);
                setPrefWidth(360);
            } else {
                openCloseButton.getStyleClass().addAll("button", "openCloseButton", "openButton");
                setWidth(0);
                setMinWidth(0);
                setPrefWidth(0);
                getChildren().remove(tabPane);
            }
            requestFocus();
            closed = !closed;
        });
    }

    public void init(GridPane mainPanel) {
        this.mainPanel = mainPanel;
    }

    public void initLayers(ShapeManager sm) {layersController.init(sm);}
}
