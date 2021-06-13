package main_java.controllers.main_window;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.MenuBar;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import main_java.controllers.canvas.CanvasController;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

public class MainWindowPanelController extends GridPane {
    @FXML
    public Pane canvasWrapper;
    @FXML
    public CanvasController mainCanvas;
    private Stage primaryStage;

    public MainWindowPanelController() {
        super();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "/resources/view/main_window/MainWindowPanelView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        maxWidth(Double.POSITIVE_INFINITY);
        maxHeight(Double.POSITIVE_INFINITY);


        mainCanvas.widthProperty().bind(canvasWrapper.widthProperty());
        mainCanvas.heightProperty().bind(canvasWrapper.heightProperty());
    }

    public void init() {
        mainCanvas.init();
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

        File file = fileChooser.showSaveDialog(primaryStage);

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

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
}
