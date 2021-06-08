package main_java.controllers.main_window;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import main_java.controllers.canvas.CanvasController;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

public class MainWindowController extends Application {
    @FXML
    private MenuBar menuBar = new MenuBar();

    @Override
    public void start(Stage primaryStage) throws Exception {
        CustomPanelController pane = new CustomPanelController();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/view/main_window/MainWindowView.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);

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
//        Node node = (Node) actionEvent.getSource();
//        Stage thisStage = (Stage) node.getScene().getWindow();
//
//        FileChooser fileChooser = new FileChooser();
//
//        FileChooser.ExtensionFilter extFilter =
//                new FileChooser.ExtensionFilter("png files (*.png)", "*.png");
//        fileChooser.getExtensionFilters().add(extFilter);
//
//        File file = fileChooser.showSaveDialog(thisStage);
//
//        if(file != null){
//            try {
//                WritableImage writableImage = new WritableImage((int)canvas.getWidth(), (int)canvas.getHeight());
//                canvas.snapshot(null, writableImage);
//                RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
//                ImageIO.write(renderedImage, "png", file);
//            } catch (IOException ex) {
//                ex.printStackTrace();
//            }
//        }
//
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
}
