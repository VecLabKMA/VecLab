package logic;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import sample.Controller;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

public class Shape {
    private ArrayList<Vertex> vertices;
    private double buffer_x[], buffer_y[];

    private Color stroke_color, fill_color;
    private float border_thickness;

    float center_x, center_y, width, height;

    private boolean filled;


    public class SelectManipulator implements Manipulator {
        @Override
        public void Press(float x, float y) {
            ShapeManager.manager.Select(Shape.this);
        }

        @Override
        public boolean Intersects(float x, float y) {
            return (center_x - x)*(center_x - x) + (center_y - y)*(center_y - y) < 10f*10f;
        }

        @Override
        public void Draw(GraphicsContext gc) {
            gc.setFill(Color.CHOCOLATE);
            gc.fillOval(center_x - 10f, center_y - 10f, 2f*10f, 2f*10f);
        }

        public Shape GetShape(){
            return Shape.this;
        }
    }

    public final SelectManipulator select_manipulator = new SelectManipulator();

    Shape(Color stroke_color, Color fill_color, float border_thickness, boolean filled, Vertex... vert) {
        this.stroke_color = stroke_color;
        this.fill_color = fill_color;
        this.border_thickness = border_thickness;
        this.filled = filled;

        vertices = new ArrayList<Vertex>();

        for (Vertex curr : vert) {
            curr.shape = this;
            vertices.add(curr);
        }

        Build();
    }



    /**Sets transform of this shape*/
    public final void SetTransform(float origin_x, float origin_y, float shift_x, float shift_y, float scale_x, float scale_y, float angle) {
        float cos = (float)Math.cos(angle), sin = (float)Math.sin(angle);
        for (Vertex curr : vertices) curr.Transform(origin_x, origin_y, shift_x, shift_y, scale_x, scale_y, cos, sin);
        Build();
    }

    /**Inserts new vertex in this shape in position, which is more closest to (x, y)*/
    public final void InsertClose(float x, float y) {
        int index = 0;
        float min_dist = Float.POSITIVE_INFINITY;
        float min_t = 0f;



        for (int i = 0; i < vertices.size() - 1; i++){
            Vertex prev = vertices.get(i), next = vertices.get(i+1);
            float curr_t = Vertex.GetClosestT(prev, next, x, y);
            float curr_dist = Vertex.GetDistance(prev, next, x, y);

            if (curr_dist < min_dist) {
                min_dist = curr_dist;
                min_t = curr_t;
                index = i+1;
            }
        }

        if (filled) {
            Vertex prev = vertices.get(vertices.size()-1), next = vertices.get(0);
            float curr_t = Vertex.GetClosestT(prev, next, x, y);
            float curr_dist = Vertex.GetDistance(prev, next, x, y);

            if (curr_dist < min_dist) {
                min_dist = curr_dist;
                min_t = curr_t;
                index = vertices.size();
            }
        }

        Vertex prev = vertices.get(index-1);
        Vertex curr = new Vertex();
        Vertex next = (index >= vertices.size())? vertices.get(0) : vertices.get(index);

        Vertex.SplitCurve(prev, curr, next, min_t);

        Insert(curr, index);
    }

    /**Removes given vertex from this shape if it contains it*/
    public final void RemoveVertex(Vertex vertex) {
        if (vertices.size() <= 2) return;
        if (vertices.remove(vertex)) {
            Build();
        }
    }

    /**Returns array from vertices of this shape*/
    public final Vertex[] GetVertices(){
        return vertices.toArray(new Vertex[0]);
    }

    /**Converts filled shape to path (if filled == false) and path to filled shape (if filled == true)*/
    public final void SetFilled(boolean filled) {
        this.filled = filled;
        Build();
    }

    /**Returns true if this shape is filled shape and false if it is a path*/
    public final boolean GetFilled() {
        return filled;
    }

    public final void SetFillColor(Color color) {
        fill_color = color;
        ShapeManager.manager.Redraw();
    }

    public final void SetStrokeColor(Color color) {
        stroke_color = color;
        ShapeManager.manager.Redraw();
    }

    public final void SetBorderThickness(float thickness) {
        border_thickness = thickness;
        ShapeManager.manager.Redraw();
    }

    public final Color GetFillColor() {
        return fill_color;
    }

    public final Color GetStrokeColor() {
        return stroke_color;
    }

    public final float GetBorderThickness() {
        return border_thickness;
    }

    /**Inserts new vertex in given position*/
    final void Insert(Vertex vertex, int position) {
        if (position < 0) position = 0;
        if (position > vertices.size()) position = vertices.size();

        vertex.shape = this;
        vertices.add(position, vertex);
    }

    /**Adds vertex in the end of this shape*/
    final void PushBack(Vertex vertex){
        vertex.shape = this;
        vertices.add(vertex);
    }


