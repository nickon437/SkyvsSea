package skyvssea.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import skyvssea.model.Player;
import skyvssea.model.piece.AbstractPiece;
import skyvssea.util.ColorUtil;

public class InfoPane extends VBox {
    private Text playerNameText = new Text("Player's name");
    private StackPane playerNameTextHolder = new StackPane(playerNameText);
    private TextArea statusText = new TextArea();
    private TextArea specialEffectText = new TextArea();

    public InfoPane() {
        this.setAlignment(Pos.CENTER);

        formatPlayerNameText();
        setTextAreaDisabled(statusText, true);
        setTextAreaDisabled(specialEffectText, true);

        this.getChildren().addAll(playerNameTextHolder, statusText, specialEffectText);

        VBox.setVgrow(statusText, Priority.ALWAYS);
        VBox.setVgrow(specialEffectText, Priority.ALWAYS);
        this.setPadding(new Insets(20d));
        this.setSpacing(20d);
    }

    private void formatPlayerNameText() {
        playerNameTextHolder.setPadding(new Insets(20d));
        playerNameText.setStyle("-fx-font-weight: bold;");
    }

    // Nick - TODO: This kinda belongs in the TextArea, review what lecturer talked about this situation (ActionPane also has sth similar)
    private void setTextAreaDisabled(TextArea textArea, boolean isDisabled) {
        textArea.setEditable(!isDisabled);
        textArea.setFocusTraversable(!isDisabled);
        textArea.setMouseTransparent(isDisabled);
    }

    public void setPlayerInfo(Player player) {
        playerNameText.setText(player.getName());
        Color color = player.getColor();
        playerNameTextHolder.setStyle("-fx-background-radius: 20;" +
                "-fx-background-color: rgb(" + color.getRed()*255 + ", " +
                color.getGreen()*255 + ", " + color.getBlue()*255 + ");");
        playerNameText.setFill(ColorUtil.getTextContrastColor(color));
    }

    public void setPieceInfo(AbstractPiece piece) {
        statusText.setText(piece.toString());
        specialEffectText.setText(piece.getSpecialEffectSummary());
    }

    public void clearPieceInfo() {
        statusText.clear();
        specialEffectText.clear();
    }
}
