package main_java.models.canvas;


import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import main_java.controllers.canvas.CanvasController;

import java.util.LinkedList;

public class CanvasModel {
    LinkedList<Shape> shapes;
    CanvasController canvas;

    Manipulator selected;
    LinkedList<Manipulator> manipulators;

    public CanvasModel(CanvasController canvas) {
        shapes = new LinkedList<Shape>();
        manipulators = new LinkedList<Manipulator>();

        this.canvas = canvas;

        canvas.setOnMousePressed(event -> Pressed((float)event.getSceneX(), (float)event.getSceneY()));
        canvas.setOnMouseReleased(event -> Released((float)event.getSceneX(), (float)event.getSceneY()));
        canvas.setOnMouseDragged(event -> Dragged((float)event.getSceneX(), (float)event.getSceneY()));

    }

    private void Pressed(float x, float y) {
        Redraw();

        for (Manipulator curr : manipulators) {
            if (curr.Intersects(x, y)) {
                selected = curr;
                selected.Press(x, y);
            }
        }
    }

    private void Released(float x, float y){
        Redraw();

        selected = null;
    }

    private void Dragged(float x, float y){
        Redraw();

        if (selected != null) selected.Move(x, y);
    }

    private void Redraw(){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        manipulators.clear();
        for (Shape curr : shapes) {
            curr.Build();
            curr.GetManipulators(manipulators);
            curr.Draw(gc);
        }

        for (Manipulator curr : manipulators) curr.Draw(gc);
    }

    public void AddSimple(){
        AddCircle(200, 200, 80, Color.LIGHTCORAL, Color.RED, 5f);
        AddSquare(400, 200, 160, Color.LIGHTBLUE, Color.BLUE, 5f);

        AddEllipse(200, 400, 100, 200, Color.LIGHTGREEN, Color.GREEN, 5f);
        AddRectangle(400, 400, 100, 200, Color.LIGHTYELLOW, Color.YELLOW, 5f);
        Redraw();
    }

    public void AddEllipse(float center_x, float center_y, float width, float height, Color fill_color, Color stroke_color, float border_thickness) {
        final float k = 0.552284749831f;
        Shape figure = new Shape(
                fill_color,
                stroke_color,
                border_thickness,
                new Vertex(1f, 0f, 1f, -k, 1f, k, true),
                new Vertex(0f, -1f, -k, -1f, k, -1f, true),
                new Vertex(-1f, 0f, -1f, k, -1f, -k, true),
                new Vertex(0f, 1f, k, 1f, -k, 1f, true)
        );
        figure.Scale(0f, 0f, width*0.5f, height*0.5f);
        figure.Translate(center_x, center_y);

        shapes.add(figure);
    }

    public void AddRectangle(float center_x, float center_y, float width, float height, Color fill_color, Color stroke_color, float border_thickness) {
        final float k = 0.552284749831f;
        Shape figure = new Shape(
                fill_color,
                stroke_color,
                border_thickness,
                new Vertex(1f, 1f, 1f, 1f, 1f, 1f, false),
                new Vertex(1f, -1f, 1f, -1f, 1f, -1f, false),
                new Vertex(-1f, -1f, -1f, -1f, -1f, -1f, false),
                new Vertex(-1f, 1f, -1f, 1f, -1f, 1f, false)
        );

        figure.Scale(0f, 0f, width*0.5f, height*0.5f);
        figure.Translate(center_x, center_y);

        shapes.add(figure);

        // shapes.add(new Rectangle());
    }

    public void AddCircle(float center_x, float center_y, float radius, Color fill_color, Color stroke_color, float border_thickness) {
        AddEllipse(center_x, center_y, radius*2f, radius*2f, fill_color, stroke_color, border_thickness);
    }

    public void AddSquare(float center_x, float center_y, float size, Color fill_color, Color stroke_color, float border_thickness) {
        AddRectangle(center_x, center_y, size, size, fill_color, stroke_color, border_thickness);
    }
}
