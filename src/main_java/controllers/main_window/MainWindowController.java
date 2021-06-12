package main_java.controllers.main_window;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import main_java.controllers.canvas.CanvasController;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class MainWindowController extends Application {
    @FXML
    private MenuBar menuBar = new MenuBar();
    @FXML
    private CanvasController mainCanvas;
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/view/main_window/MainWindowView.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);

        InputStream iconStream = getClass().getResourceAsStream("/sample/assets/curve-icon.png");
        Image image = new Image(iconStream);
        primaryStage.getIcons().add(image);

        primaryStage.setScene(scene);
        primaryStage.setTitle("VecLab");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
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
        mainCanvas.handleClick(actionEvent);
    }
}
