package skyvssea.view;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.Control;
import javafx.scene.control.ToggleButton;
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
    private ToggleButton passiveEffectBtn = new ToggleButton("Passive Effect");
    private Button endBtn = new Button("End");

    private static final double BUTTON_HEIGHT = 50;
    private static final double SPACING = 20;

    public ActionPane(Controller controller) {
        this.getChildren().addAll(actionIndicator, buttonHolder);
        this.setSpacing(5);

        buttonHolder.getChildren().addAll(killBtn, specialEffectBtn, passiveEffectBtn, endBtn);
        buttonHolder.setSpacing(SPACING);

        formatActionIndicator();
        formatKillBtn(killBtn, controller);
        formatSpecialEffectBtn(specialEffectBtn, controller);
        formatPassiveEffectBtn(passiveEffectBtn, controller);
        formatEndBtn(endBtn, controller);
    }


	private void formatActionIndicator() {
        actionIndicator.setPrefHeight(3);
        actionIndicator.setMaxWidth(0);
        actionIndicator.setBackground(new Background(new BackgroundFill(ColorUtil.STANDARD_BUTTON_COLOR, new CornerRadii(5), null)));
    }

    private void formatKillBtn(Button button, Controller controller) {
        maximizeControlSize(button);
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

    private void formatPassiveEffectBtn(ToggleButton button, Controller controller) {
    	maximizeControlSize(button);
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
    
    public void activatePassiveEffectBtn() {
    	passiveEffectBtn.setSelected(true);
    	ButtonUtil.formatStandardButton(passiveEffectBtn, ColorUtil.ACTIVATED_BUTTON_COLOR);
    }
    
    public void deactivatePassiveEffectBtn() {
    	passiveEffectBtn.setSelected(false);
    	ButtonUtil.formatStandardButton(passiveEffectBtn, ColorUtil.STANDARD_BUTTON_COLOR);
    }
    
    public void disablePassiveEffectBtn() {
    	passiveEffectBtn.setDisable(true);
    }
    
    public void enablePassiveEffectBtn(boolean passiveEffectActivated) {
    	passiveEffectBtn.setDisable(false);
    	if (passiveEffectActivated) {
    		activatePassiveEffectBtn();
    	} else {
    		deactivatePassiveEffectBtn();
    	}
    }
    
    private void formatSpecialEffectBtn(Button button, Controller controller) {
        maximizeControlSize(button);
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
        maximizeControlSize(button);
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

    private void maximizeControlSize(Control control) {
        control.setPrefHeight(BUTTON_HEIGHT);
        HBox.setHgrow(control, Priority.ALWAYS);
        control.setMaxWidth(Double.MAX_VALUE);
    }

    private void shiftActionIndicator(Button button) {
        int buttonIndex = buttonHolder.getChildren().indexOf(button);
        double xCoord = 0;
        for (int i = 0; i < buttonIndex; i++) {
            xCoord += ((ButtonBase) buttonHolder.getChildren().get(i)).getWidth() + SPACING;
        }
        double width = ((ButtonBase) buttonHolder.getChildren().get(buttonIndex)).getWidth();
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

    public void enableSpecialEffectBtn() {
        specialEffectBtn.setDisable(false);
    }
    public void disableSpecialEffectBtn() {
    	specialEffectBtn.setDisable(true);
    }
    
    public void disableKillBtn() {
    	killBtn.setDisable(true);
    }
    public void enableKillBtn() {
    	killBtn.setDisable(false);
    }


	public void disableAllButtons() {
        disableSpecialEffectBtn();
        disablePassiveEffectBtn();
        disableKillBtn();
        hideActionIndicator();		
	}
}
