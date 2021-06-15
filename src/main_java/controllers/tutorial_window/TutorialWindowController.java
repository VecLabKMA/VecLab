package main_java.controllers.tutorial_window;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.LinkedList;

public class TutorialWindowController {

    static Stage window;
    static Label label;
    static Button changeTextsButton;

    public TutorialWindowController() {

        window = new Stage();

        VBox mainLayout = new VBox();
        mainLayout.setSpacing(25);
        mainLayout.setPadding(new Insets(25, 25, 0,25));
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
                    "It's intended as simple editor to teach people what vector graphics are",
                    "Okay"
            });
            tutorialTexts.add(new String[]{
                    "How does it work?",
                    "It has a couple drawing modes: Bezier curve, rectangle and ellipsis.",
                    "Wait, what is a Bezier curve?"
            });
            tutorialTexts.add(new String[]{
                    "What is a Bezier curve?",
                    "In a nutshell, imagine a point moving between two points on a straight line. A program records this point's position at equal time intervals and joins them at the end, making (in this case) a Bezier line.",
                    "But how is it a curve, if it's a line?"
            });
            tutorialTexts.add(new String[]{
                    "What is a Bezier curve?",
                    "The same procedure applies when three or more points used. You have control points A, B and C. Now, add point D for AB segment, and point E for BC segment.",
                    "Go on"
            });
            tutorialTexts.add(new String[]{
                    "What is a Bezier curve?",
                    "Points D and E are moving along their respective segments. When recording intermediary positions, the program joins those points into DE segment and adds another point F to this segment. This point moves and is being recorded at the same rate as points D and E, and its recorded positions are joined together into a curve",
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
                    "Yes. This is a Bezier curve with four control points",
                    "Woah..."
            });
        }
        Iterator<String[]> it = tutorialTexts.iterator();

        changeTextsButton = new Button("Oh, hi!");
        changeTextsButton.setOnAction(event -> {
            if (it.hasNext()) {
                String[] texts = it.next();
                display(texts[0], texts[1], texts[2]);
            }
        });

        Button closeButton = new Button("I don't need a tutorial, close the window!");
        closeButton.setOnAction(event -> window.close());

        buttons.getChildren().addAll(changeTextsButton, closeButton);
        mainLayout.getChildren().addAll(label, buttons);
        mainLayout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(mainLayout);
        window.setScene(scene);
        window.showAndWait();
    }

    public static void display(String title, String message, String buttonText) {
        window.setTitle(title);
        label.setText(message);
        changeTextsButton.setText(buttonText);
        window.sizeToScene();
    }

}