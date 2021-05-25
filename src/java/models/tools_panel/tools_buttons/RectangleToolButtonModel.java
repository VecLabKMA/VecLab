package java.models.tools_panel.tools_buttons;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import main_panel.tools_buttons.models.RectangleToolButtonModel;

public class RectangleToolButtonModel {
    @FXML
    private Button b;
    @FXML
    private Label l;
    private main_panel.tools_buttons.models.RectangleToolButtonModel model;

    void addListeners() {
        b.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                l.setText("Done smth " + model.doSmth());
            }
        });
    }
}
