package logic;


import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.LinkedList;


public class ShapeManager {
    static ShapeManager manager;

    public final Layer root_layer = new Layer("root");
    private Layer current_layer = root_layer;

    private final ArrayList<Manipulator> manipulators = new ArrayList<Manipulator>();
    private final ArrayList<Shape> selected_shapes = new ArrayList<Shape>();
    private Canvas canvas;

    private Manipulator selected;


    private float pos_x, pos_y, prev_pos_x, prev_pos_y;
    private float cor_x, cor_y, prev_cor_x, prev_cor_y;


    private Shape pen_shape;


    //Drawing parameters
    private Color current_fill_color = Color.LIGHTBLUE;
    private Color current_stroke_color = Color.BLUE;
    private float current_border_thickness = 2f;
    private boolean append_selection = true;
    private boolean fixed_rotation = false;
    private boolean show_vertex = true;
    private boolean show_anchor_points = true;
    private DrawingMode drawing_mode = DrawingMode.NO;




    public class CenterManipulator implements Manipulator {
        @Override
        public void Move(float x, float y) {
            cor_x += x - pos_x;
            cor_y += y - pos_y;

            pos_x = x;
            pos_y = y;
        }

        @Override
        public boolean Intersects(float x, float y) {
            return (pos_x - x)*(pos_x - x) + (pos_y - y)*(pos_y - y) < 10f*10f;
        }

        @Override
        public void Draw(GraphicsContext gc) {
            gc.setFill(Color.CHOCOLATE);
            gc.fillOval(pos_x - 10f, pos_y - 10f, 2f*10f, 2f*10f);
        }
    }

    public class CornerManipulator implements Manipulator {
        @Override
        public void Move(float x, float y) {
            cor_x = x;
            cor_y = y;
        }

        @Override
        public boolean Intersects(float x, float y) {
            return (cor_x - x)*(cor_x - x) + (cor_y - y)*(cor_y - y) < 10f*10f;
        }

        @Override
        public void Draw(GraphicsContext gc) {
            gc.setFill(Color.DARKORANGE);
            gc.fillOval(cor_x - 10f, cor_y - 10f, 2f*10f, 2f*10f);
        }
    }

    public final CenterManipulator center_manipulator = new CenterManipulator();
    public final CornerManipulator corner_manipulator = new CornerManipulator();



    public ShapeManager(Canvas canvas) {
        if (manager != null) throw new NullPointerException("Creating more than one manager is not allowed!");
        manager = this;
        this.canvas = canvas;
    }

    public void Example() {
        root_layer.AddLayer(new Layer("child1"));
        root_layer.AddLayer(new Layer("child2"));
        root_layer.AddLayer(new Layer("child3"));
        root_layer.AddLayer(new Layer("child4"));
        //AddEllipse(0, 0, 30, 30, Color.BLACK, Color.WHITE, 2);
        current_layer = root_layer.GetLayers()[2];
        current_layer.AddLayer(new Layer("child3.1"));
        current_layer.AddLayer(new Layer("child3.2"));
        current_layer.AddLayer(new Layer("child3.3"));
        Layer child3_4 = new Layer("child3.4");
        current_layer.AddLayer(child3_4);

        root_layer.MoveTop(child3_4);
        root_layer.MoveUp(child3_4);
        root_layer.MoveUp(child3_4);
        root_layer.MoveUp(child3_4);

    }



    public final void OnPressed(float x, float y) {
        Redraw();

        for (Manipulator curr : manipulators) {
            if (curr.Intersects(x, y)) {
                selected = curr;
                OnManipulatorSelect();

                selected.Press(x, y);
                return;
            }
        }

        if (drawing_mode == DrawingMode.PEN) {
            append_selection = false;
            Vertex vertex = new Vertex(x, y, x, y, x, y, true);

            selected = vertex.next;
            OnManipulatorSelect();

            if (pen_shape == null) {
                pen_shape = new Shape(current_stroke_color, current_fill_color, current_border_thickness, false, vertex);
                current_layer.AddShape(pen_shape);
            } else {
                pen_shape.PushBack(vertex);
            }
        }

        if (drawing_mode == DrawingMode.ELLIPSE) {
            append_selection = false;
            Select(AddEllipse(x, y, 50f, 50f, current_stroke_color, current_fill_color, current_border_thickness));
            cor_x = pos_x;
            cor_y = pos_y;

            selected = corner_manipulator;
            OnManipulatorSelect();
        }

        if (drawing_mode == DrawingMode.RECTANGLE) {
            append_selection = false;
            Select(AddRectangle(x, y, 50f, 50f, current_stroke_color, current_fill_color, current_border_thickness));
            cor_x = pos_x;
            cor_y = pos_y;

            selected = corner_manipulator;
            OnManipulatorSelect();
        }
    }

