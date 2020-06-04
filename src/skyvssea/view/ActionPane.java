package skyvssea.view;

import javafx.animation.*;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.*;
import javafx.util.Duration;
import skyvssea.controller.Controller;
import skyvssea.util.AnimationUtil;
import skyvssea.util.ButtonUtil;
import skyvssea.util.ColorUtil;
import skyvssea.util.RegionUtil;

public class ActionPane extends VBox {
    private HBox buttonHolder = new HBox();
    private Pane actionIndicator = new Pane();
    private Button killBtn = new Button("Kill");
    private Button activeEffectBtn = new Button("Active Effect");
    private ToggleButton passiveEffectBtn = new ToggleButton("Passive Effect");
	private Button endBtn = new Button("End");

    private AdvancedActionPane advancedActionPane;

    public ActionPane(Controller controller) {
        advancedActionPane = new AdvancedActionPane(controller);
        this.getChildren().addAll(actionIndicator, buttonHolder, advancedActionPane);
        this.setSpacing(5);

        buttonHolder.getChildren().addAll(killBtn, activeEffectBtn, passiveEffectBtn, endBtn);
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

    private void formatPassiveEffectBtn(ToggleButton button, Controller controller) {
    	ButtonUtil.maximizeHBoxControlSize(button);
        ButtonUtil.formatStandardButton(button, ColorUtil.STANDARD_BUTTON_COLOR);
        button.setOnMouseEntered(e -> {
            ButtonUtil.formatHoveringEffect(button, true);
        });
        button.setOnMouseExited(e -> {
            ButtonUtil.formatHoveringEffect(button, false);
        });
        button.setOnAction(e -> {
        	if (button.isSelected()) {
        		ButtonUtil.formatStandardButton(button, ColorUtil.ACTIVATED_BUTTON_COLOR);
        	} else {
        		ButtonUtil.formatStandardButton(button, ColorUtil.STANDARD_BUTTON_COLOR);
        	}
        	controller.handlePassiveEffectButton();        		
        });
    }
    
    public ToggleButton getPassiveEffectBtn() {
		return passiveEffectBtn;
	}
    
    public void setPassiveEffectBtnActivated(boolean isActivated) {
    	passiveEffectBtn.setSelected(isActivated);
    	
    	if (isActivated) {
    		ButtonUtil.formatStandardButton(passiveEffectBtn, ColorUtil.ACTIVATED_BUTTON_COLOR);
    	} else {
    		ButtonUtil.formatStandardButton(passiveEffectBtn, ColorUtil.STANDARD_BUTTON_COLOR);
    	}
    }
    
    public void setPassiveEffectBtnDisable(boolean isDisabled) {
    	passiveEffectBtn.setDisable(isDisabled);
    }
    
    public void disableAndDeactivatePassiveEffectBtn() {
    	setPassiveEffectBtnActivated(false);
    	passiveEffectBtn.setDisable(true);
    }
    
    private void formatActiveEffectBtn(Button button, Controller controller) {
        ButtonUtil.maximizeHBoxControlSize(button);
        ButtonUtil.formatStandardButton(button, ColorUtil.STANDARD_BUTTON_COLOR);
        ButtonUtil.formatGraphic(button, "file:resources/icons/special-effect.png");
        button.setOnMouseEntered(e -> {
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
    }

    private void setActionIndicatorPosition(double xTranslate, double width) {
        KeyFrame kfXCoord = AnimationUtil.formatKeyFrame(actionIndicator.translateXProperty(), xTranslate, Duration.seconds(0.5));
        KeyFrame kfWidth = AnimationUtil.formatKeyFrame(actionIndicator.maxWidthProperty(), width, Duration.seconds(0.5));
        Timeline timeline = new Timeline(kfXCoord, kfWidth);
        timeline.play();
    }

    public void disableRegularActionPane() {
    	activeEffectBtn.setDisable(true);
    	passiveEffectBtn.setDisable(true);
        setPassiveEffectBtnActivated(false);
        killBtn.setDisable(true);
        endBtn.setDisable(true);
    }

    public void setActiveEffectBtnDisable(boolean isDisabled) {
    	activeEffectBtn.setDisable(isDisabled);
    }
    
    public void setKillBtnDisable(boolean isDisabled) {
    	killBtn.setDisable(isDisabled);
    }
    
    public void setEndBtnDisable(boolean isDisabled) {
    	endBtn.setDisable(isDisabled);
    }
    
    public void setUndoBtnDisable(boolean isDisabled) {
        advancedActionPane.setUndoBtnDisable(isDisabled);
    }
}
