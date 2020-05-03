package skyvssea.view;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.image.ImageView;
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

    private static final double SPACING = 20;

    public ActionPane(Controller controller) {
        this.getChildren().addAll(actionIndicator, buttonHolder);
        this.setSpacing(5);

        buttonHolder.getChildren().addAll(killBtn, specialEffectBtn, endBtn);
        buttonHolder.setSpacing(SPACING);

        formatActionIndicator();
        formatKillBtn(controller);
        formatSpecialEffectBtn(controller);
        formatEndBtn(controller);
    }

    private void formatActionIndicator() {
        actionIndicator.setPrefHeight(3);
        actionIndicator.setMaxWidth(0);
        actionIndicator.setBackground(new Background(new BackgroundFill(ColorUtil.STANDARD_BUTTON_COLOR, new CornerRadii(5), null)));
    }

    private void formatKillBtn(Controller controller) {
        maximizeControlSize(killBtn);
        ButtonUtil.formatStandardButton(killBtn, ColorUtil.STANDARD_BUTTON_COLOR);
        killBtn.setGraphic(new ImageView("resources/icons/kill.png"));
        killBtn.setGraphicTextGap(10);
        killBtn.setOnMouseEntered(e -> ButtonUtil.formatHoveringEffect(killBtn, true));
        killBtn.setOnMouseExited(e -> ButtonUtil.formatHoveringEffect(killBtn, false));
        killBtn.setOnAction(e -> controller.handleKillButton(killBtn));
    }

    private void formatSpecialEffectBtn(Controller controller) {
        maximizeControlSize(specialEffectBtn);
        ButtonUtil.formatStandardButton(specialEffectBtn, ColorUtil.STANDARD_BUTTON_COLOR);
        specialEffectBtn.setGraphic(new ImageView("resources/icons/special-effect.png"));
        specialEffectBtn.setGraphicTextGap(10);
        specialEffectBtn.setOnMouseEntered(e -> ButtonUtil.formatHoveringEffect(specialEffectBtn, true));
        specialEffectBtn.setOnMouseExited(e -> ButtonUtil.formatHoveringEffect(specialEffectBtn, false));
        specialEffectBtn.setOnAction(e -> controller.handleSpecialEffectButton(specialEffectBtn));
    }

    private void formatEndBtn(Controller controller) {
        maximizeControlSize(endBtn);
        endBtn.setStyle("-fx-font-weight: bold;");
        endBtn.setCursor(Cursor.HAND);
        endBtn.setCancelButton(true);
        endBtn.setGraphic(new ImageView("resources/icons/end-turn.png"));
        endBtn.setGraphicTextGap(8);
        endBtn.setOnAction(e -> controller.handleEndButton(endBtn));
    }

    private void maximizeControlSize(Control control) {
        control.setPrefHeight(50d);
        HBox.setHgrow(control, Priority.ALWAYS);
        control.setMaxWidth(Double.MAX_VALUE);
    }

    public void shiftActionIndicator(Button button) {
        int buttonIndex = buttonHolder.getChildren().indexOf(button);
        double xCoord = 0;
        for (int i = 0; i < buttonIndex; i++) {
            xCoord += ((Button) buttonHolder.getChildren().get(i)).getWidth() + SPACING;
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

    public void setSpecialEffectBtnDisable(boolean isDisabled) {
        specialEffectBtn.setDisable(isDisabled);
    }
}
