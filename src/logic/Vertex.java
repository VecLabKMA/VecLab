package logic;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Collection;
import java.util.LinkedList;

public class Vertex {
    private float curr_x, curr_y;
    private float next_x, next_y;
    private float prev_x, prev_y;
    private boolean symmetric;
    Shape shape;


    public class PrevManipulator implements Manipulator {
        @Override
        public final void Move(float x, float y) {
            prev_x = x;
            prev_y = y;
            if (symmetric){
                next_x = curr_x + curr_x - prev_x;
                next_y = curr_y + curr_y - prev_y;
            }

            shape.Build();
        }

        @Override
        public final boolean Intersects(float x, float y) { return (prev_x - x)*(prev_x - x) + (prev_y - y)*(prev_y - y) < Constants.radius*Constants.radius; }

        @Override
        public final void Draw(GraphicsContext gc) {
            gc.setStroke(Color.AQUA);
            gc.setLineWidth(Constants.width);
            gc.strokeLine(curr_x, curr_y, prev_x, prev_y);

            gc.setFill(Color.RED);
            gc.fillOval(prev_x - Constants.radius, prev_y - Constants.radius, 2f*Constants.radius, 2f*Constants.radius);

            gc.setFill(Color.BLUE);
            gc.fillOval(curr_x - Constants.radius, curr_y - Constants.radius, 2f*Constants.radius, 2f*Constants.radius);
        }

        public final Vertex GetVertex(){
            return Vertex.this;
        }
    }

    public class CurrManipulator implements Manipulator {
        @Override
        public void Move(float x, float y) {
            prev_x += x - curr_x;
            prev_y += y - curr_y;

            next_x += x - curr_x;
            next_y += y - curr_y;

            curr_x = x;
            curr_y = y;

            shape.Build();
        }

        @Override
        public boolean Intersects(float x, float y) {
            return (curr_x - x)*(curr_x - x) + (curr_y - y)*(curr_y - y) < Constants.radius*Constants.radius;
        }

        @Override
        public void Draw(GraphicsContext gc) {
            gc.setFill(Color.BLUE);
            gc.fillOval(curr_x - Constants.radius, curr_y - Constants.radius, 2f*Constants.radius, 2f*Constants.radius);
        }

        public final Vertex GetVertex(){
            return Vertex.this;
        }
    }

    public class NextManipulator implements Manipulator {
        @Override
        public void Move(float x, float y) {
            next_x = x;
            next_y = y;
            if (symmetric){
                prev_x = curr_x + curr_x - next_x;
                prev_y = curr_y + curr_y - next_y;
            }

            shape.Build();
        }

        @Override
        public boolean Intersects(float x, float y) { return (next_x - x)*(next_x - x) + (next_y - y)*(next_y - y) < Constants.radius*Constants.radius; }

        @Override
        public void Draw(GraphicsContext gc) {
            gc.setStroke(Color.AQUA);
            gc.setLineWidth(Constants.width);
            gc.strokeLine(curr_x, curr_y, next_x, next_y);

            gc.setFill(Color.RED);
            gc.fillOval(next_x - Constants.radius, next_y - Constants.radius, 2f*Constants.radius, 2f*Constants.radius);

            gc.setFill(Color.BLUE);
            gc.fillOval(curr_x - Constants.radius, curr_y - Constants.radius, 2f*Constants.radius, 2f*Constants.radius);
        }

        public final Vertex GetVertex(){
            return Vertex.this;
        }
    }

    public final PrevManipulator prev = new PrevManipulator();
    public final CurrManipulator curr = new CurrManipulator();
    public final NextManipulator next = new NextManipulator();


    Vertex(float x, float y, float next_x, float next_y, float prev_x, float prev_y, boolean symmetric) {
        this.curr_x = x;
        this.curr_y = y;
        this.next_x = next_x;
        this.next_y = next_y;
        this.prev_x = prev_x;
        this.prev_y = prev_y;
        this.symmetric = symmetric;
    }

    Vertex() { }

    public final float GetPrevX() { return prev_x; }
    public final float GetPrevY() { return prev_y; }
    public final float GetCurrX() { return curr_x; }
    public final float GetCurrY() { return curr_y; }
    public final float GetNextX() { return next_x; }
    public final float GetNextY() { return next_y; }

    public final Shape GetShape() { return shape; }

    public final boolean GetSymmetric() { return symmetric; }


    public final void SetSymmetric(boolean symmetric) {
        if (symmetric == this.symmetric) return;
        if (symmetric) {
            this.symmetric = true;
            prev_x = curr_x + curr_x - next_x;
            prev_y = curr_y + curr_y - next_y;
        } else {
            this.symmetric = false;
        }
        shape.Build();
    }