    public final void OnReleased(float x, float y){
        Redraw();

        if (selected != null){
            OnManipulatorUnselect();
            selected = null;
        }
    }

    public final void OnDragged(float x, float y){

        Redraw();

        if (selected != null) selected.Move(x, y);
    }

    /**Calls when some manipulator is selected*/
    public void OnManipulatorSelect() {};

    /**Calls when manipulator is unselected*/
    public void OnManipulatorUnselect() {};

    /**Selects given shape*/
    public void Select(Shape shape){
        if (!append_selection) ClearSelection();
        if (!selected_shapes.contains(shape)) selected_shapes.add(shape);
        if (selected_shapes.isEmpty()) return;

        float right = selected_shapes.get(0).center_x + selected_shapes.get(0).width*0.5f;
        float left = selected_shapes.get(0).center_x - selected_shapes.get(0).width*0.5f;
        float up = selected_shapes.get(0).center_y + selected_shapes.get(0).height*0.5f;
        float down = selected_shapes.get(0).center_y - selected_shapes.get(0).height*0.5f;


        for (Shape curr : selected_shapes) {
            float curr_right = curr.center_x + curr.width*0.5f;
            float curr_left = curr.center_x - curr.width*0.5f;
            float curr_up = curr.center_y + curr.height*0.5f;
            float curr_down = curr.center_y - curr.height*0.5f;

            if (curr_right > right) right = curr_right;
            if (curr_left < left) left = curr_left;
            if (curr_up > up) up = curr_up;
            if (curr_down < down) down = curr_down;
        }

        prev_pos_x = pos_x = (right+left)*0.5f;
        prev_pos_y = pos_y = (up+down)*0.5f;
        prev_cor_x = cor_x = right;
        prev_cor_y = cor_y = up;

        Redraw();
    }

    /**Clear selected shapes from list but does not delete them*/
    public void ClearSelection() {
        for (Shape curr : selected_shapes) {
            float shift_x = pos_x - prev_pos_x;
            float shift_y = pos_y - prev_pos_y;
            float pd_x = prev_cor_x - prev_pos_x, pd_y = prev_cor_y - prev_pos_y;
            float nd_x = cor_x - pos_x, nd_y = cor_y - pos_y;

            if (fixed_rotation) {
                float angle = (float)(Math.atan2(pd_x, pd_y) - Math.atan2(nd_x, nd_y));
                float scale = (float)(Math.sqrt(nd_x*nd_x+nd_y*nd_y) / Math.sqrt(pd_x*pd_x+pd_y*pd_y));

                curr.SetTransform(prev_pos_x, prev_pos_y, shift_x, shift_y, scale, scale, angle);
            } else {
                float scale_x = nd_x/pd_x;
                float scale_y = nd_y/pd_y;

                curr.SetTransform(prev_pos_x, prev_pos_y, shift_x, shift_y, scale_x, scale_y, 0f);
            }
        }

        selected_shapes.clear();

        Redraw();
    }

    /**Redraws all shapes*/
    void Redraw(){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        manipulators.clear();

        ArrayList<Shape> shapes = new ArrayList<Shape>();
        root_layer.FillShapes(shapes);

        for (Shape curr : shapes) {
            if (selected_shapes.contains(curr)) {
                float shift_x = pos_x - prev_pos_x;
                float shift_y = pos_y - prev_pos_y;
                float pd_x = prev_cor_x - prev_pos_x, pd_y = prev_cor_y - prev_pos_y;
                float nd_x = cor_x - pos_x, nd_y = cor_y - pos_y;

                if (fixed_rotation) {
                    float angle = (float)(Math.atan2(pd_x, pd_y) - Math.atan2(nd_x, nd_y));
                    float scale = (float)(Math.sqrt(nd_x*nd_x+nd_y*nd_y) / Math.sqrt(pd_x*pd_x+pd_y*pd_y));

                    curr.Draw(gc, prev_pos_x, prev_pos_y, shift_x, shift_y, scale, scale, angle);
                } else {
                    float scale_x = nd_x/pd_x;
                    float scale_y = nd_y/pd_y;

                    curr.Draw(gc, prev_pos_x, prev_pos_y, shift_x, shift_y, scale_x, scale_y, 0f);
                }

                continue;
            }
            curr.GetManipulators(manipulators, show_vertex, show_anchor_points);
            curr.Draw(gc);
        }

        /*if (!selected_shapes.isEmpty()) {
            double bound_buffer_x[] = new double[] {cor_x, 2*pos_x - cor_x, 2*pos_x - cor_x, pos_x};
            double bound_buffer_y[] = new double[] {cor_y, cor_y, 2*pos_y - cor_y, 2*pos_y - cor_y};

            for (int i = 0; i < 4; i++) {
                float next_x = Vertex.TransformX((float)bound_buffer_x[i], (float)bound_buffer_y[i], origin_x, origin_y, shift_x, shift_y, scale_x, scale_y, cos, sin);
                float next_y = Vertex.TransformY((float)bound_buffer_x[i], (float)bound_buffer_y[i], origin_x, origin_y, shift_x, shift_y, scale_x, scale_y, cos, sin);
                bound_buffer_x[i] = next_x;
                bound_buffer_y[i] = next_y;
            }

            gc.setLineWidth(1.5);
            gc.setStroke(Color.LIGHTBLUE);
            gc.strokePolygon(bound_buffer_x, bound_buffer_y, 4);
        }*/

        if (!selected_shapes.isEmpty()) {
            manipulators.add(center_manipulator);
            manipulators.add(corner_manipulator);
        }

        for (Manipulator curr : manipulators)
            if (curr != null) curr.Draw(gc);
    }





