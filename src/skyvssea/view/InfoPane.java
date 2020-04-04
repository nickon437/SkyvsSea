package skyvssea.view;

import javafx.geometry.Insets;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class InfoPane extends VBox {
    public InfoPane() {
        TextArea statusText = new TextArea();
        TextArea specialEffectText = new TextArea();
        setTextAreaDisabled(statusText, true);
        setTextAreaDisabled(specialEffectText, true);

        this.getChildren().addAll(statusText, specialEffectText);

        VBox.setVgrow(statusText, Priority.ALWAYS);
        VBox.setVgrow(specialEffectText, Priority.ALWAYS);
        this.setPadding(new Insets(20d));
        this.setSpacing(20d);
    }

    // TODO: This kinda belongs in the TextArea, review what lecturer talked about this situation (ActionPane also has sth similar)
    private void setTextAreaDisabled(TextArea textArea, boolean isDisabled) {
        textArea.setEditable(!isDisabled);
        textArea.setFocusTraversable(!isDisabled);
        textArea.setMouseTransparent(isDisabled);
    }
}
