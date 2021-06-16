package main_java.controllers.main_window.status_bar;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class StatusBarController extends HBox {
    private Canvas cs;
    private GridPane mainPanel;
    private double zoomFactor = 0.9;
    private double currentZoom = 100.00;

    public StatusBarController() {
        super();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "/resources/view/main_window/status_bar/StatusBarView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        Label zoomLabel = new Label();
        zoomLabel.setText(currentZoom + "%");

        Button zoomIn = new Button("-");
        zoomIn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (currentZoom > 10) {
                    cs.setScaleX(cs.getScaleX() * zoomFactor);
                    cs.setScaleY(cs.getScaleY() * zoomFactor);
                    currentZoom *= zoomFactor;
                    zoomLabel.setText(String.format("%.2f", currentZoom) + "%");
                }
            }
        });

        Button zoomOut = new Button("+");
        zoomOut.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (currentZoom < 200) {
                    cs.setScaleX(cs.getScaleX() / zoomFactor);
                    cs.setScaleY(cs.getScaleY() / zoomFactor);
                    currentZoom /= zoomFactor;
                    zoomLabel.setText(String.format("%.2f", currentZoom) + "%");
                }
            }
        });

        Separator separator = new Separator(Orientation.VERTICAL);

        this.getChildren().setAll(separator, zoomIn, zoomLabel, zoomOut);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void getCanvas(Canvas currentCanvas) {
        cs = currentCanvas;
    }

    public void init(GridPane mainPanel) {
        this.mainPanel = mainPanel;
    }
}
