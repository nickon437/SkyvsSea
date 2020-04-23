package skyvssea.view;

import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import skyvssea.controller.Controller;

public class ActionPane extends HBox {
    private Button killBtn = new Button("Kill");
    private Button specialEffectBtn = new Button("Special Effect");
    private Button skipBtn = new Button("Skip");

    public ActionPane(Controller controller) {
        this.getChildren().addAll(killBtn, specialEffectBtn,skipBtn);
        this.setSpacing(20d);

        maximizeControlSize(killBtn);
        maximizeControlSize(specialEffectBtn);
        maximizeControlSize(skipBtn);

        skipBtn.setOnAction(e -> controller.handleSkipButton());
    }

    private void maximizeControlSize(Control control) {
        control.setPrefHeight(50d);
        HBox.setHgrow(control, Priority.ALWAYS);
        control.setMaxWidth(Double.MAX_VALUE);
    }
}
