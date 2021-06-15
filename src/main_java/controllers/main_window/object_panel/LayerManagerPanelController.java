package main_java.controllers.main_window.object_panel;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.GridPane;
import logic.Layer;
import logic.ShapeManager;
import java.io.IOException;

public class LayerManagerPanelController extends GridPane {

    @FXML
    Button addLayerButton;
    @FXML
    Button deleteLayerButton;
    @FXML
    Button setCurrentLayerButton;
    @FXML
    Button moveLayerUpButton;
    @FXML
    Button moveLayerToTopButton;
    @FXML
    Button moveLayerDownButton;
    @FXML
    Button moveLayerToBottomButton;
    @FXML
    TreeView<String> layersView;
    @FXML
    TextField nameField;

    public LayerManagerPanelController() {
        super();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "/resources/view/main_window/object_panel/LayerManagerPanelView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void init(ShapeManager sm) {

        TreeItem<String> rootLayer = new TreeItem<>(sm.root_layer.toString());
        rootLayer.setExpanded(true);
        layersView.setRoot(rootLayer);

        addLayerButton.setOnAction(e -> {
            System.out.println(nameField.getText());
                if (!nameField.getText().equals("")) {
                    sm.root_layer.AddLayer(new Layer(nameField.getText()));
                }
        });

        deleteLayerButton.setOnAction(e -> {});

        setCurrentLayerButton.setOnAction(e -> {});

        moveLayerDownButton.setOnAction(e -> {});

        moveLayerToBottomButton.setOnAction(e -> {});

        moveLayerUpButton.setOnAction(e -> {});

        moveLayerToTopButton.setOnAction(e -> {});


    }
}
