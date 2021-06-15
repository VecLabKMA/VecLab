package main_java.controllers.main_window.menu_bar;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import main_java.controllers.canvas.CanvasController;
import main_java.controllers.main_window.FileController;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

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
        String currentFileName = FileController.getCurrentFileName();
        String defaultFileName = FileController.getDefaultFileName();
        File currentFile = FileController.getCurrentFile();

        if (currentFileName.equals(defaultFileName)) {
            handleSaveAsAction(actionEvent);
        } else {
            if (currentFile != null) {
                try {

                    WritableImage writableImage = new WritableImage((int) mainCanvas.getWidth(), (int) mainCanvas.getHeight());
                    mainCanvas.snapshot(null, writableImage);
                    RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
                    ImageIO.write(renderedImage, "png", currentFile );

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    @FXML
    public void handleSaveAsAction(ActionEvent actionEvent) {
        String currentFileName = FileController.getCurrentFileName();
//        File currentFile = FileController.getCurrentFile();

        Stage thisStage = ((Stage) getScene().getWindow());
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("png files (*.png)", "*.png");

        fileChooser.setInitialFileName(currentFileName);
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showSaveDialog(thisStage);

        if (file != null) {
            try {
                WritableImage writableImage = new WritableImage((int) mainCanvas.getWidth(), (int) mainCanvas.getHeight());
                mainCanvas.snapshot(null, writableImage);
                RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
                ImageIO.write(renderedImage, "png", file);

                // this will change the window title to the current file
                FileController.setCurrentFile(file);
                FileController.setCurrentFileName(file.getName());
                thisStage.setTitle(currentFileName + " - VecLab");

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }

    @FXML
    public void handleExitAction(ActionEvent actionEvent) {
        String currentFileName = FileController.getCurrentFileName();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("");
        alert.setHeaderText("Save changes to document \"" + currentFileName + "\" before closing?");
        alert.setContentText("If you close without saving, your changes will be discarded.");

        ButtonType discardButton = new ButtonType("Discard and exit");
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        ButtonType saveButton = new ButtonType("Save");

        alert.getButtonTypes().setAll(discardButton, cancelButton, saveButton);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == discardButton) {
            Platform.exit();
        } else if (result.get() == saveButton) {
            handleSaveAction(actionEvent);
        }

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
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About VecLab");
        alert.setHeaderText("VecLab 0.1\na small vector painting program illustrating the power of Bezier curves");
        alert.setContentText("Developed by:\n" +
                "\tAnton Atanasov\n\tDmytro Sytnikov\n\tIllya Poeta\n\tYuriy Skoryk");
        alert.showAndWait();
    }

    public CanvasController getMainCanvas() {
        return mainCanvas;
    }

    public void setMainCanvas(CanvasController mainCanvas) {
        this.mainCanvas = mainCanvas;
    }
}
