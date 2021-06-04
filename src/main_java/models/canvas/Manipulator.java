package main_java.models.canvas;

import javafx.scene.canvas.GraphicsContext;

//This interface describes a simple manipulator
//It uses for changing something (position, rotation, size) by mouse

public interface Manipulator {
    //Calls, when someone grabs this manipulator by mouse
    default void Press(float x, float y) { }

    //Calls, when someone grabs this manipulator and tries to move it by mouse
    default void Move(float x, float y) { }

    //Checks if point (x, y) is in this manipulator
    boolean Intersects(float x, float y);

    //Draws this manipulator
    void Draw(GraphicsContext gc);
}
