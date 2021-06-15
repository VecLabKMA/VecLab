package main_java.controllers.canvas;

import javafx.scene.canvas.Canvas;
import javafx.scene.input.*;
import javafx.scene.paint.Color;
import logic.*;
import main_java.controllers.main_window.tools_panel.ToolsPanelController;

public class CanvasController extends Canvas {

    public static ShapeManager sm;

    public void init(ToolsPanelController toolsPanel) {
        if (sm == null) {
            sm = new ShapeManager(this) {
                @Override
                public void OnChange() {
                }
                @Override
                public void OnManipulatorSelect() {
                    if (sm.GetSelectedManipulator() instanceof Shape.SelectManipulator) {
                        toolsPanel.setNoMode();
                    }
                }
            };
        }

//        sm.Example();
        sm.SetShowAnchorPoints(false);
        sm.SetShowManipulators(false);
        sm.SetRotationFixed(true);

        toolsPanel.noMode.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            if (toolsPanel.noMode.isEnabled())
                sm.SetDrawingMode(DrawingMode.NO);
        });
        toolsPanel.drawTriangle.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            sm.SetDrawingMode(DrawingMode.NO);
            if (toolsPanel.drawTriangle.isEnabled())
                sm.SetDrawingMode(DrawingMode.TRIANGLE);
        });
        toolsPanel.drawCurve.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            sm.SetDrawingMode(DrawingMode.NO);
            if (toolsPanel.drawCurve.isEnabled()) {
                sm.SetDrawingMode(DrawingMode.PEN);
            }
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

        toolsPanel.showVertexes.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
            if (toolsPanel.showVertexes.isEnabled() && toolsPanel.showAnchorPoints.isEnabled()) {
                sm.SetShowAnchorPoints(false);
                toolsPanel.showAnchorPoints.setEnabled(false);
            }
            sm.SetShowManipulators(!sm.GetShowManipulators());
        });

        toolsPanel.showAnchorPoints.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
            if (!toolsPanel.showAnchorPoints.isEnabled() && !toolsPanel.showVertexes.isEnabled()) {
                sm.SetShowManipulators(true);
                toolsPanel.showVertexes.setEnabled(true);
            }
            sm.SetShowAnchorPoints(!sm.GetShowAnchorPoints());
        });

        toolsPanel.rotationFixed.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            Shape[] currentSelectedShapes = sm.GetSelectedShapes();
            sm.ClearSelection();
            sm.SetRotationFixed(!sm.GetRotationFixed());
            for (Shape sh : currentSelectedShapes) {
                sm.Select(sh);
            }
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
    }
}
