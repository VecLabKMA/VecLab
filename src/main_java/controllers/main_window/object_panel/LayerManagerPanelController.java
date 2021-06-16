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
import sun.misc.Queue;

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
    TreeView<Layer> layersView;
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
        update(sm.root_layer);

        nameField.setOnAction(e -> {
            addLayer(sm, nameField.getText());
        });

        addLayerButton.setOnAction(e -> {
            addLayer(sm, nameField.getText());
        });

        deleteLayerButton.setOnAction(e -> {
            TreeItem<Layer> selectedItem = layersView.getSelectionModel().getSelectedItem();
            if (selectedItem != null && selectedItem != layersView.getRoot()) {
                selectedItem.getParent().getValue().RemoveLayer(selectedItem.getValue());
                selectedItem.getParent().getChildren().remove(selectedItem);
            }
        });

        setCurrentLayerButton.setOnAction(e -> {
            TreeItem<Layer> selectedItem = layersView.getSelectionModel().getSelectedItem();
            if (selectedItem != null)
                sm.SetCurrentLayer(selectedItem.getValue());
        });

        moveLayerDownButton.setOnAction(e -> {
            TreeItem<Layer> selectedItem = layersView.getSelectionModel().getSelectedItem();
            if (selectedItem != null && selectedItem != layersView.getRoot()) {
                selectedItem.getParent().getValue().MoveUp(selectedItem.getValue());
                update(layersView.getRoot().getValue());

                TreeItem<Layer> newItem = getTreeItemByLayer(selectedItem.getValue());
                layersView.getSelectionModel().select(newItem);
                expand(newItem);
            }
        });

        moveLayerToBottomButton.setOnAction(e -> {
            TreeItem<Layer> selectedItem = layersView.getSelectionModel().getSelectedItem();
            if (selectedItem != null && selectedItem != layersView.getRoot()) {
                selectedItem.getParent().getValue().MoveBottom(selectedItem.getValue());
                update(layersView.getRoot().getValue());

                TreeItem<Layer> newItem = getTreeItemByLayer(selectedItem.getValue());
                layersView.getSelectionModel().select(newItem);
                expand(newItem);
            }
        });

        moveLayerUpButton.setOnAction(e -> {
            TreeItem<Layer> selectedItem = layersView.getSelectionModel().getSelectedItem();
            if (selectedItem != null && selectedItem != layersView.getRoot()) {
                selectedItem.getParent().getValue().MoveDown(selectedItem.getValue());

                update(layersView.getRoot().getValue());

                TreeItem<Layer> newItem = getTreeItemByLayer(selectedItem.getValue());
                layersView.getSelectionModel().select(newItem);
                expand(newItem);
            }
        });

        moveLayerToTopButton.setOnAction(e -> {
            TreeItem<Layer> selectedItem = layersView.getSelectionModel().getSelectedItem();
            if (selectedItem != null && selectedItem != layersView.getRoot()) {
                selectedItem.getParent().getValue().MoveTop(selectedItem.getValue());
                update(layersView.getRoot().getValue());

                TreeItem<Layer> newItem = getTreeItemByLayer(selectedItem.getValue());
                layersView.getSelectionModel().select(newItem);
                expand(newItem);
            }
        });
    }

    public void update(Layer rootLayer) {
        TreeItem<Layer> rootLayerItem = new TreeItem<>(rootLayer);
        addLayer(rootLayerItem);
        layersView.setRoot(rootLayerItem);
    }

    private void addLayer(TreeItem<Layer> layerItem) {
//        for (Layer l : layerItem.getValue().GetLayers())
//        {
//            TreeItem<Layer> newLayerItem = new TreeItem<>(l);
            Layer[] childrenLayers = layerItem.getValue().GetLayers();
        for (int i = childrenLayers.length - 1; i > -1; --i) {
            TreeItem<Layer> newLayerItem = new TreeItem<>(childrenLayers[i]);
            layerItem.getChildren().add(newLayerItem);
            addLayer(newLayerItem);
        }
    }

    private TreeItem<Layer> getTreeItemByLayer(Layer layer) {
        Queue<TreeItem<Layer>> queue = new Queue<>();
        queue.enqueue(layersView.getRoot());

        try {
            while (true) {
                TreeItem<Layer> current = queue.dequeue();
                if (current.getValue() == layer) {
                    return current;
                } else {
                    for (TreeItem<Layer> childLayer : current.getChildren()) {
                        queue.enqueue(childLayer);
                    }
                }
            }
        } catch (InterruptedException ignored) {

        }
        return null;
    }

    public void expand(TreeItem<Layer> item) {
        item.setExpanded(true);
        while (item.getParent() != null) {
            item = item.getParent();
            item.setExpanded(true);
        }
    }

    private void addLayer(ShapeManager sm, String name) {
        TreeItem<Layer> selectedItem = layersView.getSelectionModel().getSelectedItem();
        if (name != null && !name.isEmpty() && selectedItem != null) {
            Layer newLayer = new Layer(nameField.getText());
            selectedItem.getValue().AddLayer(newLayer);
            selectedItem.getChildren().add(0, new TreeItem<>(newLayer));
            selectedItem.setExpanded(true);
            nameField.setText("");
        }
    }

}
