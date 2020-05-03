package skyvssea.view;

import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import skyvssea.controller.Controller;
import skyvssea.util.ButtonUtil;
import skyvssea.util.ColorUtil;

public class ActionPane extends HBox {
    private Button killBtn = new Button("Kill");
    private Button specialEffectBtn = new Button("Special Effect");
    private Button endBtn = new Button("End");

    public ActionPane(Controller controller) {
        this.getChildren().addAll(killBtn, specialEffectBtn, endBtn);
        this.setSpacing(20d);

        formatKillBtn(controller);
        formatSpecialEffectBtn(controller);
        formatEndBtn(controller);
    }

    private void formatKillBtn(Controller controller) {
        maximizeControlSize(killBtn);
        ButtonUtil.formatStandardButton(killBtn, ColorUtil.STANDARD_BUTTON_COLOR);
        killBtn.setOnMouseEntered(e -> ButtonUtil.formatHoveringEffect(killBtn, true));
        killBtn.setOnMouseExited(e -> ButtonUtil.formatHoveringEffect(killBtn, false));
        killBtn.setOnAction(e -> controller.handleKillButton());
    }

    private void formatSpecialEffectBtn(Controller controller) {
        maximizeControlSize(specialEffectBtn);
        ButtonUtil.formatStandardButton(specialEffectBtn, ColorUtil.STANDARD_BUTTON_COLOR);
        specialEffectBtn.setOnMouseEntered(e -> ButtonUtil.formatHoveringEffect(specialEffectBtn, true));
        specialEffectBtn.setOnMouseExited(e -> ButtonUtil.formatHoveringEffect(specialEffectBtn, false));
        specialEffectBtn.setOnAction(e -> controller.handleSpecialEffectButton());
    }

    private void formatEndBtn(Controller controller) {
        maximizeControlSize(endBtn);
        endBtn.setStyle("-fx-font-weight: bold;");
        endBtn.setCursor(Cursor.HAND);
        endBtn.setCancelButton(true);
        endBtn.setOnAction(e -> controller.handleEndButton());
    }

    private void maximizeControlSize(Control control) {
        control.setPrefHeight(50d);
        HBox.setHgrow(control, Priority.ALWAYS);
        control.setMaxWidth(Double.MAX_VALUE);
    }

    public void setSpecialEffectBtnDisable(boolean isDisabled) {
        specialEffectBtn.setDisable(isDisabled);
    }
}
