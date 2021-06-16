package main_java.controllers.canvas;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.*;
import javafx.scene.paint.Color;
import javafx.util.StringConverter;
import logic.*;
import logic.DrawingMode;
import logic.Layer;
import logic.Shape;
import logic.ShapeManager;
import logic.Vertex;
import main_java.controllers.main_window.object_panel.ObjectPanelController;
import main_java.controllers.main_window.tools_panel.ToolsPanelController;
import main_java.controllers.main_window.tools_panel.parameters_panel.ParametersPanelController;

public class CanvasController extends Canvas {

    public static ShapeManager sm;

    private boolean manuallyX = false;
    private boolean manuallyY = false;


    private ToolsPanelController toolsPanel;
    private ObjectPanelController objectPanel;

    public void reloadShapeManager() {
        if (sm != null) {
            sm.deleteManager();
            sm = null;
            this.init(toolsPanel, objectPanel);
        }
    }

    public void init(ToolsPanelController toolsPanel, ObjectPanelController objectPanel) {
        this.toolsPanel = toolsPanel;
        this.objectPanel = objectPanel;

        if (sm == null) {
            sm = new ShapeManager(this) {
                @Override
                public void OnChange() {
                    manuallyX = true;
                    manuallyY = true;

                    toolsPanel.currentLayerLabel.setText(sm.GetCurrentLayer().GetName());

                    ParametersPanelController parametersPanel = toolsPanel.parametersPanel;
                    if (sm.GetSelectedShapes().length != 1) {
                        parametersPanel.heightLabel.setText("-");
                        parametersPanel.widthLabel.setText("-");

                        parametersPanel.xInput.setText("0.0");
                        parametersPanel.yInput.setText("0.0");
                        return;
                    }

                    parametersPanel.heightLabel.setText(String.valueOf(sm.GetSelectionHeight()));
                    parametersPanel.widthLabel.setText(String.valueOf(sm.GetSelectionWidth()));

                    parametersPanel.xInput.setText(String.valueOf(sm.GetSelectionCenterX()));
                    parametersPanel.yInput.setText(String.valueOf(sm.GetSelectionCenterY()));

                    objectPanel.update(sm.GetSelectedShapes()[0]);
                }
                @Override
                public void OnManipulatorSelect() {
                    if (sm.GetSelectedManipulator() instanceof Shape.SelectManipulator) {
                        toolsPanel.setNoMode();
                    }
                }
                @Override
                public void OnManipulatorUnselect() {
                }
            };
        }

        objectPanel.initLayers(sm);

//        sm.Example();
        sm.SetShowAnchorPoints(false);
        sm.SetShowManipulators(true);
        sm.SetShowManipulators(false);
        sm.SetRotationFixed(false);

        toolsPanel.noMode.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            if (toolsPanel.noMode.isEnabled())
                sm.SetDrawingMode(DrawingMode.NO);
        });
        toolsPanel.drawCurve.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            sm.SetDrawingMode(DrawingMode.NO);
            if (toolsPanel.drawCurve.isEnabled()) {
                sm.SetDrawingMode(DrawingMode.PEN);
            }
        });
        toolsPanel.drawTriangle.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            sm.SetDrawingMode(DrawingMode.NO);
            if (toolsPanel.drawTriangle.isEnabled())
                sm.SetDrawingMode(DrawingMode.TRIANGLE);
        });
        toolsPanel.drawEllipse.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            sm.SetDrawingMode(DrawingMode.NO);
            if (toolsPanel.drawEllipse.isEnabled())
                sm.SetDrawingMode(DrawingMode.ELLIPSE);
        });
        toolsPanel.drawRectangle.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            sm.SetDrawingMode(DrawingMode.NO);
            if (toolsPanel.drawRectangle.isEnabled())
                sm.SetDrawingMode(DrawingMode.RECTANGLE);
        });
        toolsPanel.drawPolygon.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            sm.SetDrawingMode(DrawingMode.NO);
            if (toolsPanel.drawCurve.isEnabled()) {
                sm.SetDrawingMode(DrawingMode.PEN);
            }
        });

        toolsPanel.clearAll.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            toolsPanel.setNoMode();

            for (Shape sh : sm.root_layer.GetShapes()) {
                sm.root_layer.RemoveShape(sh);
            }
            for (Layer l : sm.root_layer.GetLayers()) {
                sm.root_layer.RemoveLayer(l);
            }

            sm.SetCurrentLayer(sm.root_layer);
        });

        toolsPanel.deleteShape.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            for (Shape sh : sm.GetSelectedShapes())
                sm.root_layer.RemoveShape(sh);
        });

        toolsPanel.appendSelection.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            sm.SetAppendSelection(!sm.GetAppendSelection());
        });

        toolsPanel.selectShape.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            toolsPanel.setNoMode();
            sm.SetShowAnchorPoints(false);
            toolsPanel.showAnchorPoints.setEnabled(false);
            sm.SetShowVertices(false);
            toolsPanel.showVertexes.setEnabled(false);
            sm.SetShowManipulators(!sm.GetShowManipulators());
        });

        toolsPanel.showVertexes.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
            if (!toolsPanel.showVertexes.isEnabled()) {
                sm.SetShowManipulators(true);
                toolsPanel.selectShape.setEnabled(true);
            } else if (toolsPanel.showAnchorPoints.isEnabled()) {
                sm.SetShowAnchorPoints(false);
                toolsPanel.showAnchorPoints.setEnabled(false);
            }
            sm.SetShowVertices(!sm.GetShowVertices());
        });

        toolsPanel.showAnchorPoints.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
            if (!toolsPanel.showAnchorPoints.isEnabled() ) {
                if (!toolsPanel.showVertexes.isEnabled()) {
                    sm.SetShowVertices(true);
                    toolsPanel.showVertexes.setEnabled(true);
                }
                sm.SetShowManipulators(true);
                toolsPanel.selectShape.setEnabled(true);
            }
            sm.SetShowAnchorPoints(!sm.GetShowAnchorPoints());
        });

        toolsPanel.rotationFixed.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            Shape[] currentSelectedShapes = sm.GetSelectedShapes();
            sm.ClearSelection();
            sm.SetRotationFixed(!sm.GetRotationFixed());
            toolsPanel.setNoMode();

            for (Shape sh : currentSelectedShapes) {
                sm.Select(sh);
            }
        });

        toolsPanel.selectAll.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            sm.SetAppendSelection(true);
            toolsPanel.appendSelection.setEnabled(true);

            sm.SetShowAnchorPoints(false);
            toolsPanel.showAnchorPoints.setEnabled(false);
            sm.SetShowVertices(false);
            toolsPanel.showVertexes.setEnabled(false);
            sm.SetShowManipulators(true);
            toolsPanel.selectShape.setEnabled(true);

            for (Shape sh : sm.GetCurrentLayer().GetShapes()) {
                sm.Select(sh);
            }
        });

        toolsPanel.parametersPanel.rotateLeftButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            boolean rotationFixed = sm.GetRotationFixed();
            Shape[] currentSelectedShapes = sm.GetSelectedShapes();
            sm.ClearSelection();
            sm.SetRotationFixed(false);
            for (Shape sh : currentSelectedShapes) {
                sm.Select(sh);
                sh.SetTransform(sh.getCenter_x(), sh.getCenter_y(), 0, 0, 1, 1, (float) -Math.PI / 2);
            }
            sm.SetRotationFixed(rotationFixed);
        });
        toolsPanel.parametersPanel.rotateRightButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            boolean rotationFixed = sm.GetRotationFixed();
            Shape[] currentSelectedShapes = sm.GetSelectedShapes();
            sm.ClearSelection();
            sm.SetRotationFixed(false);
            for (Shape sh : currentSelectedShapes) {
                sm.Select(sh);
                sh.SetTransform(sh.getCenter_x(), sh.getCenter_y(), 0, 0, 1, 1, (float) Math.PI / 2);
            }
            sm.SetRotationFixed(rotationFixed);
        });

        toolsPanel.parametersPanel.xInput.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
                if (!manuallyX) {
                    toolsPanel.parametersPanel.xInput.setText(String.valueOf(sm.GetSelectionCenterX()));
                    manuallyX = true;
                } else {
                    sm.center_manipulator.Move(Float.parseFloat(toolsPanel.parametersPanel.xInput.getText()), sm.GetSelectionCenterY());
                    sm.Redraw();
                }
            }
        });

        toolsPanel.parametersPanel.xInput.setOnAction(e -> {
            requestFocus();
        });


        toolsPanel.parametersPanel.yInput.focusedProperty().addListener(new ChangeListener<Boolean>()
        {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
            {
                if (!newPropertyValue) {
                    if (!manuallyY) {
                        toolsPanel.parametersPanel.xInput.setText(String.valueOf(sm.GetSelectionCenterX()));
                        manuallyY = false;
                    } else {
                        sm.center_manipulator.Move(sm.GetSelectionCenterX(), Float.parseFloat(toolsPanel.parametersPanel.yInput.getText()));
                        sm.Redraw();
                    }
                }
            }
        });

        toolsPanel.parametersPanel.yInput.setOnAction(e -> {
            requestFocus();
        });

        toolsPanel.parametersPanel.toBottomButton.setOnAction(e -> {
            for (Shape sh : sm.GetSelectedShapes())
                sm.GetCurrentLayer().MoveTop(sh);
            sm.Redraw();
        });
        toolsPanel.parametersPanel.toTopButton.setOnAction(e -> {
            for (Shape sh : sm.GetSelectedShapes())
                sm.GetCurrentLayer().MoveBottom(sh);
            sm.Redraw();
        });
        toolsPanel.parametersPanel.layerDownButton.setOnAction(e -> {
            for (Shape sh : sm.GetSelectedShapes())
                sm.GetCurrentLayer().MoveUp(sh);
            sm.Redraw();
        });
        toolsPanel.parametersPanel.layerUpButton.setOnAction(e -> {
            for (Shape sh : sm.GetSelectedShapes())
                sm.GetCurrentLayer().MoveDown(sh);
            sm.Redraw();
        });

