package main_java.controllers.main_window.tools_panel;

import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import main_java.controllers.main_window.tools_panel.tools_buttons.ToolButtonBaseController;

import java.io.IOException;

public class ToolsPanelController extends FlowPane {
    private ToolButtonBaseController currentToolButton;
//    @FXML
//    ToolButtonBaseController drawLine;
//    @FXML
//    ToolButtonBaseController drawCurve;
//    @FXML
//    ToolButtonBaseController drawArrow;
//    @FXML
//    ToolButtonBaseController drawEllipse;
//    @FXML
//    ToolButtonBaseController drawTriangle;
//    @FXML
//    ToolButtonBaseController drawRectangle;
//    @FXML
//    ToolButtonBaseController drawPolygon;
//    @FXML
//    ToolButtonBaseController fillArea;
//    @FXML
//    ToolButtonBaseController selectArea;
//    @FXML
//    ToolButtonBaseController selectObject;
//    @FXML
//    ToolButtonBaseController freeSelect;


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
            ToolButtonBaseController toolButton = (ToolButtonBaseController) child;
            toolButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                toolButton.enable();
                if (currentToolButton != null)
                    currentToolButton.disable();
                if (toolButton != currentToolButton)
                    currentToolButton = toolButton;
                else
                    currentToolButton = null;
            });
        }
    }
}
