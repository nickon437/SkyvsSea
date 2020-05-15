package skyvssea.view;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.layout.*;
import javafx.util.Duration;
import skyvssea.controller.Controller;
import skyvssea.util.ButtonUtil;
import skyvssea.util.ColorUtil;

public class ActionPane extends VBox {
    private HBox buttonHolder = new HBox();
    private Pane actionIndicator = new Pane();
    private Button killBtn = new Button("Kill");
    private Button specialEffectBtn = new Button("Special Effect");
    private Button endBtn = new Button("End");

    private AdvancedActionPane advancedActionPane;

    public ActionPane(Controller controller) {
        this.advancedActionPane = new AdvancedActionPane(controller);
        this.getChildren().addAll(actionIndicator, buttonHolder, advancedActionPane);
        this.setSpacing(5);

        buttonHolder.getChildren().addAll(killBtn, specialEffectBtn, endBtn);
        buttonHolder.setSpacing(ButtonUtil.BUTTON_SPACING);

        formatActionIndicator();
        formatKillBtn(killBtn, controller);
        formatSpecialEffectBtn(specialEffectBtn, controller);
        formatEndBtn(endBtn, controller);
    }

    private void formatActionIndicator() {
        actionIndicator.setPrefHeight(3);
        actionIndicator.setMaxWidth(0);
        actionIndicator.setBackground(new Background(new BackgroundFill(ColorUtil.STANDARD_BUTTON_COLOR, new CornerRadii(5), null)));
    }

    private void formatKillBtn(Button button, Controller controller) {
        ButtonUtil.maximizeHBoxControlSize(button);
        ButtonUtil.formatStandardButton(button, ColorUtil.STANDARD_BUTTON_COLOR);
        ButtonUtil.formatGraphic(button, "resources/icons/kill.png");
        button.setOnMouseEntered(e -> {
            ButtonUtil.formatHoveringEffect(button, true);
            controller.handleMouseEnteredKillBtn();
        });
        button.setOnMouseExited(e -> {
            ButtonUtil.formatHoveringEffect(button, false);
            controller.handleMouseExitedKillBtn();
        });
        button.setOnAction(e -> {
            shiftActionIndicator(button);
            controller.handleKillButton();
        });
    }

    private void formatSpecialEffectBtn(Button button, Controller controller) {
        ButtonUtil.maximizeHBoxControlSize(button);
        ButtonUtil.formatStandardButton(button, ColorUtil.STANDARD_BUTTON_COLOR);
        ButtonUtil.formatGraphic(button, "resources/icons/special-effect.png");
        button.setOnMouseEntered(e -> {
            ButtonUtil.formatHoveringEffect(button, true);
            controller.handleMouseEnteredSpecialEffectBtn();
        });
        button.setOnMouseExited(e -> {
            ButtonUtil.formatHoveringEffect(button, false);
            controller.handleMouseExitedSpecialEffectBtn();
        });
        button.setOnAction(e -> {
            shiftActionIndicator(button);
            controller.handleSpecialEffectButton();
        });
    }

    private void formatEndBtn(Button button, Controller controller) {
        ButtonUtil.maximizeHBoxControlSize(button);
        ButtonUtil.formatStandardButton(button, ColorUtil.SECONDARY_BUTTON_COLOR);
        ButtonUtil.formatGraphic(button, "resources/icons/end-turn.png");
        button.setCancelButton(true);
        button.setOnMouseEntered(e -> ButtonUtil.formatHoveringEffect(button, true));
        button.setOnMouseExited(e -> ButtonUtil.formatHoveringEffect(button, false));
        button.setOnAction(e -> {
            shiftActionIndicator(button);
            controller.handleEndButton();
        });
    }



    private void shiftActionIndicator(Button button) {
        int buttonIndex = buttonHolder.getChildren().indexOf(button);
        double xCoord = 0;
        for (int i = 0; i < buttonIndex; i++) {
            xCoord += ((Button) buttonHolder.getChildren().get(i)).getWidth() + ButtonUtil.BUTTON_SPACING;
        }
        double width = ((Button) buttonHolder.getChildren().get(buttonIndex)).getWidth();
        setActionIndicatorPosition(xCoord, width);
    }

    public void hideActionIndicator() {
        setActionIndicatorPosition(this.getWidth(), 0);
    }

    private void setActionIndicatorPosition(double xTranslate, double width) {
        Timeline timeline = new Timeline();
        KeyValue kvXCoord = new KeyValue(actionIndicator.translateXProperty(), xTranslate, Interpolator.EASE_IN);
        KeyFrame kfXCoord = new KeyFrame(Duration.seconds(0.5), kvXCoord);
        KeyValue kvWidth = new KeyValue(actionIndicator.maxWidthProperty(), width, Interpolator.EASE_IN);
        KeyFrame kfWidth = new KeyFrame(Duration.seconds(0.5), kvWidth);
        timeline.getKeyFrames().addAll(kfXCoord, kfWidth);
        timeline.play();
    }

    public void setRegularActionPaneDisable(boolean isDisabled) {
        buttonHolder.setDisable(isDisabled);
    }

    public void setSpecialEffectBtnDisable(boolean isDisabled) {
        specialEffectBtn.setDisable(isDisabled);
    }
}
