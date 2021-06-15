package logic;

import javafx.scene.paint.Color;

import java.io.Serializable;

public class SerializableColor implements Serializable {
    private float r, g, b, a;

    public SerializableColor(Color color) {
        r = (float)color.getRed();
        g = (float)color.getGreen();
        b = (float)color.getBlue();
        a = (float)color.getOpacity();
    }

    public final void Set(Color color){
        r = (float)color.getRed();
        g = (float)color.getGreen();
        b = (float)color.getBlue();
        a = (float)color.getOpacity();
    }

    public final Color Get(){
        return new Color(r, g, b, a);
    }
}