//        getScene().getAccelerators().put(
//                new KeyCodeCombination(KeyCode.A, KeyCombination.CONTROL_DOWN),
//                () -> {
//                    boolean turned_on = sm.GetAppendSelection();
//
//                    sm.SetAppendSelection(true);
//                    for (Shape sh : sm.root_layer.GetShapes()) {
//                        sm.Select(sh);
//                    }
//
//
//                    sm.SetAppendSelection(turned_on);
//                }
//        );

        getScene().setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ALT) { sm.SetRotationFixed(true); }
            if (e.getCode() == KeyCode.CONTROL) { sm.SetAppendSelection(true); }

            if (e.getCode() == KeyCode.V) {
                if (sm.GetShowAnchorPoints())
                    sm.SetShowAnchorPoints(false);
                sm.SetShowManipulators(!sm.GetShowManipulators());
            }
            if (e.getCode() == KeyCode.B) {
                if (!sm.GetShowManipulators())
                    sm.SetShowManipulators(true);
                sm.SetShowAnchorPoints(!sm.GetShowAnchorPoints());
            }

            if (e.getCode() == KeyCode.DIGIT0) { sm.SetDrawingMode(DrawingMode.NO); }
            if (e.getCode() == KeyCode.DIGIT1) { sm.SetDrawingMode(DrawingMode.PEN); }
            if (e.getCode() == KeyCode.DIGIT2) { sm.SetDrawingMode(DrawingMode.RECTANGLE); }
            if (e.getCode() == KeyCode.DIGIT3) { sm.SetDrawingMode(DrawingMode.ELLIPSE); }

            if (e.getCode() == KeyCode.ESCAPE) {
                sm.ClearSelection();
//                System.out.println("Cleared");
            }

            if (e.getCode() == KeyCode.R) {
                sm.SetBorderThickness(4f);
                sm.SetStrokeColor(Color.DARKGREEN);
                sm.SetFillColor(Color.GREEN);
            }

            if (e.getCode() == KeyCode.DELETE) {
                Shape shapes[] = sm.GetSelectedShapes();
                for (Shape curr : shapes) {
                    sm.root_layer.RemoveShape(curr);
                }

                if (sm.GetSelectedManipulator() instanceof Vertex.CurrManipulator) {
                    Vertex deleted = ((Vertex.CurrManipulator) sm.GetSelectedManipulator()).GetVertex();
                    deleted.GetShape().RemoveVertex(deleted);
                }
            }

            if (e.getCode() == KeyCode.S) {
                if (sm.GetSelectedManipulator() instanceof Vertex.CurrManipulator) {
                    Vertex symmetred = ((Vertex.CurrManipulator) sm.GetSelectedManipulator()).GetVertex();
                    symmetred.SetSymmetric(!symmetred.GetSymmetric());
                }
            }

            if (e.getCode() == KeyCode.F) {
                Shape shapes[] = sm.GetSelectedShapes();
                for (Shape curr : shapes) {
                    curr.SetFilled(!curr.GetFilled());
                }
            }

        });

        this.getScene().setOnKeyReleased(e -> {
            if (e.getCode() == KeyCode.ALT) { sm.SetRotationFixed(false); }
            if (e.getCode() == KeyCode.CONTROL) { sm.SetAppendSelection(false); }

        });


        this.setOnMousePressed(e -> {
            if (e.getButton() == MouseButton.SECONDARY) {
                if (sm.GetSelectedShapes().length == 1)
                    sm.GetSelectedShapes()[0].InsertClose((float)e.getX(), (float)e.getY());
            }

            if (e.getButton() == MouseButton.PRIMARY) {
                sm.OnPressed((float)e.getX(), (float)e.getY());
            }
        });

        this.setOnMouseDragged(e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                sm.OnDragged((float)e.getX(), (float)e.getY());
            }
        });

        this.setOnMouseReleased(e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                sm.OnReleased((float)e.getX(), (float)e.getY());
            }
        });


        initObjectPanel(objectPanel);
    }

    private void initObjectPanel(ObjectPanelController objectPanel) {
        objectPanel.colorManager.fillColorPicker.setOnAction(e -> {
            for (Shape sh : sm.GetSelectedShapes()) {
                sh.SetFillColor(objectPanel.colorManager.fillColorPicker.getValue());
            }
        });
        objectPanel.colorManager.borderColorPicker.setOnAction(e -> {
            for (Shape sh : sm.GetSelectedShapes()) {
                sh.SetStrokeColor(objectPanel.colorManager.borderColorPicker.getValue());
            }
        });
        objectPanel.colorManager.shapeFilled.setOnAction(e -> {
            for (Shape sh : sm.GetSelectedShapes()) {
                sh.SetFilled(objectPanel.colorManager.shapeFilled.isSelected());
            }
        });
        objectPanel.colorManager.strokeWidthField.setOnAction(e -> {
            for (Shape sh : sm.GetSelectedShapes()) {
                sh.SetBorderThickness(Float.parseFloat(objectPanel.colorManager.strokeWidthField.getText()));
            }
        });

    }
}