    Shape AddEllipse(float center_x, float center_y, float width, float height, Color fill_color, Color stroke_color, float border_thickness) {
        final float k = 0.552284749831f;
        Shape figure = new Shape(
                fill_color,
                stroke_color,
                border_thickness,
                true,
                new Vertex(1f, 0f, 1f, -k, 1f, k, true),
                new Vertex(0f, -1f, -k, -1f, k, -1f, true),
                new Vertex(-1f, 0f, -1f, k, -1f, -k, true),
                new Vertex(0f, 1f, k, 1f, -k, 1f, true)
        );

        figure.SetTransform(0f, 0f, center_x, center_y, width*0.5f, height*0.5f, 0f);

        current_layer.AddShape(figure);

        return figure;
    }

    Shape AddRectangle(float center_x, float center_y, float width, float height, Color fill_color, Color stroke_color, float border_thickness) {
        final float k = 0.552284749831f;
        Shape figure = new Shape(
                fill_color,
                stroke_color,
                border_thickness,
                true,
                new Vertex(1f, 1f, 1f, 1f, 1f, 1f, false),
                new Vertex(1f, -1f, 1f, -1f, 1f, -1f, false),
                new Vertex(-1f, -1f, -1f, -1f, -1f, -1f, false),
                new Vertex(-1f, 1f, -1f, 1f, -1f, 1f, false)
        );

        figure.SetTransform(0f, 0f, center_x, center_y, width*0.5f, height*0.5f, 0f);

        current_layer.AddShape(figure);

        return figure;
    }



    /**Enable or disable showing anchor points*/
    public final void SetShowAnchorPoints(boolean show) {
        show_anchor_points = show;
        Redraw();
    }

    /**Checks if showing anchor points is enabled*/
    public final boolean GetShowAnchorPoints() {
        return show_anchor_points;
    }

    /**Enable or disable showing vertices*/
    public final void SetShowVertex(boolean show) {
        show_vertex = show;
        Redraw();
    }

    /**Checks if showing vertices is enabled*/
    public final boolean GetShowVertex() {
        return show_vertex;
    }

    /**Set mode of shape transforming by mouse. */
    public final void SetRotationFixed(boolean fixed) {
        fixed_rotation = fixed;
        Redraw();
    }

    /**Checks if rotation is fixed*/
    public final boolean GetRotationFixed() {
        return fixed_rotation;
    }

    /**Set drawing mode.*/
    public final void SetDrawingMode(DrawingMode mode) {
        drawing_mode = mode;
        if (drawing_mode != DrawingMode.PEN) pen_shape = null;
        Redraw();
    }

    /**Gets drawing mode*/
    public final DrawingMode GetDrawingMode() {
        return drawing_mode;
    }

    /**Sets append selection mode. If append == true you can select multiple shapes and only one shape otherwise*/
    public final void SetAppendSelection(boolean append){
        append_selection = append;
    }

    /**Gets append selection mode */
    public final boolean GetAppendSelection(){
        return append_selection;
    }

    /**Selects fill color which will use as default color of next shape*/
    public final void SetFillColor(Color color){
        current_fill_color = color;
    }

    /**Selects stroke color which will use as default color of next shape*/
    public final void SetStrokeColor(Color color){
        current_stroke_color = color;
    }

    /**Selects border thickness which will use as default thickness of next shape*/
    public final void SetBorderThickness(float thickness) {
        current_border_thickness = thickness;
    }

    /**Returns selected shape*/
    public final Shape[] GetSelectedShapes() {
        return selected_shapes.toArray(new Shape[0]);
    }

    /**Returns selected manipulator. It may return null value!*/
    public final Manipulator GetSelectedManipulator() {
        return selected;
    }
}