    final void Build() {
        LinkedList<Float> pre_buffer_x = new LinkedList<Float>();
        LinkedList<Float> pre_buffer_y = new LinkedList<Float>();

        for (int i = 0; i < vertices.size() - 1; i++)
            Vertex.CreateCurve(vertices.get(i), vertices.get(i+1), pre_buffer_x, pre_buffer_y);
        if (filled && vertices.size() >= 2)
            Vertex.CreateCurve(vertices.get(vertices.size() - 1), vertices.get(0), pre_buffer_x, pre_buffer_y);


        buffer_x = new double[pre_buffer_x.size()];
        buffer_y = new double[pre_buffer_y.size()];


        float right, left, up, down;

        if (buffer_x.length > 0){
            buffer_x[0] = pre_buffer_x.poll();
            buffer_y[0] = pre_buffer_y.poll();

            right = left = (float)buffer_x[0];
            up = down = (float)buffer_y[0];
        } else {
            right = left =  vertices.get(0).GetCurrX();
            up = down = vertices.get(0).GetCurrY();
        }



        for (int i = 1; i < buffer_x.length; i++) {
            float x = pre_buffer_x.poll(), y = pre_buffer_y.poll();

            if (x > right) right = x;
            if (x < left) left = x;
            if (y > up) up = y;
            if (y < down) down = y;

            buffer_x[i] = x;
            buffer_y[i] = y;
        }

        center_x = (right+left)*0.5f;
        center_y = (up+down)*0.5f;
        width = right - left;
        height = up - down;

        ShapeManager.manager.Redraw();
    }


    final void Draw(GraphicsContext gc){
        if (filled) {
            gc.setFill(fill_color);
            gc.fillPolygon(buffer_x, buffer_y, buffer_x.length);
            gc.setLineWidth(border_thickness);
            gc.setStroke(stroke_color);
            gc.strokePolygon(buffer_x, buffer_y, buffer_x.length);
        } else {
            gc.setLineWidth(border_thickness);
            gc.setStroke(stroke_color);
            gc.strokePolyline(buffer_x, buffer_y, buffer_x.length);
        }

        //float half_width = Math.abs(corner_x - center_x), half_height = Math.abs(corner_y - center_y);
        //gc.setStroke(Color.BLACK);
        //gc.strokeRect(center_x - half_width, center_y - half_height, 2f*half_width, 2f*half_height);
    }

    final void Draw(GraphicsContext gc, float origin_x, float origin_y, float shift_x, float shift_y, float scale_x, float scale_y, float angle) {
        float cos = (float)Math.cos(angle), sin = (float)Math.sin(angle);
        double[] transformed_buffer_x = new double[buffer_x.length];
        double[] transformed_buffer_y = new double[buffer_y.length];

        for (int i = 0; i < buffer_x.length; i++) {
            transformed_buffer_x[i] = Vertex.TransformX((float)buffer_x[i], (float)buffer_y[i], origin_x, origin_y, shift_x, shift_y, scale_x, scale_y, cos, sin);
            transformed_buffer_y[i] = Vertex.TransformY((float)buffer_x[i], (float)buffer_y[i], origin_x, origin_y, shift_x, shift_y, scale_x, scale_y, cos, sin);
        }

        if (filled) {
            gc.setFill(fill_color);
            gc.fillPolygon(transformed_buffer_x, transformed_buffer_y, transformed_buffer_x.length);
            gc.setLineWidth(border_thickness);
            gc.setStroke(stroke_color);
            gc.strokePolygon(transformed_buffer_x, transformed_buffer_y, transformed_buffer_x.length);
        } else {
            gc.setLineWidth(border_thickness);
            gc.setStroke(stroke_color);
            gc.strokePolyline(transformed_buffer_x, transformed_buffer_y, transformed_buffer_x.length);
        }




        double bound_buffer_x[] = new double[] {center_x + width*0.5f, center_x - width*0.5f, center_x - width*0.5f, center_x + width*0.5f};
        double bound_buffer_y[] = new double[] {center_y + height*0.5f, center_y + height*0.5f, center_y - height*0.5f, center_y - height*0.5f};

        for (int i = 0; i < 4; i++) {
            float next_x = Vertex.TransformX((float)bound_buffer_x[i], (float)bound_buffer_y[i], origin_x, origin_y, shift_x, shift_y, scale_x, scale_y, cos, sin);
            float next_y = Vertex.TransformY((float)bound_buffer_x[i], (float)bound_buffer_y[i], origin_x, origin_y, shift_x, shift_y, scale_x, scale_y, cos, sin);
            bound_buffer_x[i] = next_x;
            bound_buffer_y[i] = next_y;
        }

        gc.setLineWidth(1.5);
        gc.setStroke(Color.LIGHTBLUE);
        gc.strokePolygon(bound_buffer_x, bound_buffer_y, 4);
    }


    final void GetManipulators(Collection<Manipulator> buffer, boolean show_vertex, boolean show_anchor_points) {
        for (Vertex curr : vertices) {

            if (!show_vertex) continue;
            if (show_anchor_points) {
                buffer.add(curr.next);
                buffer.add(curr.prev);
                buffer.add(curr.curr);
            } else {
                buffer.add(curr.curr);
            }
        }

        buffer.add(select_manipulator);
    }
}
