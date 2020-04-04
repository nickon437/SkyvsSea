package skyvssea.view;

import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class ActionPane extends HBox {
    public ActionPane() {
        Button moveBtn = new Button("Move");
        Button killBtn = new Button("Kill");
        Button specialEffectBtn = new Button("Special Effect");
        Button skipBtn = new Button("Skip");

        this.getChildren().addAll(moveBtn, killBtn, specialEffectBtn,skipBtn);
        this.setSpacing(20d);

        //this.setStyle("-fx-background-color: #ffb578;"); // TODO: Remove this later

        maximizeControlSize(moveBtn);
        maximizeControlSize(killBtn);
        maximizeControlSize(specialEffectBtn);
        maximizeControlSize(skipBtn);
    }

    private void maximizeControlSize(Control control) {
        control.setPrefHeight(50d);
        HBox.setHgrow(control, Priority.ALWAYS);
        control.setMaxWidth(Double.MAX_VALUE);
    }
}
