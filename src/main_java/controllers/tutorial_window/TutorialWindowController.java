package main_java.controllers.tutorial_window;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main_java.controllers.main_window.MainWindowPanelController;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Thread.sleep;

public class TutorialWindowController {

    static Stage window;
    static Label label;
    static Button changeTextsButton;
    private MainWindowPanelController mainWindow;
    private HBox mainPane;

    public TutorialWindowController(MainWindowPanelController mainWindow) {
        this.mainWindow = mainWindow;
        window = new Stage();

        mainPane = new HBox();
        VBox mainContentPane = new VBox();

        mainContentPane.setSpacing(25);
        mainContentPane.setPadding(new Insets(25, 25, 0,25));
        HBox buttons = new HBox();
        buttons.setPadding(new Insets(0, 25, 25,0));
        buttons.setSpacing(25);
        buttons.setAlignment(Pos.CENTER_RIGHT);

        window.initModality(Modality.WINDOW_MODAL);
        window.setTitle("Hello!");
        InputStream iconStream = getClass().getResourceAsStream("/resources/assets/images/about-icon.png");
        Image image = new Image(iconStream);
        window.getIcons().add(image);
        window.setMinWidth(350);

        label = new Label();
        label.setMaxWidth(500);
        label.setWrapText(true);
        label.setText("Welcome to VecLab, our educational vector graphics editor!");

        LinkedList<String[]> tutorialTexts = new LinkedList<>();
        {
            tutorialTexts.add(new String[]{
                    "What is this program?",
                    "It's intended as simple, amateur editor to teach people what vector graphics are",
                    "Okay"
            });
            tutorialTexts.add(new String[]{
                    "How does it work?",
                    "It has a couple drawing modes: Bezier curve, ellipsis, rectangle, triangle and polygon.",
                    "Wait, what is a Bezier curve?"
            });
            tutorialTexts.add(new String[]{
                    "What is a Bezier curve?",
                    "In a nutshell, imagine a point moving between two points on a straight line. A program records this point's position at equal time intervals and joins them at the end, making (in this case) a Bezier line.",
                    "But how is it a curve, if it's a line?"
            });
            tutorialTexts.add(new String[]{
                    "What is a Bezier curve?",
                    "The same procedure applies when three or more points used. You have control points P0, P1 and P2. Now, add point Q0 for P0P1 segment, and point Q1 for P1P2 segment.",
                    "Go on"
            });
            tutorialTexts.add(new String[]{
                    "What is a Bezier curve?",
                    "Points Q0 and Q1 are moving along their respective segments. When recording intermediary positions, the program joins those points into Q0Q1 segment and adds another point B to this segment. This point moves and is being recorded at the same rate as points Q0 and Q1, and its recorded positions are joined together into a curve",
                    "Sounds complicated *brain frying sounds*"
            });
            tutorialTexts.add(new String[]{
                    "Oh, well",
                    "Yeah, I know, it's quite technical, so, if you want, you can read about its mathematical details on Internet",
                    "How do I draw one?"
            });
            tutorialTexts.add(new String[]{
                    "How to draw a Bezier curve",
                    "Press a button that represents a curved line or press 1 on your keyboard. You'll enter pen drawing mode",
                    "Okay, what now?"
            });
            tutorialTexts.add(new String[]{
                    "How to draw a Bezier curve",
                    "Press a mouse button and drag it somewhere on drawing space",
                    "There's something weird..."
            });
            tutorialTexts.add(new String[]{
                    "How to draw a Bezier curve",
                    "You created two control points for a curve. Now do it again",
                    "Woah..."
            });
            tutorialTexts.add(new String[]{
                    "How to draw a Bezier curve",
                    "Yes. This is a Bezier curve with four control points. You can move them across the canvas to make the shape you need. Or to play around",
                    "So... What now?"
            });
            tutorialTexts.add(new String[]{
                    "Well...",
                    "This was pretty much the most confusing part of an editor, everything else works like in any other editor. We have simple shapes, color changing etc. It works just like in MS Paint.",
                    "Oh, okay. Bye, then."
            });
            tutorialTexts.add(new String[]{
                    "Bye",
                    "If you need help with Bezier curves again, you can come here.\n See you later",
                    "Bye."
            });
        }
        Iterator<String[]> it = tutorialTexts.iterator();
        List<TutorialAnimation> animations = getAnimations();
        AtomicInteger currentAnimationIndex = new AtomicInteger();

        changeTextsButton = new Button("Oh, hi!");
        changeTextsButton.setOnAction(event -> {
            if (it.hasNext()) {
                if (currentAnimationIndex.get() != 0) {
                    animations.get(currentAnimationIndex.get() - 1).endAnimation();
                }
                animations.get(currentAnimationIndex.getAndIncrement()).start();
                String[] texts = it.next();
                display(texts[0], texts[1], texts[2]);
            } else {
                window.close();
            }
        });

        Button closeButton = new Button("I don't need a tutorial, close the window!");
        closeButton.setOnAction(event -> window.close());

        buttons.getChildren().addAll(changeTextsButton, closeButton);
        mainContentPane.getChildren().addAll(label, buttons);
        mainContentPane.setAlignment(Pos.CENTER);

        mainPane.getChildren().add(mainContentPane);
        Scene scene = new Scene(mainPane);
        window.setScene(scene);
        window.showAndWait();
    }

