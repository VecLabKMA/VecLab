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
import main_java.controllers.main_window.MainWindowPanelController;
import main_java.controllers.tutorial_window.TutorialWindowController;
import logic.ShapeManager;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class MenuBarController extends VBox {

    private CanvasController mainCanvas;
    private MainWindowPanelController mainWindow;

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

    public void init(MainWindowPanelController mainWindow) {
        this.mainWindow = mainWindow;
    }

    @FXML
    public void handleNewAction(ActionEvent actionEvent) {
        String currentFileName = FileController.getCurrentFileName();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("");
        alert.setHeaderText("Зберегти зміни у документі \"" + currentFileName + "\" перед створенням нового файлу?");
        alert.setContentText("Якщо ви створите новий файл, не зберігши старий, усі зміни будуть скасовані.");

        ButtonType discardButton = new ButtonType("Не зберігати");
        ButtonType cancelButton = new ButtonType("Скасувати", ButtonBar.ButtonData.CANCEL_CLOSE);
        ButtonType saveButton = new ButtonType("Зберігти");

        alert.getButtonTypes().setAll(discardButton, cancelButton, saveButton);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == discardButton) {
            FileController.createNewFile();
            mainCanvas.reloadShapeManager();
        } else if (result.get() == saveButton) {
            handleExportImageAction(actionEvent);
            FileController.createNewFile();
            mainCanvas.reloadShapeManager();
        }
    }

    @FXML
    public void handleOpenAction(ActionEvent actionEvent) {
        String currentFileName = FileController.getCurrentFileName();
        File currentFile = FileController.getCurrentProjectFile();

        Stage thisStage = ((Stage) getScene().getWindow());
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("vlp files (*.vlp)", "*.vlp");

        fileChooser.setInitialFileName(currentFileName);
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(thisStage);

        if (file != null) {
            ShapeManager.OpenFromFile(file.getName(), mainCanvas);
            CanvasController.sm = ShapeManager.manager;

            // this will change the window title to the current file
            FileController.setCurrentProjectFile(file);
            FileController.setCurrentFileName(file.getName());
            thisStage.setTitle(currentFileName + " - VecLab");
        }

    }

    @FXML
    public void handleExportImageAction(ActionEvent actionEvent) {
        String currentFileName = FileController.getCurrentFileName();

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

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }

    @FXML
    public void handleSaveProjectAction(ActionEvent actionEvent) {
        String currentFileName = FileController.getCurrentFileName();
        File currentFile = FileController.getCurrentProjectFile();

        Stage thisStage = ((Stage) getScene().getWindow());
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("vlp files (*.vlp)", "*.vlp");


        fileChooser.setInitialFileName("Untitled.vlp");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showSaveDialog(thisStage);

        if (file != null) {
            ShapeManager.SaveToFile(file);

            // this will change the window title to the current file
            FileController.setCurrentProjectFile(file);
            FileController.setCurrentFileName(file.getName());
            thisStage.setTitle(currentFileName + " - VecLab");
        }
    }

    @FXML
    public void handleExitAction(ActionEvent actionEvent) {
        String currentFileName = FileController.getCurrentFileName();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("");
        alert.setHeaderText("Зберегти зміни у документі \\\"\" + currentFileName + \"\\\" перед закриттям програми?");
        alert.setContentText("Якщо не зберігти документ, усі зміни будуть скасовані.");

        ButtonType discardButton = new ButtonType("Вийти без зберігання");
        ButtonType cancelButton = new ButtonType("Не виходити", ButtonBar.ButtonData.CANCEL_CLOSE);
        ButtonType saveButton = new ButtonType("Зберегти та вийти");

        alert.getButtonTypes().setAll(discardButton, cancelButton, saveButton);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == discardButton) {
            Platform.exit();
        } else if (result.get() == saveButton) {
            handleExportImageAction(actionEvent);
        }

    }

    @FXML
    public void handleTutorialAction(ActionEvent actionEvent) {
        new TutorialWindowController(mainWindow);
    }

    @FXML
    public void handleAboutAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Про VecLab");
        alert.setHeaderText("VecLab 0.1\nНепрофесійний редактор векторної графіки, призначений для показу важливості кривих Безьє");
        alert.setContentText("Розробники:\n" +
                "\tАнтон Атанасов\n\tДмитро Ситніков\n\tІлья Поета\n\tЮрій Скорик");
        alert.showAndWait();
    }

    public CanvasController getMainCanvas() {
        return mainCanvas;
    }

    public void setMainCanvas(CanvasController mainCanvas) {
        this.mainCanvas = mainCanvas;
    }
}
