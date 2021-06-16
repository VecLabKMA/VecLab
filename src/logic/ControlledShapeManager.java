package logic;

import javafx.scene.canvas.Canvas;
import main_java.controllers.canvas.CanvasController;
import main_java.controllers.main_window.object_panel.ObjectPanelController;
import main_java.controllers.main_window.tools_panel.ToolsPanelController;
import main_java.controllers.main_window.tools_panel.parameters_panel.ParametersPanelController;

public class ControlledShapeManager extends ShapeManager{
    private ToolsPanelController toolsPanel;
    private ObjectPanelController objectPanel;

    public ControlledShapeManager(Canvas canvas) {
        super(canvas);
    }

    public ControlledShapeManager(Canvas canvas, ToolsPanelController toolsPanel, ObjectPanelController objectPanel) {
        super(canvas);
        this.toolsPanel = toolsPanel;
        this.objectPanel = objectPanel;
    }
    public void OnChange() {
        CanvasController.controller.manuallyX = true;
        CanvasController.controller.manuallyY = true;


        if (toolsPanel == null)
            return;

        toolsPanel.currentLayerLabel.setText(ShapeManager.manager.GetCurrentLayer().GetName());
        toolsPanel.currentShapeLayer.setText("-");

        ParametersPanelController parametersPanel = toolsPanel.parametersPanel;
        if (ShapeManager.manager.GetSelectedShapes().length != 1) {
            parametersPanel.heightLabel.setText("-");
            parametersPanel.widthLabel.setText("-");

            parametersPanel.xInput.setText("0.0");
            parametersPanel.yInput.setText("0.0");
            return;
        }

        toolsPanel.currentShapeLayer.setText(ShapeManager.manager.GetRootLayer().GetLayerByShape(ShapeManager.manager.GetSelectedShapes()[0]).GetName());

        parametersPanel.heightLabel.setText(String.valueOf(ShapeManager.manager.GetSelectionHeight()));
        parametersPanel.widthLabel.setText(String.valueOf(ShapeManager.manager.GetSelectionWidth()));

        parametersPanel.xInput.setText(String.valueOf(ShapeManager.manager.GetSelectionCenterX()));
        parametersPanel.yInput.setText(String.valueOf(ShapeManager.manager.GetSelectionCenterY()));

        objectPanel.update(ShapeManager.manager.GetSelectedShapes()[0]);
    }
    @Override
    public void OnManipulatorSelect() {
        if (ShapeManager.manager.GetSelectedManipulator() instanceof Shape.SelectManipulator) {
            if (toolsPanel == null) {
                return;
            }
            toolsPanel.setNoMode();
        }
    }
    @Override
    public void OnManipulatorUnselect() {
    }


}
