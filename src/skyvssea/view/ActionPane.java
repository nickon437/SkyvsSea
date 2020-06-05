package skyvssea.view;

import com.google.java.contract.Requires;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import skyvssea.controller.Controller;
import skyvssea.util.AnimationUtil;
import skyvssea.util.ButtonUtil;
import skyvssea.util.ColorUtil;
import skyvssea.util.RegionUtil;

import java.util.Timer;
import java.util.TimerTask;

public class ActionPane extends VBox {
    private StackPane buttonHolderStack = new StackPane();
    private HBox buttonHolder = new HBox();
    private Pane actionIndicator = new Pane();
    private Button killBtn = new Button("Kill");
    private Button activeEffectBtn = new Button("Active Effect");
    private ToggleButton passiveEffectBtn = new ToggleButton("Passive Effect");
	private Button endBtn = new Button("End");
    private ProgressBar timerProgressBar = new ProgressBar();
    private double runningTimer;

	private Button selectedActionBtn; // To shift the actionIndicator appropriately when the board is resize

    private AdvancedActionPane advancedActionPane;

    private static final double DEFAULT_DURATION = 30;

    @Requires("controller != null")
    public ActionPane(Controller controller) {
        advancedActionPane = new AdvancedActionPane(controller);
        this.getChildren().addAll(actionIndicator, buttonHolderStack, advancedActionPane, new HBox(timerProgressBar));
        this.setSpacing(5);

        buttonHolderStack.getChildren().addAll(buttonHolder, passiveEffectBtn);
        buttonHolder.getChildren().addAll(killBtn, activeEffectBtn, endBtn);
        buttonHolder.setSpacing(ButtonUtil.BUTTON_SPACING);

        formatActionIndicator(actionIndicator);
        formatKillBtn(killBtn, controller);
        formatActiveEffectBtn(activeEffectBtn, controller);
        formatPassiveEffectBtn(passiveEffectBtn, controller);
        formatEndBtn(endBtn, controller);
        formatTimerProgressBar(timerProgressBar);
    }

    @Requires("indicator != null")
    private void formatActionIndicator(Pane indicator) {
        indicator.setPrefHeight(3);
        indicator.setMaxWidth(0);
        RegionUtil.setBackground(indicator, ColorUtil.STANDARD_BUTTON_COLOR, new CornerRadii(5), null);
        this.widthProperty().addListener(((observable, oldValue, newValue) -> {
            if (selectedActionBtn != null) { shiftActionIndicator(selectedActionBtn); }
        }));
    }

