package main_java.controllers.main_window.status_bar;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class StatusBarController extends HBox {
    @FXML
    public Label mouseLocationLabel = new Label("hello");
    private Canvas cs;
    private GridPane mainPanel;

    public StatusBarController() {
        super();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "/resources/view/main_window/status_bar/StatusBarView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        handleMouseLocationLabel();

        Button zoomIn = new Button("+");
        zoomIn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                cs.setScaleX(cs.getScaleX() * 0.5);
                cs.setScaleY(cs.getScaleY() * 0.5);
            }
        });

        this.getChildren().setAll(mouseLocationLabel, zoomIn);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void handleMouseLocationLabel() {
        mouseLocationLabel.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                String message =
                        "X: " + event.getX() +
                        "\n" +
                        "Y: " + event.getY();
                mouseLocationLabel.setText(message);
            }
        });
    }

    public void getCanvas(Canvas currentCanvas) {
        cs = currentCanvas;
    }

    public void init(GridPane mainPanel) {
        this.mainPanel = mainPanel;
    }
}
