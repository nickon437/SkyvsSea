package skyvssea.view;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.*;
import javafx.util.Duration;
import skyvssea.controller.Controller;
import skyvssea.util.AnimationUtil;
import skyvssea.util.ButtonUtil;
import skyvssea.util.ColorUtil;
import skyvssea.util.RegionUtil;

import java.util.Timer;
import java.util.TimerTask;

public class ActionPane extends VBox {
    private HBox buttonHolder = new HBox();
    private Pane actionIndicator = new Pane();
    private Button killBtn = new Button("Kill");
    private Button specialEffectBtn = new Button("Special Effect");
    private Button endBtn = new Button("End");
    private ProgressBar timerProgressBar = new ProgressBar();
    private double runningTimer;

    private AdvancedActionPane advancedActionPane;

    private static final double DEFAULT_DURATION = 30;

    public ActionPane(Controller controller) {
        advancedActionPane = new AdvancedActionPane(controller);
        this.getChildren().addAll(actionIndicator, buttonHolder, advancedActionPane, new HBox(timerProgressBar));
        this.setSpacing(5);

        buttonHolder.getChildren().addAll(killBtn, specialEffectBtn, endBtn);
        buttonHolder.setSpacing(ButtonUtil.BUTTON_SPACING);

        formatActionIndicator(actionIndicator);
        formatKillBtn(killBtn, controller);
        formatSpecialEffectBtn(specialEffectBtn, controller);
        formatEndBtn(endBtn, controller);
        formatTimerProgressBar(timerProgressBar);
    }

    private void formatActionIndicator(Pane indicator) {
        indicator.setPrefHeight(3);
        indicator.setMaxWidth(0);
        RegionUtil.setBackground(indicator, ColorUtil.STANDARD_BUTTON_COLOR, new CornerRadii(5), null);
    }

    private void formatKillBtn(Button button, Controller controller) {
        ButtonUtil.maximizeHBoxControlSize(button);
        ButtonUtil.formatStandardButton(button, ColorUtil.STANDARD_BUTTON_COLOR);
        ButtonUtil.formatGraphic(button, "file:resources/icons/kill.png");
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
        ButtonUtil.formatGraphic(button, "file:resources/icons/special-effect.png");
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
        ButtonUtil.formatGraphic(button, "file:resources/icons/end-turn.png");
        button.setCancelButton(true);
        button.setOnMouseEntered(e -> ButtonUtil.formatHoveringEffect(button, true));
        button.setOnMouseExited(e -> ButtonUtil.formatHoveringEffect(button, false));
        button.setOnAction(e -> {
            shiftActionIndicator(button);
            controller.handleEndButton();
        });
    }

    private void formatTimerProgressBar(ProgressBar timerProgressBar) {
        ButtonUtil.maximizeHBoxControlSize(timerProgressBar);
        timerProgressBar.setPrefHeight(10);
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
        KeyFrame kfXCoord = AnimationUtil.formatKeyFrame(actionIndicator.translateXProperty(), xTranslate, Duration.seconds(0.5));
        KeyFrame kfWidth = AnimationUtil.formatKeyFrame(actionIndicator.maxWidthProperty(), width, Duration.seconds(0.5));
        timeline.getKeyFrames().addAll(kfXCoord, kfWidth);
        timeline.play();
    }

    public void startTimer(Controller controller) {
        runningTimer = DEFAULT_DURATION;

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    if (runningTimer > 0) {
                        runningTimer = runningTimer - 0.05;
                        timerProgressBar.setProgress(runningTimer / DEFAULT_DURATION);
                    } else {
                        controller.handleEndButton();
                        this.cancel();
                    }
                });
            }
        };

        Timer timer = new Timer();
        timer.schedule(timerTask, 0, 50);
    }

    public void setRegularActionPaneDisable(boolean isDisabled) {
        buttonHolder.setDisable(isDisabled);
    }

    public void setSpecialEffectBtnDisable(boolean isDisabled) {
        specialEffectBtn.setDisable(isDisabled);
    }

    public void setUndoBtnDisable(boolean isDisabled) {
        advancedActionPane.setUndoBtnDisable(isDisabled);
    }
}
