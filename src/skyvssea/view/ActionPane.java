package skyvssea.view;

import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import skyvssea.controller.Controller;

public class ActionPane extends HBox {
    private Button killBtn = new Button("Kill");
    private Button specialEffectBtn = new Button("Special Effect");
    private Button endBtn = new Button("End");

    public ActionPane(Controller controller) {
        this.getChildren().addAll(killBtn, specialEffectBtn, endBtn);
        this.setSpacing(20d);

        killBtn.setStyle("-fx-background-color: #1E88E5; -fx-text-fill: white; -fx-font-weight: bold;");
        specialEffectBtn.setStyle("-fx-background-color: #1E88E5; -fx-text-fill: white; -fx-font-weight: bold;");
        endBtn.setStyle("-fx-font-weight: bold;");
        endBtn.setCancelButton(true);

        maximizeControlSize(killBtn);
        maximizeControlSize(specialEffectBtn);
        maximizeControlSize(endBtn);

        killBtn.setOnAction(e -> controller.handleKillButton());
        specialEffectBtn.setOnAction(e -> controller.handleSpecialEffectButton());
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
