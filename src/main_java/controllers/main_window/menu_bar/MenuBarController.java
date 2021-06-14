package main_java.controllers.main_window.menu_bar;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import main_java.controllers.canvas.CanvasController;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

public class MenuBarController extends VBox {

    private CanvasController mainCanvas;

    public MenuBarController() {
        super();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "/resources/view/main_window/menu_bar/MenuBarView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @FXML
    public void handleNewAction(ActionEvent actionEvent) {
    }

    @FXML
    public void handleOpenAction(ActionEvent actionEvent) {
    }

    @FXML
    public void handleSaveAction(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("png files (*.png)", "*.png");
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showSaveDialog(getScene().getWindow());

        if(file != null){
            try {
                WritableImage writableImage = new WritableImage((int)mainCanvas.getWidth(), (int)mainCanvas.getHeight());
                mainCanvas.snapshot(null, writableImage);
                RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
                ImageIO.write(renderedImage, "png", file);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    @FXML
    public void handleSaveAsAction(ActionEvent actionEvent) {
    }

    @FXML
    public void handleExitAction(ActionEvent actionEvent) {
    }

    @FXML
    public void handleCopyAction(ActionEvent actionEvent) {
    }

    @FXML
    public void handleCutAction(ActionEvent actionEvent) {
    }

    @FXML
    public void handlePasteAction(ActionEvent actionEvent) {
    }

    @FXML
    public void handleAboutAction(ActionEvent actionEvent) {
    }

    public CanvasController getMainCanvas() {
        return mainCanvas;
    }

    public void setMainCanvas(CanvasController mainCanvas) {
        this.mainCanvas = mainCanvas;
    }
}
