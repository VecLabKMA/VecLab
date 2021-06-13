package main_java.controllers.canvas;

import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import logic.DrawingMode;
import logic.Shape;
import logic.ShapeManager;
import logic.Vertex;

public class CanvasController extends Canvas {

    public static ShapeManager sm;

    public void init() {
        if (sm == null) {
            sm = new ShapeManager(this) {
                @Override
                public void OnManipulatorSelect() {
                    System.out.println("Manipulator selected");
                }

                @Override
                public void OnManipulatorUnselect() {
                    System.out.println("Manipulator unselected");
                }
            };
        }

        sm.Example();

        this.getScene().setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ALT) { sm.SetRotationFixed(true); }
            if (e.getCode() == KeyCode.CONTROL) { sm.SetAppendSelection(true); }

            if (e.getCode() == KeyCode.V) { sm.SetShowVertex(!sm.GetShowVertex()); }
            if (e.getCode() == KeyCode.A) { sm.SetShowAnchorPoints(!sm.GetShowAnchorPoints()); }

            if (e.getCode() == KeyCode.DIGIT0) { sm.SetDrawingMode(DrawingMode.NO); }
            if (e.getCode() == KeyCode.DIGIT1) { sm.SetDrawingMode(DrawingMode.PEN); }
            if (e.getCode() == KeyCode.DIGIT2) { sm.SetDrawingMode(DrawingMode.RECTANGLE); }
            if (e.getCode() == KeyCode.DIGIT3) { sm.SetDrawingMode(DrawingMode.ELLIPSE); }

            if (e.getCode() == KeyCode.E) {
                sm.ClearSelection();
                System.out.println("Cleared");
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
