package skyvssea.view;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import skyvssea.util.ButtonUtil;
import skyvssea.util.ColorUtil;

public class LandingPage extends AnchorPane {

    public static final int LANDING_PAGE_HEIGHT = 500;
    public static final int LANDING_PAGE_WIDTH = 1100;

    public LandingPage() {
        ImageView backgroundImage = new ImageView("file:resources/images/landing-page-background.jpg");
        Label titleLable = new Label("Sky vs. Sea");
        Button startBtn = new Button("Start");
        Button loadBtn = new Button("Load");
        VBox controlPane = new VBox(titleLable, startBtn, loadBtn);
        this.getChildren().addAll(backgroundImage, controlPane);

        this.setPrefSize(LANDING_PAGE_WIDTH, LANDING_PAGE_HEIGHT);

        formatBackgroundImage(backgroundImage);
        formatControlPane(controlPane);
        formatTitleLabel(titleLable);
        formatStartBtn(startBtn);
        formatLoadBtn(loadBtn);
        formatButtons(startBtn, loadBtn);
    }

    private void formatBackgroundImage(ImageView image) {
        image.setFitHeight(LANDING_PAGE_HEIGHT);
//        image.setFitWidth(1200);
        image.setPreserveRatio(true);
    }

    private void formatControlPane(VBox controlPane) {
        AnchorPane.setTopAnchor(controlPane, 0.0);
        AnchorPane.setLeftAnchor(controlPane, 60.0);

        controlPane.setPadding(new Insets(30));
        controlPane.setSpacing(15);
        controlPane.setEffect(new DropShadow());
        controlPane.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(0, 0, 30, 30, false), null)));
    }

    private void formatTitleLabel(Label label) {
        label.setFont(Font.loadFont("file:resources/fonts/Roboto/Roboto-Bold.ttf", 50));
    }

    private void formatButtons(Button... buttons) {
        for (Button button : buttons) {
            ButtonUtil.setCornerRadii(button, new CornerRadii(10));
            button.setPrefHeight(60);
            button.setMaxWidth(Double.MAX_VALUE);
            button.setStyle("-fx-font-size: 15; -fx-font-weight: bold;");
            button.setOnMouseEntered(e -> ButtonUtil.formatHoveringEffect(button, true));
            button.setOnMouseExited(e -> ButtonUtil.formatHoveringEffect(button, false));
        }
    }

    private void formatStartBtn(Button button) {
        button.setDefaultButton(true);
        ButtonUtil.formatStandardButton(button, ColorUtil.STANDARD_BUTTON_COLOR);
        ButtonUtil.formatGraphic(button, "file:resources/icons/play.png");
    }

    private void formatLoadBtn(Button button) {
        ButtonUtil.formatStandardButton(button, ColorUtil.SECONDARY_BUTTON_COLOR);
        ButtonUtil.formatGraphic(button, "file:resources/icons/load.png");    }
}
