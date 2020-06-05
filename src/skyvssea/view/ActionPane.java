package skyvssea.view;

import javafx.animation.*;
import javafx.scene.control.Button;
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

public class ActionPane extends VBox {
    private StackPane buttonHolderStack = new StackPane();
    private HBox buttonHolder = new HBox();
    private Pane actionIndicator = new Pane();
    private Button killBtn = new Button("Kill");
    private Button activeEffectBtn = new Button("Active Effect");
    private ToggleButton passiveEffectBtn = new ToggleButton("Passive Effect");
	private Button endBtn = new Button("End");

	private Button selectedActionBtn;

    private AdvancedActionPane advancedActionPane;

    public ActionPane(Controller controller) {
        advancedActionPane = new AdvancedActionPane(controller);
        this.getChildren().addAll(actionIndicator, buttonHolderStack, advancedActionPane);
        this.setSpacing(5);

        buttonHolderStack.getChildren().addAll(buttonHolder, passiveEffectBtn);
        buttonHolder.getChildren().addAll(killBtn, activeEffectBtn, endBtn);
        buttonHolder.setSpacing(ButtonUtil.BUTTON_SPACING);

        formatActionIndicator(actionIndicator);
        formatKillBtn(killBtn, controller);
        formatActiveEffectBtn(activeEffectBtn, controller);
        formatPassiveEffectBtn(passiveEffectBtn, controller);
        formatEndBtn(endBtn, controller);
    }

    private void formatActionIndicator(Pane indicator) {
        indicator.setPrefHeight(3);
        indicator.setMaxWidth(0);
        RegionUtil.setBackground(indicator, ColorUtil.STANDARD_BUTTON_COLOR, new CornerRadii(5), null);
        this.widthProperty().addListener(((observable, oldValue, newValue) -> {
            if (selectedActionBtn != null) { shiftActionIndicator(selectedActionBtn); }
        }));
    }

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
