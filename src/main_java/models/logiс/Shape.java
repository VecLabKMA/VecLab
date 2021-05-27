package main_java.models.logiс;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Collection;
import java.util.LinkedList;

public class Shape {
    private Vertex[] vertices;
    private double buffer_x[], buffer_y[];

    private Color fill_color, stroke_color;
    private float border_thickness;


    public Shape(Color fill_color, Color stroke_color, float border_thickness, Vertex... vertices) {
        assert vertices.length >= 2 : "Vertices count must be more than 1!";

        this.fill_color = fill_color;
        this.stroke_color = stroke_color;
        this.border_thickness = border_thickness;

        this.vertices = vertices;

        Build();
    }

    public void Translate(float shift_x, float shift_y) {
        for (Vertex curr : vertices) curr.Translate(shift_x, shift_y);
    }

    public void Rotate(float point_x, float point_y, float radians) {
        for (Vertex curr : vertices) curr.Rotate(point_x, point_y, radians);
    }

    public void Scale(float point_x, float point_y, float factor_x, float factor_y) {
        for (Vertex curr : vertices) curr.Scale(point_x, point_y, factor_x, factor_y);
    }




    void Build() {
        LinkedList<Float> pre_buffer_x = new LinkedList<Float>();
        LinkedList<Float> pre_buffer_y = new LinkedList<Float>();

        for (int i = 0; i < vertices.length - 1; i++)
            Vertex.CreateCurve(vertices[i], vertices[i+1], pre_buffer_x, pre_buffer_y, 30);
        Vertex.CreateCurve(vertices[vertices.length-1], vertices[0], pre_buffer_x, pre_buffer_y, 30);


        buffer_x = new double[pre_buffer_x.size()];
        buffer_y = new double[pre_buffer_y.size()];

        for (int i = 0; i < buffer_x.length; i++) {
            buffer_x[i] = pre_buffer_x.poll();
            buffer_y[i] = pre_buffer_y.poll();
        }
    }

    public void Draw(GraphicsContext gc) {
        gc.setLineWidth(border_thickness);
        gc.setFill(fill_color);
        gc.fillPolygon(buffer_x, buffer_y, buffer_x.length);
        gc.setStroke(stroke_color);
        gc.strokePolygon(buffer_x, buffer_y, buffer_x.length);
    }

    public void GetManipulators(Collection<Manipulator> buffer) {
        for (Vertex curr : vertices) curr.GetManipulators(buffer);
    }
}
