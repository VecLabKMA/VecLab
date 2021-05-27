package main_java.models.logiс;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Collection;
import java.util.LinkedList;

public class Vertex {
    private static final float RADIUS = 10f;

    float curr_x, curr_y;
    float next_x, next_y;
    float prev_x, prev_y;

    boolean symmetric;

    private Manipulator prev, curr, next;

    private class PrevManipulator implements Manipulator {
        @Override
        public void Move(float x, float y) {
            prev_x = x;
            prev_y = y;
            if (symmetric){
                next_x = curr_x + curr_x - prev_x;
                next_y = curr_y + curr_y - prev_y;
            }
        }

        @Override
        public boolean Intersects(float x, float y) { return (prev_x - x)*(prev_x - x) + (prev_y - y)*(prev_y - y) < RADIUS*RADIUS; }

        @Override
        public void Draw(GraphicsContext gc) {
            gc.setStroke(Color.AQUA);
            gc.setLineWidth(RADIUS*0.5f);
            gc.strokeLine(curr_x, curr_y, prev_x, prev_y);

            gc.setFill(Color.RED);
            gc.fillOval(prev_x - RADIUS, prev_y - RADIUS, 2f*RADIUS, 2f*RADIUS);

            gc.setFill(Color.BLUE);
            gc.fillOval(curr_x - RADIUS, curr_y - RADIUS, 2f*RADIUS, 2f*RADIUS);
        }
    }

    private class CurrManipulator implements Manipulator {
        @Override
        public void Move(float x, float y) {
            prev_x += x - curr_x;
            prev_y += y - curr_y;

            next_x += x - curr_x;
            next_y += y - curr_y;

            curr_x = x;
            curr_y = y;
        }

        @Override
        public boolean Intersects(float x, float y) {
            return (curr_x - x)*(curr_x - x) + (curr_y - y)*(curr_y - y) < RADIUS*RADIUS;
        }

        @Override
        public void Draw(GraphicsContext gc) {
            gc.setFill(Color.BLUE);
            gc.fillOval(curr_x - RADIUS, curr_y - RADIUS, 2f*RADIUS, 2f*RADIUS);
        }
    }

    private class NextManipulator implements Manipulator {
        @Override
        public void Move(float x, float y) {
            next_x = x;
            next_y = y;
            if (symmetric){
                prev_x = curr_x + curr_x - next_x;
                prev_y = curr_y + curr_y - next_y;
            }
        }

        @Override
        public boolean Intersects(float x, float y) { return (next_x - x)*(next_x - x) + (next_y - y)*(next_y - y) < RADIUS*RADIUS; }

        @Override
        public void Draw(GraphicsContext gc) {
            gc.setStroke(Color.AQUA);
            gc.setLineWidth(RADIUS*0.5f);
            gc.strokeLine(curr_x, curr_y, next_x, next_y);

            gc.setFill(Color.RED);
            gc.fillOval(next_x - RADIUS, next_y - RADIUS, 2f*RADIUS, 2f*RADIUS);

            gc.setFill(Color.BLUE);
            gc.fillOval(curr_x - RADIUS, curr_y - RADIUS, 2f*RADIUS, 2f*RADIUS);
        }
    }



    public Vertex(float x, float y, float next_x, float next_y, float prev_x, float prev_y, boolean symmetric) {
        this.curr_x = x;
        this.curr_y = y;
        this.next_x = next_x;
        this.next_y = next_y;
        this.prev_x = prev_x;
        this.prev_y = prev_y;
        this.symmetric = symmetric;

        prev = new PrevManipulator();
        curr = new CurrManipulator();
        next = new NextManipulator();
    }

    public void Rotate(float point_x, float point_y, float radians) {
        float cos = (float)Math.cos(radians);
        float sin = (float)Math.sin(radians);

        float prev_x_1 = prev_x*cos - prev_y*sin;
        float prev_y_1 = prev_x*sin + prev_y*cos;

        float curr_x_1 = curr_x*cos - curr_y*sin;
        float curr_y_1 = curr_x*sin + curr_y*cos;

        float next_x_1 = next_x*cos - next_y*sin;
        float next_y_1 = next_x*sin + next_y*cos;

        Translate(-point_x, -point_y);
        prev_x = prev_x_1;
        prev_y = prev_y_1;

        curr_x = curr_x_1;
        curr_y = curr_y_1;

        next_x = next_x_1;
        next_y = next_y_1;
        Translate(point_x, point_y);
    }

    public void Translate(float shift_x, float shift_y) {
        prev_x += shift_x;
        prev_y += shift_y;

        curr_x += shift_x;
        curr_y += shift_y;

        next_x += shift_x;
        next_y += shift_y;
    }

    public void Scale(float point_x, float point_y, float factor_x, float factor_y) {
        Translate(-point_x, -point_y);
        prev_x *= factor_x;
        prev_y *= factor_y;

        curr_x *= factor_x;
        curr_y *= factor_y;

        next_x *= factor_x;
        next_y *= factor_y;
        Translate(point_x, point_y);
    }


    public void GetManipulators(Collection<Manipulator> buffer) {
        buffer.add(prev);
        buffer.add(curr);
        buffer.add(next);
    }

    public static void CreateCurve(Vertex prev, Vertex next, LinkedList<Float> buffer_x, LinkedList<Float> buffer_y, int point_count) {
        for (int i = 0; i < point_count; i++) {
            float t = (float)i/point_count;

            float px = (1f - t)*(1f - t)*(1f - t)*prev.curr_x +
                       (1f - t)*(1f - t)*t*3f*prev.next_x +
                       (1f - t)*t*t*3f*next.prev_x +
                       t*t*t*next.curr_x;

            float py = (1f - t)*(1f - t)*(1f - t)*prev.curr_y +
                       (1f - t)*(1f - t)*t*3f*prev.next_y +
                       (1f - t)*t*t*3f*next.prev_y +
                       t*t*t*next.curr_y;

            buffer_x.add(px);
            buffer_y.add(py);
        }
    }
}
