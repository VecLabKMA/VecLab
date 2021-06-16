package main_java.controllers.main_window.tools_panel;

import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import logic.DrawingMode;
import main_java.controllers.canvas.CanvasController;
import main_java.controllers.main_window.tools_panel.parameters_panel.ParametersPanelController;
import main_java.controllers.main_window.tools_panel.tools_buttons.ToolButtonBaseController;

import java.io.IOException;

public class ToolsPanelController extends FlowPane {
    private ToolButtonBaseController currentToolButton;
    @FXML
    public ToolButtonBaseController noMode;
    @FXML
    public ToolButtonBaseController drawCurve;
    @FXML
    public ToolButtonBaseController drawArrow;
    @FXML
    public ToolButtonBaseController drawEllipse;
    @FXML
    public ToolButtonBaseController drawTriangle;
    @FXML
    public ToolButtonBaseController drawRectangle;
    @FXML
    public ToolButtonBaseController drawPolygon;
    @FXML
    public Button clearAll;
    @FXML
    public Button deleteShape;
    @FXML
    public ToolButtonBaseController selectShape;
    @FXML
    public ToolButtonBaseController appendSelection;
    @FXML
    public ToolButtonBaseController showVertexes;
    @FXML
    public ToolButtonBaseController showAnchorPoints;
    @FXML
    public ParametersPanelController parametersPanel;
    @FXML
    public Label currentLayerLabel;
    @FXML
    public ToolButtonBaseController rotationFixed;
    @FXML
    public Button selectAll;

    public ToolsPanelController() {
        super();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "/resources/view/main_window/tools_panel/ToolsPanelView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        setHgap(5);
        setVgap(5);
        setRowValignment(VPos.CENTER);
        setAlignment(Pos.CENTER_LEFT);

        addEventHandlers();
    }

    private void addEventHandlers() {
        for (Node child : getChildren()) {
            if (child.getClass() != ToolButtonBaseController.class)
                continue;
            ToolButtonBaseController toolButton = (ToolButtonBaseController) child;

            if (child.getStyleClass().contains("modeButton")) {
                toolButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                    toolButton.setEnabled(true);

                    if (currentToolButton != null)
                        currentToolButton.setEnabled(false);

                    if (toolButton != currentToolButton) {
                        currentToolButton = toolButton;
                    } else {
                        currentToolButton = noMode;
                        noMode.setEnabled(true);
                        CanvasController.sm.SetDrawingMode(DrawingMode.NO);
                    }
                });
            } else {
                toolButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                    toolButton.setEnabled(!toolButton.isEnabled());
                    toolButton.getStyleClass().add("optionButton");
                });
            }
        }
        currentToolButton = noMode;
        noMode.setEnabled(true);
    }

    public void setNoMode() {
        CanvasController.sm.SetDrawingMode(DrawingMode.NO);
        currentToolButton.setEnabled(false);
        currentToolButton = noMode;
        noMode.setEnabled(true);

    }
}
