package skyvssea.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class InfoPane extends VBox {
    private Text playerNameText = new Text("Player's name");
    private TextArea statusText = new TextArea();
    private TextArea specialEffectText = new TextArea();

    public InfoPane() {
        formatPlayerNameText();
        setTextAreaDisabled(statusText, true);
        setTextAreaDisabled(specialEffectText, true);

        this.getChildren().addAll(playerNameText, statusText, specialEffectText);

        VBox.setVgrow(statusText, Priority.ALWAYS);
        VBox.setVgrow(specialEffectText, Priority.ALWAYS);
        this.setPadding(new Insets(20d));
        this.setSpacing(20d);
    }

    private void formatPlayerNameText() {
        this.setAlignment(Pos.CENTER);
        playerNameText.setStyle("-fx-font-weight: bold");
    }

    // Nick - TODO: This kinda belongs in the TextArea, review what lecturer talked about this situation (ActionPane also has sth similar)
    private void setTextAreaDisabled(TextArea textArea, boolean isDisabled) {
        textArea.setEditable(!isDisabled);
        textArea.setFocusTraversable(!isDisabled);
        textArea.setMouseTransparent(isDisabled);
    }

    public void setPlayerName(String name) {
        playerNameText.setText(name);
    }

    public void clearInfo() {
        statusText.clear();
        specialEffectText.clear();
    }
}