    private List<TutorialAnimation> getAnimations() {
        ArrayList<TutorialAnimation> animations = new ArrayList<>(10);

        animations.add(new TutorialAnimation(() -> {}, () -> {}));

        animations.add(new TutorialAnimation(() -> {
            mainWindow.toolsPanel.drawTriangle.setBorder(new Border(new BorderStroke(Color.RED,
                    BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(3))));
            mainWindow.toolsPanel.drawRectangle.setBorder(new Border(new BorderStroke(Color.RED,
                    BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(3))));
            mainWindow.toolsPanel.drawPolygon.setBorder(new Border(new BorderStroke(Color.RED,
                    BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(3))));
            mainWindow.toolsPanel.drawEllipse.setBorder(new Border(new BorderStroke(Color.RED,
                    BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(3))));
            mainWindow.toolsPanel.drawCurve.setBorder(new Border(new BorderStroke(Color.RED,
                    BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(3))));
            try {
                sleep(2000);
            } catch (InterruptedException ignored) {

            }
        }, () -> {
            mainWindow.toolsPanel.drawTriangle.setBorder(new Border(new BorderStroke(Color.RED,
                    BorderStrokeStyle.NONE, CornerRadii.EMPTY, new BorderWidths(3))));
            mainWindow.toolsPanel.drawRectangle.setBorder(new Border(new BorderStroke(Color.RED,
                    BorderStrokeStyle.NONE, CornerRadii.EMPTY, new BorderWidths(3))));
            mainWindow.toolsPanel.drawPolygon.setBorder(new Border(new BorderStroke(Color.RED,
                    BorderStrokeStyle.NONE, CornerRadii.EMPTY, new BorderWidths(3))));
            mainWindow.toolsPanel.drawEllipse.setBorder(new Border(new BorderStroke(Color.RED,
                    BorderStrokeStyle.NONE, CornerRadii.EMPTY, new BorderWidths(3))));
            mainWindow.toolsPanel.drawCurve.setBorder(new Border(new BorderStroke(Color.RED,
                    BorderStrokeStyle.NONE, CornerRadii.EMPTY, new BorderWidths(3))));
        }));
        Image i = new Image(new File("location/file.gif").toURI().toString());
        ImageView imgView = new ImageView(i);
        imgView.setFitHeight(50);
        imgView.setFitWidth(50);
        animations.add(new TutorialAnimation(() -> {
//            toolsP
            Platform.runLater(() -> {
                mainPane.getChildren().add(imgView);});
                while (true) {
//                    imgView
                    break;
                }
        }, () -> {

            Platform.runLater(() -> {
                mainPane.getChildren().remove(imgView);});
        }));
        animations.add(new TutorialAnimation(() -> {
        }, () -> {}));
        animations.add(new TutorialAnimation(() -> {}, () -> {}));
        animations.add(new TutorialAnimation(() -> {}, () -> {}));
        animations.add(new TutorialAnimation(() -> {

            mainWindow.toolsPanel.drawCurve.setBorder(new Border(new BorderStroke(Color.RED,
                    BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(3))));
            while (true) {}
        }, () -> {

            mainWindow.toolsPanel.drawCurve.setBorder(new Border(new BorderStroke(Color.RED,
                    BorderStrokeStyle.NONE, CornerRadii.EMPTY, new BorderWidths(3))));
        }));
        animations.add(new TutorialAnimation(() -> {}, () -> {}));
        animations.add(new TutorialAnimation(() -> {}, () -> {}));
        animations.add(new TutorialAnimation(() -> {}, () -> {}));
        animations.add(new TutorialAnimation(() -> {}, () -> {}));
        animations.add(new TutorialAnimation(() -> {}, () -> {}));
        animations.add(new TutorialAnimation(() -> {}, () -> {}));
        animations.add(new TutorialAnimation(() -> {}, () -> {}));

        return animations;
    }

    public static void display(String title, String message, String buttonText) {
        window.setTitle(title);
        label.setText(message);
        changeTextsButton.setText(buttonText);
        window.sizeToScene();
    }

}
