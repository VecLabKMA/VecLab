package logic;

import java.io.Serializable;
import java.util.ArrayList;

public class Layer implements Serializable {
    private String name;
    private final ArrayList<Shape> shapes = new ArrayList<Shape>();
    private final ArrayList<Layer> layers = new ArrayList<Layer>();

    public Layer(String name) {
        this.name = name;
    }

    /**Returns name of this layer*/
    public final String GetName() {
        return name;
    }

    /**Sets name of this layer*/
    public final void SetName(String name) {
        this.name = name;
    }

    /**Returns array of shapes in this layer*/
    public final Shape[] GetShapes() {
        return shapes.toArray(new Shape[0]);
    }

    /**Returns array of child layers of this layer*/
    public final Layer[] GetLayers() {
        return layers.toArray(new Layer[0]);
    }

    /**Adds layer into this layer*/
    public final void AddLayer(Layer layer) {
        layers.add(layer);
    }

    /**Removes layer from this layer or from child layers*/
    public final void RemoveLayer(Layer layer) {
        if (layers.remove(layer)) {
            ShapeManager.manager.ClearSelection();
            ShapeManager.manager.Redraw();
        }

        for (Layer curr : layers)
            curr.RemoveLayer(layer);
    }

    /**Adds shape into this layer*/
    public final void AddShape(Shape shape) {
        shapes.add(shape);

        ShapeManager.manager.Redraw();
    }

    /**Removes shape from this layer or from child layers*/
    public final void RemoveShape(Shape shape) {
        if (shapes.remove(shape)) {
            ShapeManager.manager.ClearSelection();
            ShapeManager.manager.Redraw();
        }

        for (Layer curr : layers)
            curr.RemoveShape(shape);
    }

    /**Returns layer, which contains given shape. Is shape does not exist, returns null*/
    public final Layer GetLayerByShape(Shape shape) {
        if (shapes.contains(shape)) return this;
        for (Layer curr : layers) {
            Layer result = curr.GetLayerByShape(shape);
            if (result != null) return result;
        }
        return null;
    }

    /**Moves given shape to the top of this layer or child layers*/
    public final void MoveTop(Shape shape) {
        int index = shapes.indexOf(shape);
        if (index >= 0) {
            Shape replaced = shapes.get(0);
            shapes.set(0, shape);
            shapes.set(index, replaced);


            ShapeManager.manager.Redraw();
            return;
        }

        for (Layer curr : layers)
            curr.MoveTop(shape);
    }


    /**Moves given shape one point up */
    public final void MoveUp(Shape shape) {
        int index = shapes.indexOf(shape);
        if (index >= 1) {
            Shape replaced = shapes.get(index-1);
            shapes.set(index-1, shape);
            shapes.set(index, replaced);

            ShapeManager.manager.Redraw();
            return;
        }

        for (Layer curr : layers)
            curr.MoveUp(shape);
    }

    /**Moves given shape one point down */
    public final void MoveDown(Shape shape) {
        int index = shapes.indexOf(shape);
        if (index != -1 && index <= shapes.size()-2) {
            Shape replaced = shapes.get(index+1);
            shapes.set(index+1, shape);
            shapes.set(index, replaced);

            ShapeManager.manager.Redraw();
            return;
        }

        for (Layer curr : layers)
            curr.MoveDown(shape);
    }

    /**Moves given shape to the bottom of this layer or child layers*/
    public final void MoveBottom(Shape shape) {
        int index = shapes.indexOf(shape);
        if (index != -1) {
            Shape replaced = shapes.get(shapes.size()-1);
            shapes.set(shapes.size()-1, shape);
            shapes.set(index, replaced);


            ShapeManager.manager.Redraw();
            return;
        }

        for (Layer curr : layers)
            curr.MoveTop(shape);
    }





    /**Moves given layer to the top of this layer or child layers*/
    public final void MoveTop(Layer layer) {
        int index = layers.indexOf(layer);
        if (index >= 0) {
            Layer replaced = layers.get(0);
            layers.set(0, layer);
            layers.set(index, replaced);


            ShapeManager.manager.Redraw();
            return;
        }

        for (Layer curr : layers)
            curr.MoveTop(layer);
    }

    /**Moves given layer one point up */
    public final void MoveUp(Layer layer) {
        int index = layers.indexOf(layer);
        if (index >= 1) {
            Layer replaced = layers.get(index-1);
            layers.set(index-1, layer);
            layers.set(index, replaced);

            ShapeManager.manager.Redraw();
            return;
        }

        for (Layer curr : layers)
            curr.MoveUp(layer);
    }

    /**Moves given layer one point down */
    public final void MoveDown(Layer layer) {
        int index = layers.indexOf(layer);
        if (index != -1 && index <= layers.size()-2) {
            Layer replaced = layers.get(index+1);
            layers.set(index+1, layer);
            layers.set(index, replaced);

            ShapeManager.manager.Redraw();
            return;
        }

        for (Layer curr : layers)
            curr.MoveUp(layer);
    }

    /**Moves given layer to the bottom of this layer or child layers*/
    public final void MoveBottom(Layer layer) {
        int index = layers.indexOf(layer);
        if (index != -1) {
            Layer replaced = layers.get(layers.size()-1);
            layers.set(layers.size()-1, layer);
            layers.set(index, replaced);


            ShapeManager.manager.Redraw();
            return;
        }

        for (Layer curr : layers)
            curr.MoveTop(layer);
    }



    /**Fills buffer with shapes of this and child layers in correct order*/
    public final void FillShapes(ArrayList<Shape> buffer) {
        for (Layer curr : layers)
            curr.FillShapes(buffer);
        buffer.addAll(shapes);
    }

    @Override
    public String toString() {
        return name;
    }
}
