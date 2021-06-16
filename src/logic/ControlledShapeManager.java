package logic;

import javafx.scene.canvas.Canvas;
import main_java.controllers.canvas.CanvasController;
import main_java.controllers.main_window.object_panel.ObjectPanelController;
import main_java.controllers.main_window.tools_panel.ToolsPanelController;
import main_java.controllers.main_window.tools_panel.parameters_panel.ParametersPanelController;

public class ControlledShapeManager extends ShapeManager{
//    private ToolsPanelController toolsPanel;
//    private ObjectPanelController objectPanel;

    public ControlledShapeManager(Canvas canvas) {
        super(canvas);
    }


    @Override
    public void OnChange() {
        try {
            CanvasController.controller.manuallyX = true;
            CanvasController.controller.manuallyY = true;


            if (CanvasController.toolsPanel == null)
                return;

            CanvasController.toolsPanel.currentLayerLabel.setText(ShapeManager.manager.GetCurrentLayer().GetName());
            CanvasController.toolsPanel.currentShapeLayer.setText("-");

            ParametersPanelController parametersPanel = CanvasController.toolsPanel.parametersPanel;
            if (ShapeManager.manager.GetSelectedShapes().length != 1) {
                parametersPanel.heightLabel.setText("-");
                parametersPanel.widthLabel.setText("-");

                parametersPanel.xInput.setText("0.0");
                parametersPanel.yInput.setText("0.0");
                return;
            }

            if (CanvasController.toolsPanel == null) {
                return;
            }

            CanvasController.toolsPanel.currentShapeLayer.setText(ShapeManager.manager.GetRootLayer().GetLayerByShape(ShapeManager.manager.GetSelectedShapes()[0]).GetName());

            parametersPanel.heightLabel.setText(String.valueOf(ShapeManager.manager.GetSelectionHeight()));
            parametersPanel.widthLabel.setText(String.valueOf(ShapeManager.manager.GetSelectionWidth()));

            parametersPanel.xInput.setText(String.valueOf(ShapeManager.manager.GetSelectionCenterX()));
            parametersPanel.yInput.setText(String.valueOf(ShapeManager.manager.GetSelectionCenterY()));

            CanvasController.objectPanel.update(ShapeManager.manager.GetSelectedShapes()[0]);
        } catch (NullPointerException ex) {

        }
    }
    @Override
    public void OnManipulatorSelect() {
        if (ShapeManager.manager.GetSelectedManipulator() instanceof Shape.SelectManipulator) {
            if (CanvasController.toolsPanel == null) {
                return;
            }
            CanvasController.toolsPanel.setNoMode();
        }
    }
    @Override
    public void OnManipulatorUnselect() {
    }


}