    @Requires("button != null && controller != null")
    private void formatKillBtn(Button button, Controller controller) {
        ButtonUtil.maximizeHBoxControlSize(button);
        ButtonUtil.formatStandardButton(button, ColorUtil.STANDARD_BUTTON_COLOR);
        ButtonUtil.formatGraphic(button, "file:resources/icons/kill.png");
        button.setOnMouseEntered(e -> {
            selectedActionBtn = button;
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

    @Requires("button != null && controller != null")
    private void formatPassiveEffectBtn(ToggleButton button, Controller controller) {
    	ButtonUtil.maximizeHBoxControlSize(button);
        ButtonUtil.formatStandardButton(button, ColorUtil.STANDARD_BUTTON_COLOR);
        ButtonUtil.formatHoveringEffect(button);
        button.setOnAction(e -> {
            Color buttonColor = button.isSelected() ? ColorUtil.ACTIVATED_BUTTON_COLOR : ColorUtil.STANDARD_BUTTON_COLOR;
            ButtonUtil.setFill(button, buttonColor);
        	controller.handlePassiveEffectButton();        		
        });
    }

    @Requires("button != null && controller != null")
    private void formatActiveEffectBtn(Button button, Controller controller) {
        ButtonUtil.maximizeHBoxControlSize(button);
        ButtonUtil.formatStandardButton(button, ColorUtil.STANDARD_BUTTON_COLOR);
        ButtonUtil.formatGraphic(button, "file:resources/icons/special-effect.png");
        activeEffectBtn.widthProperty().addListener(((observable, oldValue, newValue) -> {
            if (buttonHolder.isVisible()) { shiftPassiveEffectBtn((double) newValue); }
        }));
        button.setOnMouseEntered(e -> {
            selectedActionBtn = button;
            ButtonUtil.formatHoveringEffect(button, true);
            controller.handleMouseEnteredActiveEffectBtn();
        });
        button.setOnMouseExited(e -> {
            ButtonUtil.formatHoveringEffect(button, false);
            controller.handleMouseExitedActiveEffectBtn();
        });
        button.setOnAction(e -> {
            shiftActionIndicator(button);
            controller.handleActiveEffectButton();
        });
    }

    @Requires("button != null && controller != null")
    private void formatEndBtn(Button button, Controller controller) {
        ButtonUtil.maximizeHBoxControlSize(button);
        ButtonUtil.formatStandardButton(button, ColorUtil.SECONDARY_BUTTON_COLOR);
        ButtonUtil.formatGraphic(button, "file:resources/icons/end-turn.png");
        button.setCancelButton(true);
        ButtonUtil.formatHoveringEffect(button);
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
            xCoord += ((ButtonBase) buttonHolder.getChildren().get(i)).getWidth() + ButtonUtil.BUTTON_SPACING;
        }
        double width = ((ButtonBase) buttonHolder.getChildren().get(buttonIndex)).getWidth();
        setActionIndicatorPosition(xCoord, width);
    }

    public void hideActionIndicator() {
        setActionIndicatorPosition(this.getWidth(), 0);
        selectedActionBtn = null;
    }

    private void setActionIndicatorPosition(double xTranslate, double width) {
        KeyFrame kfXCoord = AnimationUtil.formatKeyFrame(actionIndicator.translateXProperty(), xTranslate, Duration.seconds(0.5));
        KeyFrame kfWidth = AnimationUtil.formatKeyFrame(actionIndicator.maxWidthProperty(), width, Duration.seconds(0.5));
        Timeline timeline = new Timeline(kfXCoord, kfWidth);
        timeline.play();
    }

    @Requires("controller != null")
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

    public void setPassiveEffectBtnFocus(boolean isFocused) {
        if (isFocused) {
            buttonHolder.setVisible(false);
            passiveEffectBtn.setVisible(true);
            passiveEffectBtn.setMaxWidth(Double.MAX_VALUE);
        } else {
            buttonHolder.setVisible(true);
            if (passiveEffectBtn.isSelected()) {
                double newWidth = activeEffectBtn.getWidth();
                shiftPassiveEffectBtn(newWidth);
            } else {
                passiveEffectBtn.setVisible(false);
            }
        }
    }

    private void shiftPassiveEffectBtn(double width) {
        passiveEffectBtn.setMaxWidth(width + 3); // 3 for the offset
    }

    public ToggleButton getPassiveEffectBtn() {
        return passiveEffectBtn;
    }

    public void setActiveEffectBtnDisable(boolean isDisabled) {
    	activeEffectBtn.setDisable(isDisabled);
    }

    public void setPassiveEffectBtnActivated(boolean isActivated) {
        passiveEffectBtn.setSelected(isActivated);
        Color buttonColor = isActivated ? ColorUtil.ACTIVATED_BUTTON_COLOR : ColorUtil.STANDARD_BUTTON_COLOR;
        ButtonUtil.setFill(passiveEffectBtn, buttonColor);
    }

    public void setPassiveEffectBtnDisable(boolean isDisabled) {
        passiveEffectBtn.setDisable(isDisabled);
        passiveEffectBtn.setOpacity(1);

        if (!passiveEffectBtn.isSelected()) {
            Color buttonColor = isDisabled ? ColorUtil.STANDARD_DISABLED_COLOR : ColorUtil.STANDARD_BUTTON_COLOR;
            RegionUtil.setFill(passiveEffectBtn, buttonColor);
        }
    }

    public void disableAndDeactivatePassiveEffectBtn() {
        setPassiveEffectBtnActivated(false);
        setPassiveEffectBtnDisable(true);
    }
    
    public void setUndoBtnDisable(boolean isDisabled) {
        advancedActionPane.setUndoBtnDisable(isDisabled);
    }

    public void fireKillBtn() {
        killBtn.fire();
    }

    public void fireActiveEffectBtn() {
        activeEffectBtn.fire();
    }
}
