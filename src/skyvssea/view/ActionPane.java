package skyvssea.view;

import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class ActionPane extends HBox {
    Button moveBtn = new Button("Move");
    Button killBtn = new Button("Kill");
    Button specialEffectBtn = new Button("Special Effect");
    Button skipBtn = new Button("Skip");

    boolean moveStatus =false;
    boolean killStatus =false;
    boolean specialStatus=false;
    boolean skipStatus=false;

    Text playerText = new Text();

    public ActionPane() {

        playerText.setText("player name");

        playerText.setFont(Font.font(15));
        playerText.setVisible(true);

        this.getChildren().addAll(playerText,moveBtn, killBtn, specialEffectBtn,skipBtn);
        this.setSpacing(20d);

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

    public Button getMoveBtn() {
        return moveBtn;
    }

    public Button getKillBtn() {
        return killBtn;
    }

    public Button getSpecialEffectBtn() {
        return specialEffectBtn;
    }

    public Button getSkipBtn() {
        return skipBtn;
    }

    public void setPlayerText(Text playerText) {
        this.playerText = playerText;
    }

    public Text getPlayerText() {
        return playerText;
    }

    public boolean isKillStatus() {
        return killStatus;
    }

    public void setKillStatus(boolean killStatus) {
        this.killStatus = killStatus;
    }

    public boolean isSpecialStatus() {
        return specialStatus;
    }

    public void setSpecialStatus(boolean specialStatus) {
        this.specialStatus = specialStatus;
    }
}