    void Transform(float origin_x, float origin_y, float shift_x, float shift_y, float scale_x, float scale_y, float cos, float sin) {
        float n_prev_x = TransformX(prev_x, prev_y, origin_x, origin_y, shift_x, shift_y, scale_x, scale_y, cos, sin);
        float n_prev_y = TransformY(prev_x, prev_y, origin_x, origin_y, shift_x, shift_y, scale_x, scale_y, cos, sin);

        float n_curr_x = TransformX(curr_x, curr_y, origin_x, origin_y, shift_x, shift_y, scale_x, scale_y, cos, sin);
        float n_curr_y = TransformY(curr_x, curr_y, origin_x, origin_y, shift_x, shift_y, scale_x, scale_y, cos, sin);

        float n_next_x = TransformX(next_x, next_y, origin_x, origin_y, shift_x, shift_y, scale_x, scale_y, cos, sin);
        float n_next_y = TransformY(next_x, next_y, origin_x, origin_y, shift_x, shift_y, scale_x, scale_y, cos, sin);

        prev_x = n_prev_x;
        prev_y = n_prev_y;
        curr_x = n_curr_x;
        curr_y = n_curr_y;
        next_x = n_next_x;
        next_y = n_next_y;
    }

    static void CreateCurve(Vertex prev, Vertex next, LinkedList<Float> buffer_x, LinkedList<Float> buffer_y) {
        for (int i = 0; i < Constants.quality; i++) {
            float t = (float)i/Constants.quality;
            float px = CurveX(prev, next, t);
            float py = CurveY(prev, next, t);

            buffer_x.add(px);
            buffer_y.add(py);
        }
    }

    static void SplitCurve(Vertex prev, Vertex curr, Vertex next, float t){
        float p_x = prev.curr_x*(1f-t) + prev.next_x*t;
        float p_y = prev.curr_y*(1f-t) + prev.next_y*t;

        float c_x = prev.next_x*(1f-t) + next.prev_x*t;
        float c_y = prev.next_y*(1f-t) + next.prev_y*t;

        float n_x = next.prev_x*(1f-t) + next.curr_x*t;
        float n_y = next.prev_y*(1f-t) + next.curr_y*t;

        float p_c_x = p_x*(1f-t) + c_x*t;
        float p_c_y = p_y*(1f-t) + c_y*t;

        float c_n_x = c_x*(1f-t) + n_x*t;
        float c_n_y = c_y*(1f-t) + n_y*t;

        float p_c_n_x = p_c_x*(1f-t) + c_n_x*t;
        float p_c_n_y = p_c_y*(1f-t) + c_n_y*t;

        prev.next_x = p_x;
        prev.next_y = p_y;

        curr.prev_x = p_c_x;
        curr.prev_y = p_c_y;
        curr.curr_x = p_c_n_x;
        curr.curr_y = p_c_n_y;
        curr.next_x = c_n_x;
        curr.next_y = c_n_y;

        next.prev_x = n_x;
        next.prev_y = n_y;

        next.symmetric = false;
    }

    static float GetDistance(Vertex prev, Vertex next, float x, float y){
        float t = GetClosestT(prev, next, x, y);
        float px = CurveX(prev, next, t), py = CurveY(prev, next, t);
        return (float)Math.sqrt((px-x)*(px-x) + (py-y)*(py-y));
    }

    static float GetClosestT(Vertex prev, Vertex next, float x, float y){
        float min_sq_dist = (next.curr_x-x)*(next.curr_x-x) + (next.curr_y-y)*(next.curr_y-y);
        float min_t = 1f;

        for (int i = 0; i < Constants.quality; i++) {
            float t = (float)i/Constants.quality;
            float px = CurveX(prev, next, t);
            float py = CurveY(prev, next, t);

            float curr_sq_dist = (px-x)*(px-x) + (py-y)*(py-y);

            if (curr_sq_dist < min_sq_dist){
                min_sq_dist = curr_sq_dist;
                min_t = t;
            }
        }

        return min_t;
    }

    static float CurveX(Vertex prev, Vertex next, float t){
        return (1f - t)*(1f - t)*(1f - t)*prev.curr_x +
               (1f - t)*(1f - t)*t*3f*prev.next_x +
               (1f - t)*t*t*3f*next.prev_x +
               t*t*t*next.curr_x;
    }

    static float CurveY(Vertex prev, Vertex next, float t){
        return (1f - t)*(1f - t)*(1f - t)*prev.curr_y +
               (1f - t)*(1f - t)*t*3f*prev.next_y +
               (1f - t)*t*t*3f*next.prev_y +
               t*t*t*next.curr_y;
    }

    static float TransformX(float x, float y, float origin_x, float origin_y, float shift_x, float shift_y, float scale_x, float scale_y, float cos, float sin) {
        //Move to origin
        x -= origin_x;
        y -= origin_y;

        //Scale
        x *= scale_x;
        y *= scale_y;

        //Rotate
        float nx = x*cos - y*sin;
        float ny = x*sin + y*cos;
        x = nx;
        y = ny;

        //Translate
        x += shift_x;
        y += shift_y;

        //Move from origin
        x += origin_x;
        y += origin_y;

        return x;
    }

    static float TransformY(float x, float y, float origin_x, float origin_y, float shift_x, float shift_y, float scale_x, float scale_y, float cos, float sin) {
        //Move to origin
        x -= origin_x;
        y -= origin_y;

        //Scale
        x *= scale_x;
        y *= scale_y;

        //Rotate
        float nx = x*cos - y*sin;
        float ny = x*sin + y*cos;
        x = nx;
        y = ny;

        //Translate
        x += shift_x;
        y += shift_y;

        //Move from origin
        x += origin_x;
        y += origin_y;

        return y;
    }
}
