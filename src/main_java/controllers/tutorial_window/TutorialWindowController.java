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
        buttons.setAlignment(Pos.CENTER);

        window.initModality(Modality.WINDOW_MODAL);
        window.setTitle("Привіт!");
        InputStream iconStream = getClass().getResourceAsStream("/resources/assets/images/about-icon.png");
        Image image = new Image(iconStream);
        window.getIcons().add(image);
        window.setMinWidth(350);

        label = new Label();
        label.setMaxWidth(500);
        label.setWrapText(true);
        label.setText("Ласкаво просимо до VecLab, нашого навчального редактору векторної графіки!");

        LinkedList<String[]> tutorialTexts = new LinkedList<>();
        {
            tutorialTexts.add(new String[]{
                    "Що це?",
                    "Цей редактор розрахований на тих, кто не знає, як працює векторна графіка, і хоче навчитись",
                    "Зрозуміло"
            });
            tutorialTexts.add(new String[]{
                    "Як вона працює?",
                    "Бачиш панель вгорі? Там є декілька режимів малювання: без малювання, крива Безьє, еліпс, прямокутник та трикутник",
                    "Крива Безьє?"
            });
            tutorialTexts.add(new String[]{
                    "Що таке крива Безьє?",
                    "Уяви, що в тебе є три точки 1, 2 і 3. З'єднай їх так: 1->2->3",
                    "Так?"
            });
            tutorialTexts.add(new String[]{
                    "Що таке крива Безьє?",
                    "Поділи отримані відрізки на декілька частин (бажано кількома десятками точок). З'єднай першу точку відрізка 1-2 з першою точкою відрзіка 2-3. Отриманий відрізок так само поділи такою ж кількістю точок. Обери першу та виділи її",
                    "Зачекай, я не можу так швидко..."
            });
            tutorialTexts.add(new String[]{
                    "Що таке крива Безьє?",
                    "Так само з'єднуєш другі точки на початкових відрізках, ділиш отриманий відрізок на декілька частин, виділяєш другу точку, і так далі, поки не дійдеш до останньої точки. Вітаю, ти створив криву Безьє!",
                    "Це займає стільки часу!"
            });
            tutorialTexts.add(new String[]{
                    "Розумію",
                    "Так, але приблизно так само ці криві створюються тут та в інших векторних редакторах, просто комп'ютер здатний зробити це за долю секунди",
                    "А як мені тут це зробити?"
            });
            tutorialTexts.add(new String[]{
                    "Як малювати криві Безьє",
                    "Бачиш вгорі кнопку, на якій намальовано криву лінію? Натисни на неї",
                    "Так"
            });
            tutorialTexts.add(new String[]{
                    "Як малювати криві Безьє",
                    "Потім натисни лівою кнопкою миші на полі для малювання та відтягни курсор",
                    "Є..."
            });
            tutorialTexts.add(new String[]{
                    "Як малювати криві Безьє",
                    "Ти створив дві опорні точки для кривої. Тепер повтори попередній крок десь в іншому місці поля",
                    "О, щось є"
            });
            tutorialTexts.add(new String[]{
                    "Як малювати криві Безьє",
                    "Так, це і є крива Безьє з чотирма опорними точками. Я пояснював на прикладі з трьома точками, але воно працює так само для будь-якої кількості точок, якщо їх більше двох.",
                    "І що тепер?"
            });
            tutorialTexts.add(new String[]{
                    "Ну...",
                    "Як я вже сказав, в нас також є прості геометричні фігури. Натисни кнопку з овалом/кругом/прямокутником, потім на поле малювання та відтягни курсор. Точка на полі, де ти натиснув, буде центром фігури, і від того, як далеко ти відтягуєш курсор, залежить розмір фігури.",
                    "Зрозуміло."
            });
            tutorialTexts.add(new String[]{
                    "Ну, тоді...",
                    "Це все, що я мав розповісти про малювання фігур та як вони працюють. Якщо ти забув, як тут все працює, повертайся, я розповім.",
                    "Бувай"
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

        Button closeButton = new Button("Вийти з вікна навчання");
        closeButton.setOnAction(event -> window.close());

        buttons.getChildren().addAll(changeTextsButton, closeButton);
        mainContentPane.getChildren().addAll(label, buttons);
        mainContentPane.setAlignment(Pos.CENTER);

        mainPane.getChildren().add(mainContentPane);
        Scene scene = new Scene(mainPane);
        window.setAlwaysOnTop(true);
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
            Platform.runLater(() -> mainWindow.toolsPanel.drawCurve.setBorder(new Border(new BorderStroke(Color.RED,
                    BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(3)))));
            while (true) {}
        }, () -> {
            Platform.runLater(() -> mainWindow.toolsPanel.drawCurve.setBorder(new Border(new BorderStroke(Color.RED,
                    BorderStrokeStyle.NONE, CornerRadii.EMPTY, new BorderWidths(3)))));
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
